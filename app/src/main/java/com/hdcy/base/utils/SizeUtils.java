package com.hdcy.base.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.hdcy.base.BaseData;
import com.zhy.autolayout.utils.AutoUtils;

public class SizeUtils implements BaseData {

    /**
     * 获取屏幕信息
     *
     * @return
     */
    public static DisplayMetrics getScreenDisplayMetrics() {
        return Resources.getSystem().getDisplayMetrics();
    }

    /**
     * 获取屏幕密度
     *
     * @return
     */
    public static float getDensity() {
        DisplayMetrics display = getScreenDisplayMetrics();
        return display.density;
    }

    /**
     * 获取屏幕长宽比
     *
     * @return
     */
    public static float getScreenRate() {
        DisplayMetrics display = getScreenDisplayMetrics();
        float height = display.heightPixels;
        float width = display.widthPixels;
        return (height / width);
    }

    /**
     * 获取屏幕宽
     *
     * @return
     */
    public static int getScreenWidth() {
        DisplayMetrics display = getScreenDisplayMetrics();
        return display.widthPixels;
    }

    /**
     * 获取屏幕高
     *
     * @return
     */
    public static int getScreenHeight() {
        DisplayMetrics display = getScreenDisplayMetrics();
        return display.heightPixels;
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int dpToPx(int dp) {
        return (int) (dp * getDensity() + 0.5f);
    }

    /**
     * px转dp
     *
     * @param px
     * @return
     */
    public static int pxToDp(int px) {
        return (int) (px / getDensity() + 0.5f);
    }

    /**
     * 取得状态栏高度
     *
     * @return 状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取适配大小
     *
     * @param size
     * @return
     */
    @Deprecated
    public static int getAutoSize(int size) {
        return getAutoWidth(size);
    }

    /**
     * 获取适配宽
     *
     * @param width
     * @return
     */
    public static int getAutoWidth(int width) {
        return AutoUtils.getPercentWidthSize(width);
    }

    /**
     * 获取适配高
     *
     * @param height
     * @return
     */
    public static int getAutoHeight(int height) {
        return AutoUtils.getPercentHeightSize(height);
    }


    /**
     * 透明通知栏标题间距
     *
     * @param view
     */
    public static void setTitleViewTopPadding(View view) {
        setTitleViewTopPadding(view, 0);
    }

    /**
     * 透明通知栏标题间距
     *
     * @param view
     * @param height 自定义高度
     */
    public static void setTitleViewTopPadding(final View view, final int height) {
        view.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (height > 0 ? height : view.getMeasuredHeight()) + statusBarHeight;
                view.setPadding(view.getPaddingLeft(),
                        view.getPaddingTop() + statusBarHeight,
                        view.getPaddingRight(),
                        view.getPaddingBottom());
                view.setLayoutParams(params);
            }
        });
    }

}
