package com.laka.libnet.mvp;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.subjects.BehaviorSubject;

/**
 * @Author:summer
 * @Date:2019/7/19
 * @Description:
 */
public interface ILifecycle<R> extends LifecycleProvider<R> {

    BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
