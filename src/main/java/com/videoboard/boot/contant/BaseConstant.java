package com.videoboard.boot.contant;

/**
 * @Description 기본 상수
 * @author Cho Oh-jung (2016. 6. 22.)
 */
public interface BaseConstant {
	
	/**
	 * @Description 작업 상태
	 * @author Cho Oh-jung (2016. 6. 22.)
	 */
	public enum JOB_STATE {
		RUN("실행", "03"), COMPLETE("종료", "10"), ERROR("오류", "09");

		private final String name;
		private final String value;

		private JOB_STATE(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return this.name;
		}

		public String getValue() {
			return this.value;
		}
	}

	/**
	 * @Description 작업 결과
	 * @author Cho Oh-jung (2016. 6. 22.)
	 */
	public enum JOB_RESULT {
		COMPLETE("정상", "10"), ERROR("오류", "20");

		private final String name;
		private final String value;

		private JOB_RESULT(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return this.name;
		}

		public String getValue() {
			return this.value;
		}
	}

	/**
	 * @Description 프로세스 상세
	 * @author Cho Oh-jung (2016. 6. 22.)
	 */
	public enum PROCESS_EXE {
		RUN("실행", "10"), COMPLETE("종료", "20");

		private final String name;
		private final String value;

		private PROCESS_EXE(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return this.name;
		}

		public String getValue() {
			return this.value;
		}
	}
	
	/** 트랜스코더 */
	public static String COMPONENT_TYPE_1001 = "1001";
	/** 오디오 트랜스코더 */
	public static String COMPONENT_TYPE_1002 = "1002";
	/** 비디오 카탈로깅 */
	public static String COMPONENT_TYPE_1003 = "1003";
	/** 이미지 포멧 변환 */
	public static String COMPONENT_TYPE_1005 = "1005";
	/** 문서 텍스트 추출 */
	public static String COMPONENT_TYPE_1006 = "1006";
	/** QC 비디오 - 비디오*/
	public static String COMPONENT_TYPE_1011 = "1011";
	/** Cutter*/
	public static String COMPONENT_TYPE_1012 = "1012";
	/** QC 비디오 - 오디오*/
	public static String COMPONENT_TYPE_1013 = "1013";
	/** QC 오디오 - 오디오*/
	public static String COMPONENT_TYPE_1014 = "1014";
	/** 마커 - 비디오*/
	public static String COMPONENT_TYPE_1015 = "1015";
	/** 인제스트 컨버터 */
	public static String COMPONENT_TYPE_1016 = "1016";
	/** QC 이미지*/
	public static String COMPONENT_TYPE_1017 = "1017";
	/** 공유 사이트 업로드*/
	public static String COMPONENT_TYPE_1018 = "1018";
	/** 전송 */
	public static String COMPONENT_TYPE_1007 = "1007";
	/** 아카이브 */
	public static String COMPONENT_TYPE_1008 = "1008";
	/** 전송-부조 */
	public static String COMPONENT_TYPE_1031 = "1031";
	/** 전송-고속 */
	public static String COMPONENT_TYPE_1032 = "1032";
	/** 삭제 모듈 */
	public static String COMPONENT_TYPE_1050 = "1050";
	
	/** AME */
	public static String COMPONENT_TYPE_1060 = "1060";
	public static String COMPONENT_TYPE_1061 = "1061";
	public static String COMPONENT_TYPE_1062 = "1062";
	public static String COMPONENT_TYPE_1080 = "1080";
	
	/** Diva Archive */
	public static String COMPONENT_TYPE_1070 = "1070";
	public static String COMPONENT_TYPE_1071 = "1071";
	public static String COMPONENT_TYPE_1072 = "1072";
	
	public static String COMPONENT_TYPE_1090 = "1090";
	
	/** FTP 파일전송 */
	public static String COMPONENT_TYPE_1010 = "1010";
}
