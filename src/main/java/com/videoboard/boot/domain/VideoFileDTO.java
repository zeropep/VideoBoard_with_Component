package com.videoboard.boot.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter @ToString
@AllArgsConstructor
public class VideoFileDTO {
	private BoardVO bvo;
	private VideoVO vvo;
	private FileVO fvo;
	private boolean like;
	private boolean isMine;
	
	public VideoFileDTO(VideoVO vvo, FileVO fvo) {
		this.vvo = vvo;
		this.fvo = fvo;
		this.like = false;
		this.isMine = false;
	}
	
	public VideoFileDTO(BoardVO bvo, FileVO fvo) {
		this.bvo = bvo;
		this.fvo = fvo;
		this.like = false;
		this.isMine = false;
	}
}
