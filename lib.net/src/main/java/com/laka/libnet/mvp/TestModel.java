package com.laka.libnet.mvp;

import com.laka.libnet.mvp.model.BaseModel;
import com.laka.libnet.mvp.model.IBaseModel;
import com.laka.libnet.rx.RetrofitHelper;
import com.laka.libnet.rx.callback.ResponseCallBack;
import com.laka.libutils.LogUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:
 */
public class TestModel extends BaseModel implements IBaseModel {

    public void getUserInfo(HashMap<String, String> params, ResponseCallBack<String> callBack) {
        doBaseRequest(RetrofitHelper.getApiService().onTest(params), callBack);
    }

    public void onTest() {
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        LogUtils.info("Unsubscribing subscription from onCreate()");
                    }
                })
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LogUtils.info("Started in onCreate(), running until in onDestroy()");
                    }
                });
    }

}
