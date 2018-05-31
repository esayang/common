package com.sczy.common.http;

import com.google.gson.JsonParseException;
import com.sczy.common.constant.Constant;
import com.sczy.common.exception.ApiException;
import com.sczy.common.exception.ServerException;
import com.sczy.common.exception.TokenInvalidException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * Created by SC16004984 on 2018/2/8.
 */

public class ExceptionEngine {

    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        ApiException ex = null;
        while (e != null) {
            if (e instanceof HttpException){             //HTTP错误
                HttpException httpException = (HttpException) e;
                ex = new ApiException(e, ErrorType.HTTP_ERROR);
                switch(httpException.code()){
                    case FORBIDDEN:
                        ex.message = "服务器拒绝该请求";
                        break;
                    case NOT_FOUND:
                        ex.message = "请求资源不存在";
                        break;
                    case REQUEST_TIMEOUT:
                        ex.message = "请求超时";
                        break;
                    case GATEWAY_TIMEOUT:
                        ex.message = "网关连接超时";
                    case INTERNAL_SERVER_ERROR:
                        ex.message = "对不起，服务器处理请求出现异常";
                        break;
                    case BAD_GATEWAY:
                    case SERVICE_UNAVAILABLE:
                        ex.message = "服务器当前无法处理请求";
                        break;
                    default:
                        ex.message = Constant.HTTP_ERROR_MESSAGE;  //均视为网络错误
                        break;
                }
            } else if (e instanceof ServerException){    //服务器返回的错误
                ServerException resultException = (ServerException) e;
                ex = new ApiException(resultException, ErrorType.SERVER_ERROR);
                ex.message = resultException.getMessage();
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException){
                ex = new ApiException(e, ErrorType.PARSE_ERROR);
                ex.message = Constant.DATA_PARSE_ERROR;
                break;//均视为解析错误
            }else if (e instanceof SocketTimeoutException){
                ex = new ApiException(e,ErrorType.HTTP_ERROR);
                ex.message = Constant.HTTP_TIMEOUT_MESSAGE;
                break;
            }
            else if(e instanceof ConnectException ||e instanceof SocketException){
                ex = new ApiException(e, ErrorType.CONNECT_ERROR);
                ex.message = Constant.HTTP_CONNECT_MESSAGE;  //均视为网络错误
                break;
            }else if (e instanceof TokenInvalidException){
                ex = new ApiException(e,ErrorType.TOKEN_UNVAILD_ERROR);
                ex.message = Constant.TOKEN_UNVALID_MESSAGE;
                break;
            }if (e instanceof UnknownHostException || e instanceof UnknownServiceException
                    ||e instanceof URISyntaxException || e instanceof ProtocolException ){
                ex = new ApiException(e,ErrorType.HTTP_ERROR);
                ex.message = Constant.HTTP_CONNECT_MESSAGE;
                break;
            }
            e = e.getCause();
        }
        if (ex == null){
            ex = new ApiException(e, ErrorType.UNKNOWN);
            ex.message = Constant.HTTP_ERROR_MESSAGE;          //未知错误
        }
        return ex;
    }
}
