package com.videoboard.boot.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.videoboard.boot.domain.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
									WebDataBinderFactory binderFactory) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) return null;
		
		Object principal = auth.getPrincipal();
		Class<?> parameterType = parameter.getParameterType();
		if (principal instanceof MemberVO) {
			MemberVO mvo = (MemberVO) principal;
			if (MemberVO.class.equals(parameterType)) {
				mvo.setPwd(null);
				return mvo;
			}
		}
		return null;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> parameterType = parameter.getParameterType();
		return MemberVO.class.equals(parameterType);
	}

}
