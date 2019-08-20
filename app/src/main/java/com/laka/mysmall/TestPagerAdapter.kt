package com.laka.mysmall

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @Author:summer
 * @Date:2019/8/5
 * @Description:
 */
class TestPagerAdapter : FragmentPagerAdapter {

    private var fragmentManager: FragmentManager
    private var fragmentList: ArrayList<Fragment>

    constructor(fm: FragmentManager, fragmentList: ArrayList<Fragment>) : super(fm) {
        this.fragmentManager = fm
        this.fragmentList = fragmentList
    }

    override fun getItem(position: Int): Fragment? {
        if (position >= 0 && position < fragmentList.size) {
            return fragmentList[position]
        }
        return null
    }

    override fun getCount(): Int {
        return fragmentList?.size ?: 0
    }

}