package com.base.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.base.exception.AuthExceptionEntryPoint;
import com.base.exception.CustomExceptionTranslator;
import com.base.exception.SelfAccessDeniedHandler;
import com.base.utils.PasswordEncoderUtils;

@Configuration
@EnableAuthorizationServer
@Order(-1)
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthExceptionEntryPoint authenticationEntryPoint;

	@Autowired
	private SelfAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private CustomExceptionTranslator customExceptionTranslator;
	
	@Autowired
	private TokenEnhancer customeTokenEnhancer;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

	clients.inMemory()
				.withClient("base-app01")
				.secret(new PasswordEncoderUtils().encode("123456"))
				.authorizedGrantTypes("password","authorization_code","refresh_token")
				.scopes("all")
				.autoApprove(true)
				.redirectUris("http://127.0.0.1:8000/login")
			.and()
				.withClient("base-app02")
				.secret(new PasswordEncoderUtils().encode("123456"))
				.authorizedGrantTypes("password","authorization_code","refresh_token")
				.scopes("all")
				.autoApprove(true)
				.redirectUris("http://127.0.0.1:8083/login")
			.and()
			.withClient("base-app04")
			.secret(new PasswordEncoderUtils().encode("123456")
					)
			.authorizedGrantTypes("password","authorization_code","refresh_token")
			.scopes("all")
			.autoApprove(true)
			.redirectUris("http://127.0.0.1:8084/login")
			
			;

	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		endpoints.tokenStore(jwtTokenStore())
		
		.authenticationManager(authenticationManager).userDetailsService(userDetailsService)
		.exceptionTranslator(customExceptionTranslator);
		
		 TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
         List<TokenEnhancer> enhancers = new ArrayList<>();
         enhancers.add(customeTokenEnhancer);
         enhancers.add(jwtAccessTokenConverter());
         enhancerChain.setTokenEnhancers(enhancers);
		endpoints.tokenEnhancer(enhancerChain).accessTokenConverter(jwtAccessTokenConverter());
		
		
	}
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		
		security
		.allowFormAuthenticationForClients()
		
		.checkTokenAccess("permitAll()")//实现每个节点之间的负载均衡时需要配置此选项
//		.tokenKeyAccess("isAuthenticated()")
		
		
		.accessDeniedHandler(accessDeniedHandler)
		.authenticationEntryPoint(authenticationEntryPoint)
		; 
		
	}
	
	
	 /***
     * 配置JwtTokenStore ---> TokenStore只负责token的存储，不负责token的生成
     * @return
     */
	@Bean
	public TokenStore jwtTokenStore(){
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	 /***
     * JwtAccessTokenConverter 其实就是一个TokenEnhancer
     * 通过阅读源码可知：TokenEnhancer是对生成的Token进行后续处理的（或者说增强），
     * 其实JwtAccessTokenConverter就是将默认生成的token做进一步处理使其成为一个JWT
     * @return
     */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(){
		
		 JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            //加入密签 --- 一定要保护好
            converter.setSigningKey("base");
            return converter;
	}
	
	
	
	
	
	
}
