package com.laka.libnet.rx.callback;

import android.support.annotation.NonNull;

import com.laka.libnet.exception.BaseException;


/**
 * @Author:summer
 * @Date:2019/1/17
 * @Description:网络请求统一回调接口
 */
public interface ResponseCallBack<T> {
    void onSuccess(@NonNull T t);

    void onFail(BaseException e);
}
