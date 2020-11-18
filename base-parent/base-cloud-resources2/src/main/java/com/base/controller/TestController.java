package com.base.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {
	
	@Value("${server.port}")
	private String port;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String testServiceUrl="http://CLOUD-TEST-SERVICE";
	
	@GetMapping("/get")
	public Object get() {
		
		log.info("测试得到数据："+restTemplate.getForEntity(testServiceUrl+"/t", String.class).toString());
		
		return UUID.randomUUID().toString()+"+++port: "+port;
	}
	
	
	@GetMapping("/user")
	public Authentication getInfo(Authentication authentication) {
		
		return  authentication;
	}
	
	

}
