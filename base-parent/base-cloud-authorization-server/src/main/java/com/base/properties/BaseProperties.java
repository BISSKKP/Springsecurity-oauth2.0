package com.base.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "base")
public class BaseProperties {
	
	/**
	 * 处理登陆请求
	 */
	private  String processLoginUrl=BaseConstants.processLoginUrl;
	
	
	/**
	 * 第三方请求
	 */
	private String loginWithAuthen="/loginWithAuthen";
	
	/**
	 * 普通表单登陆请求
	 */
	private String loginWithForm="/loginWithForm";
	
	
	/**
	 * 登陆方式
	 */
	private LoginMethods loginMethod=LoginMethods.Form;
	
	/**
	 * 默认的授权账户id
	 */
	private String defAppId="base-app01";
	
	/**
	 * 默认的授权账户secret
	 */
	private String defSecret="123456";
	
	

}
