package com.base.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.base.authentication.AuthenticationSuccessHandler;
import com.base.authentication.SeAuthenticationFailHandler;
import com.base.exception.AuthExceptionEntryPoint;
import com.base.exception.SelfAccessDeniedHandler;
import com.base.utils.PasswordEncoderUtils;

@Configuration
@EnableResourceServer
@Order(9)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private AuthExceptionEntryPoint authenticationEntryPoint;

	@Autowired
	private SelfAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private SeAuthenticationFailHandler authenticationFailHandler;
	
	@Autowired
	private UserDetailsService userService;
	
	 @Override
	    public void configure(HttpSecurity http) throws Exception {
	        http
	        .formLogin().loginProcessingUrl("/dologin")
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailHandler)
            .and()
	                .csrf().disable()
	                .exceptionHandling()
	                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
	            .and()
	                .authorizeRequests()
	                .antMatchers("/actuator/*","/login.html","/login","/dologin","/favicon.ico","/oauth/**").permitAll()
	                .anyRequest().authenticated()
	            .and()
	            .userDetailsService(userService)
	                ;
	        
	        
	    }
	
	 @Bean
		public PasswordEncoder passwordEncoder() {
	        return new PasswordEncoderUtils();
	    }
		
	 @Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		 resources.authenticationEntryPoint(authenticationEntryPoint) //自定义错误信息
		 .accessDeniedHandler(accessDeniedHandler) //自定义没有权限信息
		 ;
		  
		 
	}
	
	
}
