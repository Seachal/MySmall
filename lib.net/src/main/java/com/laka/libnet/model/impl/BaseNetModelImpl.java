package com.laka.libnet.model.impl;

import com.laka.libnet.model.INetModel;
import com.laka.libnet.rx.scriber.RxSchedulerComposer;
import com.laka.libnet.rx.scriber.RxSubscriber;
import com.laka.libnet.rx.callback.ResponseCallBack;

import io.reactivex.Observable;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:
 */
public class BaseNetModelImpl implements INetModel {

    @Override
    public <T> void doBaseRequest(Observable<T> observable, ResponseCallBack<T> callBack) {
        observable.compose(RxSchedulerComposer.normalSchedulersTransformer())
                .subscribe(new RxSubscriber(callBack));
    }

}
