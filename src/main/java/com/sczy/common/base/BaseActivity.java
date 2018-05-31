package com.sczy.common.base;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.systembar.SystemBarHelper;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.jakewharton.rxbinding2.view.RxView;
import com.sczy.common.R;
import com.sczy.common.http.ErrorType;
import com.sczy.common.util.SweetToast;
import com.sczy.common.widget.LoadingDialog;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * @author SC16004984
 * @date 2018/3/6.
 */

public abstract class BaseActivity<V extends IView,P extends IPresenter<V>> extends MvpActivity<V,P> implements IView {

    private LoadingDialog loadingDialog;
    private final LifecycleProvider<Lifecycle.Event> provider
            = AndroidLifecycle.createLifecycleProvider(this);
    private TextView title;
    private TextView mActionBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        SystemBarHelper.immersiveStatusBar(this);
        setContentView(getLayoutId());

        initTitleBar();

        initView();
        setViewListener();
        initData();

    }

    /**
     * activity 布局
     * @return  Layout ID 
     */
    @NonNull
    protected abstract @LayoutRes int getLayoutId();

    /**
     * 视图 初始化
     */
    protected abstract void initView();

    /**
     * 添加 监听、回调等
     */

    protected void setViewListener(){

    }

    /**
     * 初始化数据
     */
    protected abstract void initData();


    protected void initTitleBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar == null){
            return;
        }
        SystemBarHelper.setHeightAndPadding(this,toolbar);
        title = findViewById(R.id.tv_title);
        title.setSelected(true);
        mActionBtn = findViewById(R.id.tv_operation);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void setTitle(@StringRes int resId){
        if (title != null){
            title.setText(getResources().getString(resId));
        }
    }

    @Override
    public void setTitle(CharSequence resStr){
        if (title != null){
            title.setText(resStr);
        }
    }

    public void setBackVisible(boolean visible){
        if (title != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        }
    }


    public Observable<Object> addAction(CharSequence action){
        if (mActionBtn != null){
            mActionBtn.setVisibility(View.VISIBLE);
            mActionBtn.setText(action);
            return RxView.clicks(mActionBtn)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .compose(bindLifeEvent());
        }
        return Observable.empty();
    }

    public Observable<Object> addAction(@DrawableRes int resId){
        if (mActionBtn != null){
            mActionBtn.setVisibility(View.VISIBLE);
            mActionBtn.setBackgroundResource(resId);
            return RxView.clicks(mActionBtn)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .compose(bindLifeEvent());
        }
        return Observable.empty();
    }

    public TextView getActionView(){
        return mActionBtn;
    }


    @Override
    public void showLoadingDialog() {
        if (loadingDialog == null){
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCancelable(false);
        }
        if (loadingDialog.isShowing()){
            return;
        }
        loadingDialog.show();
    }

    @Override
    public void cancelLoadingDialog() {
        if (loadingDialog == null){
            return;
        }
        if (loadingDialog.isShowing()){
            loadingDialog.cancel();
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindLifeEvent() {
        return provider.bindToLifecycle();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @UiThread
    @Override
    public void failed(ErrorType type, String msg) {
        cancelLoadingDialog();
        if (type.equals(ErrorType.TOKEN_UNVAILD_ERROR)){
            // TODO: 2018/3/9   登录token 失效处理
        }
        if (type.equals(ErrorType.LOCAL_ERROR) || type.equals(ErrorType.SERVER_ERROR) || type.equals(ErrorType.PARSE_ERROR)){
            SweetToast.show(SweetToast.WARN,msg);
        }else {
            SweetToast.show(SweetToast.ERROR,msg);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==  android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
