package com.base.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService ;
	
	@Autowired
    private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory()
			.withClient("base-app01")
			.secret("123456")
			.authorizedGrantTypes("password","authorization_code","refresh_token")
			.scopes("all")
			.autoApprove(true)
			.redirectUris("http://127.0.0.1:8082/client1/login")
		.and()
			.withClient("base-app02")
			.secret("123456")
			.authorizedGrantTypes("password","authorization_code","refresh_token")
			.scopes("all")
			.autoApprove(true)
			.redirectUris("http://127.0.0.1:8083/client2/login")
		.and()
			;
	}
	
	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter())
		.authenticationManager(authenticationManager).userDetailsService(userDetailsService)//开启password 模式需要注入 authenticationManager
		
		;
	}
	

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		
		
		security.
		// 开启/oauth/token_key验证端口认证权限访问
		tokenKeyAccess("isAuthenticated()")
	//  开启/oauth/check_token验证端口认证权限访问
		.tokenKeyAccess("isAuthenticated()")
		.allowFormAuthenticationForClients()
		.accessDeniedHandler(new SsoAccessDeniedHandler())
		.authenticationEntryPoint(new AuthExceptionEntryPoint())
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
