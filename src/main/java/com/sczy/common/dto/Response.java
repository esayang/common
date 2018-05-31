package com.sczy.common.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SC16004984 on 2018/2/7.
 */

public class Response<T> {
    private boolean status;
    private String message;
    private T data;
    @SerializedName(value = "pagecount")
    private int totalPage;
    private int pageSize;
    private int code;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data<T> getData() {
        return new Data(data);
    }

    public void setData(T data) {
        this.data = data;
    }


    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
