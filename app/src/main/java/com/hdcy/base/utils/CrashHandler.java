package com.hdcy.base.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.hdcy.app.activity.MainActivity;

import org.xutils.common.util.LogUtil;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 崩溃异常处理
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private Context context;

    /**
     * 构造器
     */
    private CrashHandler() {

    }

    /**
     * 静态内部类
     */
    private static class CrashHandlerHolder {
        private static final CrashHandler instance = new CrashHandler();
    }

    /**
     * 取得单例
     *
     * @return 当前类单例
     */
    public static CrashHandler getInstance() {
        return CrashHandlerHolder.instance;
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //输出日志
        LogUtil.e("UncaughtException, thread: {" + thread + ", Name: " + thread.getName() + ", ID: " + thread.getId() + ", Exception: " + ex + "}");
        LogUtil.e(ex.toString());

        //关闭所有Activity
        LocalActivityMgr.getInstance().clearActivity();

        //重新启动
        Intent startActivity = new Intent(context, MainActivity.class);
        int pendingIntentId = 123456;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, pendingIntentId, startActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        System.exit(0);
    }

}
