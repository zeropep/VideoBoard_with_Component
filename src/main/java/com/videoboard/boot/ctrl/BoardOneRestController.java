package com.videoboard.boot.ctrl;

import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.videoboard.boot.domain.BoardVO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.PageDTO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.domain.VideoFileDTO;
import com.videoboard.boot.handler.PagingHandler;
import com.videoboard.boot.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BoardOneRestController {

	@Autowired
	private BoardService bsv;
	
	@PostMapping("/boardone")
	public int register(BoardVO bvo, MultipartFile[] files, MemberVO mvo) {
		bvo.setUsername(mvo.getUsername());
		bvo.setMno(mvo.getMno());
		return bsv.registerBoard(bvo, files);
	}
	
	@GetMapping("/boardone/listup")
	public PageDTO getList(@RequestParam(value = "pageNo") int pageNo) {
		PagingVO pgvo = new PagingVO(pageNo, 10);
		PageDTO dto = new PageDTO(new PagingHandler(pgvo, bsv.getTotalCount(pgvo)));
		dto.setBoardList(bsv.getBoardList(pgvo));
		return dto;
	}
	
	@GetMapping("/boardone/{bno}")
	public VideoFileDTO detail(@PathVariable long bno, MemberVO mvo) {
		return bsv.getBoardDetail(bno, mvo.getMno());
	} 
	
	@PutMapping("/boardone/{bno}")
	public int modify(@PathVariable long bno, @RequestBody BoardVO bvo, MemberVO mvo) {
		return bsv.modifyBoard(bvo, mvo.getMno());
	}
	
	@DeleteMapping("/boardone/{bno}")
	public int delete(@PathVariable long bno, @AuthenticationPrincipal MemberVO mvo) {
		return bsv.removeBoard(bno, mvo.getMno());
	}
}
