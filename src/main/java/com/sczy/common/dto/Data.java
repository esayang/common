package com.sczy.common.dto;

/**
 * @author SC16004984
 * @date 2018/5/23 0023.
 */

/**
 * 用于rxjava2 避免null 值无法传递
 * @param <T>
 */
public class Data<T> {
    private T data;

    public  Data(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

}
