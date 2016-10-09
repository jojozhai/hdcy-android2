package com.hdcy.base.application;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDexApplication;

import com.hdcy.base.utils.BaseUtils;
import com.hdcy.base.utils.logger.AndroidLogTool;
import com.hdcy.base.utils.logger.LogF;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

import java.util.Random;


public class BaseApplication extends MultiDexApplication {

    private static BaseApplication instance;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initData();
/*        // 视频播放
        UEasyStreaming.initStreaming("publish3-key");
        UEasyStreaming.syncMobileConfig(this, 3600 * 24);*/
        handler.post(new Runnable() {
            @Override
            public void run() {
                initDataThread();
            }
        });

//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        //初始化
//        EMClient.getInstance().init(this, options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);

/*        EaseUI.getInstance().init(this);

        SDKInitializer.initialize(getApplicationContext());*/

        LogF
                .init("car-app")                 // default PRETTYLOGGER or use just init()
                .methodCount(2)                 // default 2
                .hideThreadInfo()               // default shown
//				.logLevel(LogLevel.NONE)        // default LogLevel.FULL//正式发版本将此行代码打开
                .methodOffset(2)                // default 0
                .logTool(new AndroidLogTool()); // custom log tool, optional


    }

    private void initData() {
        // 崩溃异常初始化
        //CrashHandler.getInstance().init(instance);

        // xUtils 3.0初始化
        x.Ext.init(instance);

        // 创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(instance);
        ImageLoader.getInstance().init(configuration);

        //微信appid
/*
         PlatformConfig.setWeixin("wx6619f92e0cc550da","431c26c014b6ea3c4aab0b1d8016b2b9");
*/

    }

    private void initDataThread() {
        //百度地图
///*        SDKInitializer.initialize(getApplicationContext());
//
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
//
//        // 友盟统计
//        MobclickAgent.setDebugMode(false);// 设置开启日志,发布时请关闭日志
//        MobclickAgent.openActivityDurationTrack(false);// 禁止默认的页面统计方式(可以自定义名字)
//
//        // IM初始化
//        IMHelper.init(instance);*/

        //LogUtil.e("DeviceInfo：" + getDeviceInfo(instance));
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return getInstance();
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (BaseUtils.isEmptyString(device_id)) {
                device_id = mac;
            }

            if (BaseUtils.isEmptyString(device_id)) {
                device_id = android.provider.Settings.Secure.getString(
                        context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int getRandomStreamId() {
        Random random = new Random();
        int randint =(int)Math.floor((random.nextDouble()*10000.0 + 10000.0));
        return randint;
    }

}
