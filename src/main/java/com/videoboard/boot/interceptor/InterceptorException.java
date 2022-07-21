package com.videoboard.boot.interceptor;

import com.videoboard.boot.enums.InterceptorErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InterceptorException extends RuntimeException {
	private InterceptorErrorCode error;
	
	public InterceptorException(InterceptorErrorCode error) {
		super(error.getMessage());
		this.error = error;
	}

}
