package com.videoboard.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = { "com.videoboard.boot.repository", "com.videoboard.boot.ctrl", "com.videoboard.boot.service",
		"com.videoboard.boot.handler", "com.videoboard.boot.config", "com.videoboard.boot.security", "com.videoboard.boot.Component" })
public class VideoboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoboardApplication.class, args);
	}

}
