package com.sczy.common;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.sczy.common.base.BaseActivity;
import com.sczy.common.widget.DefaultWebSetting;

/**
 * @author SC16004984
 * @date 2018/5/28 0028.
 */
@Route(path = "/web/site")
public class WebActivity extends BaseActivity {
    private FrameLayout mContainer;
    @Autowired(name = "url")
    String url;
    @Autowired(name = "title")
    String title;
    private AgentWeb mAgentWeb;


    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }

        @Override
        public void onReceivedTitle(WebView view, String titleText) {
            super.onReceivedTitle(view, titleText);
            if (TextUtils.isEmpty(title)) {
                setTitle(titleText);
            }
        }
    };



    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        if (title != null) {
            setTitle(title);
        }
        mContainer = findViewById(R.id.web_container);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mContainer, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .setAgentWebWebSettings(new DefaultWebSetting() )
                .interceptUnkownUrl()
                .setWebChromeClient(mWebChromeClient)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .createAgentWeb()
                .ready()
                .go(url);

    }

    @Override
    protected void initData() {

    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return new MvpBasePresenter();
    }


    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.back()) {
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
