package com.videoboard.boot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.videoboard.boot.domain.CommentVO;
import com.videoboard.boot.domain.PagingVO;

@Mapper
public interface CommentDAO {
	int insertComment(CommentVO cvo);
	List<CommentVO> selectListComment(@Param("bno") long bno, @Param("pgvo") PagingVO pgvo);
	int selectCommentTotalCount(long bno);
	int updateComment(CommentVO cvo);
	int deleteComment(long cno);
	int deleteAllComment(long bno);
	
}
