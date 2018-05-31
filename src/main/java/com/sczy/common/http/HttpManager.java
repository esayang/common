package com.sczy.common.http;

import android.content.Context;

import com.sczy.common.api.BaseApi;
import com.sczy.common.api.CommonServiceApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;





/***
 *
 * POST JSON:
     Map<String,Object> params = new HashMap<>();
     params.put("name","yw");
     params.put("password","123");
     params.put("age","23");
     params.put("sex","male");

     return HttpManager.api()
     .postJson("http://10.12.247.121:8080/update",params)
     .compose(Transforms.<User>trans(new TypeToken<Response<User>>(){}))
     .compose(Transforms.<User>switchSchedulers());
 */
/**
 *
 *      UPLOAD FILE :
        File cacheDir = Utils.getApp().getCacheDir();
        final StringBuffer sb = new StringBuffer(cacheDir.getAbsolutePath())
                .append(File.separator).append(getPackageName()).append(File.separator)
                .append("download").append(File.separator).append("qq.apk");
        final File file = new File(sb.toString());
        HttpManager.file().upload("http://10.12.247.121:8080/upload",file)
                .filter(new Predicate<FileProgress>() {
                    @Override
                    public boolean test(FileProgress fileProgress) throws Exception {
                        if (!fileProgress.isStatus()){
                            return false;
                        }
                        seekBar.setProgress((int) (fileProgress.getInProgress()/fileProgress.getTotal()*100));
                        return fileProgress.isComplete();
                    }
                })
                .flatMap(new Function<FileProgress, Publisher<ResponseBody>>() {
                    @Override
                    public Flowable<ResponseBody> apply(FileProgress fileProgress) throws Exception {
                        return Flowable.just(fileProgress.getResponse());
                    }
                })
                .compose(Transforms.transFlowable(new TypeToken<Response<String>>(){}))
                .compose(Transforms.<String>switchSchedulerFlowable())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                        mTextView.setText(s);
                    }
                });
    */
/**
 * Created by SC16004984 on 2018/2/8.
 * @author SC16004984
 *
 */

public class HttpManager {
    private static Retrofit retrofit;
    private static OkHttpClient client;

    public static void init(Context application){
        client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();
    }

    public static Retrofit client(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BaseApi.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static CommonServiceApi api(){
       return client().create(CommonServiceApi.class);
    }

    public static HttpFileProvider file(){
        return HttpFileProvider.getInstance();
    }
}
