package com.base.sso.client.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 与 @EnableOAuth2Sso  只能二选一
 * @author lqq
 *
 */
@Configuration
@EnableResourceServer
@Order(9)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	 @Override
	    public void configure(HttpSecurity http) throws Exception {
	        http
	                .csrf().disable()
	                .exceptionHandling()
	                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
	            .and()
	                .authorizeRequests()
	                .antMatchers("/login.html","/favicon.ico").permitAll()
	                .anyRequest().authenticated()
	            .and()
	                .httpBasic();
	    }
	
	
	
}
