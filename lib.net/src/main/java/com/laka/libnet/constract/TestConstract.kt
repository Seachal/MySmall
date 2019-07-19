package com.laka.libnet.constract

import com.laka.libnet.mvp.model.IBaseModel
import com.laka.libnet.mvp.presenter.IBasePresenter

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:
 */
interface TestConstract {

    interface TestModel : IBaseModel {
        fun onTest()
    }

    interface TestPresenter : IBasePresenter {
        fun onTest()
    }

}