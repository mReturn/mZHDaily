package com.mreturn.zhihudaily.Presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by mReturn
 * on 2017/5/17.
 */

public class BasePresenter {

    //把所有正在处理的Subscription添加到CompositeSubscription中，退出时统一注销观察
    private CompositeDisposable mCompositeDisposable;

    protected void addDisposable(Disposable subscription){
        //CompositeDisposable如果解绑了 Subscription需要新的实例 否则绑定无效
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    //统一解绑观察者 防止Rx造成内存泄漏
    public void dispose(){
        if (mCompositeDisposable != null){
            mCompositeDisposable.dispose();
        }
    }
}
