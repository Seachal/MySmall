package com.laka.libnet.activity

import android.os.Bundle
import android.view.View
import com.laka.libnet.R
import com.laka.libnet.exception.BaseException
import com.laka.libnet.mvp.TestModel
import com.laka.libnet.rx.callback.ResponseCallBack
import com.laka.libutils.LogUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * @Author:summer
 * @Date:2019/7/18
 * @Description:
 */
class NetTestActivity : RxAppCompatActivity() {

    private lateinit var mTestModel: TestModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_test)
        mTestModel = TestModel()
        mTestModel.onCreate()
    }

    override fun onStart() {
        super.onStart()
        mTestModel.onStart()
    }

    override fun onResume() {
        super.onResume()
        mTestModel.onResume()
    }

    override fun onPause() {
        mTestModel.onPause()
        super.onPause()
    }

    override fun onStop() {
        mTestModel.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mTestModel.onDestroy()
        super.onDestroy()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn1 -> {
                startSubscrition()
            }
        }
    }

    private fun startSubscrition() {
//        Observable.interval(1, TimeUnit.SECONDS)
//                .doOnDispose({
//                    LogUtils.info("Unsubscribing subscription from onCreate()")
//                })
//                .compose(bindUntilEvent(ActivityEvent.DESTROY))
//                .subscribe {
//                    LogUtils.info("Started in onCreate(), running until in onDestroy()")
//                }
//

        TestModel().getUserInfo(HashMap(), object : ResponseCallBack<String> {
            override fun onSuccess(t: String) {
                LogUtils.info("$t")
            }

            override fun onFail(e: BaseException?) {
                LogUtils.info("${e?.message}")
            }
        })

//        mTestModel.onTest()

    }


}