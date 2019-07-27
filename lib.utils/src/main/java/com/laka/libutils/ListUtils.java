package com.laka.libutils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author:summer
 * @Date:2019/7/12
 * @Description:
 */
public class ListUtils {
    private ListUtils() {
        throw new UnsupportedOperationException("do not instantiate me , please.");
    }

    /**
     * 防止数据越界
     * */
    public static <T> T get(List<T> list, int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    /**
     * 判断List是否为空
     */
    public static boolean isNotEmpty(Collection<?> list) {
        return !isEmpty(list);
    }

    /**
     * 判断List是否为空
     */
    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 判断筛选列表数据和下标是否有效
     * 防止数组越界
     *
     * @return
     */
    public static boolean isListValidPosition(List targetList, int position) {
        boolean isDataNotEmpty = ListUtils.isNotEmpty(targetList);
        boolean isValidIndex = false;
        if (isDataNotEmpty) {
            if (position < 0 || position >= targetList.size()) {
                isValidIndex = false;
            } else {
                isValidIndex = true;
            }
        }
        return isValidIndex;
    }

    public static <T> void printList(ArrayList<T> list) {
        if (isNotEmpty(list)) {
            for (T object : list) {
                LogUtils.info("输出列表数据：" + object.toString());
            }
        }
    }
}