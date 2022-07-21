package com.videoboard.boot.Component;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.videoboard.boot.contant.ComponentConstants;
import com.videoboard.boot.domain.FileVO;
import com.videoboard.boot.domain.JobVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageBuilder {
	
	// format
	private final String PATH_ORG_VIDEO = "%s%s%s%s\\%s_%s%s";
	private final String PATH_PXY_VIDEO = "%s%s%s%s\\%s_%s.mp4";
	private final String PATH_PXY_STORY = "%s%s%s%s\\%s\\";
	
	// Transcoder
	private final int TRANSCODER_SEQ = 1;
	private final int TRANSCODER_HEIGHT = 720;
	private final int TRANSCODER_SCALE = 1;
	private final int TRANSCODER_VBITRATE = 1048576;
	private final String TRANSCODER_VCODEC = "H.264";
	private final int TRANSCODER_REPORTPROGRESS = 1;

	// Cataloger
	private final String CATALOGER_POSTERINTERVAL = "1000";
	private final String CATALOGER_FRAMEINTERVAL = "2";
	private final String CATALOGER_MININTERVAL = "30";
	private final String CATALOGER_WIDTH = "320";
	private final String CATALOGER_SENSITIVITY = "0.4";
	private final String CATALOGER_HEIGHT = "180";
	
	JSONObject jsonObj = new JSONObject();
	JSONObject input = new JSONObject();
	JSONObject output = new JSONObject();
	JSONArray arr = new JSONArray();
	
	@SuppressWarnings("unchecked")
	public String transcoderMessage(JobVO jvo, FileVO fvo) {
		
		input.put("uri", this.getPath(PATH_ORG_VIDEO, fvo));
		input.put("seq", TRANSCODER_SEQ);
		arr.add(input);
		
		output.put("height", TRANSCODER_HEIGHT);
		output.put("scale", TRANSCODER_SCALE);
		output.put("vbitrate", TRANSCODER_VBITRATE);
		output.put("vcodec", TRANSCODER_VCODEC);
		output.put("uri", this.getPath(PATH_PXY_VIDEO, fvo));
		output.put("reportProgress", TRANSCODER_REPORTPROGRESS);
		
		jsonObj.put("jobSeq", jvo.getJobSeq());
		jsonObj.put("contentId", jvo.getVno());
		jsonObj.put("componentType", jvo.getComponent());
		jsonObj.put("type", this.getType(jvo.getFileType()));
		jsonObj.put("inputs", arr);
		jsonObj.put("output", output);
		
		return jsonObj.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String CatalogerMessage(JobVO jvo, FileVO fvo) {
		
		jsonObj.put("componentType", jvo.getComponent());
		jsonObj.put("posterInterval", CATALOGER_POSTERINTERVAL);
		jsonObj.put("frameInterval", CATALOGER_FRAMEINTERVAL);
		jsonObj.put("minInterval", CATALOGER_MININTERVAL);
		jsonObj.put("filePath", this.getPath(PATH_PXY_VIDEO, fvo));
		jsonObj.put("width", CATALOGER_WIDTH);
		jsonObj.put("contentId", jvo.getVno());
		jsonObj.put("jobSeq", jvo.getJobSeq());
		jsonObj.put("sensitivity", CATALOGER_SENSITIVITY);
		jsonObj.put("height", CATALOGER_HEIGHT);
		jsonObj.put("fileId", fvo.getFno());
		jsonObj.put("outputFilePath", this.getPath(PATH_PXY_STORY, fvo));
		return jsonObj.toString();
	}
	
	private String getPath(String format, FileVO fvo ) {
		String path = "";
		
		switch (format) {
		case PATH_ORG_VIDEO:
			path = String.format(PATH_ORG_VIDEO, 
										ComponentConstants.BASE_DIR, 
									    ComponentConstants.ORG_DIR, 
									    ComponentConstants.VIDEO_DIR, 
									    fvo.getSaveDir(), 
									    fvo.getUuid(), 
									    fvo.getFileName(), 
									    fvo.getFileExt());
			break;
		case PATH_PXY_VIDEO:
			path = String.format(PATH_PXY_VIDEO, 
										ComponentConstants.BASE_DIR, 
										ComponentConstants.PXY_DIR, 
										ComponentConstants.VIDEO_DIR, 
										fvo.getSaveDir(), 
										fvo.getUuid(), 
										fvo.getFileName());
			break;
		case PATH_PXY_STORY:
			path = String.format(PATH_PXY_STORY, 
										ComponentConstants.BASE_DIR, 
										ComponentConstants.PXY_DIR, 
										ComponentConstants.STORY_DIR, 
										fvo.getSaveDir(), 
										fvo.getFno());
			break;

		default:
			break;
		}
		
		return path;
	}
	
	private Object getType(String fileType) {
		String result = "";
		switch (fileType) {
		case "01":
			result = "IMAGE";
			break;
		case "02":
			result = "VIDEO";
			break;
		case "03":
			result = "AUDIO";
			break;

		default:
			break;
		}
		return result;
	}

}
