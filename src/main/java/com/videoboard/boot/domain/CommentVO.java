package com.videoboard.boot.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter @ToString
public class CommentVO {
	private long cno;
	private long bno;
	private long vno;
	private long mno;
	private String username;
	private String content;
	private String createdAt;
	private String modifiedAt;
	private boolean isMine;
}
