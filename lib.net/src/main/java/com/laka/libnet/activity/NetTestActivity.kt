package com.laka.libnet.activity

import android.os.Bundle
import android.view.View
import com.laka.libnet.R
import com.laka.libutils.LogUtils
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Observable
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @Author:summer
 * @Date:2019/7/18
 * @Description:
 */
class NetTestActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_test)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn1 -> {
                startSubscrition()
            }
        }
    }

    private fun startSubscrition() {
        Observable.interval(1,TimeUnit.SECONDS)
                .doOnDispose({
                    LogUtils.info("Unsubscribing subscription from onCreate()")
                })
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe{
                    LogUtils.info("Started in onCreate(), running until in onDestroy()")
                }


        OkHttpClient.Builder().connectTimeout()
    }









}