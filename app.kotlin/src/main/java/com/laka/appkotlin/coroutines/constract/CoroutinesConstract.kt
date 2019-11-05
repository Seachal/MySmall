package com.laka.appkotlin.coroutines.constract

import com.laka.libnet.mvp.model.IBaseModel
import com.laka.libnet.mvp.presenter.IBasePresenter

interface CoroutinesConstract {

    interface ICoroutinesModel : IBaseModel {
        fun getUserInfo()
    }

    interface ICoroutinesPresenter : IBasePresenter {
        fun getUserInfo()
    }
}