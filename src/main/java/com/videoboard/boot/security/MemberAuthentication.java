package com.videoboard.boot.security;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberAuthentication extends UsernamePasswordAuthenticationToken{
	
	public MemberAuthentication(String principal, String credentials) {
		super(principal, credentials);
	}
	
	public MemberAuthentication(String principal, String credentials, List<GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}
}
