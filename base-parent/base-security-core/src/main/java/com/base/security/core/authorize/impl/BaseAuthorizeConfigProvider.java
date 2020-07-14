package com.base.security.core.authorize.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.base.security.core.authorize.AuthorizeConfigProvider;
import com.base.security.core.constants.SecurityConstants;
import com.base.security.core.properties.SecurityProperties;

@Component
public class BaseAuthorizeConfigProvider implements AuthorizeConfigProvider {
	
	@Autowired
	private SecurityProperties securityProperties;
	

	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		
		config.antMatchers(
				SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
				securityProperties.getBrowser().getLoginPage(),
				SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*"
				,SecurityConstants.DEFAULT_REGIST_PROCESSING_URL//处理登录
				,securityProperties.getBrowser().getSession().getSessionInvalidUrl()//session 失效
				,securityProperties.getBrowser().getSignUpUrl()//注册界面
				,securityProperties.getBrowser().getLogoutUrl() //退出登录跳转的界面
				
				).permitAll();
	}

}
