package com.videoboard.boot.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.videoboard.boot.domain.MemberVO;
import com.videoboard.boot.domain.VideoVO;
import com.videoboard.boot.enums.InterceptorErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class Interceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) return false;
		
		// 요청한 사람
		MemberVO requestUser = new MemberVO();
		Object principal = authentication.getPrincipal();
		if (principal instanceof MemberVO) {
			requestUser = (MemberVO) principal;
		}
//		log.info(requestUser.toString());
		
		// put(수정), delete(삭제) method에 작성자 검증과정 수행
		switch (request.getMethod()) {
		case "PUT":
			log.info(Boolean.toString(Long.valueOf(request.getParameter("mno")) == requestUser.getMno()));
			if (Long.valueOf(request.getParameter("mno")) != requestUser.getMno()) {
				log.info("return error");
			}
			break;
		case "DELETE":
			JSONObject vvo = this.getBodyFromRequest(request);
//			if ((Long) vvo.get("mno") != 3L) {
			if ((Long) vvo.get("mno") != requestUser.getMno()) {
				log.info("return error");
				response.sendError(0, null);
				this.setResponse(response);
//				throw new InterceptorException(InterceptorErrorCode.UNAUTHORIZED);
			}
			break;

		default:
			break;
		}
		
//		log.info(request.getParameterMap().toString());
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@SuppressWarnings("unchecked")
	private void setResponse(HttpServletResponse response) throws IOException {
		InterceptorErrorCode errorCode = InterceptorErrorCode.UNAUTHORIZED;
		
		JSONObject json = new JSONObject();
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setStatus(errorCode.getCode());
		
		json.put("code", errorCode.getCode());
		json.put("message", errorCode.getMessage());
		response.getWriter().print(json);
	}

	private JSONObject getBodyFromRequest(HttpServletRequest request) {
		String bodyJson = "";
        
		StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = null;
        //한줄씩 담을 변수
        String line = "";
        
        try {
        	//body내용 inputstream에 담는다.
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                br = new BufferedReader(new InputStreamReader(inputStream));
                //더 읽을 라인이 없을때까지 계속
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }else {
            	log.info("Data 없음");
            }
        } catch (IOException e) {
        	e.printStackTrace();
        } 
 
        bodyJson = stringBuilder.toString();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		try {
	        //json 형태로 변환하기
			jsonObject = (JSONObject) jsonParser.parse(bodyJson);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
}
