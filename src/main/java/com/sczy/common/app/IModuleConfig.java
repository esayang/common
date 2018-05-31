package com.sczy.common.app;

import android.content.Context;

import java.util.List;

/**
 * @author SC16004984
 * @date 2018/4/12.
 */

public interface IModuleConfig {
     void injectAppLifecycle(Context base, List<IAppLifecycle> iAppLifecycle);

}
