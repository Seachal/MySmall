package com.laka.librouter.utils;

import android.content.Intent;
import android.text.TextUtils;

import com.laka.librouter.BuildConfig;

/**
 * @Author:summer
 * @Date:2019/7/18
 * @Description:Intent 工具类，try{}cache(){} 包裹，Intent#getStringExtra 在代码逻辑出错或者恶意攻击的情况下可能会抛出ClassNotFoundException异常
 * 获取 Intent 里面的参数可以直接使用 IntentUtils 更安全
 */
public class IntentUtils {

    private static final String TAG = "IntentUtils";

    private static void customThrowException(Exception e) {
        if (BuildConfig.DEBUG) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static int getIntExtra(Intent intent, String key, int defaultValue) {
        try {
            int value = intent.getIntExtra(key, defaultValue);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return defaultValue;
    }

    public static int getIntExtra(Intent intent, String key) {
        try {
            int value = intent.getIntExtra(key, 0);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return 0;
    }


    public static float getFloatExtra(Intent intent, String key, float defaultValue) {
        try {
            float value = intent.getFloatExtra(key, defaultValue);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return defaultValue;
    }

    public static float getFloatExtra(Intent intent, String key) {
        try {
            float value = intent.getFloatExtra(key, 0f);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return 0f;
    }


    public static double getDoubleExtra(Intent intent, String key, double defaultValue) {
        try {
            double value = intent.getDoubleExtra(key, defaultValue);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return defaultValue;
    }

    public static double getDoubleExtra(Intent intent, String key) {
        try {
            double value = intent.getDoubleExtra(key, 0.0);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return 0.0;
    }


    public static long getLongExtra(Intent intent, String key, long defaultValue) {
        try {
            long value = intent.getLongExtra(key, defaultValue);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return defaultValue;
    }

    public static long getLongExtra(Intent intent, String key) {
        try {
            long value = intent.getLongExtra(key, 0);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return 0;
    }

    public static boolean getBooleanExtra(Intent intent, String key, boolean defaultValue) {
        try {
            boolean value = intent.getBooleanExtra(key, defaultValue);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return defaultValue;
    }

    public static boolean getBooleanExtra(Intent intent, String key) {
        try {
            boolean value = intent.getBooleanExtra(key, false);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return false;
    }


    public static String getStringExtra(Intent intent, String key, String defaultValue) {
        try {
            String value = intent.getStringExtra(key);
            if (TextUtils.isEmpty(value)) return defaultValue;
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return defaultValue;
    }

    public static String getStringExtra(Intent intent, String key) {
        try {
            String value = intent.getStringExtra(key);
            if (TextUtils.isEmpty(value)) return "";
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            customThrowException(e);
        }
        return "";
    }

}
