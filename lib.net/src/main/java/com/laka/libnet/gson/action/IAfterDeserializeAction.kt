package com.laka.libnet.gson.action

/**
 * @Author:summer
 * @Date:2019/7/16
 * @Description:解析后预处理数据（比如：name 为空时，显示为 “匿名”）
 */
interface IAfterDeserializeAction {
    fun doAfterDeserialize()
}