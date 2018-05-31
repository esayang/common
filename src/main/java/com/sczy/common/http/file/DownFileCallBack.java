package com.sczy.common.http.file;

/**
 * @author SC16004984
 * @date 2018/2/23.
 */

import android.support.annotation.UiThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by miya95 on 2016/12/5.
 */
public abstract class DownFileCallBack implements Callback<ResponseBody>,FileCallback {

    private String destFileDir;
    private String destFileName;
    private Disposable disposable;


    public DownFileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    @UiThread
    public abstract void progress(long progress, long total);
    public abstract void onSuccess(File file);
    public abstract void onError(Throwable t);

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        disposable = Flowable.just(response)
                .subscribeOn(Schedulers.io())
                .map(new Function<Response<ResponseBody>, File>() {
                    @Override
                    public File apply(Response<ResponseBody> body) throws Exception {
                        return saveFile(body);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        onSuccess(file);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onError(throwable);
                    }
                });
    }


    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        if (!call.isCanceled()){
            call.cancel();
        }
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
        onError(t);
    }

    public File saveFile(Response<ResponseBody> response) throws IOException
    {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try
        {
            is = response.body().byteStream();
            final long total = response.body().contentLength();

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1)
            {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;

                progress(finalSum,total);
            }
            fos.flush();
            return file;

        } finally
        {
            try
            {
                response.body().close();
                if (is != null) is.close();
            } catch (IOException e)
            {
            }
            try
            {
                if (fos != null) fos.close();
            } catch (IOException e)
            {
            }

        }
    }


}
