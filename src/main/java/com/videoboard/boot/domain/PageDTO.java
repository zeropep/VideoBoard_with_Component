package com.videoboard.boot.domain;

import java.util.List;

import com.videoboard.boot.handler.PagingHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
	private MemberVO mvo;
	private List<BoardVO> boardList;
	private List<CommentVO> cmtList;
	private List<VideoVO> videoList;
	private List<FileVO> fileList;
	private List<JobVO> jobList;
	private PagingHandler pgn;
	
	public PageDTO(PagingHandler pgn) {
		this.pgn = pgn;
	}
	
	
}
