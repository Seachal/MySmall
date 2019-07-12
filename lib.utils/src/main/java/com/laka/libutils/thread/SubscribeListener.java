package com.laka.libutils.thread;

/**
 * @Author:summer
 * @Date:2019/7/12
 * @Description:
 */
public interface SubscribeListener<T> {

    /**
     * Do some works on SubThread, You can't change or update your UI within it.
     * @return return null if any of errors occurs.
     * @throws Exception
     */
    T runOnSubThread() throws Exception;
}