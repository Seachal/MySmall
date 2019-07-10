package com.laka.mysmall

import android.app.Application
import net.wequick.small.Small

/**
 * @Author:summer
 * @Date:2019/7/10
 * @Description:
 */
class SmallApp : Application {

    /**
     * 由于 contentProvider 是在 application onCreate之前被调用的，所以为了兼容这个组件，Small 的初始化要在 onCreate 前进行，
     * 所以提前到构建方法中进行，如果不用兼容该组件，可以放在 onCreate 中进行
     * */
    constructor() : super() {
        Small.preSetUp(this)
    }

}