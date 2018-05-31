package com.sczy.common.exception;


import com.sczy.common.http.ErrorType;

/**
 * Created by SC16004984 on 2018/2/8.
 */

public class ApiException extends Exception {
    public ErrorType type;
    public String message;

    public ApiException(Throwable throwable, ErrorType type) {
        super(throwable);
        this.type = type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorType getType() {
        return type;
    }
}
