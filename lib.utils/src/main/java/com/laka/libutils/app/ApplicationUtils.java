package com.laka.libutils.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.laka.libutils.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author:summer
 * @Date:2019/7/11
 * @Description:用于初始化application Context
 */
public final class ApplicationUtils {
    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;
    private static Boolean sDebug = BuildConfig.DEBUG;
    public static List<Activity> activities = new LinkedList<>();
    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Context mContext;//上下文
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler


    private ApplicationUtils() {
        throw new UnsupportedOperationException("do not instantiate me , please.");
    }

    private static boolean verticallyIsInit() {
        if (sApplication == null) {
            throw new RuntimeException("ApplicationUtils has not yet been initialized");
        }
        return true;
    }

    public static void init(@NonNull Application application) {
        sApplication = application;
        mContext = sApplication;
        //对全局属性赋值
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();
        //Logger初始化
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    /**
     * 获取Application
     *
     * @return Application实例
     * @throws NullPointerException 当Application为空时
     */
    public static Application getApplication() {
        verticallyIsInit();
        if (sApplication == null) {
            throw new NullPointerException("application is null ,call init() please");
        }
        return sApplication;
    }

    /**
     * 初始化debug状态
     */
    public static void initDebug(boolean debug) {
        if (verticallyIsInit()) {
            sDebug = debug;
        }
    }

    /**
     * 判断是否是debug状态
     *
     * @return true ，debug状态；false ，非debug状态
     */
    public static boolean isDebug() {
        verticallyIsInit();
        return sDebug;
    }

    /**
     * 完全退出
     * 一般用于“退出程序”功能
     */
    public static void exit() {
        verticallyIsInit();
        for (Activity activity : activities) {
            activity.finish();
        }
    }

    /**
     * 重启当前应用
     */
    public static void restart() {
        verticallyIsInit();
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    /**
     * 判断当前activity是否还在活动状态
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isVaildActivity(Activity activity) {
        verticallyIsInit();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return false;
        }
        return true;
    }

    public static Context getContext() {
        verticallyIsInit();
        return mContext;
    }

    public static void setContext(Context context) {
        verticallyIsInit();
        mContext = context;
    }

    public static Thread getMainThread() {
        verticallyIsInit();
        return mMainThread;
    }

    public static void setMainThread(Thread mainThread) {
        verticallyIsInit();
        mMainThread = mainThread;
    }

    public static long getMainThreadId() {
        verticallyIsInit();
        return mMainThreadId;
    }

    public static void setMainThreadId(long mainThreadId) {
        verticallyIsInit();
        mMainThreadId = mainThreadId;
    }

    public static Looper getMainThreadLooper() {
        verticallyIsInit();
        return mMainLooper;
    }

    public static void setMainThreadLooper(Looper looper) {
        verticallyIsInit();
        mMainLooper = looper;
    }

    public static Handler getMainHandler() {
        verticallyIsInit();
        return mHandler;
    }

    public static void setMainHandler(Handler handler) {
        verticallyIsInit();
        mHandler = handler;
    }

}
