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
public class FileVO {
	private long fno;
	private long bno;
	private long vno;
	private String uuid;
	private String saveDir;
	private String fileName;
	private String fileExt;
	private int fileType;
	private long fileSize;
	private String createdAt;
	private String isDelete;
	private String poster;
}
