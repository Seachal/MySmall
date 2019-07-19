package com.laka.libnet.rx.scriber;

import com.google.gson.JsonSyntaxException;
import com.laka.libnet.constant.RequestCodeConstant;
import com.laka.libnet.exception.ApiException;
import com.laka.libnet.exception.BaseException;
import com.laka.libnet.rx.callback.ResponseCallBack;
import com.laka.libutils.BuildConfig;
import com.laka.libutils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:
 */
public class RxSubscriber<T> implements Observer<T> {

    protected Disposable disposable;
    private ResponseCallBack<T> mCallBack;
    /**
     * 请求接口时，接口返回错误码是 4 或者 5，是否在这里直接拦截并且统一跳转登录页面
     */
    private boolean mIsToLogin = true;

    public RxSubscriber(ResponseCallBack<T> callBack) {
        mCallBack = callBack;
    }

    public RxSubscriber(ResponseCallBack<T> callBack, boolean isToLogin) {
        this.mCallBack = callBack;
        this.mIsToLogin = isToLogin;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }


    /**
     * 请求成功，code=0
     */
    @Override
    public void onNext(T t) {
        if (mCallBack != null) {
            mCallBack.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof BaseException) {
            mCallBack.onFail((BaseException) e);
            BaseException exception = (BaseException) e;
            if (exception.getCode() == RequestCodeConstant.LK_WRONG_USER_TOKEN
                    || exception.getCode() == RequestCodeConstant.LK_NOT_LOGIN) {
                if (mIsToLogin) {
                    // 强制跳转到用户登陆页面

                } else {
                    if (exception.getCode() == RequestCodeConstant.LK_WRONG_USER_TOKEN) {
                        mCallBack.onFail(new ApiException(RequestCodeConstant.LK_WRONG_USER_TOKEN, RequestCodeConstant.USER_TOKEN_INVALID_MSG));
                    } else {
                        mCallBack.onFail(new ApiException(RequestCodeConstant.LK_NOT_LOGIN, RequestCodeConstant.USER_NOT_LOGIN_MSG));
                    }
                }
            }
        } else if (e instanceof JsonSyntaxException) {  // 需要针对性处理的异常
            if (mCallBack != null) {
                LogUtils.error("数据解析异常");
                e.printStackTrace();
                mCallBack.onFail(new ApiException(RequestCodeConstant.LK_ERROR_JSON_FORMAT, RequestCodeConstant.DATA_FORMAT_EXCEPTION_MSG));
            }
        } else { // 异常错误
            if (BuildConfig.DEBUG) {
                if (mCallBack != null) {
                    String str = e.getMessage();
                    mCallBack.onFail(new ApiException(RequestCodeConstant.LK_ERROR_OTHER, e.getMessage()));
                }
            } else {
                if (mCallBack != null) {
                    mCallBack.onFail(new ApiException(RequestCodeConstant.LK_ERROR_OTHER, RequestCodeConstant.REQUEST_FAIL));
                }
            }
        }
    }

    @Override
    public void onComplete() {

    }
}
