package com.base.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="base.security")
public class SecurityProperties {

	private BrowserPorperties browser=new BrowserPorperties();
	
	private ValidateCodeProperties code=new ValidateCodeProperties();
	
	private SocialProperties social=new SocialProperties(); 

	private OAuth2Properties oauth2 =new OAuth2Properties();
	
}
