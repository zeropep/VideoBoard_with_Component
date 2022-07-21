package com.videoboard.boot.service;

import com.videoboard.boot.domain.LikeVO;

public interface LikeService {
	int register(LikeVO lvo);
	int remove(LikeVO lvo);
}
