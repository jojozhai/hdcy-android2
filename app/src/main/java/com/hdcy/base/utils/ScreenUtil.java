package com.hdcy.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.WindowManager;

import com.hdcy.app.model.TextBean;


/**
 *
 * @author easonyang
 */
public class ScreenUtil {


    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int SreenWidth = wm.getDefaultDisplay().getWidth();
        return  SreenWidth;
    }

    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int SreenHeight = wm.getDefaultDisplay().getHeight();
        return  SreenHeight;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     *
     * @param gContext
     * @return
     */
    public static Bitmap drawTextToBitmap(Context gContext,
                                          Bitmap bm,
                                          TextBean textBean, int aplha) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap =bm;
        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Color.BLACK);
        paint.setAlpha(130);
        //画矩形
        int fontWidth = (int) (textBean.getText().length()*textBean.getFontsize()*scale);
        int fontHeight = (int) (textBean.getFontsize()*scale);

        //画背景   2b倍行距，左5右10；
        canvas.drawRect(textBean.getFontX()-5, (float) (textBean.getFontY()-fontHeight*1.4), textBean.getFontX()+fontWidth+10, (float) (textBean.getFontY()+fontHeight*0.6), paint);
        canvas.save();
        //画三角
        Path path=new Path();
        path.moveTo(textBean.getFontX()-fontHeight-20, (float) (textBean.getFontY()-fontHeight*0.4));
        path.lineTo(textBean.getFontX()-5, (float) (textBean.getFontY()-fontHeight*1.4));
        path.lineTo(textBean.getFontX()-5,(float) (textBean.getFontY()+fontHeight*0.6));
        path.close();
        canvas.drawPath(path,paint);
        //
        paint.setColor(Color.WHITE);
        // 画原点
        canvas.drawCircle(textBean.getFontX()-fontHeight-50, (float) (textBean.getFontY()-fontHeight*0.4), 10+aplha, paint);
        // text size in pixels
        paint.setTextSize((int) (textBean.getFontsize() * scale));
        // text shadow
        Rect bounds = new Rect();
        paint.getTextBounds(textBean.getText(), 0, textBean.getText().length(), bounds);
//        int x = (bitmap.getWidth() - bounds.width())/2;
//        int y = (bitmap.getHeight() + bounds.height())/2;

        canvas.drawText(textBean.getText(), textBean.getFontX(), textBean.getFontY(), paint);
        return bitmap;
    }

}
