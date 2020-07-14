package com.base.security.core.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import com.base.security.core.properties.QQProperties;
import com.base.security.core.properties.SecurityProperties;
import com.base.security.core.social.qq.connect.QQConnectFactory;
import com.base.security.core.social.support.SocialAutoConfigurerAdapter;

@Configuration
@ConditionalOnProperty(prefix="base.security.social.qq",name="app-id")//当系统存在此配置时才起作用
public class QQAutoConfig extends SocialAutoConfigurerAdapter{
	
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
			QQProperties properties=securityProperties.getSocial().getQq();
		
		return new QQConnectFactory(properties.getProviderId(), properties.getAppId(), properties.getAppSecret());
	}
	

	
	
	
}
