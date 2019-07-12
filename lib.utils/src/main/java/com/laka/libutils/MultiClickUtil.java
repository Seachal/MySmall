package com.laka.libutils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:summer
 * @Date:2019/7/12
 * @Description:防抖工具类
 */
public class MultiClickUtil {

    protected static final int CLICK_INTERVAL = 500;
    private static long mPreClickTime = 0;
    private static Map<Integer, Long> clickTimeMap = new HashMap<>();

    /**
     * 针对Id进行判断
     *
     * @param viewId
     * @return
     */
    public static boolean checkClickValid(int viewId) {
        boolean isValid;
        mPreClickTime = clickTimeMap.get(viewId) == null ? mPreClickTime : clickTimeMap.get(viewId);
        if (System.currentTimeMillis() - mPreClickTime > CLICK_INTERVAL) {
            mPreClickTime = System.currentTimeMillis();
            isValid = true;
        } else {
            isValid = false;
        }
        clickTimeMap.put(viewId, mPreClickTime);
        return isValid;
    }

}
