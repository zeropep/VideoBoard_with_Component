package com.videoboard.boot.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videoboard.boot.domain.LikeVO;
import com.videoboard.boot.service.LikeService;
import com.videoboard.boot.service.VideoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MetaController {
	
	@Autowired
	private LikeService lsv;
	
	@Autowired
	private VideoService vsv;
	
	@PostMapping("/like/{vno}")
	public int regLike(@PathVariable(value = "vno") long vno) {
		return lsv.register(new LikeVO(2, vno));
	}
	
	@DeleteMapping("/like/{vno}")
	public int isLike(@PathVariable(value = "vno") long vno) {
		return lsv.remove(new LikeVO(2, vno));
	}
	
	@PutMapping("/view/{vno}")
	public int addViewCount(@PathVariable(value = "vno") long vno) {
		return vsv.viewCountUp(vno);
	}
}
