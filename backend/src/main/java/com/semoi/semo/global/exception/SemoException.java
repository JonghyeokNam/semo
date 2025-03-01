package com.semoi.semo.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 차현철
@AllArgsConstructor
@Getter
public class SemoException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public SemoException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        }
        return String.format("%s. %s", errorCode.getMessage(), message);
    }
}
