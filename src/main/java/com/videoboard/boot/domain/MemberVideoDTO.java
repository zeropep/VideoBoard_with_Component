package com.videoboard.boot.domain;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberVideoDTO {
	private MemberVO mvo;
	private List<VideoVO> videoList;
}
