package com.videoboard.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.videoboard.boot.domain.CommentVO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.handler.PagingHandler;
import com.videoboard.boot.repository.BoardDAO;
import com.videoboard.boot.repository.CommentDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImple implements CommentService {
	
	@Autowired
	private CommentDAO cdao;

	@Autowired
	private BoardDAO bdao;
	
	@Override
	@Transactional
	public int register(CommentVO cvo, MemberVO mvo) {
//		cvo.setMno(mvo.getMno());
//		cvo.setUsername(mvo.getUsername());
		cvo.setMno(2);
		cvo.setUsername("asd");
		int result = cdao.insertComment(cvo);
		result *= bdao.addCmtCount(cvo.getBno());
		return result;
	}

	@Override
	@Transactional
	public PagingHandler getList(long bno, PagingVO pgvo, long mno) {
		List<CommentVO> cmtList = cdao.selectListComment(bno, pgvo);
		for (CommentVO cvo : cmtList) {
			if (cvo.getMno() == mno) {
				cvo.setMine(true);
			}
		}
		return new PagingHandler(
						pgvo, 
						cdao.selectCommentTotalCount(bno), 
						cmtList);
	}

	@Override
	public int modify(CommentVO cvo, long mno) {
		return cvo.getMno() == mno ? cdao.updateComment(cvo) : 0;
	}

	@Override
	@Transactional
	public int remove(CommentVO cvo, long mno) {
		int result = cvo.getMno() == mno ? cdao.deleteComment(cvo.getCno()) : 0;
		result *= bdao.downCmtCount(cvo.getBno());
		return result;
	}

	@Override
	public int removeAll(long bno) {
		return cdao.deleteAllComment(bno);
	}

}
