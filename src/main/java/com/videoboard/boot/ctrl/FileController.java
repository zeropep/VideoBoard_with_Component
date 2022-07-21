package com.videoboard.boot.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.videoboard.boot.domain.FileVO;
import com.videoboard.boot.handler.FileHandler;
import com.videoboard.boot.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FileService fsv;
	
	@PostMapping("/{vno}")
	public int register(@PathVariable(value = "vno") long vno, MultipartFile[] files) {
		return fsv.registerFile(vno, files);
	}
	
}
