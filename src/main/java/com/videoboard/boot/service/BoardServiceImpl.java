package com.videoboard.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.videoboard.boot.domain.BoardVO;
import com.videoboard.boot.domain.FileVO;
import com.videoboard.boot.domain.JobVO;
import com.videoboard.boot.domain.LikeVO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.domain.VideoFileDTO;
import com.videoboard.boot.handler.FileHandler;
import com.videoboard.boot.repository.BoardDAO;
import com.videoboard.boot.repository.CommentDAO;
import com.videoboard.boot.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDAO bdao;
	
	@Autowired
	private CommentDAO cdao;
	
	@Autowired
	private FileDAO fdao;
	
	@Autowired
	private FileHandler fileHandler;
	
	@Override
	public int registerBoard(BoardVO bvo, MultipartFile[] files) {
		if (files.length < 0) {
			return 0;
		}
		
		bdao.insertBoard(bvo);
		
		List<FileVO> fList = fileHandler.uploadFiles(files);
		for (FileVO fvo : fList) {
			fvo.setBno(bvo.getBno());
			fdao.insertImage(fvo);
		}

		return 1;
	}

	@Override
	public List<BoardVO> getBoardList(PagingVO pgvo) {
		return bdao.selectListBoardPaging(pgvo);
	}

	@Override
	public VideoFileDTO getBoardDetail(long bno, long mno) {
		VideoFileDTO ifdto = new VideoFileDTO(bdao.BoardDetail(bno), fdao.selectImageFile(bno));
		ifdto.setMine(ifdto.getBvo().getMno() == mno ? true : false);
		log.info(ifdto.getFvo().toString());
		return ifdto;
	}

	@Override
	public int modifyBoard(BoardVO bvo, long mno) {
		return bvo.getMno() == mno ? bdao.updateBoard(bvo) : 0;
	}

	@Override
	@Transactional
	public int removeBoard(long bno, long mno) {
		int result = 1;
		BoardVO bvo = bdao.BoardDetail(bno);
		if (bvo.getCmtCount() > 0) {
			result = cdao.deleteAllComment(bno);
		}
		if (bvo.getMno() == mno) {
			result = bdao.deleteBoard(bno);
		}
		return result;
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return bdao.selectTotalCount(pgvo);
	}

}
