package com.videoboard.boot.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class CatalogResultVO {
	private long jobSeq;
	private long vno;
	private long fno;
	private String imagePath;
	private String startTime;
	private String endTime;
	private String playTime;
	private int startFrame;
	private int endFrame;
	private int playFrame;
}
