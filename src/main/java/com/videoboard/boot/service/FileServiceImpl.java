package com.videoboard.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.videoboard.boot.domain.FileVO;
import com.videoboard.boot.handler.FileHandler;
import com.videoboard.boot.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileDAO fdao;
	
	@Autowired
	private FileHandler fileHandler;
	
	@Override
	@Transactional
	public int registerFile(long vno, MultipartFile[] files) {
		int result = 1;
		List<FileVO> fList = fileHandler.uploadFiles(files);
		for (FileVO fvo : fList) {
			fvo.setVno(vno);
			result *= fdao.insertFile(fvo);
		}
		return result;
	}

	@Override
	public FileVO getFileList(long vno) {
		return fdao.selectFile(vno);
	}

	@Override
	public int deleteFile(long fno) {
		return fdao.deleteFile(fno);
	}

}
