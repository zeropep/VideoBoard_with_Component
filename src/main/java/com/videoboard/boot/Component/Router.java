package com.videoboard.boot.Component;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.videoboard.boot.contant.ComponentConstants;
import com.videoboard.boot.domain.FileVO;
import com.videoboard.boot.domain.JobVO;
import com.videoboard.boot.repository.FileDAO;
import com.videoboard.boot.repository.JobDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Router {
	
	@Autowired
	private JobDAO jdao;
	
	@Autowired
	private FileDAO fdao;
	
	@Autowired
	private MessageBuilder messageBuilder;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@SuppressWarnings("unchecked")
	public String buildMessage(JobVO jvo, FileVO fvo) {
		// 이미 끝난 작업 확인
		if (jvo.getStatus() == "03") {
			return null;
		}
		
		// 필요 컴포넌트별 송신
		String message = "";
		JSONObject jsonObj = new JSONObject();
		JSONObject input = new JSONObject();
		JSONObject output = new JSONObject();
		JSONArray arr = new JSONArray();
		
		switch (jvo.getComponent()) {
		case ComponentConstants.COMPONENT_1001:
			message = messageBuilder.transcoderMessage(jvo, fvo);
			break;
		case ComponentConstants.COMPONENT_1003:
			message = messageBuilder.CatalogerMessage(jvo, fvo);
			break;

		default:
			break;
		}
		
				
		this.CallComponent(jvo.getComponent(), message);
		jvo.setStatus("02");
		jdao.updateJob(jvo);
		
		return "Message Sent!";
	}
	
	public void CallComponent(String component, String message) {
		String exchangeName = "";
		String routingName = "";
		
		exchangeName = "esjee.component.job";
		switch (component) {
		case "1001":
//			exchangeName = "esjee.encoder";
//			routingName = "esjee.transcoder";
			routingName = "esjee.pool.1001.125";
			
			break;
		case "1003":
//			exchangeName = "esjee.catalog";
			routingName = "esjee.pool.1003.126";
			
			break;

		default:
			break;
		}
		rabbitTemplate.convertAndSend(exchangeName, routingName, message);
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

	@Transactional(rollbackFor = Exception.class)
	public void settingForNext(long jobSeq) {
		// 1. 기존 job 종료선언
		jdao.finishJob(jobSeq);
		
		JobVO jvo = jdao.selectJob(jobSeq);
		
		switch (jvo.getComponent()) {
		case "1001":
			// 2. job 정보 가져오기
			FileVO fvo = fdao.selectFile(jvo.getVno());
			
			// 3. cataloger 태우기위한 셋팅
			jvo.setComponent("1003");
			
			// 4. job jobseq 새로받기
			jdao.insertJob(jvo);
			
			// 5. 메세지 빌더로 보내기
			this.buildMessage(jvo, fvo);
			break;
		case "1003":
			// 2. poster 생성완료
			fdao.posterCreated(jvo.getFno());
			
			break;

		default:
			break;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void connect(Map<String, Object> request) {
		log.info(">>> Connection Start");
		
		JSONObject jsonObj = new JSONObject();
		JSONObject queueList = new JSONObject();
		JSONArray arr = new JSONArray();
		
		String componentNo = "";
		switch (request.get("type").toString()) {
		case "1001":
			componentNo = "125";
			break;
		case "1003":
			componentNo = "126";
		break;
		case "1011":
			componentNo = "127";
			break;

		default:
			break;
		}
		
		String poolKey = String.format("%s.%s.%s", request.get("type").toString(), request.get("ip").toString(), request.get("port").toString());
		String key = String.format("%s.%s.%s", request.get("ip").toString(), request.get("port").toString(), request.get("type").toString());
		
		arr.add(String.format("%s.%s.%s", "esjee.pool", request.get("type").toString(), componentNo));
		queueList.put("queueList", arr);
		jsonObj.put(poolKey, queueList);
		String message = jsonObj.toString();
		log.info(message);
		
		rabbitTemplate.convertAndSend("esjee.pool.info", String.format("%s.%s", "esjee.pool.info", key), message);
		log.info(">>> Connection Finish");
	}
}