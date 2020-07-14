package com.base.security.app.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.base.security.app.authentication.CustomAccessDeniedHandler;
import com.base.security.app.exception.AuthExceptionEntryPoint;
import com.base.security.core.properties.OAuth2ClientProperties;
import com.base.security.core.properties.SecurityProperties;

/**
 * 认证服务器
 * 
 * @author lqq
 *
 *         操作详情请阅读 app登录说明.txt
 */
@Configuration
@EnableAuthorizationServer
@Order(0)
public class BaseAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private PasswordEncoder passwordEncoderUtils;

	@Autowired
	private TokenStore tokenStore;
	
	@Autowired(required=false)
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	
	@Autowired(required=false)
	private TokenEnhancer jwtTokenEnhancer;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints.tokenStore(tokenStore)// 将授权信息保存再redis 中
				// 下面的配置主要用来指定"对正在进行授权的用户进行认证+校验"的类
				// 在实现了AuthorizationServerConfigurerAdapter适配器类后，必须指定下面两项
				.authenticationManager(authenticationManager).userDetailsService(userDetailsService);

		if(jwtAccessTokenConverter !=null&& jwtTokenEnhancer !=null){
			 //配置增强器链
            // 并利用增强器链将生成jwt的TokenEnhancer（jwtAccessTokenConverter）
            // 和我们扩展的TokenEnhancer设置到token的生成类中
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);
            endpoints.tokenEnhancer(enhancerChain)
                    //将JwtAccessTokenConverter设置到token的生成类中
                    .accessTokenConverter(jwtAccessTokenConverter)
            ;
		}
		
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		security.authenticationEntryPoint(new AuthExceptionEntryPoint())// 自定义异常
				.accessDeniedHandler(new CustomAccessDeniedHandler());// 自定义授权失败

	}

	// 重写此方法时 配置文件中 失效
	/**
	 * security: oauth2: client: clientId: base-app clientSecret:
	 * base-app-secret
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		// 可以选择多个地址
		// clients.inMemory().withClient("base-app")
		// .secret("base-app-secret")
		// .accessTokenValiditySeconds(7200)
		// .authorizedGrantTypes("refresh_token","password")//指定可用授权模式，共4
		// .scopes("all","wirte","read")//指定权限，主动配置之后请求时可以不带参数。制定之后一定要在指定的内容中选择
		//
		//// .and()
		//// .withClient("XXX")//可通过and配置多个
		// ;
		InMemoryClientDetailsServiceBuilder builder = clients.inMemory();

		if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
			for (OAuth2ClientProperties clientProperties : securityProperties.getOauth2().getClients()) {
				builder.withClient(clientProperties.getClientId())
						.secret(
								passwordEncoderUtils.encode(clientProperties.getClientSecret())//再 没有重写 WebSecurityConfigurerAdapter中 passwordEncoder 密码加密时需要此方法
//								clientProperties.getClientSecret()
								)
						.accessTokenValiditySeconds(clientProperties.getAccessTokenValiditySeconds())
						.authorizedGrantTypes(clientProperties.getAuthorizedGrantTypes())
						.refreshTokenValiditySeconds(clientProperties.getRefreshTokenValiditySeconds())
						.scopes(clientProperties.getScopes());
			}

		}

	}

}
