package com.videoboard.boot.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.videoboard.boot.domain.BoardVO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.domain.VideoFileDTO;


public interface BoardService {
	int registerBoard(BoardVO bvo, MultipartFile[] files);
	List<BoardVO> getBoardList(PagingVO pgvo);
	VideoFileDTO getBoardDetail(long bno, long mno);
	int modifyBoard(BoardVO bvo, long mno);
	int removeBoard(long bno, long mno);
	int getTotalCount(PagingVO pgvo);
}
