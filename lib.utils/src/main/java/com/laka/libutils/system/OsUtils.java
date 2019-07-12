package com.laka.libutils.system;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author:summer
 * @Date:2019/2/21
 * @Description:android手机厂商判断工具类
 */
public class OsUtils {
    public static final String MIUI_VIRTUAL_KEY_EXIT_KEY = "force_fsg_nav_bar";
    //小米机型prop
    private static final String MIUI_OS_KEY = "ro.miui.ui.version.name";
    //华为机型prop
    private static final String HUAWEI_OS_KEY = "ro.build.version.emui";
    //vivo机型prop
    private static final String VIVO_OS_KEY = "ro.build.version.opporom";
    //锤子机型prop
    private static final String SMARTISAN_OS_KEY = "ro.smartisan.version";
    //魅族
    private static final String MEIZU_OS_KEY = "FLYME";
    //360
    private static final String KEY_OS_360_1 = "QIKU";
    private static final String KEY_OS_360_2 = "360";
    //其他机型会陆续补上

    public static boolean isMIUI() {
        return !TextUtils.isEmpty(getSystemProperty(MIUI_OS_KEY));
    }

    public static boolean isHuaWei() {
        return !TextUtils.isEmpty(getSystemProperty(HUAWEI_OS_KEY));
    }

    public static boolean isViVo() {
        return !TextUtils.isEmpty(getSystemProperty(VIVO_OS_KEY));
    }

    public static boolean isSmartisan() {
        return !TextUtils.isEmpty(getSystemProperty(SMARTISAN_OS_KEY));
    }

    public static boolean isMeiZu() {
        return !TextUtils.isEmpty(getSystemProperty(MEIZU_OS_KEY));
    }

    public static boolean is360() {
        return !TextUtils.isEmpty(getSystemProperty(KEY_OS_360_1))
                || !TextUtils.isEmpty(getSystemProperty(KEY_OS_360_2));
    }

    /**
     * 判断当前手机系统
     *
     * @param propName
     * @return
     */
    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

}
