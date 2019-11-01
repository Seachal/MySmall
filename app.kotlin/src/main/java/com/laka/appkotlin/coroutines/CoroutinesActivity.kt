package com.laka.appkotlin.coroutines

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.laka.appkotlin.R
import com.laka.appkotlin.dsl.test3
import com.laka.appkotlin.dsl.test4
import com.laka.libutils.LogUtils
import com.laka.libutils.toast.ToastHelper
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class CoroutinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)
        initData()
    }

    private fun initData() {
        GlobalScope().launch(Dispatchers.Main) {
            //使用了协程，以下的都是常规的异步方法，却可以当做同步来调用
            //getBaiduData()
            downloadImage()
            getUserInfo()
            LogUtils.info("coroutine-----end")
        }
    }

    //suspend：挂起 ，修饰的函数称为挂起函数，必须由协程或者其他的挂起函数来调用
    //Dispatchers：协程中提供的线程调度类
    private suspend fun getUserInfo() = withContext(Dispatchers.IO) {
        LogUtils.info("coroutine-----获取用户信息成功")
    }

    private suspend fun downloadImage() = withContext(Dispatchers.IO) {
        repeat(100) {
            LogUtils.info("coroutine-----下载图片$it")
        }
    }


    private fun getBaiduData() {
        val url = "http://www.baidu.com"
        val okHttpClient = OkHttpClient.Builder().connectTimeout(15000, TimeUnit.MILLISECONDS)
                .readTimeout(15000, TimeUnit.MILLISECONDS)
                .build()
        val request = Request.Builder().url(url).build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code() == 200) {
                    val result = response.body()?.string()
                    ToastHelper.showCenterToast(result)
                }
            }
        })
    }

    //runBlocking协程执行的过程中，当前线程会被挂起，等待协程执行完毕
    //这种开启协程的方法一般都只是用来做单元测试，生产环境不会使用这种方式的
    private fun test1() = runBlocking {
        repeat(10) {
            println("协程执行.....$it")
        }
    }


}