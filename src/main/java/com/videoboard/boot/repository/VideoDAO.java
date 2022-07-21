package com.videoboard.boot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.domain.VideoVO;

@Mapper
public interface VideoDAO {
	long insertVideo(VideoVO vvo);
	List<VideoVO> selectAllVideos(PagingVO pgvo);
	VideoVO videoDetail(long vno);
	int updateVideo(VideoVO vvo);
	int deleteVideo(long vno);
	long lastIdxReturn();
	int selectTotalCount(String keyword);
	int addCmtCount(long vno);
	int downCmtCount(long vno);
	int addLikeCount(long vno);
	int downLikeCount(long vno);
	int addViewCount(long vno);
	List<VideoVO> selectVideosByUsername(@Param("pgvo")PagingVO pgvo,@Param("username") String username);
	int selectCountByUsername(String username);
}
