package com.base.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.base.security.core.authentication.mobile.filter.SmsCodeAuthenticationFilter;
import com.base.security.core.properties.SecurityProperties;

/**
 * 自定义认证流程重写
 * 
 * @author ACID
 *
 */
@Component
public class SmsCodeAuthenticationSecurityConfig
		extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private AuthenticationSuccessHandler seAuthenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler seAuthenticationFailHandler;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void configure(HttpSecurity http) throws Exception {

		
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter(
				new AntPathRequestMatcher(securityProperties.getCode().getSms().getDealUrl(), "POST"));

		// 设置manager
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		// 设置成功处理器
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(seAuthenticationSuccessHandler);
		// 设置失败处理器
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(seAuthenticationFailHandler);

		// 设置失败处理器
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(seAuthenticationFailHandler);
		// 设置provider
		SmsCodeAuthenticationProvider authenticationProvider = new SmsCodeAuthenticationProvider();

		//provide 设置 userdetailservice
		  authenticationProvider.setUserDetailsService(userDetailsService);
		  http.authenticationProvider(authenticationProvider);
		
		  //添加流程
		  http.authenticationProvider(authenticationProvider)
			.addFilterAfter(smsCodeAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

	}

}
