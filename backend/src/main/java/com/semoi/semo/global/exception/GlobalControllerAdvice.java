package com.semoi.semo.global.exception;

import com.semoi.semo.global.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 차현철
@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(SemoException.class)
    public ResponseEntity<?> semoException(final SemoException e) {
        log.error("Error occur: {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().name()));

    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> runtimeException(Exception e) {
        log.error("Error occur: {}", e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpStatus.INTERNAL_SERVER_ERROR.name());
    }
}
