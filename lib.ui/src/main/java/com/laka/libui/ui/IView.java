package com.laka.libui.ui;

import android.support.annotation.NonNull;

/**
 * Created by summer on 2018/11/22.
 * MVP 结构的 View 层顶级接口
 */
public interface IView {

    /**
     * 绑定 IPresenter
     */
    void bindPresenter();

    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 隐藏加载框
     */
    void hideLoading();

    /**
     * 显示数据为空界面
     */
    void showEmptryView();

    /**
     * 显示主内容界面
     */
    void showContentView();

    /**
     * 显示数据加载失败、网络异常等内容界面
     */
    void showErrorView();

    /**
     * 显示 统一的 消息提示框（不适用Toast的时候）
     *
     * @param message
     */
    void showMessage(@NonNull String message);

    /**
     * 加载数据
     */
    void loadData();
}
