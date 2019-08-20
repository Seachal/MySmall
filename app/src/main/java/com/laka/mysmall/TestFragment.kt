package com.laka.mysmall

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @Author:summer
 * @Date:2019/8/5
 * @Description:
 */
class TestFragment : Fragment() {

    companion object {
        fun newInstance(word: String): TestFragment {
            val bundle = Bundle()
            bundle.putString("word", word)
            var fragment = TestFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_test, container, false)
        val tv = rootView.findViewById<TextView>(R.id.tv_test)
        tv.text = "${arguments?.get("word")}"
        return rootView
    }

}