package com.laka.mysmall

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * @Author:summer
 * @Date:2019/7/27
 * @Description:
 */
class TestActivity : AppCompatActivity() {

    private var list = ArrayList<Fragment>()
    private lateinit var mAdapter: TestPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        Handler().postDelayed({
            initViewPager()
        }, 1000)
        btn1.tag = 4
        initEvent()
        initTest()
    }

    private fun initTest() {
        repeat(100_000) { // 启动十万个协程试试
            runBlocking{
                //suspendPrint()
            }
        }
        Thread.sleep(1200) // 等待协程代码的结束
    }

    private suspend fun suspendPrint() {
        delay(1000)
        println(".")
    }

    suspend fun suspendTest(){
        delay(1000)
        println("_")
    }

    private fun initViewPager() {
        list.add(TestFragment.newInstance("测试测试1"))
        list.add(TestFragment.newInstance("测试测试2"))
        list.add(TestFragment.newInstance("测试测试3"))
        mAdapter = TestPagerAdapter(supportFragmentManager, list)
        view_pager.adapter = mAdapter
    }

    private fun initEvent() {
        btn1.setOnClickListener {
            list.add(TestFragment.newInstance("测试测试${it.tag}"))
            it.tag = it.tag as Int + 1
            mAdapter.notifyDataSetChanged()
        }
        btn2.setOnClickListener {
            list.removeAt(list.lastIndex)
            mAdapter.notifyDataSetChanged()
        }
    }

}