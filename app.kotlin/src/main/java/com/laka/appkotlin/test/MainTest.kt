package com.laka.appkotlin.test

import com.laka.appkotlin.dsl.cosver
import com.laka.appkotlin.dsl.customToString

/**
 * @Author:summer
 * @Date:2019/10/11
 * @Description:
 */
class MainTest {


    fun main() {
        val str = "1234"
        val result = str.cosver {
            this
            ""
        }

        str.customToString()


    }


    suspend fun doSomething(content:String):String{

        return "23"
    }

}