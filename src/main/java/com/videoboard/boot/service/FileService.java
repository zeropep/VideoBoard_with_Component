package com.videoboard.boot.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.videoboard.boot.domain.FileVO;

public interface FileService {
	int registerFile(long vno, MultipartFile[] files);
	FileVO getFileList(long vno);
	int deleteFile(long fno);
}
