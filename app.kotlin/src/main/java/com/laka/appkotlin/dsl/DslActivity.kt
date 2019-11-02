package com.laka.appkotlin.dsl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.laka.appkotlin.R

class DslActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dsl)
        initData()
    }

    private fun initData() {
        initAnim()

    }

    private fun initAnim() {
        animSet {

            duration = 1500

        }
    }
}