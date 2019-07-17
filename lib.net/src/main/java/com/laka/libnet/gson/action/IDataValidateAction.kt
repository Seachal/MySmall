package com.laka.libnet.gson.action

/**
 * @Author:summer
 * @Date:2019/7/16
 * @Description:校验数据合法性Action，如果不合法，则视为无效数据，直接丢弃
 */
interface IDataValidateAction {
    //数据是否合法
    fun isDataValid():Boolean
}