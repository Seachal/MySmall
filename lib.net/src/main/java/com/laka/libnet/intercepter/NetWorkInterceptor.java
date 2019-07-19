package com.laka.libnet.intercepter;

import android.content.Context;

import com.laka.libnet.exception.BaseException;
import com.laka.libutils.network.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * desc:
 * 网络状态拦截器---待完善
 *
 * @author Rayman
 * @date 2017/12/15
 */

public class NetWorkInterceptor implements Interceptor {

    private Context context;

    public NetWorkInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (NetworkUtils.isNetworkAvailable()) {
            //LogUtils.info("NetWork is Available");
            //有网络的情况下不拦截，同时也会将http转化为https。
            Request request = chain.request();
            return chain.proceed(request);
        } else {
            //LogUtils.error("NetWork Error");
            // 无网络状态下，直接抛出异常。假若需要在业务逻辑判断
            // 在对应的处理类，加上对exception的判断，假若是code==NETWORK_ERROR的，就自行处理
            throw new BaseException(BaseException.NETWORK_ERROR, "网络连接失败，请检查网络设置");
        }
    }
}
