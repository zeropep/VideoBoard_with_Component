package com.videoboard.boot.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.videoboard.boot.domain.AuthDTO;
import com.videoboard.boot.enums.ErrorCode;
import com.videoboard.boot.handler.ApiResponse;
import com.videoboard.boot.handler.ResponseMap;
import com.videoboard.boot.security.JwtTokenGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final JwtTokenGenerator jwtTokenGenerator;
	private final AuthenticationManager authenticationManager;
	// AuthenticationManager
	// 사용자 아이디,비번 인증정보 인증을 처리하는 곳
	// 1. 요청에 담긴 authentication 인자를 받아 
	// 2. AuthenticationProvider에서 인증이 유효한지 확인한 후
	// 3. 유효한 인증이면 Authentication 객체를 리턴
	
	// Authentication 객체?
	// principal, credentials, grantedAuthorities 로 구성
	// principal: UserDetailService를 상속받아 만들어진 유저객체 (여기서는 MemberVO)
	// credentials: 패스워드같이 principal의 자격을 증명할 때 쓰이는 정보. 보통 인증이 끝나면 삭제된다.
	// grantedAuthorities: 현재 principal이 가지고있는 허가 또는 권한
	
	public ApiResponse login(AuthDTO.LoginDTO loginDTO) {
		ResponseMap result = new ResponseMap();
		
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPwd()));
//			log.info(authentication.toString());
//			log.info(authentication.getAuthorities().toString());
//			log.info(authentication.getPrincipal().toString());
//			log.info(Boolean.toString(authentication.isAuthenticated()));
			
			Map<String, String> generateToken = generateTokenReturn(loginDTO);
			result.setResponseData("accessToken", generateToken.get("accessToken"));
			result.setResponseData("refreshToken", generateToken.get("refreshToken"));
		} catch (Exception e) {
			e.printStackTrace();
//			throw new AuthenticationException(ErrorCode.UsernameOrPasswordNotFoundException);
		}
		
		return result;
	}
	
	public ApiResponse newAccessToken(HttpServletRequest request) {
		ResponseMap result = new ResponseMap();
		String refreshToken = jwtTokenGenerator.resolveRefreshToken(request);
		// access토큰 만료, refresh 살아있음
		if (jwtTokenGenerator.TokenValidation(request, refreshToken)) {
			String email = jwtTokenGenerator.getUserInfoRefresh(refreshToken);
			AuthDTO.LoginDTO loginDTO = new AuthDTO.LoginDTO();
			loginDTO.setEmail(email);
			
			Map<String, String> generateToken = generateTokenReturn(loginDTO);
			result.setResponseData("accessToken", generateToken.get("accessToken"));
			result.setResponseData("refreshToken", generateToken.get("refreshToken"));
		} else {
			// refresh도 만료
			result.setResponseData("code", ErrorCode.ReLogin.getCode());
			result.setResponseData("message", ErrorCode.ReLogin.getMessage());
			result.setResponseData("HttpStatus", ErrorCode.ReLogin.getStatus());
		}
		return result;
	}

	private Map<String, String> generateTokenReturn(AuthDTO.LoginDTO loginDTO) {
		Map<String, String> result = new HashMap<>();
		
		result.put("accessToken", jwtTokenGenerator.generateToken(loginDTO));
		result.put("refreshToken", jwtTokenGenerator.generateRefreshToken(loginDTO));
		
		return result;
	}
}
