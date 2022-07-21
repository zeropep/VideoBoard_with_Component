package com.videoboard.boot.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.MessageVO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.handler.BCryptHandler;
import com.videoboard.boot.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService msv;
	
	@Autowired
	private BCryptHandler bCryptHandler;
	
	@Operation(summary = "Member Controller", description = "Member CRUD")
	
	@GetMapping("/login")
	public void login(Model model, @ModelAttribute MessageVO msg) {
		model.addAttribute("sort", msg.getSort() == null ? "noMsg" : msg.getSort());
		model.addAttribute("msg", msg.getMsg() == null ? "noMsg" : msg.getMsg());
	}
	
	@GetMapping({"/register", "/list"})
	public void getLocation() {}
	
	@PostMapping("/register")
	public String register(MemberVO mvo) {
		mvo.setPwd(bCryptHandler.encrypt(mvo.getPwd()));
		msv.registerMember(mvo);
		return "redirect:/";
	}
	
	@GetMapping("/detail")
	public void detail() {
	}
	
	@GetMapping("/mywork")
	public void mywork(Model model, @CookieValue(value = "curPage", defaultValue = "1") long curPage) {
		model.addAttribute("curPage", curPage);
	}
}
