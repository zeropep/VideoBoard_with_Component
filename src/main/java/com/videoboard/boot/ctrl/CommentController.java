package com.videoboard.boot.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CommentController {
	
	@Autowired
	private CommentService csv;
	
	@PostMapping("/comment")
	public int register(@RequestBody CommentVO cvo) {
		// 멤버받기
		MemberVO mvo = new MemberVO();
		return csv.register(cvo, mvo);
	}
	
	@GetMapping("/comment/{bno}")
	public PageDTO getList(@PathVariable(value = "bno") long bno, 
						@RequestParam(value = "cmtPageNo") int pageNo, 
						@AuthenticationPrincipal MemberVO mvo) {
		PagingVO pgvo = new PagingVO(pageNo, 10);
		PagingHandler pgn = csv.getList(bno, pgvo, mvo.getMno());
		PageDTO dto = new PageDTO(pgn);
		dto.setCmtList(pgn.getCmtList());
		return dto;
	}
	
	@PutMapping("/comment/{bno}")
	public int modify(@PathVariable(value = "bno") long bno, 
					@RequestBody CommentVO cvo, @AuthenticationPrincipal MemberVO mvo) {
		return csv.modify(cvo, mvo.getMno());
	}
	
	@DeleteMapping("/comment/{bno}")
	public int remove(@PathVariable(value = "bno") long bno, 
					@RequestBody CommentVO cvo, @AuthenticationPrincipal MemberVO mvo) {
		cvo.setBno(bno);
		return csv.remove(cvo, mvo.getMno());
	}
	
}
