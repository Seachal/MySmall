package com.laka.libnet.model;


import com.laka.libnet.rx.callback.ResponseCallBack;

import io.reactivex.Observable;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:针对网络数据层进行最基本的封装接口
 */
public interface INetModel {
    /**
     * 普通网络请求方法
     * */
    <T> void doBaseRequest(Observable<T> observable, ResponseCallBack<T> callBack);

}
