package com.hdcy.base;

import android.graphics.Bitmap;
import android.os.Build;

import com.hdcy.app.R;
import com.hdcy.base.utils.SizeUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by WeiYanGeorge on 2016-08-09.
 * 邮箱: yanweigeorge@gmail.com
 */

public interface BaseData {

    /**********************************************URL**********************************************/
    /**
     * 开发环境(测试、正式)
     */
    enum ENVIRONMENT {
        TEST, FORMAL
    }

    /**
     * 环境
     */
    ENVIRONMENT ENVIRONMENT_TYPE = ENVIRONMENT.TEST;

    /**
     * 地址头部
     * 测试环境:dev 开发环境:app
     */
    String URl_HEADER = (ENVIRONMENT_TYPE.equals(ENVIRONMENT.TEST) ? "dev" : "app");

    /**
     * 域名
     */
    String URL_BASE = "http://" + URl_HEADER + ".haoduocheyou.com/app2";
    /**
     * 请求地址
     */
    String URL_REQUEST = URL_BASE;
    /**
     * 客户端类型
     */
    String CLIENT_TYPE = "android";
    /**
     * 手机品牌
     */
    String PHONE_BRAND = Build.BRAND;
    /**
     * 手机型号
     */
    String PHONE_MODEL = Build.MODEL;
    /**
     * API版本
     */
    int version_api = Build.VERSION.SDK_INT;
    /**
     * 是否大于等于API19
     */
    boolean isOverKitKat = version_api >=19;
    /**
     * 通知栏高度
     */
    int statusBarHeight = isOverKitKat ? SizeUtils.getStatusBarHeight() : 0;

    /**
     * 360渠道
     */
    String CHANNEL_QH360 = "QH360";
    /**
     * 百度渠道
     */
    String CHANNEL_BAIDU = "BaiDu";
    /**
     * 小米渠道
     */
    String CHANNEL_XIAOMI = "XiaoMi";
    /**
     * 应用宝渠道
     */
    String CHANNEL_YINGYONGBAO = "YingYongBao";
    /**
     * 豌豆荚渠道
     */
    String CHANNEL_WANDOUJIA = "WanDouJia";
    /**
     * 华为渠道
     */
    String CHANNEL_HUAWEI = "HuaWei";

    /**
     * 登陆平台代号(QQ)
     */
    String PLATFORM_SOURCE_QQ = "qq";
    /**
     * 登陆平台代号(微信)
     */
    String PLATFORM_SOURCE_WECHAT = "wechat";
    /**
     * 登陆平台代号(微博)
     */
    String PLATFORM_SOURCE_WEIBO = "weibo";

    /**
     * 消息类型(资讯)
     */
    String MESSAGE_TYPE_NEWS = "news";

    /**
     * 图片缩放(级别1 120*120)
     */
    int PHOTO_ZOOM_LEVEL_1 = 240;
    /**
     * Picasso加载等待图
     */
    int PICASSO_PLACEHOLDER = R.color.transparent;
    /**
     * Picasso加载错误图
     */
    int PICASSO_ERROR = R.color.transparent;
    /**
     * ImageLoader配置（默认）
     */
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .showImageForEmptyUri(PICASSO_ERROR)
            .showImageOnFail(PICASSO_ERROR)
            .showImageOnLoading(PICASSO_PLACEHOLDER)
            .build();
    /**********************************************常量值**********************************************/

    /**********************************************KEY**********************************************/
    /**
     * Bundle Key
     */
    String KEY_BUNDLE = "Bundle";

    static final String KEY_RESULT_COMMENT = "articlecomment";

    int REQUEST_SPLASH = 1;

    int REQUST_ENTER = 2;







}
