package com.laka.appmain.app

import android.app.Application
import com.laka.libutils.app.ApplicationUtils

/**
 * @Author:summer
 * @Date:2019/7/18
 * @Description:
 */
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationUtils.init(this)
    }

}