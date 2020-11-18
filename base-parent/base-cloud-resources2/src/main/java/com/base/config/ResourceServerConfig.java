package com.base.config;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;


import lombok.extern.slf4j.Slf4j;

/**
 * //@EnableOAuth2Sso 只能二选一
 * 
 * @author lqq
 *
 */
@Configuration
@EnableResourceServer
@Slf4j
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

//	@Autowired(required = true)
//	private RemoteTokenServices remoteTokenServices;

	@Autowired
	public RestTemplate restTemplate;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	

	@Autowired
	private SelfAccessDeniedHandler accessDeniedHandler;
	

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(

				authenticationEntryPoint).and().authorizeRequests()
				.antMatchers("/login.html", "/favicon.ico", "/oauth/**", "/get").permitAll().anyRequest()
				.authenticated().and().httpBasic();

	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//			super.configure(resources);

		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override // 忽略 400
			public void handleError(ClientHttpResponse response) throws IOException {
				log.info("oauth2. 负载均衡 请求遇到错误：" + response.getRawStatusCode() + "--" + response.getBody());

				if (response.getRawStatusCode() != 400) {
					super.handleError(response);
				}
			}
		});
		RemoteTokenServices remoteTokenServices = tokenService();
		;
		if (Objects.nonNull(remoteTokenServices)) {
			remoteTokenServices.setRestTemplate(restTemplate);
			resources.tokenServices(remoteTokenServices);
		}
		//这里把自定义异常加进去
				resources.tokenStore(tokenStore).authenticationEntryPoint(new AuthExceptionEntryPoint())
				.accessDeniedHandler(accessDeniedHandler);
	}

	/**
	 * oauth2.0 负载均衡开启
	 * 
	 * @return
	 */
	@Primary
	@Bean
	public RemoteTokenServices tokenService() {
		RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setRestTemplate(restTemplate);
		tokenService.setCheckTokenEndpointUrl("http://CLOUD-AUTHORIZATION-SERVICE/oauth/check_token");
		tokenService.setClientId("base-app04");
		tokenService.setClientSecret("123456");
		return tokenService;
	}

}
