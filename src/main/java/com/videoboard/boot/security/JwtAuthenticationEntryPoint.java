package com.videoboard.boot.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.videoboard.boot.enums.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException authException) throws IOException, ServletException {
		
		String exception = (String) request.getAttribute("exception");
		log.info("exception {}", exception);
		log.info("authException {}", authException.toString());
		ErrorCode errorCode;
		
		if (exception.equals("IllegalArgumentException") || exception.equals("MalformedJwtException")) {
			errorCode = ErrorCode.UNAUTHORIZEDException;
			setResponse(response, errorCode);
			return;
		}
		if (exception.equals("ExpiredJwtException")) {
			errorCode = ErrorCode.ExpriredJwtException;
			log.info(Integer.toString(errorCode.getCode()));
			setResponse(response, errorCode);
			return;
		}
		
	}

	private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setStatus(errorCode.getCode());
		
		json.put("code", errorCode.getCode());
		json.put("message", errorCode.getMessage());
		log.info(json.get("code").toString());
		response.getWriter().print(json);
	}
	
	
}
