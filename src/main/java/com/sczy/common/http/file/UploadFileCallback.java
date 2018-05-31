package com.sczy.common.http.file;

import android.support.annotation.UiThread;

import com.sczy.common.http.file.FileCallback;
import com.sczy.common.http.file.FileProgressEvent;
import com.sczy.common.util.RxBus;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author SC16004984
 * @date 2018/4/13.
 */

public abstract class UploadFileCallback implements Callback<ResponseBody>,FileCallback {

    private Disposable disposable;

    public UploadFileCallback() {
        subscribeLoadProgress();
    }

    @UiThread
    public abstract void progress(long progress, long total);
    public abstract void onSuccess(ResponseBody response);
    public abstract void onError(Throwable t);


    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        call.cancel();
        onSuccess(response.body());
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        call.cancel();
        onError(t);

    }
    /**
     * 订阅加载的进度条
     */
    public void subscribeLoadProgress() {
        disposable = RxBus.getDefault().register(FileProgressEvent.class, new Consumer<FileProgressEvent>() {
            @Override
            public void accept(FileProgressEvent fileProgressEvent) throws Exception {
                progress(fileProgressEvent.getProgress(), fileProgressEvent.getTotal());
            }
        });

    }
}
