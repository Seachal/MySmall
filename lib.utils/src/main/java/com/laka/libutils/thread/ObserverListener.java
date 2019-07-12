package com.laka.libutils.thread;

import android.support.annotation.Nullable;

/**
 * @Author:summer
 * @Date:2019/7/12
 * @Description:
 */
public interface ObserverListener<T> {

    /**
     * You can change or update your UI in here. But you can't do any heavy operations.
     * Such as, networks、read and write on database and so on.
     * @param t
     */
    void runOnUiThread(@Nullable T t);
}