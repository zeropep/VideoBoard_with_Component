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
public class HistoryVO {
	private long hno;
	private long mno;
	private long vno;
	private String uuid;
	private String saveDir;
	private String fileName;
	private String deleteTime;

}
