package com.base.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.base.sso.utils.CustomPasswordEncoder;



@SpringBootApplication
@EnableTransactionManagement
public class SSOAppliaction {

	public static void main(String[] args) {
		SpringApplication.run(SSOAppliaction.class, args);
	}
	
	@Bean
	public PasswordEncoder customPasswordEncoder() {
		return new CustomPasswordEncoder();
	}
	
	
}
