package com.videoboard.boot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.videoboard.boot.domain.FileVO;

@Mapper
public interface FileDAO {
	long insertFile(FileVO fvo);
	int insertImage(FileVO fvo);
	FileVO selectFile(long vno);
	FileVO selectImageFile(long bno);
	int deleteFile(long vno);
	List<FileVO> selectDeletedFiles();
	int deleteFileData();
	void posterCreated(long fno);
}
