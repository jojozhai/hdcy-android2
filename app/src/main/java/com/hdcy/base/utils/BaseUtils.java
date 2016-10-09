package com.hdcy.base.utils;

import android.text.TextUtils;

import com.hdcy.base.BaseData;

import java.util.List;

/**
 * Created by WeiYanGeorge on 2016-08-10.
 */

public class BaseUtils implements BaseData {

    private static long lastClickTime = 0;

    public static String userName="chiwenheng";
    public static String userPwd="123456";



    /**
     * String是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmptyString(CharSequence str) {
        return TextUtils.isEmpty(str) || "null".equals(str);
    }

    /**
     * 是否快速点击两次
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        lastClickTime = time;
        return timeD <= 300;
    }

    /**
     * 获取缩放图片地址
     *
     * @param url
     * @return
     */
    public static String getPhotoZoomUrl(String url) {
        return getPhotoZoomUrl(PHOTO_ZOOM_LEVEL_1, PHOTO_ZOOM_LEVEL_1, url);
    }

    /**
     * 获取缩放图片地址
     *
     * @param width
     * @param height
     * @param url
     * @return
     */
    public static String getPhotoZoomUrl(int width, int height, String url) {
        if (!BaseUtils.isEmptyString(url) && !url.contains("/storage/") && !url.contains("/system/")
                && !url.contains("/media/") && !url.startsWith("file") && !url.startsWith("content") && !url.startsWith("assets") && !url.startsWith("drawable")) {
            if (url.contains("/qn/")) {
                url += "?imageView/1/w/" + width + "/h/" + height;
            } else {
                if (url.contains("/video/") && !url.endsWith(".jpg")) {
                    url += ".jpg";
                }
                url += "/" + width + "/" + height;
            }
        } else {
            url = "";
        }
        return url;
    }

    /**
     * List是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmptyList(List list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

}
