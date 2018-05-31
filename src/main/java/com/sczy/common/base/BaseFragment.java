package com.sczy.common.base;

import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.systembar.SystemBarHelper;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;
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
 * @date 2018/4/3.
 */

public abstract class BaseFragment<V extends IView,P extends IPresenter<V>> extends MvpFragment<V,P> implements IView {

    /**
     * 加载框
     */
    private AlertDialog loadingDialog;

    private boolean isFragmentVisible;

    private boolean isPrepared;
    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;
    /**
     * <pre>
     * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
     * 一般用于PagerAdapter需要刷新各个子Fragment的场景
     * 不要new 新的 PagerAdapter 而采取reset数据的方式
     * 所以要求Fragment重新走initData方法
     * </pre>
     */
    private boolean forceLoad = false;
    private final LifecycleProvider<Lifecycle.Event> provider
            = AndroidLifecycle.createLifecycleProvider(this);
    private TextView title;
    private TextView mActionBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(getLayoutId(), container, false);
        if (getHideSoftInput()) {
            setHideSoftIntPutUI(mFragmentView);
        }
        isFirstLoad = true;
        isPrepared = true;
        initView(mFragmentView);
        return mFragmentView;
    }


    protected void initTitleBar(Toolbar toolbar){
        if (toolbar == null){
            return;
        }
        SystemBarHelper.setHeightAndPadding(getContext(),toolbar);
        title = toolbar.findViewById(R.id.tv_title);
        title.setSelected(true);
        mActionBtn = toolbar.findViewById(R.id.tv_operation);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setTitle(@StringRes int resId){
        if (title != null){
            title.setText(getResources().getString(resId));
        }
    }

    public void setTitle(CharSequence resStr){
        if (title != null){
            title.setText(resStr);
        }
    }

    public void setBackVisible(boolean visible){
        if (title != null){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
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



    /**
     * 默认点击其他View 不隐藏软键盘
     *
     * @return
     */
    public boolean getHideSoftInput() {
        return false;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addViewListener();
        lazyLoad();
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     * visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    protected void onVisible() {
        isFragmentVisible = true;
        lazyLoad();
    }

    protected void onInvisible() {
        isFragmentVisible = false;
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected void lazyLoad() {
        if (isPrepared && isFragmentVisible) {
            if (forceLoad || isFirstLoad) {
                forceLoad = false;
                isFirstLoad = false;
                initData();
            }
        }
    }

    @Override
    public P createPresenter() {
        return (P) new BasePresenter<V>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
    }

    /**
     * activity 布局
     * @return  Layout ID
     */
    @NonNull
    protected abstract @LayoutRes int getLayoutId();

    /**
     * 只能做视图 初始化
     * @param mFragmentView
     */
    protected abstract void initView(View mFragmentView);

    /**
     * 添加 监听、回调等
     */

    protected void addViewListener(){

    }

    /**
     * 初始化数据
     */
    protected abstract void initData();



    @Override
    public void showLoadingDialog() {
        if (getActivity() == null){
            return;
        }
        if (loadingDialog == null){
            loadingDialog = new LoadingDialog(getContext());
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

    public Context getCurrentContext() {
        return getContext();
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

    protected void hideSoftInPut(View view) {
        if (getActivity() != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setHideSoftIntPutUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    hideSoftInPut(arg0);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setHideSoftIntPutUI(innerView);
            }
        }
    }

}
