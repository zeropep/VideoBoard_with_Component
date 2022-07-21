package com.videoboard.boot.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.videoboard.boot.domain.AuthDTO;
import com.videoboard.boot.domain.JobVO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.PageDTO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.handler.ApiResponse;
import com.videoboard.boot.handler.BCryptHandler;
import com.videoboard.boot.service.AuthService;
import com.videoboard.boot.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth/member")
@RequiredArgsConstructor
public class MemberRestController {
	
	@Autowired
	private final AuthService authService;
	@Autowired
	private MemberService msv;
	@Autowired
	private BCryptHandler bCryptHandler;

	@PostMapping("/login")
	public ApiResponse login(@RequestBody @Valid AuthDTO.LoginDTO loginDTO) {
		return authService.login(loginDTO);
	}

	@PostMapping("/refreshToken")
	public ApiResponse newAccessToken(HttpServletRequest request) {
		return authService.newAccessToken(request);
	}
	
	@PostMapping("/register")
	public int register(@RequestBody MemberVO mvo) {
		mvo.setPwd(bCryptHandler.encrypt(mvo.getPwd()));
		return msv.registerMember(mvo);
	}
	
	@GetMapping("/detail")
	public PageDTO getDetail(@CookieValue(value = "curPage", defaultValue = "1") int pageNo
						, MemberVO mvo) {
		PagingVO pgvo = new PagingVO(pageNo, 8);
		return msv.getMemberDetail(mvo.getUsername(), pgvo);
	}
	
	@GetMapping("/email")
	public int dupleEmail(@RequestParam(value = "email") String email) {
		return msv.emailDuple(email);
	}
	
	@GetMapping("/name")
	public int dupleName(@RequestParam(value = "username") String username) {
		return msv.usernameDuple(username);
	}
	
	@GetMapping("/mywork")
	public PageDTO mywork(@CookieValue(value = "curPage", defaultValue = "1") int pageNo, 
								MemberVO mvo) {
		PagingVO pgvo = new PagingVO(pageNo, 10);
		return msv.getJobList(mvo.getMno(), pgvo);
	}
}
