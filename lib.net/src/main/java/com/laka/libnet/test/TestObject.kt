package com.laka.libnet.test

import android.text.TextUtils
import com.laka.libnet.gson.action.IDataValidateAction

/**
 * @Author:summer
 * @Date:2019/7/16
 * @Description:
 */
data class TestObject(val name: String, val list: ArrayList<apple>) : IDataValidateAction {
    override fun isDataValid(): Boolean {
        return !TextUtils.isEmpty(name)
    }
}