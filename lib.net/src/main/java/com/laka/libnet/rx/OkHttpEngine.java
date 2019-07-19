package com.laka.libnet.rx;

import com.laka.libnet.intercepter.AuthorizationInterceptor;
import com.laka.libnet.intercepter.GlobalParamsInterceptor;
import com.laka.libnet.intercepter.LoggingInterceptor;
import com.laka.libnet.intercepter.NetWorkInterceptor;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:
 */
public class OkHttpEngine {

    public static OkHttpEngine create;


    public static class Builder {
        // 超时时间
        long mReadTimeout = 1000 * 10;
        long mConnectTimeout = 1000 * 10;
        long readTime = 1000 * 10;
        //拦截器
        AuthorizationInterceptor mAuthorInterceptor;
        GlobalParamsInterceptor mGpInterceptor;
        LoggingInterceptor mLoggingInterceptor;
        NetWorkInterceptor mNetWorkInterceptor;
        //host
        String mBaseHost;


    }
}
