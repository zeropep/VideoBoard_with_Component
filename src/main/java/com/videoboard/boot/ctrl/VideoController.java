package com.videoboard.boot.ctrl;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.videoboard.boot.domain.MessageVO;
import com.videoboard.boot.domain.VideoVO;
import com.videoboard.boot.service.VideoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/video")
public class VideoController {
	
	@Autowired
	private VideoService vsv;
	
	@GetMapping("register")
	public void getLocation() {}
	
	@GetMapping("list")
	public void list(Model model, @CookieValue(value = "curPage", defaultValue = "1") long curPage,
						@ModelAttribute MessageVO msg, @RequestParam(value = "key", defaultValue = "1") int key) {
		model.addAttribute("key", key);
		model.addAttribute("curPage", curPage);
		model.addAttribute("sort", msg.getSort() == null ? "noMsg" : msg.getSort());
		model.addAttribute("msg", msg.getMsg() == null ? "noMsg" : msg.getMsg());
	}
	
	@GetMapping("/detail/{vno}")
	public String detail(@PathVariable long vno, Model model, @ModelAttribute MessageVO msg) {
		model.addAttribute("vno", vno);
		model.addAttribute("sort", msg.getSort() == null ? "noMsg" : msg.getSort());
		model.addAttribute("msg", msg.getMsg() == null ? "noMsg" : msg.getMsg());
		return "video/detail";
	}
	
	@GetMapping("/modify/{vno}")
	public String modify(@PathVariable long vno, Model model) {
		model.addAttribute("vno", vno);
		return "video/modify";
	}
	
	@GetMapping("/comp/{vno}")
	public String comp(@PathVariable long vno, Model model, @ModelAttribute MessageVO msg) {
		model.addAttribute("vno", vno);
		model.addAttribute("sort", msg.getSort() == null ? "noMsg" : msg.getSort());
		model.addAttribute("msg", msg.getMsg() == null ? "noMsg" : msg.getMsg());
		return "video/comp";
	}
}
