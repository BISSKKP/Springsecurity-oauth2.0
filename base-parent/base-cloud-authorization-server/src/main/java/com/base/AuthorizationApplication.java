package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.base.utils.CustomPasswordEncoder;


@SpringBootApplication
@EnableEurekaClient
public class AuthorizationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AuthorizationApplication.class, args);
	}
	
//	@Bean
//	public PasswordEncoder customPasswordEncoder() {
//		return new CustomPasswordEncoder();
//	}

}
