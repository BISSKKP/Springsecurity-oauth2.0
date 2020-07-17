package com.base.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	

}
