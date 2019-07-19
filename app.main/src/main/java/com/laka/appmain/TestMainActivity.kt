package com.laka.appmain

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.laka.libui.imageload.ImageLoader
import com.laka.libutils.app.ApplicationUtils
import kotlinx.android.synthetic.main.activity_main_test.*

/**
 * @Author:summer
 * @Date:2019/7/17
 * @Description:
 */
class TestMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn1 -> {
                val imageUrl = "http://ww3.sinaimg.cn/large/7a8aed7bgw1eswencfur6j20hq0qodhs.jpg"
                ImageLoader.displayBlurImage(this, imageUrl, iv_test)
            }
        }
    }

}