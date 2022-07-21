package com.videoboard.boot.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.videoboard.boot.interceptor.Interceptor;
import com.videoboard.boot.security.UserMethodArgumentResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/**")
//				.addResourceLocations("file:///C:/Users/sj_nb_028/Documents/");
				.addResourceLocations("file:////root/videoboard/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new Interceptor())
				.addPathPatterns("/video/**")
				.excludePathPatterns("/video/list", "/video/register", "/video/detail",
									"/video/modify", "/video/");
		// comment도 넣어야됨
	}

	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new UserMethodArgumentResolver());
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
	}
}
