package com.videoboard.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.videoboard.boot.domain.CommentVO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.PageDTO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.handler.PagingHandler;
import com.videoboard.boot.repository.BoardDAO;
import com.videoboard.boot.repository.CommentDAO;
import com.videoboard.boot.repository.VCommentDAO;
import com.videoboard.boot.repository.VideoDAO;

import groovy.transform.Undefined.EXCEPTION;
import groovyjarjarantlr4.v4.parse.ANTLRParser.exceptionGroup_return;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VCommentServiceImple implements VCommentService {
	
	@Autowired
	private VCommentDAO cdao;

	@Autowired
	private VideoDAO vdao;
	
	@Override
	@Transactional
	public int register(CommentVO cvo) {
		int result = cdao.insertComment(cvo);
		result *= vdao.addCmtCount(cvo.getVno());
		return result;
	}

	@Override
	@Transactional
	public PageDTO getList(long vno, PagingVO pgvo, long mno) {
		List<CommentVO> cmtList = cdao.selectListComment(vno, pgvo);
		for (CommentVO cvo : cmtList) {
			if (cvo.getMno() == mno) {
				cvo.setMine(true);
			}
		}
		PagingHandler pgn = new PagingHandler(pgvo, 
										cdao.selectCommentTotalCount(vno), cmtList);
		PageDTO dto = new PageDTO(pgn);
		dto.setCmtList(pgn.getCmtList());
		return dto;
	}

	@Override
	public int modify(CommentVO cvo, long mno) {
		return cvo.getMno() == mno ? cdao.updateComment(cvo) : 0;
	}

	@Override
	@Transactional(rollbackFor = EXCEPTION.class)
	public int remove(CommentVO cvo, long mno) {
		if (cvo.getMno() != mno) {
			return 0;
		}
		int result = cdao.deleteComment(cvo.getCno());
		result *= vdao.downCmtCount(cvo.getVno());
		return result;
	}

	@Override
	public int removeAll(long vno) {
		return cdao.deleteAllComment(vno);
	}

}
