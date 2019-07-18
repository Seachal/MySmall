package com.laka.appmain

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.laka.appglide.GlideActivity
import com.laka.librouter.utils.IntentUtils
import com.laka.libutils.LogUtils
import com.laka.libutils.app.ApplicationUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * @Author:summer
 * @Date:2019/7/10
 * @Description:Main Module 主入口
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn1 -> {
                startActivity(Intent(this, MessageActivity::class.java))
            }
            R.id.btn2 -> {
                startActivity(Intent(this, GlideActivity::class.java))
            }
            R.id.btn3 -> {
                val value = IntentUtils.getStringExtra(intent, "key", "defaultValue")
                LogUtils.info("value1------:$value-----:${BuildConfig.DEBUG}")
                Log.i("value", "value2------$value--------:${BuildConfig.DEBUG}")
                if (ApplicationUtils.isDebug2(this)) {
                    Logger.i("value3------:$value-----:${BuildConfig.DEBUG}")
                    Logger.i("value4------:$value-----:${BuildConfig.DEBUG}")
                }
            }
            else -> {

            }
        }
    }
}