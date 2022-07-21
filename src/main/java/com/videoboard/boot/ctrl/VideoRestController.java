package com.videoboard.boot.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.multipart.MultipartFile;

import com.videoboard.boot.domain.BoardVO;
import com.videoboard.boot.domain.ComponentDTO;
import com.videoboard.boot.domain.FileVO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.PageDTO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.domain.VideoFileDTO;
import com.videoboard.boot.domain.VideoVO;
import com.videoboard.boot.handler.FileHandler;
import com.videoboard.boot.handler.PagingHandler;
import com.videoboard.boot.service.VideoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class VideoRestController {
	
	@Autowired
	private VideoService vsv;

	@PostMapping("/video")
	public long register(VideoVO vvo, MultipartFile[] files, MemberVO mvo) {
		vvo.setUsername(mvo.getUsername());
		vvo.setMno(mvo.getMno());
		return vsv.registerVideo(vvo, files);
	}
	
	@GetMapping("/video/listup")
	public PageDTO getList(@CookieValue(value = "curPage", defaultValue = "1") int pageNo, 
							@CookieValue(value = "keyword", defaultValue = "noword") String keyword) {
		PagingVO pgvo = new PagingVO(pageNo, 8, keyword.equals("noword") ? null : keyword);
		return vsv.getVideoList(pgvo);
	}
	
	@GetMapping("/video/{vno}")
	public VideoFileDTO detail(@PathVariable long vno, MemberVO mvo) {
		return vsv.getVideoDetail(vno, mvo.getMno());
	}
	
	@GetMapping("/video/overview/{vno}")
	public ComponentDTO overview(@PathVariable long vno) {
		return vsv.getVideoComponent(vno);
	}
	
	@PutMapping("/video/{vno}")
	public int modify(VideoVO vvo,@RequestParam(value ="files", required=false) MultipartFile[] files) {
		return vsv.modifyVideo(vvo, files);
	}
	
	@DeleteMapping("/video/{vno}")
	public int remove(@PathVariable(value = "vno") long vno) {
		log.info(Long.toString(vno));
		return vsv.removeVideo(vno);
//		return 1;
	}
	
}
