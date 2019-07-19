package com.laka.libnet.mvp.model;

import android.support.annotation.NonNull;

import com.laka.libnet.mvp.ILifecycle;
import com.laka.libnet.rx.scriber.RxSchedulerComposer;
import com.laka.libnet.rx.scriber.RxSubscriber;
import com.laka.libnet.rx.callback.ResponseCallBack;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:
 */
public abstract class BaseModel implements ILifecycle<ActivityEvent> {

    public <T> void doBaseRequest(Observable<T> observable, ResponseCallBack<T> callBack) {
        observable.compose(RxSchedulerComposer.normalSchedulersTransformer())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new RxSubscriber(callBack));
    }

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
    }

    @Override
    public void onStart() {
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    public void onResume() {
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
    }
}
