package com.base.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	
	@GetMapping("/get")
	public Object get() {
		
		return UUID.randomUUID().toString();
	}
	
	
}
