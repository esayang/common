package com.sczy.common.base;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author SC16004984
 * @date 2018/2/23.
 */

public class BasePresenter<V extends IView> extends MvpBasePresenter<V> implements IPresenter<V> {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void add(Disposable disposable){
        compositeDisposable.add(disposable);
    }

    protected void cancel(){
        compositeDisposable.dispose();
    }

    @Override
    public void destroy() {
        super.destroy();
        if (compositeDisposable != null){
            compositeDisposable.dispose();
        }
    }
}
