package com.laka.appmain

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
        //解决lib总是走 release 构建方式的方案1：
        //所有模块都会依赖 lib.utils ，所以判断构建方式，统一用 ApplicationUtils 来判断
        ApplicationUtils.init(this)
        ApplicationUtils.initDebug(BuildConfig.DEBUG)
    }

}