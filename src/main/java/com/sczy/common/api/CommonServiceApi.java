package com.sczy.common.api;

import com.sczy.common.domain.User;
import com.sczy.common.dto.Response;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author SC16004984
 * @date 2018/4/27 0027.
 */

public interface CommonServiceApi {

    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String,String> map);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String,String> map);

    @Headers("Content-Type: application/json")
    @POST
    Observable<ResponseBody> postJson(@Url String url, @Body Map<String,Object> body);

    @FormUrlEncoded
    @PUT
    Observable<ResponseBody> put(@Url String url, @FieldMap Map<String,String> map);

    @Headers("Content-Type: application/json")
    @PUT
    Observable<ResponseBody> putJson(@Url String url, @Body Map<String,Object> body);

    @FormUrlEncoded
    @DELETE
    Observable<ResponseBody> delete(@Url String url,@FieldMap Map<String,String> map);

    @Headers("Content-Type: application/json")
    @DELETE
     Observable<ResponseBody> deleteJson(@Url String url, @Body Map<String,Object> body);
}
