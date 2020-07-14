package com.base.security.app.config;

import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import com.base.security.app.authentication.AuthenticationSuccessHandler;
import com.base.security.app.authentication.CustomAccessDeniedHandler;
import com.base.security.app.authentication.SeAuthenticationFailHandler;
import com.base.security.app.exception.AuthExceptionEntryPoint;
import com.base.security.app.social.OpenIdAuthenticationSecurityConfig;
import com.base.security.app.util.PasswordEncoderUtils;
import com.base.security.app.validate.code.RedisValidateCodeRepository;
import com.base.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.base.security.core.authorize.AuthorizeConfigManager;
import com.base.security.core.constants.SecurityConstants;
import com.base.security.core.properties.SecurityProperties;
import com.base.security.core.validate.code.filter.SmsCodeFilter;
import com.base.security.core.validate.code.filter.ValidateCodeFilter;

/**
 * 资源服务器
 * @author lqq
 *
 */
@Configuration
@EnableResourceServer
//@Order(6)
public class BaseResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	
	@Autowired
	private SeAuthenticationFailHandler authenticationFailHandler;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Autowired
	private SpringSocialConfigurer securitySocialConfig;
	
	@Autowired
	private RedisValidateCodeRepository redisValidateCodeRepository;
	
	@Autowired
	private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		//.addFilterBefore(ValidateCodeFilter(), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(smsCodeFilter(),UsernamePasswordAuthenticationFilter.class)
		.formLogin().loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
		.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
		.successHandler(authenticationSuccessHandler)
		.failureHandler(authenticationFailHandler)
		.and()
	
		.csrf().disable()
		
		.apply(smsCodeAuthenticationSecurityConfig)//短信自定义认证流程
		.and()
		.apply(securitySocialConfig)//增加自定义流程-spring security config
		.and()
		.apply(openIdAuthenticationSecurityConfig)//社交登录自定义流程
		;
		
		//通过管理类实现自动加载
				authorizeConfigManager.config(http.authorizeRequests());
	}
	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		  
		//这里把自定义异常加进去
		resources.tokenStore(tokenStore).authenticationEntryPoint(new AuthExceptionEntryPoint())
		.accessDeniedHandler(new CustomAccessDeniedHandler())
		;
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderUtils();
    }
	
	
	@Bean
	public SmsCodeFilter smsCodeFilter() throws ServletException{
		SmsCodeFilter smsCodeFilter=new SmsCodeFilter();
		smsCodeFilter.setAuthenticationFailureHandler(authenticationFailHandler);
		smsCodeFilter.setSecurityProperties(securityProperties);
		smsCodeFilter.afterPropertiesSet();
		smsCodeFilter.setValidateCodeRepository(redisValidateCodeRepository);
		return smsCodeFilter;
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository(){
		JdbcTokenRepositoryImpl tokenRepository=new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true);//主动创建表
		return tokenRepository;
	}
	
	@Bean
	public ValidateCodeFilter ValidateCodeFilter() throws ServletException{
		ValidateCodeFilter validateCodeFilter=new ValidateCodeFilter();
		
		validateCodeFilter.setSecurityProperties(securityProperties);
		validateCodeFilter.setAuthenticationFailureHandler(authenticationFailHandler);
		validateCodeFilter.afterPropertiesSet();
		validateCodeFilter.setValidateCodeRepository(redisValidateCodeRepository);
		
		return validateCodeFilter;
	}
	
	
	
	
	
	

}
