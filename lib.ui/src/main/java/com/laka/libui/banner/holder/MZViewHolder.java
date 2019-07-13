package com.laka.libui.banner.holder;

import android.content.Context;
import android.view.View;

/**
 * @Author:summer
 * @Date:2019/7/13
 * @Description:
 */
public interface MZViewHolder<T> {
    /**
     *  创建View
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * 绑定数据
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context, int position, T data);
}
