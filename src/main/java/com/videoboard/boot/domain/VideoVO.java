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
public class VideoVO {
	private long vno;
	private long mno;
	private String title;
	private String description;
	private String username;
	private int likeCount;
	private int cmtCount;
	private int viewCount;
	private String createdAt;
	private String modifiedAt;
	
	// file
	private long fno;
	private String uuid;
	private String saveDir;
	private String fileName;
	private String fileExt;
	private int fileType;
	private String poster;
	
	// job
//	private String status;
//	private String component;
}
