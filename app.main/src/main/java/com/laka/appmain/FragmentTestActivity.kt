package com.laka.appmain

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.laka.appmain.fragment.ListFragment
import com.laka.appmain.fragment.ListFragmentViewPager
import kotlinx.android.synthetic.main.activity_fragment_test.*

/**
 * @Author:summer
 * @Date:2019/8/8
 * @Description:
 */
class FragmentTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)
        initViewPager()
        initEvent()
    }

    private var mClickTime = 0

    private fun initEvent() {
        btn1.setOnClickListener {
            mClickTime++
            mFragmentList.clear()
            mTitleList.clear()
            for (i in 0 until 5) {
                mTitleList.add("测试Fragment----$i----$mClickTime")
                mFragmentList.add(ListFragment.getInstance(mTitleList[i]))
            }
            if (::mAdapter.isInitialized) {
                mAdapter.setFragments(mFragmentList, mTitleList)
                view_pager.currentItem = 0
            }
        }

    }

    private lateinit var mAdapter: ListFragmentViewPager
    private var mFragmentList = ArrayList<ListFragment>()
    private var mTitleList = ArrayList<String>()

    private fun initViewPager() {
        for (i in 0 until 5) {
            mTitleList.add("测试Fragment----$i")
            mFragmentList.add(ListFragment.getInstance(mTitleList[i]))
        }
        if (!::mAdapter.isInitialized) {
            mAdapter = ListFragmentViewPager(supportFragmentManager, mFragmentList, mTitleList)
        }
        view_pager.adapter = mAdapter
    }
}