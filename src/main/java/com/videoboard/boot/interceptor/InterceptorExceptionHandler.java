package com.videoboard.boot.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.videoboard.boot.enums.InterceptorErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class InterceptorExceptionHandler {

	@ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<InterceptorExceptionEntity> exceptionHandler(HttpServletRequest request, final AccessDeniedException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(InterceptorErrorCode.UNAUTHORIZED.getStatus())
                .body(InterceptorExceptionEntity.builder()
                        .errorCode(InterceptorErrorCode.UNAUTHORIZED.getCode())
                        .errorMessage(e.getMessage())
                        .build());
    }
}
