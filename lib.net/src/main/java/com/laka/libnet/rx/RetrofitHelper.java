package com.laka.libnet.rx;

import android.content.Context;

import com.laka.libnet.constant.AppConstant;
import com.laka.libnet.converter.JsonConverterFactory;
import com.laka.libnet.intercepter.AuthorizationInterceptor;
import com.laka.libnet.intercepter.LoggingInterceptor;
import com.laka.libnet.intercepter.NetWorkInterceptor;
import com.laka.libnet.utils.GsonUtils;
import com.laka.libutils.app.ApplicationUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:Retrofit构建helper
 */
public class RetrofitHelper {

    private Context context;
    private Retrofit mRetrofit;
    private static ApiService mApiService;

    /**
     * description:常规参数配置
     **/
    private String HOST = "";
    private boolean isAuthorRequest = false;
    private boolean isNetWorkInterceptor = false;
    private boolean isGlobalParamsInterceptor = false;

    public static ApiService getApiService() {
        if (mApiService == null) {
            synchronized (RetrofitHelper.class) {
                if (mApiService == null) {
                    mApiService = createApiService();
                }
            }
        }
        return mApiService;
    }

    private RetrofitHelper(Builder builder) {
        this.context = builder.context;
        this.isAuthorRequest = builder.isAuthorRequest;
        this.isNetWorkInterceptor = builder.isNetWorkInterceptor;
        this.isGlobalParamsInterceptor = builder.isGlobalParamsInterceptor;
        this.HOST = builder.requestHost;
        initRetrofit();
    }

    /**
     * 配置Retrofit信息
     */
    private void initRetrofit() {
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(context.getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        //拦截器
        if (isGlobalParamsInterceptor) {
            builder.addInterceptor(new NetWorkInterceptor(context));
        }
        if (isAuthorRequest) {
            builder.addInterceptor(new AuthorizationInterceptor(context));
        }
        if (isNetWorkInterceptor) {
            builder.addInterceptor(new NetWorkInterceptor(context));
        }
        builder.addNetworkInterceptor(new LoggingInterceptor())
                .cache(cache)
                .retryOnConnectionFailure(true)
                .connectTimeout(AppConstant.MAX_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(AppConstant.MAX_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(AppConstant.MAX_WRITE_TIME_OUT, TimeUnit.SECONDS);

        // 创建OKHttp对象
        OkHttpClient okHttpClient = builder.build();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient);
        //使用jsonConverter
        retrofitBuilder.addConverterFactory(JsonConverterFactory.create(GsonUtils.getDefaultGson()));
        mRetrofit = retrofitBuilder.build();
    }

    /**
     * 创建ApiService对象
     */
    private static ApiService createApiService() {
        return new Builder(ApplicationUtils.getApplication())
                .setAuthorRequest(true)
                .setNetWorkInterceptor(true)
                .setGlobalParamsInterceptor(true)
                .setRequestHost(AppConstant.BASE_HOST)
                .build()
                .create(ApiService.class);
    }

    /**
     * 通过create函数获取到具体的Service
     *
     * @param reqServer
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }

    public static class Builder {
        private boolean isAuthorRequest = false;
        private boolean isNetWorkInterceptor = false;
        private boolean isGlobalParamsInterceptor = true;
        private String requestHost = "";
        private Context context;
        public Builder(Context context) {
            this.context = context;
        }

        public Builder setAuthorRequest(boolean tokenRequest) {
            isAuthorRequest = tokenRequest;
            return this;
        }

        public Builder setNetWorkInterceptor(boolean netWorkInterceptor) {
            isNetWorkInterceptor = netWorkInterceptor;
            return this;
        }

        public Builder setGlobalParamsInterceptor(boolean globalParamsInterceptor) {
            isGlobalParamsInterceptor = globalParamsInterceptor;
            return this;
        }

        public Builder setRequestHost(String requestHost) {
            this.requestHost = requestHost;
            return this;
        }

        public RetrofitHelper build() {
            return new RetrofitHelper(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RetrofitHelper)) return false;

        RetrofitHelper that = (RetrofitHelper) o;

        if (isAuthorRequest != that.isAuthorRequest) return false;
        if (isNetWorkInterceptor != that.isNetWorkInterceptor) return false;
        if (isGlobalParamsInterceptor != that.isGlobalParamsInterceptor) return false;
        if (context != null ? !context.equals(that.context) : that.context != null) return false;
        if (mRetrofit != null ? !mRetrofit.equals(that.mRetrofit) : that.mRetrofit != null)
            return false;
        return HOST != null ? HOST.equals(that.HOST) : that.HOST == null;
    }

    @Override
    public int hashCode() {
        int result = context != null ? context.hashCode() : 0;
        result = 31 * result + (mRetrofit != null ? mRetrofit.hashCode() : 0);
        result = 31 * result + (HOST != null ? HOST.hashCode() : 0);
        result = 31 * result + (isAuthorRequest ? 1 : 0);
        result = 31 * result + (isNetWorkInterceptor ? 1 : 0);
        result = 31 * result + (isGlobalParamsInterceptor ? 1 : 0);
        return result;
    }
}
