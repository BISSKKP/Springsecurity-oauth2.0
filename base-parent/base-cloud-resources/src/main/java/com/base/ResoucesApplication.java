package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ResoucesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResoucesApplication.class, args);
	}
}
