package com.base.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.base.security.core.properties.SecurityProperties;
import com.base.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.base.security.core.validate.code.sms.SmsCodeSender;

@Configuration
public class ValidateCodeBeanConfig {

	@Autowired
	private SecurityProperties securityProperties;
	
	
	@Bean
	@ConditionalOnMissingBean(name="imageCodeGenerator")//spring 容器在启动时会主动找一下这个名字的bean，如果有了下面的代码不会执行
	public ValidateCodeGenerator imageCodeGenerator(){
		
		ImageCodeGenerator imageCodeGenerator=new ImageCodeGenerator();
		
		imageCodeGenerator.setSecurityProperties(securityProperties);
		
		return imageCodeGenerator;
		
	}
	
	@Bean
	@ConditionalOnMissingBean(name="smsCodeSender")//spring 容器在启动时会主动找一下这个名字的bean，如果有了下面的代码不会执行
	public SmsCodeSender smsCodeSender(){
		DefaultSmsCodeSender codeSender=new DefaultSmsCodeSender();
		
		return codeSender;
	}
	
	
	
}
