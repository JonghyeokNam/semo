package com.semoi.semo.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

    private String resultCode;
    private T result;

    public static Response<Void> error(String errorCode) {
        return new Response<Void> (errorCode, null);
    }

    public static Response<Void> success() {
        return new Response<Void> ("SUCCESS", null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<T> ("SUCCESS", result);
    }
}
