package com.base.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * //@EnableOAuth2Sso 只能二选一
 * @author lqq
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	 @Override
	    public void configure(HttpSecurity http) throws Exception {
	        http
	                .csrf().disable()
	                .exceptionHandling()
	                .authenticationEntryPoint(
//	                		(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
	                		authenticationEntryPoint
	                		
	                		)
	            .and()
	                .authorizeRequests()
	                .antMatchers("/login.html","/favicon.ico","/oauth/**").permitAll()
	                .anyRequest().authenticated()
	            .and()
	                .httpBasic()
//	            .formLogin()
//	            .failureHandler(authenticationFailureHandler)
	                
	                
	                ;
	    }
//	
	
	
}
