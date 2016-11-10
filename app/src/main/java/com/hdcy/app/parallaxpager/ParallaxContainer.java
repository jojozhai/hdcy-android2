package com.hdcy.app.parallaxpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdcy.app.R;
import com.hdcy.app.activity.LoginActivity;
import com.hdcy.app.activity.RegisterAndLoginActivity;
import com.hdcy.app.view.NoScrollViewPager;
import com.hdcy.base.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-11-10.
 */

public class ParallaxContainer extends FrameLayout {

    private String TAG = "ParallaxContainer";

    private List<View> parallaxViews = new ArrayList<View>();
    private NoScrollViewPager viewPager;
    private int pageCount = 0;
    private int containerWidth;
    private boolean isLooping = false;
    private final ParallaxPagerAdapter adapter;

    Context context;
    public ViewPager.OnPageChangeListener mCommonPageChangeListener;
    private List<View> viewlist = new ArrayList<View>();
    public int currentPosition = 0;

    private ImageView[] tips;

    private ViewGroup group;

    private int mScreenLeft = 0;


    //旗子；小车设置 参数设置
    private int pointWidth = 40;
    private int carWidth = 22;
    private int marginWidth = 40;
    private int mCarTop = 0;
    private ImageView imgCar;

    private Boolean isFiveViewOpen = true;
    private Boolean isImgCarOpen = true;
    private int[] resId = {R.drawable.five_1, R.drawable.five_2, R.drawable.five_3,
            R.drawable.five_4, R.drawable.five_5, R.drawable.five_6,
            R.drawable.five_7, R.drawable.five_8, R.drawable.five_9,
            R.drawable.five_10, R.drawable.five_11, R.drawable.five_12,
            R.drawable.five_13, R.drawable.five_14, R.drawable.five_15,
            R.drawable.five_16, R.drawable.five_17, R.drawable.five_18};
    private boolean isHasStart = true;
    private LinearLayout fiveLayout;


    public ParallaxContainer(Context context) {
        super(context);
        this.context = context;

        adapter = new ParallaxPagerAdapter(context);
    }

    public ParallaxContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new ParallaxPagerAdapter(context);
    }

    public ParallaxContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        adapter = new ParallaxPagerAdapter(context);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        containerWidth = getMeasuredWidth();
        if (viewPager != null) {
            mCommonPageChangeListener.onPageScrolled(viewPager.getCurrentItem(), 0, 0);
        }
        super.onWindowFocusChanged(hasFocus);
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
        updateAdapterCount();
    }

    private void updateAdapterCount() {
        adapter.setCount(isLooping ? Integer.MAX_VALUE : pageCount);
    }

    public void setupChildren(LayoutInflater inflater, int... childIds) {



        if (getChildCount() > 0) {
            throw new RuntimeException("setupChildren should only be called once when ParallaxContainer is empty");
        }

        ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(
                inflater, getContext());

        for (int childId : childIds) {
            View view = parallaxLayoutInflater.inflate(childId, this);
            viewlist.add(view);
        }


        pageCount = getChildCount();
        for (int i = 0; i < pageCount; i++) {
            View view = getChildAt(i);
            addParallaxView(view, i);
        }
        updateAdapterCount();


        viewPager = new NoScrollViewPager(getContext());
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        viewPager.setId(R.id.parallax_pager);
        attachOnPageChangeListener();
        viewPager.setAdapter(adapter);
        addView(viewPager, 0);
    }

    /**
     * 至少持续时间
     */
    private static final long DELAY_TIME = 600;

    protected void attachOnPageChangeListener() {
        mCommonPageChangeListener = new ViewPager.OnPageChangeListener() {
            public ImageView imgfourCar;
            public boolean isFiveViewLyOpen = true;
            public LinearLayout layout;
            public TextView btnText;
            public ImageView imDesc;
            public ImageView imLogo;

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.v(TAG, "onPageScrollStateChanged" + state);
            }

            @Override
            public void onPageScrolled(int pageIndex, float offset, int offsetPixels) {
//				Log.v(TAG, "onPageScrolled" + pageIndex + "  offset" + offset + "   offsetPixels" + offsetPixels);

                //控制旗子和小车
                initPoint(pageIndex, offset, offsetPixels);

                if (pageCount > 0) {
                    pageIndex = pageIndex % pageCount;
                }
                Log.d("deom1:", "pageIndex:" + pageIndex);
                ParallaxViewTag tag;
                for (View view : parallaxViews) {
                    tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    Log.d("deom1:", "size:" + parallaxViews.size());

//					Log.d("deom1:","_____________________________________________________________");
//					Log.d("deom1:","tag.xIn:"+tag.xIn);
//					Log.d("deom1:","tag.yIn:"+tag.yIn);
//
//					Log.d("deom1:","tag.scaleIn:"+tag.scaleIn);
//					Log.d("deom1:","tag.scaleOut:"+tag.scaleOut);
//					Log.d("deom:","tag.view"+view.getId());

                    if ((pageIndex == tag.index - 1 || (isLooping && (pageIndex == tag.index
                            - 1 + pageCount)))
                            && containerWidth != 0) {

                        // make visible
                        view.setVisibility(VISIBLE);

                        // slide in from right
                        view.setTranslationX((containerWidth - offsetPixels) * tag.xIn);

                        // slide in from top
                        view.setTranslationY(0 - (containerWidth - offsetPixels) * tag.yIn);

                        // fade in
                        view.setAlpha(1.0f - (containerWidth - offsetPixels) * tag.alphaIn / containerWidth);

                    } else if (pageIndex == tag.index) {
                        // make visible
                        view.setVisibility(VISIBLE);
                        // slide out to left
                        view.setTranslationX(0 - offsetPixels * tag.xOut);
                        // slide out to top
                        view.setTranslationY(0 - offsetPixels * tag.yOut);
                        // fade out
                        view.setAlpha(1.0f - offsetPixels * tag.alphaOut / containerWidth);
//						view.setScaleX(1.0f - (containerWidth - offsetPixels) * tag.scaleIn / containerWidth);
//						Log.d("deom:","tag.scaleIn"+tag.scaleIn);
//						Log.d("deom:","tag.offsetPixels"+offsetPixels);
                    } else {
                        view.setVisibility(GONE);
                    }

                    if (pageIndex == 1) {
                        if (view.getId() == R.id.iv_444) {
                            if (view != null) {
                                Log.d("deom1:", "______________");
                                ImageView tv = (ImageView) view;
                                tv.setScaleX((float) (1 + offset * 3.6));
                                tv.setScaleY((float) (1 + offset * 4));
                            } else {
                                Log.d("deom1:", "_______null_______");
                            }
                        }
                    }


                    //view3
                    if (view.getId() == R.id.iv_three_car || view.getId() == R.id.iv_three_persions) {
                        if (pageIndex == 2) {
                            view.setVisibility(VISIBLE);
                        } else {
                            view.setVisibility(INVISIBLE);
                        }
                    }

                    if (pageIndex == 0) {
                        if (view.getId() == R.id.splash_two_one_small) {
                            if (view != null) {
                                Log.d("deom1:", "______________");
                                ImageView tv = (ImageView) view;
                                tv.setScaleX(1 + offset * 3);
                                tv.setScaleY(1 + offset * 3);
//                                view.setVisibility(VISIBLE);
                            } else {
                                Log.d("deom1:", "_______null_______");
//                                view.setVisibility(INVISIBLE);
                            }
                        }
                    }
                    if (view.getId() == R.id.splash_two_one_small) {
                        if (pageIndex == 0 && offset < 0.3) {
                            view.setAlpha(offset * 2);
                            view.setVisibility(VISIBLE);
                        } else if (pageIndex == 0 && offset >= 0.3) {
//                            view.setAlpha(offset);
                            view.setVisibility(VISIBLE);
                        } else {
                            view.setVisibility(INVISIBLE);
                        }
                    }
                    if (view.getId() == R.id.iv_two_top || view.getId() == R.id.iv_two_bot || view.getId() == R.id.iv_444) {
                        if (pageIndex == 1) {
                            view.setVisibility(VISIBLE);
                        } else {
                            view.setVisibility(INVISIBLE);
                        }
                    }
                    //view 5
                    if (view.getId() == R.id.view_page_5&&isHasStart) {
                        isHasStart = false;
//                        if(view instanceof FrameLayout){
                        fiveLayout = (LinearLayout) view;
                    }
                    if (view.getId() == R.id.splash_five_tag && isFiveViewLyOpen) {
                        layout = (LinearLayout) view;
                        isFiveViewLyOpen = false;
                    }
                    if (view.getId() == R.id.splash_five_tag || view.getId() == R.id.ly_logo) {
                        if (pageIndex != 4) {
                            view.setVisibility(INVISIBLE);
                        }
                    }
                    if (view.getId() == R.id.splash_five_bg) {
                        if (pageIndex != 4) {
                            view.setVisibility(INVISIBLE);
                        } else {
                            view.setVisibility(VISIBLE);
                        }
                    }
                    if (view.getId() == R.id.ly_logo && isFiveViewOpen) {
                        LinearLayout layout = (LinearLayout) view;
                        imLogo = (ImageView) view.findViewById(R.id.five_logo);
                        imDesc = (ImageView) view.findViewById(R.id.five_desc);
                        btnText = (TextView) view.findViewById(R.id.five_btn);
                        isFiveViewOpen = false;
                        btnText.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                context.startActivity(new Intent(context, RegisterAndLoginActivity.class));
                                ((Activity) context).finish();
                            }
                        });
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.v(TAG, "onPageSelected" + position);
                currentPosition = position;

                if (!isFiveViewOpen && !isFiveViewOpen) {
                    if (position == 4) {
                        final AlphaAnimation alpha = new AlphaAnimation(0, 0.85f);
                        alpha.setDuration(1500);//设置动画持续时间
                        alpha.setFillAfter(true);
                        alpha.setStartOffset(1800);
                        layout.setVisibility(VISIBLE);
                        layout.startAnimation(alpha);
                        Animation imgAnima = AnimationUtils.loadAnimation(context, R.anim.bot_to_top);
                        Animation btnAnima = AnimationUtils.loadAnimation(context, R.anim.bot_to_bot);
                        imLogo.setVisibility(VISIBLE);
                        imDesc.setVisibility(VISIBLE);
                        btnText.setVisibility(VISIBLE);
                        imLogo.startAnimation(imgAnima);
                        imDesc.startAnimation(imgAnima);
                        btnText.startAnimation(btnAnima);
                        viewPager.setNoScroll(true);
                    }
                }
                if (position == 4) {
                    for (int i = 0; i < ((LinearLayout) fiveLayout).getChildCount(); i++) {
                        View lyView = ((LinearLayout) fiveLayout).getChildAt(i);
//                                if(lyView instanceof LinearLayout){
                        for (int k = 0; k < ((LinearLayout) lyView).getChildCount(); k++) {
                            ImageView imgView = (ImageView) ((LinearLayout) lyView).getChildAt(k);
                            final AlphaAnimation alpha = new AlphaAnimation(0, 1f);
//                                    imgView.setImageResource(R.drawable.splash_content_player);
                            alpha.setDuration(80);//设置动画持续时间
                            alpha.setFillAfter(true);
                            alpha.setFillBefore(false);
                            alpha.setStartOffset(240 * (i + 1) + 80 * (k + 1));
                            imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                            imgView.setImageResource((resId[3 * i + k]));
                            imgView.startAnimation(alpha);
                        }
                    }
                }
            }
        };
        viewPager.setOnPageChangeListener(mCommonPageChangeListener);
    }

    private void initPoint(int pageindex, float arg1, int arg2) {
        int scanWidth;
        if (pageindex < 4) {
            scanWidth = (int) (pointWidth * (arg1));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tips[pageindex + 1].getLayoutParams();
            layoutParams.width = pointWidth - scanWidth;
            layoutParams.height = pointWidth - scanWidth;
            layoutParams.leftMargin = marginWidth;
            layoutParams.rightMargin = marginWidth;
            layoutParams.topMargin = scanWidth;

            tips[pageindex + 1].setLayoutParams(layoutParams);
            scanWidth = (int) (pointWidth * (arg1));

            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) tips[pageindex].getLayoutParams();
            layoutParams2.width = scanWidth;
            layoutParams2.height = scanWidth;
            layoutParams2.leftMargin = marginWidth;
            layoutParams2.rightMargin = marginWidth;
            layoutParams2.topMargin = pointWidth - scanWidth;
            tips[pageindex].setLayoutParams(layoutParams2);
        }

        //改变汽车位置
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) imgCar.getLayoutParams();
        int leftPx = (int) ((int) mScreenLeft + (pointWidth + marginWidth * 2) * pageindex + (pointWidth + marginWidth * 2) * arg1);
        lp.setMargins(leftPx, 10, 0, 0);
        Log.d("carWidth:", "carWidth:" + carWidth);
        imgCar.setLayoutParams(lp);

        Log.d("test", "arg1:" + arg1);
        Log.d("test", "pageindex:" + pageindex);
    }

    boolean isEnd = false;

    private void addParallaxView(View view, int pageIndex) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0, childCount = viewGroup.getChildCount(); i < childCount; i++) {
                addParallaxView(viewGroup.getChildAt(i), pageIndex);
            }
        }

        ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
        if (tag != null) {
            tag.index = pageIndex;
            parallaxViews.add(view);
        }
    }

    public void setImageCar(ImageView imgCar) {
        this.imgCar = imgCar;
    }

//    public void setImageLists(ImageView[] tips) {
//
//        this.tips = tips;
//    }

    public void setGrountView(ViewGroup group, Context context) {
        this.group = group;
        this.context = context;
        if(getResources().getDisplayMetrics().density<=2){
            carWidth =5;
            mCarTop = 25;
        }
        initpotinPx();
    }

    /**
     * 控制底部旗子和小车的位置
     */
    private void initpotinPx() {
        tips = new ImageView[5];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.icon_point);
            tips[i] = imageView;
//
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(pointWidth, pointWidth));
            layoutParams.leftMargin = marginWidth;//设置点点点view的左边距
            layoutParams.rightMargin = marginWidth;//设置点点点view的右边距
            group.addView(imageView, layoutParams);
        }

        //car左边距
        int SreenWidth = ScreenUtil.getScreenWidth(context);
//        Log.d("mScreenLeft", "SreenWidth:" + SreenWidth);
        //-26
        mScreenLeft = -carWidth + (SreenWidth - tips.length * pointWidth - (tips.length - 1) * marginWidth * 2) / 2;
//        Log.d("mScreenLeft", "mScreenLeft:" + mScreenLeft);

//        imgCar = (ImageView) findViewById(R.id.img_car);
//        Log.d("mScreenLeft", "mScreenLeft:" + imgCar.getLayoutParams().width);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) imgCar.getLayoutParams();
        lp.setMargins(mScreenLeft, mCarTop, 0, 0);
        imgCar.setLayoutParams(lp);
    }
}

