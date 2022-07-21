package com.videoboard.boot.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.videoboard.boot.domain.CatalogResultVO;
import com.videoboard.boot.repository.CatalogerDAO;
import com.videoboard.boot.repository.JobDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ComponentListener {
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private Router router;
	
	@Autowired
	private JobDAO jdao;
	
	@Autowired
	private CatalogerDAO catalogerDAO;
	
	
	// 연결 큐
	@SuppressWarnings("unchecked")
	@RabbitListener(queues = "esjee.connect")
	public void connect(final Message message) {
		String jobMessage = new String(message.getBody(), StandardCharsets.UTF_8);
		
		log.info(">>> Connection Request : {}", jobMessage); 
		try {
			Map<String, Object> request = mapper.readValue(jobMessage, Map.class);
			router.connect(request);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

	// 컴포넌트 작업 결과 큐
	@SuppressWarnings("unchecked")
	@RabbitListener(queues = "esjee.job.status")
	public void receiveMessage(final Message message) throws JsonMappingException, JsonProcessingException {
		String jobMessage = new String(message.getBody(), StandardCharsets.UTF_8);
		
		log.info(jobMessage); 
		// {"code":"0000","contentId":"000001516661","jobSeq":1353588,"reJobReqTf":"F"}
		// code : 작업결과
		try {
			Map<String, Object> jobMap = mapper.readValue(jobMessage, Map.class);
			log.info(jobMap.toString());
			
			Map<String, Object> result = null;
			Long jobSeq = 0L;
			
			if (!jobMap.isEmpty() && jobMap.containsKey("result")) {
				// {"result":{"failCnt":0,"successCnt":1,"resultCode":"0000","jobSeq":1353588},"jobExeState":"20","jobState":"10","jobResult":"10","contentId":"000001516661","jobSeq":1353588,"poolType":"1001"}
				result = (Map<String, Object>) jobMap.get("result");
				jobSeq = Long.valueOf((int) result.get("jobSeq"));
				if (result.get("resultCode").toString().equals("0000")) {
					router.settingForNext(jobSeq);
				}
			} else {
				// {"port":"9002","ip":"127.0.0.1","jobState":"03","jobSeq":56,"type":"1003","poolType":"1003"}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// 트랜스코더 진행률 큐
	@SuppressWarnings("unchecked")
	@RabbitListener(queues = "esjee.result.transcoder.progress")
	public void progress(final Message message) {
		String jobMessage = new String(message.getBody(), StandardCharsets.UTF_8);
		try {
			Map<String, Object> jobMap = mapper.readValue(jobMessage, Map.class);
			jdao.updateProgress(jobMap.get("convertProgress").toString(), Long.valueOf((String) jobMap.get("contentId")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
	// 카탈로거 이미지 정보 큐
	@SuppressWarnings("unchecked")
	@RabbitListener(queues = "esjee.result.cataloger")
	public void catalogerResult(final Message message) {
		String jobMessage = new String(message.getBody(), StandardCharsets.UTF_8);
		log.info(jobMessage);
		try {
			Map<String, Object> jobMap = mapper.readValue(jobMessage, Map.class);
			log.info(jobMap.toString());
			
			String thumb = (String) jobMap.get("thumbnail");
			List<Map<String, Object>> thumbList = mapper.readValue(thumb, List.class);
			
			for (Map<String, Object> job : thumbList) {
				String imagePath = job.get("imagePath").toString();
				int divider = imagePath.lastIndexOf("proxy");
				
				CatalogResultVO resultvo = new CatalogResultVO(
													Long.valueOf((String) jobMap.get("jobSeq")),
													Long.valueOf((String) jobMap.get("contentId")),
													Long.valueOf((String) jobMap.get("fileId")),
													imagePath.substring(divider - 1), 
													job.get("startTime").toString(), 
													job.get("endTime").toString(), 
													job.get("playTime").toString(), 
													Integer.parseInt(job.get("startFrame").toString()), 
													Integer.parseInt(job.get("endFrame").toString()), 
													Integer.parseInt(job.get("playFrame").toString()));
				
				catalogerDAO.insertCatalogerResult(resultvo);
			}
			
//			jdao.updateProgress(jobMap.get("convertProgress").toString(), Long.valueOf((String) jobMap.get("contentId")));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}
