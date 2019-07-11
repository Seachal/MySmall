package com.laka.appmain

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.laka.appglide.GlideActivity

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
            else -> {

            }
        }
    }
}