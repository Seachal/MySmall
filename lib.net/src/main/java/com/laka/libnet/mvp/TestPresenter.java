package com.laka.libnet.mvp;

import com.laka.libnet.constract.TestConstract;
import com.laka.libnet.mvp.model.BaseModel;
import com.laka.libnet.mvp.presenter.BasePresenter;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:
 */
public class TestPresenter extends BasePresenter implements TestConstract.TestPresenter {

    private TestModel mModel;

    @Override
    public BaseModel createModel() {
        mModel = new TestModel();
        return mModel;
    }


    @Override
    public void onTest() {
        mModel.onTest();
    }

}
