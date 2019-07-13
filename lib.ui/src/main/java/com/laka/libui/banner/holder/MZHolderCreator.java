package com.laka.libui.banner.holder;

/**
 * @Author:summer
 * @Date:2019/7/13
 * @Description:
 */
public interface MZHolderCreator<VH extends MZViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
