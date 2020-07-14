package com.base.security.browser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.base.security.browser.logout.BaseLogoutSuccessHandler;
import com.base.security.browser.session.BaseExpiredSessionStrategy;
import com.base.security.browser.session.BaseInvalidSessionStrategy;
import com.base.security.core.properties.SecurityProperties;

/**
 * Session 配置
 * @author lqq
 *
 */
@Configuration
public class BrowserSecurityBeanConfig {
	
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Bean
	//用户可以通过实现一个InvalidSessionStrategy类型的bean来覆盖掉默认的实现--》NRSCInvalidSessionStrategy
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy(){
		
		return new BaseInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}
	
	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
		
		return new BaseExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(LogoutSuccessHandler.class)
	public LogoutSuccessHandler logoutSuccessHandler(){
		BaseLogoutSuccessHandler baseLogoutSuccessHandler=new BaseLogoutSuccessHandler();
		
		//退出登录
		baseLogoutSuccessHandler.setLogoutUrl(securityProperties.getBrowser().getLogoutUrl());
		
		return baseLogoutSuccessHandler;
	}
	

}
