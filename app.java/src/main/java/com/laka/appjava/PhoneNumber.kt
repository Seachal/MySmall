package com.laka.appjava

/**
 * @Author:summer
 * @Date:2019/8/28
 * @Description:
 */
class PhoneNumber : Cloneable {

    var number: String = "13570594365"

    var list: ArrayList<String> = arrayListOf("1", "2")

    /**
     * clone() 方法存在于Object中，但是如果想在子类中重写clone方法，
     * 则必须实现Cloneable 接口，以此来标志这个类是可以被clone的
     *
     * 单纯使用super.clone() 来进行克隆对象还是远远不够的，例如上面的number 和 list属性，
     * number 是基本数据类型，所以克隆是进行值传递，list是应用类型，克隆使用了引用传递，
     * 这样克隆出来的新对象跟原来的对象持有同一个list 的应用，其中一个更改list对象将会应用另外一个对象
     * 这时就需要我们在clone（） 中做点什么了
     *
     * 修改后的 clone 方法如下
     * */
    //    override fun clone(): PhoneNumber {
    //        try {
    //            return super.clone() as PhoneNumber
    //        } catch (e: Exception) {
    //            throw RuntimeException("克隆错误")
    //        }
    //    }

    override fun clone(): PhoneNumber {
        try {
            val phoneNumber = super.clone() as PhoneNumber
            phoneNumber.list = list.clone() as ArrayList<String>
            return phoneNumber
        } catch (e: Exception) {
            throw RuntimeException("克隆错误")
        }
    }

}