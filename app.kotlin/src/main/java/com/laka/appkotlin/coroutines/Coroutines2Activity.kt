package com.laka.appkotlin.coroutines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.laka.appkotlin.R
import com.laka.libutils.LogUtils
import kotlinx.coroutines.*

// CoroutineScope by MainScope() ：kotlin 的委托模式
class Coroutines2Activity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines2)
        testCoroutine()
    }

    //协程测试
    private fun testCoroutine() {
        //因为使用了委托 MainScope() ，所以可以直接调用 launch
        launch {
            onHandleStart()

            caroline1()

            onHandleEnd()
        }
    }

    private fun caroline1() {
        LogUtils.info("coroutine-----委托策略开始协程")
    }

    private suspend fun onHandleStart() = withContext(Dispatchers.IO) {
        Thread.sleep(1000)
        LogUtils.info("coroutine-----协程开始")
    }

    private suspend fun onHandleEnd() = withContext(Dispatchers.IO) {
        Thread.sleep(1000)
        LogUtils.info("coroutine-----协程结束")
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}