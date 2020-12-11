package com.base.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {
	
	@Value("${server.port}")
	private String port;
	
	@GetMapping("/get")
	public Object get() {
		
		
		return UUID.randomUUID().toString()+"+++port: "+port;
	}
	
	@GetMapping("/user")
	public Authentication getInfo(Authentication authentication) {
		
		return  authentication;
	}
	
	/**
	 * 开启负载均衡
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		
		return new RestTemplate();
	}
	

}
