package com.sczy.common.app;

import android.app.Application;
import android.content.Context;

/**
 * @author SC16004984
 * @date 2018/4/12.
 */

public interface IAppLifecycle {
    void onCreate(Application application);
    void attachBaseContext(Context context);
    void onTerminate(Application application);
}
