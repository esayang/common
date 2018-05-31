package com.sczy.common.app;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SC16004984
 * @date 2018/4/12.
 *
 * Application 组件的代理类
 * 组件中只需在继承{@link IAppLifecycle} 并在其相应的生命周期做相应的逻辑处理
 * 同时想要{@link IAppLifecycle}继承类在Application相应的生命周期调用，需要在文件AndroidManifest.xml
 * 中添加<meta-data android:name="包名.{@link IAppLifecycle}继承类" android:value="IModuleConfig"/>
 * 即可。
 */
public class AppDelegate implements IAppLifecycle{

    private List<IModuleConfig> list;
    private List<IAppLifecycle> appLifecycles;

    public AppDelegate() {
        appLifecycles = new ArrayList<>();
    }

    /**
     * 初始化Manifest文件解析器，用于解析组件在自己的Manifest文件配置的Application
     * @param base
     */
    @Override
    public void attachBaseContext(Context base) {
        ManifestParser manifestParser = new ManifestParser(base);
        list = manifestParser.parse();
        /**
         *  解析得到的组件Application列表之后，给每个组件Application注入
         context，和Application的生命周期的回调，用于实现application的同步
         */
        if (list != null && list.size() > 0) {
            for (IModuleConfig configModule : list) {
                configModule.injectAppLifecycle(base, appLifecycles);
            }
        }
        if (appLifecycles != null && appLifecycles.size() > 0) {
            for (IAppLifecycle life : appLifecycles) {
                life.attachBaseContext(base);
            }
        }
    }

    /**
     * 相应调用组件Application代理类的onCreate方法
     * @param application
     */
    @Override
    public void onCreate(Application application) {
        if (appLifecycles != null && appLifecycles.size() > 0) {
            for (IAppLifecycle life : appLifecycles) {
                life.onCreate(application);
            }
        }
    }

    /**
     * 相应调用组件Application代理类的onTerminate方法
     * @param application
     */
    @Override
    public void onTerminate(Application application) {
        if (appLifecycles != null && appLifecycles.size() > 0) {
            for (IAppLifecycle life : appLifecycles) {
                life.onTerminate(application);
            }
        }
    }
}
