package com.videoboard.boot.handler;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BCryptHandler {
	
	public String encrypt(String pwd) {
		return BCrypt.hashpw(pwd, BCrypt.gensalt());
	}
	
	public boolean jsMatch(String pwd, String hashedPwd) {
		return BCrypt.checkpw(pwd, hashedPwd);
	}
}
