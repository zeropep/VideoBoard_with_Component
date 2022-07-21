package com.videoboard.boot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.videoboard.boot.domain.BoardVO;
import com.videoboard.boot.domain.PagingVO;

@Mapper
public interface BoardDAO {
	int insertBoard(BoardVO bvo);
//	List<BoardVO> selectAllBoards();
	List<BoardVO> selectListBoardPaging(PagingVO pgvo);
	BoardVO BoardDetail(long bno);
	int updateBoard(BoardVO bvo);
	int deleteBoard(long bno);
	int selectTotalCount(PagingVO pgvo);
	int addCmtCount(long bno);
	int downCmtCount(long bno);
}
