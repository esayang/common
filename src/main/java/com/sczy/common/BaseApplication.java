package com.sczy.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.view.Gravity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.sczy.capp.push.AliPushChannel;
import com.sczy.capp.push.PushHelper;
import com.sczy.common.app.AppDelegate;
import com.sczy.common.dao.util.DaoManager;
import com.sczy.common.http.HttpManager;
import com.sczy.common.util.RealmHelper;
import com.sczy.common.util.SprfUtil;
import com.sczy.common.util.SweetToast;
import com.umeng.share.ShareHelper;

import static java.lang.System.exit;

/**
 * @author SC16004984
 * @date 2018/3/9.
 */

public class BaseApplication extends Application {

    private AppDelegate appDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openDebug();
        ARouter.openLog();
        ARouter.init(this);
        Utils.init(this);
        RealmHelper.init(this);
        ShareHelper.init(this);
        HttpManager.init(this);
        SprfUtil.init(this);
        DaoManager.init(this);
        SweetToast.init(this);


        PushHelper.getInstance().setPushChannel(new AliPushChannel(this));
        appDelegate.onCreate(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        appDelegate = new AppDelegate();
        appDelegate.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        appDelegate.onTerminate(this);
        PushHelper.getInstance().onDestroy();
        exit(0);
    }
}
