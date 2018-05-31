package com.sczy.common.http;

import com.sczy.common.api.BaseApi;
import com.sczy.common.http.file.DownFileCallBack;
import com.sczy.common.http.file.FileProgress;
import com.sczy.common.http.file.FileServiceApi;
import com.sczy.common.http.file.ProgressRequestBody;
import com.sczy.common.http.file.UploadFileCallback;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author SC16004984
 * @date 2018/4/13.
 */

 public class HttpFileProvider {

    private static HttpFileProvider instance;

    /**
     * 下载文件
     * @param filePath
     * @param fileName
     * @param url
     * @param params
     * @return
     */

    public Flowable<FileProgress> download(String filePath, String fileName, String url, Map<String, String> params){
        return Flowable.create(new FlowableOnSubscribe<FileProgress>() {
            @Override
            public void subscribe(final FlowableEmitter<FileProgress> observer) throws Exception {
                new Retrofit.Builder().baseUrl(BaseApi.BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build().create(FileServiceApi.class)
                        .download(url,params)
                        .enqueue(new DownFileCallBack(filePath, fileName) {
                            @Override
                            public void onError(Throwable t) {
                                FileProgress event = new FileProgress();
                                event.setStatus(false);
                                event.setComplete(false);
                                observer.onNext(event);
                                observer.onComplete();
                            }

                            @Override
                            public void onSuccess(File file) {
                                FileProgress event = new FileProgress();
                                event.setStatus(true);
                                event.setComplete(true);
                                event.setInProgress(1);
                                event.setTotal(1);
                                event.setFile(file);
                                observer.onNext(event);
                                observer.onComplete();
                            }

                            @Override
                            public void progress(long progress, long total) {
                                FileProgress event = new FileProgress();
                                event.setStatus(true);
                                event.setInProgress(progress);
                                event.setTotal(total);
                                event.setComplete(false);
                                observer.onNext(event);
                            }
                        });
            }
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .sample(100, TimeUnit.MILLISECONDS,true)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 上传文件
     * @param url
     * @param file
     * @return
     */
    public Flowable<FileProgress> upload(String url, File file){
        RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);

        ProgressRequestBody progressRequestBody = new ProgressRequestBody(body);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), progressRequestBody);
        return Flowable.create(new FlowableOnSubscribe<FileProgress>() {
            @Override
            public void subscribe(FlowableEmitter<FileProgress> observer) throws Exception {
                HttpManager.client().create(FileServiceApi.class)
                        .upload(url,part)
                        .enqueue(new UploadFileCallback() {
                            @Override
                            public void onError(Throwable t) {
                                FileProgress event = new FileProgress();
                                event.setStatus(false);
                                event.setComplete(false);
                                observer.onNext(event);
                                observer.onComplete();
                            }

                            @Override
                            public void onSuccess(ResponseBody responseBody) {
                                FileProgress event = new FileProgress();
                                event.setStatus(true);
                                event.setComplete(true);
                                event.setResponse(responseBody);
                                event.setInProgress(1);
                                observer.onNext(event);
                                observer.onComplete();
                            }

                            @Override
                            public void progress(long progress, long total) {
                                FileProgress event = new FileProgress();
                                event.setStatus(true);
                                event.setInProgress(progress);
                                event.setTotal(total);
                                event.setComplete(false);
                                observer.onNext(event);
                            }
                        });
            }
        },BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .sample(100, TimeUnit.MILLISECONDS,true)
                .observeOn(AndroidSchedulers.mainThread());


    }

    public static HttpFileProvider getInstance() {
        if (instance == null){
            synchronized (HttpFileProvider.class){
                if (instance == null){
                    instance = new HttpFileProvider();
                }
            }
        }
        return instance;
    }
}
