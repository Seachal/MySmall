package com.laka.libui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.laka.libui.R;

/**
 * @Author:summer
 * @Date:2019/7/13
 * @Description:dialog基类
 */
public abstract class BaseDialog extends AlertDialog {

    private Window mWindow;
    // dialog 的内容对其方式
    private int mGravityType = Gravity.CENTER;
    // 设置填充模式横向铺满父控件，纵向包裹内容，这样比较方便对弹窗布局宽度的控制
    protected int mLayoutParamsWidth = WindowManager.LayoutParams.MATCH_PARENT;
    protected int mLayoutParamsHeight = WindowManager.LayoutParams.WRAP_CONTENT;

    protected BaseDialog(Context context) {
        super(context, R.style.commonDialog); //通用dialog主题
    }

    protected BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
        initEvent();
        initAnima();
        mWindow = getWindow();
        WindowManager.LayoutParams layoutParams = mWindow.getAttributes();
        layoutParams.width = mLayoutParamsWidth;
        layoutParams.height = mLayoutParamsHeight;
        mWindow.setAttributes(layoutParams);
        mWindow.setGravity(getGravityType());
    }

    protected void initAnima() {

    }

    /**
     * layout 资源 ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract void initView();

    public void setGravityType(int gravityType) {
        mGravityType = gravityType;
        if (mWindow != null) {
            mWindow.setGravity(mGravityType);
        }
    }

    public int getGravityType() {
        return mGravityType;
    }

    /**
     * 设置dialog布局的填充模式
     */
    public void setLayoutParams(int layoutParamsWidth, int layoutParamsHeight) {
        this.mLayoutParamsWidth = mLayoutParamsWidth;
        this.mLayoutParamsHeight = mLayoutParamsHeight;
        if (mWindow != null) {
            WindowManager.LayoutParams layoutParams = mWindow.getAttributes();
            layoutParams.width = mLayoutParamsWidth;
            layoutParams.height = mLayoutParamsHeight;
            mWindow.setAttributes(layoutParams);
        }
    }

}
