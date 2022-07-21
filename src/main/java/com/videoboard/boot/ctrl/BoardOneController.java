package com.videoboard.boot.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.handler.PagingHandler;
import com.videoboard.boot.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/boardone")
public class BoardOneController {
	
	@Autowired
	private BoardService bsv;
	
	@GetMapping("register")
	public void getLocation() {}
	
	@GetMapping("list")
	public void list(Model model, @RequestParam(value = "curPage" ) int curPage) {
		model.addAttribute("curPage", curPage);
	}
	
	@GetMapping("/detail/{bno}")
	public String detail(@PathVariable long bno, Model model, @RequestParam(value = "curPage") int curPage) {
		model.addAttribute("bno", bno);
		model.addAttribute("curPage", curPage);
		return "boardone/detail";
	}
	
	@GetMapping("/modify/{bno}")
	public String modify(@PathVariable long bno, Model model, @RequestParam(value = "curPage") int curPage) {
		model.addAttribute("bno", bno);
		model.addAttribute("curPage", curPage);
		return "boardone/modify";
	}
}
