package com.videoboard.boot.contant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ComponentConstants {
	
	private ComponentConstants() {}
	
	// == Directory ==
	public static final String BASE_DIR = "C:\\Users\\sj_nb_028\\Documents\\";
	public static final String ORG_DIR = "org\\";
	public static final String PXY_DIR = "proxy\\";
	public static final String VIDEO_DIR = "video\\";
	public static final String IMAGE_DIR = "image\\";
	public static final String STORY_DIR = "storyboard\\";
	
	// == Component Type ==
	// Transcoder
	public static final String COMPONENT_1001 = "1001";
	// Cataloger
	public static final String COMPONENT_1003 = "1003";
	// VideoQC
	public static final String COMPONENT_1011 = "1011";
	
}
