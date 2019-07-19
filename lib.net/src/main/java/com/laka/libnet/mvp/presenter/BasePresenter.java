package com.laka.libnet.mvp.presenter;

import android.support.annotation.NonNull;

import com.laka.libnet.mvp.ILifecycle;
import com.laka.libnet.mvp.model.BaseModel;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:
 */
public abstract class BasePresenter implements ILifecycle<ActivityEvent> {

    private BaseModel mBaseModel;

    @Override
    public Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    public void onCreate() {
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        mBaseModel = createModel();
        mBaseModel.onCreate();
    }

    @Override
    public void onStart() {
        lifecycleSubject.onNext(ActivityEvent.START);
        mBaseModel.onStart();
    }

    @Override
    public void onResume() {
        lifecycleSubject.onNext(ActivityEvent.RESUME);
        mBaseModel.onResume();
    }

    @Override
    public void onPause() {
        mBaseModel.onPause();
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    @Override
    public void onStop() {
        mBaseModel.onStop();
        lifecycleSubject.onNext(ActivityEvent.STOP);
    }

    @Override
    public void onDestroy() {
        mBaseModel.onDestroy();
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
    }

    public abstract BaseModel createModel();
}
