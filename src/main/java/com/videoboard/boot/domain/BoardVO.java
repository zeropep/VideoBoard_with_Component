package com.videoboard.boot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {
	private long bno;
	private long mno;
	private String title;
	private String username;
	private String content;
	private int cmtCount;
	private String createdAt;
	private String modifiedAt;
	private boolean isMine = false;
}
