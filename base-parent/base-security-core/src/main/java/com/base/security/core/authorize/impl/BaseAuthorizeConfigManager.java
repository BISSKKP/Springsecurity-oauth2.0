package com.base.security.core.authorize.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import com.base.security.core.authorize.AuthorizeConfigManager;
import com.base.security.core.authorize.AuthorizeConfigProvider;

@Component
public class BaseAuthorizeConfigManager implements AuthorizeConfigManager {

	
	/**
	 * 将系统中所有实现了AuthorizeConfigProvider 接口的实现类拿出来
	 */
	@Autowired
	private Set<AuthorizeConfigProvider> authorizeConfigProviders;
	
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		for(AuthorizeConfigProvider authorizeConfigProvider:authorizeConfigProviders){
			authorizeConfigProvider.config(config);
		}
		//除了上面的配置其他请求全部要身份验证
		config.anyRequest().authenticated();
	}

}
