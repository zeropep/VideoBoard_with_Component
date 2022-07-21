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
public class LikeVO {
	private long from;
	private long to;
}
