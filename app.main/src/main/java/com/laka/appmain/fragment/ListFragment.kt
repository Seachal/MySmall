package com.laka.appmain.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.laka.appmain.R
import com.laka.libutils.LogUtils

/**
 * @Author:summer
 * @Date:2019/8/8
 * @Description:
 */
class ListFragment : Fragment() {

    private lateinit var mRootView: View

    companion object {
        fun getInstance(arguement: String): ListFragment {
            val bundle = Bundle()
            bundle.putString("str", arguement)
            val fragment = ListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.info("fragment--------onCreate---------")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_list, container, false)
        val tv = mRootView.findViewById<TextView>(R.id.tv_test)
        val content = arguments?.getString("str", "")
        LogUtils.info("fragment--------onCreateView---------$content")
        tv.text = content
        return mRootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.info("fragment--------onDestroyView---------")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.info("fragment--------onDestroy---------")
    }

}