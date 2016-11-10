package com.hdcy.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.hdcy.app.R;
import com.hdcy.app.model.TextBean;

import java.util.List;


/**
 * 响应陀螺仪改变事件
 *
 * @author easonyang
 */
public class BgImageViewHelper implements GyroScopeSensorListener.ISensorListener {


    /**
     * 默认0.02f在宽度填满屏幕的图片上，移动起来看着很舒服
     */
    public static final float TRANSFORM_FACTOR = 0.02f;
    private final List<TextBean> textlist;
    private final Context mContext;
    private final int imgReourceId;
    private float mTransformFactor = TRANSFORM_FACTOR;
    private FrameLayout mParallelView;
    private float mCurrentShiftX;
    private float mCurrentShiftY;
    private GyroScopeSensorListener mSensorListener;
    private ViewGroup.LayoutParams mLayoutParams;
    private int mViewWidth;
    private int mViewHeight;
    private Bitmap imagBitmap;
    private int screenWidth;
    private int screenHeight;
    private int mShiftDistancePX = 1;
    // 补偿居中偏移量负值左，
    private int mBuchangPx = -0;

    private int ScanX = 1;
    private int ScanY = 1;
    private int centerX;
    private int centerY;
    private int imgWidth;
    private int imgHeight;


    public BgImageViewHelper(Context context, final FrameLayout targetView, int imgId, List<TextBean> beanList) {
        this(context, targetView, context.getResources().getDimensionPixelSize(R.dimen.image_shift), imgId, beanList);
    }

    /**
     * 初始化一个
     *
     * @param context
     * @param targetView
     * @param shiftDistancePX
     */
    public BgImageViewHelper(Context context, final FrameLayout targetView, int shiftDistancePX, int imgId, List<TextBean> beanList) {
//        mShiftDistancePX = shiftDistancePX;

        this.textlist = beanList;
        mSensorListener = new GyroScopeSensorListener(context);
        mSensorListener.setSensorListener(this);
        mParallelView = targetView;
        mLayoutParams = mParallelView.getLayoutParams();
        mContext = context;

        BitmapFactory.Options opt = new BitmapFactory.Options();
        //这个isjustdecodebounds很重要
        opt.inJustDecodeBounds = true;
        try {
//            imagBitmap = BitmapFactory.decodeResource(mContext.getResources(), imgId);
            imagBitmap = BitmapFactory.decodeResource(mContext.getResources(), imgId,opt);
            imgWidth = opt.outWidth;
            imgHeight = opt.outHeight;
            imagBitmap = null;
            System.gc();;
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgReourceId = imgId;
        mViewWidth = mParallelView.getWidth();
        screenWidth = ScreenUtil.getScreenWidth(context);
        screenHeight = ScreenUtil.getScreenHeight(context);
        Log.d("test:screenWidth", screenWidth + "");
        Log.d("test:screenHeight", screenHeight + "");

        mViewHeight = mParallelView.getHeight();

        if (mViewWidth > 0 && mViewHeight > 0) {
            bindView();
            return;
        }
        ViewTreeObserver vto = targetView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                targetView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mViewWidth = targetView.getWidth();
                mViewHeight = targetView.getHeight();
                bindView();
            }
        });
    }

    void bindView() {


        Log.d("test：", "imgWidth" + imgWidth);
        Log.d("test：", "imgHeight" + imgHeight);
        mLayoutParams.width = imgWidth + mShiftDistancePX;
        mLayoutParams.height = imgHeight + mShiftDistancePX;
        mParallelView.setLayoutParams(mLayoutParams);

        mParallelView.setBackgroundResource(imgReourceId);
        centerX = -(imgWidth - screenWidth) / 2 + mBuchangPx;
        centerY = -(imgHeight - screenHeight) / 2;
        Log.d("test:centerX", centerX + "");
        ScanX = (imgWidth + centerX) / 2;
        ScanY = (imgHeight + centerY) / 2;

        Log.d("test:ScanX", ScanX + "");
        Log.d("test:ScanY", ScanY + "");
        mParallelView.setX(centerX);
        mParallelView.setY(centerY);
//        RefreshView();
//        mParallelView.setIma
        initPointView();
    }


    void initPointView(){
        mParallelView.removeAllViews();
        for(TextBean b :textlist){
            createPoint(b);
        }
    }

    void createPoint(TextBean bean){
        TextView tv = new TextView(mContext);
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(18,18, Gravity.NO_GRAVITY);
        fl.width = 18;
        fl.height = 18;
        fl.leftMargin = bean.getFontX();
        fl.topMargin = bean.getFontY();
        tv.setLayoutParams(fl);
        tv.setBackgroundResource(R.drawable.bg_point);
//        Animation a = AnimationUtils.loadAnimation(mContext,R.anim.point);
//        ScaleAnimation sa = new ScaleAnimation(mContext,)
//        tv.startAnimation(a);
        mParallelView.addView(tv);

        TextView text = new TextView(mContext);
        FrameLayout.LayoutParams fl2 = new FrameLayout.LayoutParams(bean.getFontsize()*bean.getText().length()*4,bean.getFontsize()*5, Gravity.NO_GRAVITY);
//        fl2.width = bean.getFontsize()*bean.getText().length()*4;
//        fl2.height = bean.getFontsize()*5;
        Log.d("test:font", bean.getFontsize() + "");
        Log.d("test:font", bean.getText() + "");
        Log.d("test:font", bean.getText().length() + "");
        fl2.leftMargin = bean.getFontX()+30;
        fl2.topMargin = (int) (bean.getFontY()-1.5*bean.getFontsize());
        text.setLayoutParams(fl2);
        text.setPadding(12,0,0,0);
        text.setTextColor(Color.WHITE);
        text.setTextSize(bean.getFontsize());
        text.setBackgroundResource(R.drawable.text_bg);
        text.setGravity(Gravity.CENTER);
        text.setText(bean.getText());
//        tv.setBackgroundResource(R.drawable.bg_point);

        mParallelView.addView(text);

    }

//    void RefreshView() {
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                 aplha = 0;
//                while (true) {
//                    aplha += 2;
//                    Message msg = new Message();
//                    msg.what = 1;
//                    viewHanrler.sendMessage(msg);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }).start();
//
//
//    }

    /**
     * 注册监听陀螺仪事件
     */
    public void start() {
        mSensorListener.start();
    }

    /**
     * 监听陀螺仪事件耗电，因此在onPause里需要注销监听事件
     */
    public void stop() {
        mSensorListener.stop();
    }

    /**
     * 设置移动的补偿变量，越高移动越快，标准参考{@link #TRANSFORM_FACTOR}
     *
     * @param transformFactor
     */
    public void setTransformFactor(float transformFactor) {
        mTransformFactor = transformFactor;
    }

    @Override
    public void onGyroScopeChange(float horizontalShift, float verticalShift) {
        mCurrentShiftX += 0.5 * horizontalShift * mTransformFactor * mTransformFactor * ScanX * 50;
        mCurrentShiftY += 0.5 * verticalShift * mTransformFactor * mTransformFactor * ScanY * 50;
//        Log.d("test:mCurrentShiftX", "--------------------------------------------------");

        //折中补偿 可以尝试其他方法
        if (horizontalShift == 0 && verticalShift == 0) {
//            mCurrentShiftX = (float) (mCurrentShiftX * 0.8);
//            mCurrentShiftY = (float) (mCurrentShiftY * 0.8);
            mCurrentShiftX = 0;
            mCurrentShiftY = 0;
            mParallelView.setX(centerX);
            mParallelView.setY(centerY);
//            bindView();
        }
        int mSernsorX;
        int mSernsorY;
        mSernsorX = centerX + (int) mCurrentShiftX;
        mSernsorY = centerY + (int) mCurrentShiftY;

        if (mSernsorX < 0 && mSernsorX >= -imgWidth + screenWidth) {
//            Log.d("test:mSernsorX1", mSernsorX + "");
            mSernsorX = mSernsorX;
        } else if (mSernsorX >= 0) {
//            Log.d("test:mSernsorX2", mSernsorX + "");
            mSernsorX = 0;
//                if(horizontalShift==0){
//                    mCurrentShiftX-=100;
//                }
        } else if (mSernsorX < -imgWidth + screenWidth) {
//            Log.d("test:mSernsorX3", mSernsorX + "");
            mSernsorX = -imgWidth + screenWidth;
//                if(horizontalShift==0){
//                    mCurrentShiftX+=100;
//                }
        }
        if (mSernsorY < 0 && mSernsorY >= -imgHeight + screenHeight) {
//            Log.d("test:mSernsorX1", mSernsorX + "");
            mSernsorY = mSernsorY;
        } else if (mSernsorY >= 0) {
//            Log.d("test:mSernsorX2", mSernsorX + "");
            mSernsorY = 0;
        } else if (mSernsorY < -imgHeight + screenHeight) {
//            Log.d("test:mSernsorX3", mSernsorX + "");
            mSernsorY = -imgHeight + screenHeight;
        }
//        mSernsorX = mSernsorX < - mViewWidth ? -mViewWidth:mSernsorX;
        Log.d("test:mCurrentShiftX", mCurrentShiftX + "");
//        Log.d("test:mSernsorY", mSernsorY + "");
//        }
        mParallelView.setX(mSernsorX);
        mParallelView.setY(mSernsorY);
    }


}

