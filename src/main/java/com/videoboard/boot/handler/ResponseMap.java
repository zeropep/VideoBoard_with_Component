package com.videoboard.boot.handler;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseMap extends ApiResponse {
	
	private Map<String, Object> responseData = new HashMap<>();
	
	public ResponseMap() {
		setResult(responseData);
	}
	
	public void setResponseData(String key, Object value) {
		this.responseData.put(key, value);
	}
}
