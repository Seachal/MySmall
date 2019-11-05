package com.laka.appkotlin.coroutines.model

import com.laka.appkotlin.coroutines.GlobalScope
import com.laka.appkotlin.coroutines.constract.CoroutinesConstract
import com.laka.libnet.constant.AppConstant
import com.laka.libnet.converter.JsonConverterFactory
import com.laka.libnet.gson.factory.GsonFormatTypeAdapterFactory
import com.laka.libnet.intercepter.LoggingInterceptor
import com.laka.libnet.mvp.model.BaseModel
import com.laka.libnet.utils.GsonUtils
import com.laka.libutils.LogUtils
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CoroutinesModel : BaseModel(), CoroutinesConstract.ICoroutinesModel {

    private var mCoroutinesService: CoroutinesService? = null

    override fun getUserInfo() {
        initRetrofit()
        val url = "http://www.baidu.com"
        GlobalScope().launch {
            //rxjava 2.6.0 以上支持了协程，可以直接调用返回了Observable订阅了
            //val response = RetrofitHelper.getApiService().getUserInfo(url)
            //LogUtils.info("response-----$response")
            val response = mCoroutinesService?.getBaiDuHomeInfo(url)
            LogUtils.info("response----$response")
        }
    }


    private fun initRetrofit() {
        if (mCoroutinesService == null) {
            val builder = OkHttpClient().newBuilder()
            builder.addNetworkInterceptor(LoggingInterceptor())
                    .retryOnConnectionFailure(true)
                    .connectTimeout(AppConstant.MAX_CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(AppConstant.MAX_READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(AppConstant.MAX_WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
            // 创建OKHttp对象
            val okHttpClient = builder.build()
            val retrofitBuilder = Retrofit.Builder()
                    .baseUrl("http://www.baidu.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
            val retrofit = retrofitBuilder.build()
            mCoroutinesService = retrofit?.create(CoroutinesService::class.java)
        }
    }
}