package com.sczy.common.base;

import android.content.Context;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.sczy.common.http.ErrorType;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by SC16004984 on 2018/2/8.
 */

public interface IView  extends MvpView{
    void failed(ErrorType type, String msg);
    Context getContext();
    void showLoadingDialog();
    void cancelLoadingDialog();
    <T> LifecycleTransformer<T> bindLifeEvent();
}
