package com.base.security.demo.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.base.security.core.authorize.AuthorizeConfigProvider;
@Component
public class DemoAuthorizeConfigPrivoder implements AuthorizeConfigProvider {

	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		
		config.antMatchers("/demo.html").permitAll();
		
	}

}
