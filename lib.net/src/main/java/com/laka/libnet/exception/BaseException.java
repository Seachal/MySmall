package com.laka.libnet.exception;

import java.io.IOException;

/**
 * @Author:summer
 * @Date:2019/7/17
 * @Description:封装的基础Exception类
 */
public class BaseException extends IOException {

    /**
     * description:网络请求错误（根据RxJava onError的Exception类型划分）
     **/
    public static final int NETWORK_ERROR = 0x11;

    private String errorMsg;
    private int code;

    public BaseException(int code, String errorMsg) {
        super(errorMsg);
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg == null ? "" : errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
