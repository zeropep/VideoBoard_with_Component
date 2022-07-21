package com.videoboard.boot.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.videoboard.boot.domain.CommentVO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.PageDTO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.handler.PagingHandler;
import com.videoboard.boot.service.CommentService;
import com.videoboard.boot.service.VCommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class VCommentController {
	
	@Autowired
	private VCommentService csv;
	
	@PostMapping("/vcomment")
	public int register(@RequestBody CommentVO cvo, MemberVO mvo) {
		cvo.setMno(mvo.getMno());
		cvo.setUsername(mvo.getUsername());
		return csv.register(cvo);
	}
	
	@GetMapping("/vcomment/{vno}")
	public PageDTO getList(@PathVariable(value = "vno") long vno, MemberVO mvo,
						@CookieValue(value = "cmtCurPage", defaultValue = "1") int pageNo) {
		PagingVO pgvo = new PagingVO(pageNo, 5);
		return csv.getList(vno, pgvo, mvo.getMno());
	}
	
	@PutMapping("/vcomment/{vno}")
	public int modify(@PathVariable(value = "vno") long vno, MemberVO mvo,
					@RequestBody CommentVO cvo) {
		return csv.modify(cvo, mvo.getMno());
	}
	
	@DeleteMapping("/vcomment/{vno}")
	public int remove(@PathVariable(value = "vno") long vno, MemberVO mvo,
					@RequestBody CommentVO cvo) {
		cvo.setVno(vno);
		return csv.remove(cvo, mvo.getMno());
	}
	
}
