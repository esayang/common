package com.sczy.common.http.file;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author SC16004984
 * @date 2018/4/13.
 */

public interface FileServiceApi {
    @Streaming
    @GET("")
    Call<ResponseBody> download (@Url String url, @QueryMap Map<String ,String> params);

    @Multipart
    @POST("")
    Call<ResponseBody> upload(@Url String url, @Part MultipartBody.Part file);

}
