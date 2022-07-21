package com.videoboard.boot.repository;

import org.apache.ibatis.annotations.Mapper;

import com.videoboard.boot.domain.HistoryVO;

@Mapper
public interface HistoryDAO {
	int insertHistory(HistoryVO hvo);
	int updateRemoved(long fno);
}
