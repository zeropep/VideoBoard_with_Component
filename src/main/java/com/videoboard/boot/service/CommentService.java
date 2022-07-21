package com.videoboard.boot.service;

import com.videoboard.boot.domain.CommentVO;
import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.PagingVO;
import com.videoboard.boot.handler.PagingHandler;

public interface CommentService {
	int register(CommentVO cvo, MemberVO mvo);
	PagingHandler getList(long bno, PagingVO pgvo, long mno);
	int modify(CommentVO cvo, long mno);
	int remove(CommentVO cvo, long mno);
	int removeAll(long bno);
}
