package com.laka.libui.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laka.libui.R;
import com.laka.libui.dialog.CommonLoadingDialog;
import com.laka.libui.ui.network.NetworkUtils;
import com.laka.libui.ui.observer.NetworkChangeObserver;


/**
 * Created by summer on 2018/11/23.
 * 基类 Fragment
 */

public abstract class BaseFragment extends Fragment implements IView, NetworkChangeObserver.NetworkChangeListener {

    private static final int STATE_DEFAULE = 1000;
    private static final int STATE_SHOW_EMPTRY = 1001;
    private static final int STATE_SHOW_ERROR = 1002;
    private static final int STATE_SHOW_CONTENT = 1003;
    private int mShowState = STATE_DEFAULE;
    private View mRootView;
    private boolean mIsLostInternet;
    private ViewGroup mViewContainer;
    private View mLayoutMainContent;
    private View mLayoutEmptry;
    private View mLayoutNetError;
    private CommonLoadingDialog mCommonLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // view_container ：存放主要内容布局的View，包括加载失败、数据为空等界面
        mViewContainer = mRootView.findViewById(R.id.view_container);
        if (mViewContainer != null && isShowMultipleState()) {
            mLayoutMainContent = mRootView.findViewById(R.id.main_container);
            mLayoutEmptry = LayoutInflater.from(getActivity()).inflate(getEmptryLayoutId(), (ViewGroup) mViewContainer, false);
            mLayoutNetError = LayoutInflater.from(getActivity()).inflate(getNetErrorLayoutId(), (ViewGroup) mViewContainer, false);
            mViewContainer.addView(mLayoutEmptry);
            mViewContainer.addView(mLayoutNetError);
            mLayoutEmptry.setVisibility(View.GONE);
            mLayoutNetError.setVisibility(View.GONE);
        }
        if (useEventBus()) {
            // 注册EventBus
        }
        if (isRegisterNetworkObserver()) {
            registerNetworkObserver();
        }
        initView();
        initEvent();
        loadData();
    }

    @Override
    public void showLoading() {
        if (getActivity().isFinishing()) {
            return;
        }
        if (mCommonLoadingDialog == null) {
            mCommonLoadingDialog = new CommonLoadingDialog(getActivity());
        }
        mCommonLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mCommonLoadingDialog != null) {
            mCommonLoadingDialog.dismiss();
        }
    }

    @Override
    public void showContentView() {
        if (mViewContainer == null || !isShowMultipleState() || mLayoutEmptry == null || mLayoutNetError == null || mLayoutMainContent == null)
            return;
        mLayoutEmptry.setVisibility(View.GONE);
        mLayoutNetError.setVisibility(View.GONE);
        mLayoutMainContent.setVisibility(View.VISIBLE);
        mShowState = STATE_SHOW_CONTENT;
    }

    @Override
    public void showErrorView() {
        if (mViewContainer == null || !isShowMultipleState() || mLayoutEmptry == null || mLayoutNetError == null || mLayoutMainContent == null)
            return;
        mLayoutEmptry.setVisibility(View.GONE);
        mLayoutNetError.setVisibility(View.VISIBLE);
        mLayoutMainContent.setVisibility(View.GONE);
        mShowState = STATE_SHOW_ERROR;
    }

    @Override
    public void showEmptryView() {
        if (mViewContainer == null || !isShowMultipleState() || mLayoutEmptry == null || mLayoutNetError == null || mLayoutMainContent == null)
            return;
        mLayoutEmptry.setVisibility(View.VISIBLE);
        mLayoutNetError.setVisibility(View.GONE);
        mLayoutMainContent.setVisibility(View.GONE);
        mShowState = STATE_SHOW_EMPTRY;
    }

    private int getNetErrorLayoutId() {
        return R.layout.layout_net_error;
    }

    private int getEmptryLayoutId() {
        return R.layout.layout_emptry;
    }

    private boolean isShowMultipleState() {
        return true;
    }

    protected void registerNetworkObserver() {
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isFinishing()) {
            NetworkChangeObserver.getInstance().registListener(getActivity(), this);
        }
    }

    protected void unRegisterNetworkObserver() {
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isFinishing()) {
            NetworkChangeObserver.getInstance().unRegistListener(getActivity(), this);
        }
    }

    private boolean isRegisterNetworkObserver() {
        return true;
    }

    /**
     * 网络状态监听回调方法
     *
     * @param state 当前的网络状态
     */
    @Override
    public void onNetworkChange(int state) {
        if (state == NetworkUtils.NETWORK_STATE_2G || state == NetworkUtils.NETWORK_STATE_3G
                || state == NetworkUtils.NETWORK_STATE_4G || state == NetworkUtils.NETWORK_STATE_UNKNOW
                || state == NetworkUtils.NETWORK_STATE_WIFI) {
            if (!mIsLostInternet) {
                return;
            }
            // 从无网络切换到有网络
            if (mShowState == STATE_SHOW_ERROR) {
                reLoad();
            }
        } else {
            if (mShowState != STATE_DEFAULE && mShowState != STATE_SHOW_CONTENT) {
                return;
            }
            mIsLostInternet = true;
            showErrorView(); // 无网络，直接显示错误界面
        }
    }

    private void reLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            // 解注册EventBus
        }
        if (isRegisterNetworkObserver()) {
            unRegisterNetworkObserver();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initEvent();

    protected boolean useEventBus() {
        return true;
    }


}
