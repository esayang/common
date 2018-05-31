package com.sczy.common.http;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sczy.common.dto.Data;
import com.sczy.common.dto.Response;
import com.sczy.common.exception.ServerException;

import org.json.JSONObject;
import org.reactivestreams.Publisher;

import eu.amirs.JSON;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by SC16004984 on 2018/2/8.
 */

public class Transforms {
    public static <T> ObservableTransformer<ResponseBody, Data<T>> trans(TypeToken<Response<T>> typeToken){
        return new ObservableTransformer<ResponseBody,  Data<T>>() {
            @Override
            public ObservableSource< Data<T>> apply(Observable<ResponseBody> responseObservable) {
                return responseObservable.map(new Function<ResponseBody,  Data<T>>() {
                    @Override
                    public  Data<T> apply(ResponseBody responseBody) throws Exception {
                        String res = responseBody.string();
                        Response<T> response = new Gson().fromJson(res,typeToken.getType());
                        if (!response.isStatus()){
                            throw new ServerException(response.getMessage());
                        }
                        return response.getData();
                    }
                }).onErrorResumeNext(new HttpResponseFunc<>());
            }
        };
    }

    public static ObservableTransformer<ResponseBody, String> message(){
        return new ObservableTransformer<ResponseBody, String>() {
            @Override
            public ObservableSource<String> apply(Observable<ResponseBody> responseObservable) {
                return responseObservable.map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(ResponseBody responseBody) throws Exception {
                        String res = responseBody.string();
                        JSON json = JSON.create(res);
                        JSONObject jsonObject = json.getJsonObject();
                        String message = jsonObject.getString("message");
                        boolean status = jsonObject.getBoolean("status");
                        if (status){
                            return message;
                        }else {
                            throw new ServerException(message);
                        }

                    }
                }).onErrorResumeNext( new Transforms.HttpResponseFunc<String>());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> switchSchedulers() {
       return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<ResponseBody,  Data<T>> transFlowable(TypeToken<Response<T>> typeToken){
        return new FlowableTransformer<ResponseBody,  Data<T>>() {
            @Override
            public Publisher< Data<T>> apply(Flowable<ResponseBody> upstream) {
                return upstream.map(new Function<ResponseBody,  Data<T>>() {
                    @Override
                    public  Data<T> apply(ResponseBody responseBody) throws Exception {
                        String res = responseBody.string();
                        Response<T> response = new Gson().fromJson(res,typeToken.getType());
                        if (!response.isStatus()){
                            throw new ServerException(response.getMessage());
                        }
                        return response.getData();
                    }
                }).onErrorResumeNext(new FlowableHttpResponseFunc< Data<T>>());

            }
        };
    }

    public static <T> FlowableTransformer<T, T> switchSchedulerFlowable() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(new Throwable(throwable));
        }
    }

    private static class FlowableHttpResponseFunc<T> implements Function<Throwable, Publisher<T>> {
        @Override
        public Publisher<T> apply(Throwable throwable) throws Exception {
            return Flowable.error(new Throwable(throwable));
        }
    }
}
