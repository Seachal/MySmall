package com.laka.libnet.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.laka.libnet.R
import com.laka.libnet.utils.GsonUtils
import com.laka.libutils.LogUtils

/**
 * @Author:summer
 * @Date:2019/7/16
 * @Description:
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn1 -> {
                onTestGson()
            }
        }
    }

    private fun onTestGson() {
        val fromList1 = GsonUtils.getDefaultGson().fromJson("{\"name\":\"xhh\",\"list\":[null,{\"price\":10.0}]}", TestObject::class.java)
        LogUtils.info("fromList1-----$fromList1")
    }


}