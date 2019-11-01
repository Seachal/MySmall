package com.laka.libnet.mvp;

import com.laka.libnet.constract.TestConstract;
import com.laka.libnet.mvp.model.BaseModel;
import com.laka.libnet.mvp.model.IBaseModel;
import com.laka.libnet.rx.RetrofitHelper;
import com.laka.libnet.rx.callback.ResponseCallBack;
import com.laka.libnet.rx.response.ResponseInfo;
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
public class TestModel extends BaseModel implements TestConstract.TestModel {

    /**
     * 测试生命周期绑定
     */
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

    public void getUserInfo(HashMap<String, String> params, ResponseCallBack<ResponseInfo> callBack) {
        //doBaseRequest(RetrofitHelper.getApiService().getUserInfo(params), callBack);
        String url = "s?wd=今日新鲜事&tn=SE_PclogoS_8whnvm25&sa=ire_dl_gh_logo&rsv_dl=igh_logo_pcs";
        doBaseRequest(RetrofitHelper.getApiService().getRequest(url, params), callBack);
    }


}