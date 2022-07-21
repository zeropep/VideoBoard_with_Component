package com.videoboard.boot.handler;

import java.util.List;

import com.videoboard.boot.domain.CommentVO;
import com.videoboard.boot.domain.PagingVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter @ToString
public class PagingHandler {
	private int startPage; // 현재 화면에서 보여줄 시작 페이지네이션 번호 
	private int endPage; // 현재 화면에서 보여줄 마지막 페이지네이션 번호 
	private boolean prev, next; // 이전, 다음 여부
	private boolean more;
	
	private int totalCount; // 총 게시물 수
	private PagingVO pgvo; // 사용자가 입력하는 페이징, 검색 관련 정보 값 객체
	
	private List<CommentVO> cmtList;
	
	public PagingHandler(PagingVO pgvo, int totalCount) {
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
		this.endPage = (int) ((Math.ceil(pgvo.getPageNo() / (pgvo.getQty() * 1.0))) * pgvo.getQty());
		this.startPage = this.endPage - (pgvo.getQty() - 1);
		this.more = totalCount > (pgvo.getPageNo() * pgvo.getQty()) ? true : false;
		
		int realEndPage = (int) (Math.ceil((totalCount*1.0) / pgvo.getQty()));
		
		if (realEndPage < this.endPage) {
			this.endPage = realEndPage;
		}
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEndPage;
	}
	
	public PagingHandler(PagingVO pgvo, int totalCount, List<CommentVO> cmtList) {
		this(pgvo, totalCount);
		this.cmtList = cmtList;
	}
}
	
