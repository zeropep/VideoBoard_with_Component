package com.videoboard.boot.ctrl;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoboard.boot.domain.AuthDTO;
import com.videoboard.boot.handler.ApiResponse;
import com.videoboard.boot.service.AuthService;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

	@Autowired
	private final AuthService authService;
	
	@GetMapping("/member/list")
	public String memberList(Principal principal) {
		log.info(principal.getName());
		Map<String, String> test = new HashMap<String, String>();
//		test.put("test", "ok");
		test.put("name", principal.getName().toString());
		test.put("aaa", "aaa");
		return principal.getName().toString();
	}
	
	

	@PostMapping("/login")
	public ApiResponse login(@RequestBody @Valid AuthDTO.LoginDTO loginDTO) {
		return authService.login(loginDTO);
	}

	@PostMapping("/refreshToken")
	public ApiResponse newAccessToken(HttpServletRequest request) {
		return authService.newAccessToken(request);
	}
}
