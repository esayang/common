package com.sczy.common.http;


import android.content.Context;

import com.sczy.common.base.IView;
import com.sczy.common.exception.ApiException;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;

/**
 * Created by SC16004984 on 2018/2/8.
 */

public class ResultConsumer {
    public static RxErrorHandler get(IView view){
       return RxErrorHandler
                .builder()
                .with(view.getContext())
                .responseErrorListener(new ResponseErrorListener() {
                    @Override
                    public void handleResponseError(Context context, Throwable t) {
                        ApiException apiException = ExceptionEngine.handleException(t);
                        view.failed(apiException.getType(),apiException.getMessage());
                    }
                }).build();
    }

}
