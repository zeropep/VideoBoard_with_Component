package com.videoboard.boot.repository;

import org.apache.ibatis.annotations.Mapper;

import com.videoboard.boot.domain.LikeVO;

@Mapper
public interface LikeDAO {
	int insertLike(LikeVO lvo);
	int selectLikeVideo(LikeVO lvo);
	int deleteLike(LikeVO lvo);
}
