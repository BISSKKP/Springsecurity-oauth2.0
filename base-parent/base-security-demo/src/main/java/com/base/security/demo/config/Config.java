package com.base.security.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;


@Configuration
public class Config {
	

	
	@Bean(name={"connect/weixinConnect","connect/weixinConnected","weixinConnectionView"})
	public View weixinConnectionView(){
		
		
		return new WeiXinConnectionView();
	}
	
	
//	@Bean
//	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
//		
//		return new SeesionExpire(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
//	}
	
	
}
