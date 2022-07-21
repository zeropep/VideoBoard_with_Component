package com.videoboard.boot.domain;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class JobVO {
	private long jobSeq;
	private long mno;
	private long vno;
	private long fno;
	private String fileType;
	private String fileName;
	private String Component;
	private String status;
	private String progress;
	private String startTime;
	private String finishTime;
}
