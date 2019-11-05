package com.laka.appkotlin.dsl

import android.animation.AnimatorSet
import android.text.TextUtils

/**
 * @Author:summer
 * @Date:2019/10/11
 * @Description:扩张函数测试
 */

inline fun String.test() {
    println(this)
}

inline fun <T> List<T>.cosver(): T {
    return this[0]
}

inline fun <T> List<T>.cosver(block: T.() -> Unit): T {
    return this[0]
}

inline fun <T, R> T.cosver(block: T.() -> R): R {
    return this.block()
}

inline fun <T> T.customReplace(block: T.() -> Unit) {
    block()
}

inline fun String.customToString() {
    if (TextUtils.isEmpty(this)) {
        return
    }
    println(this)
}

// inline：内联函数  ， String.()：由String进行调用的 lambda 表达式
// 内联函数：编译的时候，直接在调用点生成内联函数的代码
inline fun test3(block: String.() -> Unit) {
    "".block() //或者 block("")
}

// infix：中缀符号，用来修饰函数后，可以通过中缀的方式调用，如："A" sha "B"
infix fun String.sha(arg: String) {

}

inline fun test4(block: (String) -> Unit) {

}


fun animSet(block: AnimatorSet.() -> Unit) {
    block.invoke(AnimatorSet())
}

fun <T> T.append(block: T.() -> Unit): T {
    block.invoke(this)
    return this
}






















