package com.base.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ErrorLogController {

	@RequestMapping("/saveErrorLogger")
	public Object saveLog(HttpServletResponse response,HttpServletRequest request) {
		log.info("获得错误日志。。。。");
		return UUID.randomUUID().toString();
	}
	
	
}
