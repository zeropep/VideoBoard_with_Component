package com.videoboard.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.videoboard.boot.domain.LikeVO;
import com.videoboard.boot.repository.LikeDAO;
import com.videoboard.boot.repository.VideoDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LikeServiceImpl implements LikeService {
	@Autowired
	private LikeDAO ldao;
	@Autowired
	private VideoDAO vdao;
	
	@Override
	@Transactional
	public int register(LikeVO lvo) {
		int result = ldao.insertLike(lvo); 
		result *= vdao.addLikeCount(lvo.getTo());
		return result;
	}


	@Override
	@Transactional
	public int remove(LikeVO lvo) {
		int result = ldao.deleteLike(lvo); 
		result *= vdao.downLikeCount(lvo.getTo());
		return result;
	}
	
}
