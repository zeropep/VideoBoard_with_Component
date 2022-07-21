package com.videoboard.boot.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.videoboard.boot.domain.AuthDTO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.service.CustomUserDetailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenGenerator {
	private final long JWT_ACCESS_TOKEN_DURATION = 1000L * 60 * 60; // 1시간
	private final long JWT_REFRESH_TOKEN_DURATION = 1000L * 60 * 60 * 24 * 3; // 3일

	@Value("${JWT.ACC.SECRET}")
	private static final String ACC_SECRET_KEY = "myfirstjwtsecretkey123";
	
	@Value("${JWT.REF.SECRET}")
	private static final String REF_SECRET_KEY = "myfirstrefreshkey456";
	
	@Autowired
	private final CustomUserDetailService customUserDetailService;

	// 토큰 생성
	public String generateToken(AuthDTO.LoginDTO loginDTO) {
		Map<String, Object> headers = new HashMap<>();
		headers.put("type", "token");

		Map<String, Object> claims = new HashMap<>();
		claims.put("email", loginDTO.getEmail());
		claims.put("role", loginDTO.getRole());

		Date now = new Date();
		Date expiration = new Date(now.getTime() + JWT_ACCESS_TOKEN_DURATION);

		return Jwts.builder().setHeader(headers).setClaims(claims) // 정보 저장
				.setSubject("member").setIssuedAt(now) // 토큰 발행시간
				.setExpiration(expiration) // 토큰 만료시간
				.signWith(SignatureAlgorithm.HS256, ACC_SECRET_KEY) // 암호 알고리즘 선택, 서명에 사용될 비밀키
				.compact();
	}

	//
	public String generateRefreshToken(AuthDTO.LoginDTO loginDTO) {
		Map<String, Object> headers = new HashMap<>();
		headers.put("type", "token");

		Map<String, Object> claims = new HashMap<>();
		claims.put("email", loginDTO.getEmail());
		claims.put("role", loginDTO.getRole());

		Date now = new Date();
		Date expiration = new Date(now.getTime() + JWT_REFRESH_TOKEN_DURATION);

		return Jwts.builder().setHeader(headers).setClaims(claims) // 정보 저장
				.setSubject("member").setIssuedAt(now) // 토큰 발행시간
				.setExpiration(expiration) // 토큰 만료시간
				.signWith(SignatureAlgorithm.HS256, ACC_SECRET_KEY) // 암호 알고리즘 선택, 서명에 사용될 비밀키
				.compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserInfo(token));
		log.debug(">>> userDetails", userDetails);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

	}

	public String getUserInfo(String token) {
		return (String) Jwts.parser().setSigningKey(ACC_SECRET_KEY).parseClaimsJws(token).getBody().get("email");
	}
	
	public String getUserInfoRefresh(String refreshToken) {
		return (String) Jwts.parser().setSigningKey(ACC_SECRET_KEY).parseClaimsJws(refreshToken).getBody().get("email");
	}

	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("accessToken");
	}
	
	public String resolveRefreshToken(HttpServletRequest request) {
		return request.getHeader("refreshToken");
	}

	// 토큰 유효성검사
	public boolean TokenValidation(ServletRequest request, String token) {
		try {
			Jwts.parser().setSigningKey(ACC_SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature");
			request.setAttribute("exception", "SignatureException");
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token");
			request.setAttribute("exception", "MalformedJwtException");
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token");
			request.setAttribute("exception", "ExpiredJwtException");
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token");
			request.setAttribute("exception", "UnsupportedJwtException");
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty");
			request.setAttribute("exception", "IllegalArgumentException");
		}
		return false;
	}
}
