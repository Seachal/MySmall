package com.laka.libnet.model;

import com.laka.libnet.model.impl.BaseNetModelImpl;
import com.laka.libnet.rx.RetrofitHelper;
import com.laka.libnet.rx.callback.ResponseCallBack;

import java.util.HashMap;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:
 */
public class TestModel extends BaseNetModelImpl {

    public void getUserInfo(HashMap<String, String> params, ResponseCallBack<String> callBack) {
        doBaseRequest(RetrofitHelper.getApiService().onTest(params), callBack);
    }

}
