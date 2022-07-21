package com.videoboard.boot.ctrl;

import org.apache.tools.ant.taskdefs.Concat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.videoboard.boot.domain.FileVO;
import com.videoboard.boot.domain.JobVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MqTestController {
	
	private static final String EXCHANGE_NAME = "esjee.encoder";
	private static final String ROUTING_KEY = "esjee.transcoder";
	
	private String BASE_DIR = "C:\\Users\\sj_nb_028\\Documents\\";
	
	private String ORG_DIR = "org\\";
	
	private String PXY_DIR = "proxy\\";
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@SuppressWarnings("unchecked")
	@GetMapping("/sample/queue")
	public String publishTest() throws JsonMappingException, JsonProcessingException {
		String msg = "{\"jobSeq\":1353588,\"contentId\":\"000001516661\",\"componentType\":\"1001\",\"type\":\"VIDEO\",\"inputs\":[{\"uri\":\"Z:/storage/org/video/2022/06/21/1201678.mpg\",\"seq\":1}],\"output\":{\"height\":720,\"scale\":1,\"vbitrate\":1048576,\"vcodec\":\"H.264\",\"uri\":\"C:/Users/sj_nb_028/Documents/proxy/video/2022/06/22/1201678.mp4\",\"reportProgress\":\"1\"}}\n";
		rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, msg);
//		String jobMessage = "{\"width\":\"320\",\"height\":\"180\",\"jobSeq\":1350529,\"contentId\":\"000000493821\",\"fileId\":\"1197963\",\"filePath\":\"C:/Users/sj_nb_028/Documents/proxy/video/2022/06/22/1201678.MP4\",\"outputFilePath\":\"C:/Users/sj_nb_028/Documents/proxy/storyboard/2022/06/22/1197963/\",\"componentType\":\"1003\",\"sensitivity\":\"899.1\"}";
//		ObjectMapper mapper = new ObjectMapper();
//		Map<String, Object> jobMap = mapper.readValue(jobMessage, Map.class);
//		log.info(jobMap.toString());
		return "message sending!";
	}
	
}
