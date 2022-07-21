package com.videoboard.boot.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.videoboard.boot.domain.MessageVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home(Model model, @ModelAttribute MessageVO msg) {
		model.addAttribute("sort", msg.getSort() == null ? "noMsg" : msg.getSort());
		model.addAttribute("msg", msg.getMsg() == null ? "noMsg" : msg.getMsg());
		
		return "index";
	}
}
