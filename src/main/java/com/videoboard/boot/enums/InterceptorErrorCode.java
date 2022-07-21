package com.videoboard.boot.enums;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
public enum InterceptorErrorCode {
    // RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    // ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"),
    // INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 402, "적절한 사용자가 아닙니다."),;


    private final HttpStatus status;
    private final int code;
    private String message;

    InterceptorErrorCode(HttpStatus status, int code) {
        this.status = status;
        this.code = code;
    }

    InterceptorErrorCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
