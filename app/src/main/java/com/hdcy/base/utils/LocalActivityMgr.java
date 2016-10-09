package com.hdcy.base.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * 管理Activity
 *
 * @author chunjiang.shieh
 */
public class LocalActivityMgr {

    private ArrayList<Activity> mActivityList;

    private static class LocalActivityMgrHolder {
        private static final LocalActivityMgr instance = new LocalActivityMgr();
    }

    public static LocalActivityMgr getInstance() {
        return LocalActivityMgrHolder.instance;
    }

    private LocalActivityMgr() {
        super();
        mActivityList = new ArrayList<>();
    }

    /**
     * 放入Activity
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        mActivityList.add(activity);
    }

    /**
     * 移除某个Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (mActivityList.contains(activity)) {
            mActivityList.remove(activity);
        }
    }

    /**
     * 获取Top Activity
     *
     * @return Activity数量
     */
    public Activity getTopActivity() {
        if (mActivityList.size() > 0) {
            return mActivityList.get(mActivityList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 获取集合中Activity数量
     *
     * @return
     */
    public int getActivityCount() {
        return mActivityList.size();
    }

    /**
     * 关闭最顶层Activity
     */
    public void popActivity() {
        if (mActivityList.size() > 0) {
            Activity screen = mActivityList.remove(mActivityList.size() - 1);
            screen.finish();
        }
    }

    /**
     * 关闭所有Activity
     */
    public void clearActivity() {
        Activity screen;
        while (mActivityList.size() > 0) {
            screen = mActivityList.remove(mActivityList.size() - 1);
            screen.finish();
        }
    }

    /**
     * 获取指定下标的Activity
     *
     * @param index
     * @return
     */
    public Activity getActivityByIndex(int index) {
        return mActivityList.get(index);
    }

    /**
     * 获取Activity通过ClassName
     *
     * @param className
     * @return
     */
    public Activity findActivity(String className) {
        if (className == null)
            return null;

        for (int index = 0; index < mActivityList.size(); index++) {
            Activity ac = mActivityList.get(index);
            if (ac != null) {
                if (ac.getComponentName().getClassName().equals(className)) {
                    return ac;
                }
            }
        }

        return null;
    }

}
