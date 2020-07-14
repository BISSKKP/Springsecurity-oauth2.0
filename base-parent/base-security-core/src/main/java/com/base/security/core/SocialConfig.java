package com.base.security.core;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import com.base.security.core.properties.SecurityProperties;
import com.base.security.core.social.SocialAuthenticationFilterPostProcessor;
import com.base.security.core.social.jdbc.BaseJdbcUsersConnectionRepository;


/**
 * https://github.com/nieandsun/security
 * @author lqq
 *
 */
@Configuration
@EnableSocial
public class SocialConfig  extends SocialConfigurerAdapter{

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired(required=false)//可能不提供
	private ConnectionSignUp connectionSignUp;
	
	@Autowired(required=false)//可能不提供
	private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcess;
	
	
	/**
	 * create table UserConnection (userId varchar(255) not null,
	providerId varchar(255) not null,
	providerUserId varchar(255),
	rank int not null,
	displayName varchar(255),
	profileUrl varchar(512),
	imageUrl varchar(512),
	accessToken varchar(512) not null,
	secret varchar(512),
	refreshToken varchar(512),
	expireTime bigint,
	primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);
	 */
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//		return super.getUsersConnectionRepository(connectionFactoryLocator);
		
		//connectionFactoryLocator 中有数据表数据
		
		BaseJdbcUsersConnectionRepository repository=new BaseJdbcUsersConnectionRepository(dataSource, connectionFactoryLocator,Encryptors.noOpText() );
		
		//数据库表的前缀
		repository.setTablePrefix("cloud_");
		if(connectionSignUp!=null){
			//可在授权登录时默认创建账户
			repository.setConnectionSignUp(connectionSignUp);
		}
		
		return repository; 
	}
	
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public ConnectionRepository connectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null) {
	        throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
	    }
	    return getUsersConnectionRepository(connectionFactoryLocator).createConnectionRepository(authentication.getName());
	}
	
	/**
	 * 设置social 登录地址
	 * @return
	 */
	@Bean
	public SpringSocialConfigurer securitySocialConfig(){
		SocialConfigurer configurer=new SocialConfigurer(securityProperties.getSocial().getFilterProcessesUrl());
		configurer.setSocialAuthenticationFilterPostProcess(socialAuthenticationFilterPostProcess);
		
		configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());//注册表单
		
		return configurer;
	}
	
	/**
	 * 拿到授权登录账号的相关信息
	 * @param connectionFactoryLocator
	 * @return
	 */
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
	    return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
//	    return new ProviderSignInUtils(connectionFactoryLocator, connectionRepository(connectionFactoryLocator));
	}
	
	
	
}
