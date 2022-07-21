package com.videoboard.boot.service;

import org.springframework.web.multipart.MultipartFile;

import com.videoboard.boot.domain.ComponentDTO;
import com.videoboard.boot.domain.PageDTO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.domain.VideoFileDTO;
import com.videoboard.boot.domain.VideoVO;

public interface VideoService {
	long registerVideo(VideoVO vvo, MultipartFile[] files);
	PageDTO getVideoList(PagingVO pgvo);
	VideoFileDTO getVideoDetail(long vno, long mno);
	int modifyVideo(VideoVO vvo, MultipartFile[] files);
	int removeVideo(long vno);
	int getTotalCount(String keyword);
	int viewCountUp(long vno);
	ComponentDTO getVideoComponent(long vno);
}
