package com.videoboard.boot.service;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.videoboard.boot.domain.BoardVO;
import com.videoboard.boot.domain.FileVO;
import com.videoboard.boot.domain.JobVO;
import com.videoboard.boot.repository.BoardDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class boardService {
	
	@InjectMocks
	private BoardService bsv;
	
	@Mock
	private BoardDAO bdao;
	
	@Test
	public void reg() throws Exception {
		//
		for (int i = 0; i < 200; i++) {
			BoardVO bvo = new BoardVO();
			bvo.setTitle("게시물 테스트 " + i);
			bvo.setMno(i % 2 == 1 ? 2 : 6);
			bvo.setContent("It is a long established fact that a reader will be distracted by the readable content"
					+ " of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less"
					+ " normal distribution of letters, as opposed to using 'Content here, content here', making it look like"
					+ " readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their"
					+ " default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy."
					+ " Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour"
					+ " and the like).");
			bvo.setUsername(i % 2 == 1 ? "asd" : "aaa");
		}

	}
	

}
