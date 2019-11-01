package com.laka.appkotlin.app

import android.app.Application
import com.laka.libutils.app.ApplicationUtils

class KotlinApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        ApplicationUtils.init(this)
    }
}