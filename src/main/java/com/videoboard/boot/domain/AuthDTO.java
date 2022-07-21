package com.videoboard.boot.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class AuthDTO {
	
	@Getter @Setter @ToString
	public static class LoginDTO {
		private String email;
		private String pwd;
		private String role;
	}
}
