package com.videoboard.boot.interceptor;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
public class InterceptorExceptionEntity {
	private int errorCode;
	private String errorMessage;
	
	@Builder
	public InterceptorExceptionEntity(HttpStatus status, int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
