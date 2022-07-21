package com.videoboard.boot.domain;

import java.util.List;

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
public class ComponentDTO {
	private VideoFileDTO videoFileDTO;
	private List<JobVO> jobList;
	private List<CatalogResultVO> catalogList;
}
