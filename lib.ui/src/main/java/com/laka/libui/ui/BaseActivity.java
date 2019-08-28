package com.laka.libui.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laka.libui.R;
import com.laka.libui.dialog.CommonLoadingDialog;
import com.laka.libui.presenter.IPresenter;
import com.laka.libui.ui.network.NetworkUtils;
import com.laka.libui.ui.observer.NetworkChangeObserver;

public abstract class BaseActivity extends AppCompatActivity implements IView, NetworkChangeObserver.NetworkChangeListener {

    private static final int STATE_DEFAULE = 1000;
    private static final int STATE_SHOW_EMPTRY = 1001;
    private static final int STATE_SHOW_ERROR = 1002;
    private static final int STATE_SHOW_CONTENT = 1003;
    private int mShowState = STATE_DEFAULE;
    protected final String TAG = this.getClass().getSimpleName();
    private ViewGroup mRootView;
    private ViewGroup mViewContainer;
    private View mLayoutEmptry;
    private View mLayoutNetError;
    private View mLayoutMainContent;
    private CommonLoadingDialog mCommonLoadingDialog;
    private IPresenter mPresenter;
    private boolean mIsLostInternet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mRootView = findViewById(R.id.root_view);
        if (getLayoutId() != -1) {
            LayoutInflater.from(this).inflate(getLayoutId(), mRootView, true);
        }

        // view_container ：存放主要内容布局的View，包括加载失败、数据为空等界面
        mViewContainer = findViewById(R.id.view_container);
        if (mViewContainer != null && isShowMultipleState()) {
            mLayoutMainContent = findViewById(R.id.main_container);
            mLayoutEmptry = LayoutInflater.from(this).inflate(getEmptryLayoutId(), mViewContainer, false);
            mLayoutNetError = LayoutInflater.from(this).inflate(getNetErrorLayoutId(), mViewContainer, false);
            mViewContainer.addView(mLayoutEmptry);
            mViewContainer.addView(mLayoutNetError);
            mLayoutEmptry.setVisibility(View.GONE);
            mLayoutNetError.setVisibility(View.GONE);
            initLayoutEvent();
        }
        if (useEvent()) {
            // EventBus.getDefault().register();
        }
        if (isRegisterNetworkOserver()) {
            registerNetworkObserver();
        }
        initView();
        initEvent();
        loadData();
    }

    private void initLayoutEvent() {
        mLayoutNetError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoad();
            }
        });
        mLayoutEmptry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoad();
            }
        });
    }

    private int getNetErrorLayoutId() {
        return R.layout.layout_net_error;
    }

    private int getEmptryLayoutId() {
        return R.layout.layout_emptry;
    }

    private boolean isRegisterNetworkOserver() {
        return true;
    }

    @Override
    public void showLoading() {
        if (isFinishing()) {
            return;
        }
        if (mCommonLoadingDialog == null) {
            mCommonLoadingDialog = new CommonLoadingDialog(this);
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

    /**
     * 注册网络监听器
     */
    private void registerNetworkObserver() {
        NetworkChangeObserver.getInstance().registListener(this, this);
    }

    /**
     *
     */
    private void unRegisterNetworkObserver() {
        NetworkChangeObserver.getInstance().unRegistListener(this, this);
    }

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
            showErrorView();
        }
    }

    /**
     * 显示 统一的 消息提示框（不适用Toast的时候）
     *
     * @param message
     */
    @Override
    public void showMessage(@NonNull String message) {

    }

    /**
     * 网络异常、数据为空等情况下再次触发加载
     */
    protected void reLoad() {

    }

    /**
     * 该界面是否为多状态显示（如：显示网络异常界面、数据为空界面等）
     *
     * @return
     */
    protected boolean isShowMultipleState() {
        return true;
    }

    /**
     * 是否使用Eventbug，true：使用，则进行注册 false：则不使用
     *
     * @return
     */
    protected boolean useEvent() {
        return true;
    }

    /**
     * 初始化控件事件
     */
    protected abstract void initEvent();

    /**
     * 初始化UI控件
     */
    protected abstract void initView();

    /**
     * 返回布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEvent()) {

        }

        if (mPresenter != null) {

        }

        if (isRegisterNetworkOserver()) {
            unRegisterNetworkObserver();
        }
    }
}
