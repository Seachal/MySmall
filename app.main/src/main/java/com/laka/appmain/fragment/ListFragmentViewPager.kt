package com.laka.appmain.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup
import android.widget.Adapter

/**
 * @Author:summer
 * @Date:2019/8/8
 * @Description:
 */
class ListFragmentViewPager : FragmentPagerAdapter {

    private var mFragmentList: ArrayList<ListFragment> = ArrayList()
    private var titleList: ArrayList<String> = ArrayList()
    private var fragmentManager: FragmentManager

    constructor(fm: FragmentManager, fragmentList: ArrayList<ListFragment>, titleList: ArrayList<String>) : super(fm) {
        this.fragmentManager = fm
        this.mFragmentList.clear()
        this.mFragmentList.addAll(fragmentList)
        this.titleList.clear()
        this.titleList.addAll(titleList)
    }

    fun setFragments(fragments: ArrayList<ListFragment>, titleList: ArrayList<String>) {
        if (this.mFragmentList != null) {
            var ft = fragmentManager.beginTransaction()
            for (item in this.mFragmentList) {
                ft.remove(item)
            }
            ft.commit()
            fragmentManager.executePendingTransactions()
        }
        this.mFragmentList.clear()
        this.mFragmentList.addAll(fragments)
        this.titleList.clear()
        this.titleList.addAll(titleList)
        notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var fragment = super.instantiateItem(container, position) as Fragment
        this.fragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss()
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = mFragmentList[position]
        this.fragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss()
    }

}