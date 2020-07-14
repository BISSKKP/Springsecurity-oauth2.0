package com.base.security.browser.config;

import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import com.base.security.browser.authentication.AuthenticationSuccessHandler;
import com.base.security.browser.authentication.SeAuthenticationFailHandler;
import com.base.security.browser.session.SessionValidateCodeRepository;
import com.base.security.browser.utils.PasswordEncoderUtils;
import com.base.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.base.security.core.authorize.AuthorizeConfigManager;
import com.base.security.core.constants.SecurityConstants;
import com.base.security.core.properties.SecurityProperties;
import com.base.security.core.validate.code.filter.SmsCodeFilter;
import com.base.security.core.validate.code.filter.ValidateCodeFilter;


@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private SeAuthenticationFailHandler authenticationFailHandler;
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	
	@Autowired
	private SpringSocialConfigurer securitySocialConfig;
	
	@Autowired
	private SessionValidateCodeRepository sessionValidateCodeRepository;
	
	/**
	 * session 失效策略
	 */
	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;
	
	/**
	 * session并发策略
	 */
	@Autowired
	private SessionInformationExpiredStrategy baseExpiredSessionStrategy;
	
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http
		.addFilterBefore(ValidateCodeFilter(), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(smsCodeFilter(),UsernamePasswordAuthenticationFilter.class)
		.formLogin().loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
		.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
		.successHandler(authenticationSuccessHandler)
		.failureHandler(authenticationFailHandler)
		
		.and().
		//记住我相关配置
			 rememberMe().tokenRepository(persistentTokenRepository())//记住账号
			
			.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//token超时时间
			.userDetailsService(userDetailsService)
		
		//session失效地址
		.and().sessionManagement().
//				invalidSessionUrl(securityProperties.getBrowser().getSession().getSessionInvalidUrl())
				//指定最大的session并发数量---即一个用户只能同时在一处登陆（腾讯视频的账号好像就只能同时允许2-3个手机同时登陆）
				invalidSessionStrategy(invalidSessionStrategy)
				.maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())//设置1时一个账户只能拥有一个session
				//当超过指定的最大session并发数量时，阻止后面的登陆（感觉貌似很少会用到这种策略）
				.maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())//true时当session 达到最大数量时禁止后面session登陆行为
				//超过最大session并发数量时的策略
				.expiredSessionStrategy(baseExpiredSessionStrategy)
				.and()
		.and()
			
		.logout()
			.logoutUrl(securityProperties.getBrowser().getDealLogoutUrl())//退出登录时请求地址
//			.logoutSuccessUrl("/base-logout.html")//退出登录成功之后跳转页面
			.logoutSuccessHandler(logoutSuccessHandler)//退出登录处理器
			.deleteCookies("JSESSIONID")//删除cookie
		.and()
		
		.csrf().disable()
		
		.apply(smsCodeAuthenticationSecurityConfig)//短信自定义认证流程
		.and()
		.apply(securitySocialConfig)//增加自定义流程-spring security config
		;
		
		//通过管理类实现自动加载
		authorizeConfigManager.config(http.authorizeRequests());
		
	}
	
	@Bean
	public SmsCodeFilter smsCodeFilter() throws ServletException{
		SmsCodeFilter smsCodeFilter=new SmsCodeFilter();
		smsCodeFilter.setAuthenticationFailureHandler(authenticationFailHandler);
		smsCodeFilter.setSecurityProperties(securityProperties);
		smsCodeFilter.afterPropertiesSet();
		smsCodeFilter.setValidateCodeRepository(sessionValidateCodeRepository);
		return smsCodeFilter;
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	private PasswordEncoder passwordEncoder() {
        return new PasswordEncoderUtils();
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
		validateCodeFilter.setValidateCodeRepository(sessionValidateCodeRepository);
		
		return validateCodeFilter;
	}
	
	
	
	
	
	
	
	
}
