package com.laka.libutils.screen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.laka.libutils.app.ApplicationUtils;
import com.laka.libutils.system.OsUtils;

import java.lang.reflect.Method;

/**
 * @Author:summer
 * @Date:2019/7/12
 * @Description:屏幕工具
 */
public class ScreenUtils {
    private static int mScreenWidth = 0;
    private static int mScreenHeight = 0;
    private static int mStatusBarHeight = 0;

    public static final String SHARE_PREFERENCE_NAME = "EmotionKeyBoard";
    public static final String SHARE_PREFERENCE_SOFT_INPUT_HEIGHT = "sofe_input_height";
    public static final String SHARE_PREFERENCE_VIRTUAL_KEY_HEIGHT = "share_preference_deviation_height";
    private static final String KEY_ANDROID_SYSTEM_PROPERTIES = "android.os.SystemProperties";
    private static final String KEY_CONFIG_SHOW_NAVIGATION_BAR = "config_showNavigationBar";
    private static final String KEY_NAVIGATION_BAR_HEIGHT = "navigation_bar_height";
    private static final String KEY_CONFIG_ANDROID_DISPLAY = "android.view.Display";
    private static final String KEY_QEMU_HW_MAINKEYS = "qemu.hw.mainkeys";

    /**
     * 得到设备屏幕的宽度
     *
     * @return 屏幕宽度 pixels
     */
    public static int getScreenWidth() {
        if (mScreenWidth <= 0) {
            mScreenWidth = ApplicationUtils.getApplication().getResources().getDisplayMetrics().widthPixels;
        }
        return mScreenWidth;
    }

    /**
     * 得到设备屏幕的高度
     *
     * @return 屏幕高度 pixels
     */
    public static int getScreenHeight() {
        if (mScreenHeight <= 0) {
            mScreenHeight = ApplicationUtils.getApplication().getResources().getDisplayMetrics().heightPixels;
        }
        return mScreenHeight;
    }

    /**
     * 获取StatusBar高度
     *
     * @return StatusBar高度 pixels
     */
    public static int getStatusBarHeight() {
        if (mStatusBarHeight <= 0) {
            int resourceId = ApplicationUtils.getApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                mStatusBarHeight = ApplicationUtils.getApplication().getResources().getDimensionPixelSize(resourceId);
            }
            if (mStatusBarHeight <= 0) {
                mStatusBarHeight = dp2px(64f);
            }
        }
        return mStatusBarHeight;
    }

    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getSoftInputHeight(Context context) {
        int totalHeight = getScreenTotalHeight(context);
        int contentHeight = getScreenHeight();
        return totalHeight - contentHeight;
    }

    /**
     * 通过反射获取屏幕总高度
     */
    public static int getScreenTotalHeight(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName(KEY_CONFIG_ANDROID_DISPLAY);
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * （普通机型）
     * 获取虚拟按键高度
     */
    private static int getNavigationBarHeightNormal(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier(KEY_NAVIGATION_BAR_HEIGHT, "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * （特殊机型）
     * 获取底部虚拟按键栏的高度
     * 如果软键盘弹起的情况下调用，获取的高度将是不准确的
     *
     * @return虚拟按键的高度
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getNavigationBarHeight(Activity activity) {
        int resultHeight = 0;
        DisplayMetrics metrics = new DisplayMetrics();
        //获取屏幕可用空间的高度
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实物理高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            resultHeight = realHeight - usableHeight;
        }
        //以上方法获取不到，则通过 getNavigationBarHeightNormal 获取
        int navigationBarHeight = getNavigationBarHeightNormal(activity);
        if (navigationBarHeight <= 0) {
            return resultHeight;
        }
        //两者都不为0，返回小的一个
        return resultHeight < navigationBarHeight ? resultHeight : navigationBarHeight;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(KEY_CONFIG_SHOW_NAVIGATION_BAR, "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName(KEY_ANDROID_SYSTEM_PROPERTIES);
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, KEY_QEMU_HW_MAINKEYS);
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    /**
     * 适配小米手机的方法，判断小米手机是否开启虚拟按键
     * 根据测试发现（测试机小米8）,无论是否开启虚拟按键，
     * 通过 hasNavBar() 获取到的始终为 false，所以针对小米进行处理
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isOpenMiUiVirtualBar(Activity activity) {
        if (Settings.Global.getInt(activity.getContentResolver(), OsUtils.MIUI_VIRTUAL_KEY_EXIT_KEY, 0) != 0) {
            return false;//开启手势
        } else {
            return true;//开启虚拟按键
        }
    }

    /**
     * 获取屏幕高度。不包括状态栏的高度
     *
     * @return 屏幕高度 pixels
     */
    public static int getScreenHeightWithoutStatusBar() {
        if (mScreenHeight <= 0) {
            getScreenHeight();
        }
        if (mStatusBarHeight <= 0) {
            getStatusBarHeight();
        }
        return mScreenHeight - mStatusBarHeight;
    }

    //==========================  特殊方式处理 ===============================================

    /**
     * 通过：屏幕高度 - rootView.bottom = 虚拟按键高度 + keyboard height  获取并保存
     * 键盘弹起，则获取到的高度是：虚拟按键height（如果有） + keyboard height
     * 键盘未弹起，则获取的高度是：虚拟按键height（如果有）
     */
    public static int getScreenBottomToRootViewBottomDistance(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //获取屏幕的高度
        int screenHeight = activity.getWindow().getDecorView().getRootView().getHeight();
        int softInputHeight = screenHeight - rect.bottom;
        return softInputHeight;
    }

    /**
     * 注意：在键盘未弹起状态下，获取虚拟按键（如果有的话）
     * 在该项目中，首页是没有键盘弹起的，所以在 HomeActivity 的onResume 中获取虚拟按键的高度（如果有的话）
     */
    public static int getAndSaveVirtualKeyHeight(Activity activity) {
        int softInputHeight = getScreenBottomToRootViewBottomDistance(activity);
        if (softInputHeight > 0) {//大于0表示手机开启了虚拟按键
            SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
            sp.edit().putInt(SHARE_PREFERENCE_VIRTUAL_KEY_HEIGHT, softInputHeight).commit();
        }
        return softInputHeight;
    }

    /**
     * 获取键盘高度（键盘弹出后才能够准确获取），如果需要针对键盘高度做适配，可以在进入目标页面前调起键盘获取高度
     * 这样首次进入需要适配的页面也能适配成功了，不然需要第二次进入目标页面才能真正的适配成功
     */
    public static int getKeyboardHeight(Activity activity) {
        SharedPreferences sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        int softInputHeight = getScreenBottomToRootViewBottomDistance(activity);
        //某些机型，没有显示软键盘时减出来的高度也不为零，这是因为高度是包括了虚拟按键栏的(例如华为、小米系列)，
        //所以判断当前机型是否存在虚拟按键，我们需要减去底部虚拟按键栏的高度（如果有的话）
        if (ScreenUtils.hasNavBar(activity)) {
            if (OsUtils.isMIUI()) {
                // 小米适配，针对 通过正常方法 获取虚拟键盘 不正确 的机型 做的适配，在键盘未显示的情况下，
                // 通过: 屏幕高度 - 屏幕可用高度 = 虚拟按键高度  来获取
                if (isOpenMiUiVirtualBar(activity)) {
                    //小米机型开启虚拟按键
                    int virtualKeyHeight = sp.getInt(SHARE_PREFERENCE_VIRTUAL_KEY_HEIGHT, 0);
                    if (virtualKeyHeight > 0) { //本地里已存在想虚拟按键高度（准确）
                        softInputHeight -= virtualKeyHeight;
                    } else {//本地未存在虚拟按键高度（某些机型，获取的不准确，所以尽量在聊天页面前，获取到虚拟按键高度并保存）
                        softInputHeight -= getNavigationBarHeight(activity);
                    }
                }
            } else {
                //不需要适配的机型，直接通过 getNavigationBarHeight 即可获得准确的虚拟按键高度
                softInputHeight -= getNavigationBarHeight(activity);
            }
        }
        //保存键盘高度到本地
        if (softInputHeight > 0) {
            Log.i(SHARE_PREFERENCE_NAME, "登录页保存键盘高度：" + softInputHeight);
            sp.edit().putInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, softInputHeight).commit();
            return softInputHeight;
        }
        return 0;//获取不到键盘高度，直接返回 0
    }


    /**
     * dp值转化为px值
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(float dpValue) {
        final float scale = ApplicationUtils.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px值转化为dp值
     *
     * @param pxValue px值
     * @return dp值
     */
    public static float px2dp(float pxValue) {
        final float scale = ApplicationUtils.getApplication().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px值转化为sp值
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(float pxValue) {
        final float fontScale = ApplicationUtils.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp值转化为px值
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(float spValue) {
        final float fontScale = ApplicationUtils.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static float getDensity(Activity context) {
        //获取屏幕密度
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.density;
    }
}
