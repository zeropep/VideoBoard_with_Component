package com.videoboard.boot.handler;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class ApiResponse {
	
	private int code = HttpStatus.OK.value();
	private Object result;
	
	public ApiResponse() {}
	
	public ApiResponse(int code, Object result) {
		this.code = code;
		this.result = result;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}
}
