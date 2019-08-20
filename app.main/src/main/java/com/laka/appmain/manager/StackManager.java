package com.laka.appmain.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackManager {
    private List<Activity> activityList = null;

    private StackManager() {

    }

    private static StackManager instance = null;

    /**
     * @return ActivityManager
     * @描述 获取栈管理工具
     */
    public static StackManager getStackManager() {
        if (instance == null) {
            instance = new StackManager();
        }
        return instance;
    }


    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new ArrayList<>();
        }
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }

    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    /**
     * 关闭所有的activity，退出应用
     */
    public void finishActivitys() {
        if (activityList != null && !activityList.isEmpty()) {
            for (Activity activity1 : activityList) {
                activity1.finish();
            }
            activityList.clear();
        }
    }

    public void onDestroy() {
        if (activityList != null) {
            activityList.clear();
            activityList = null;
        }
        instance = null;
    }

    //检测activity是否存在再栈顶
    public boolean isForeground(Context context, String PackageName) {
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task = myManager.getRunningTasks(1);
        ComponentName componentInfo = task.get(0).topActivity;
        if (componentInfo.getClassName().equals(PackageName)) {
            return true;
        }
        return false;
    }
}