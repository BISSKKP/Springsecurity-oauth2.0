package com.base.security.core.validate.code.sms;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

	@Override
	public void send(String mobile, String code) {
		log.info("向手机发送短信验证码："+mobile+" code: "+code);	
		
	}
}
