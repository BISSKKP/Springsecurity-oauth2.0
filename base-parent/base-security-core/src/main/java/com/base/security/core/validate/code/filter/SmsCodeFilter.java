package com.base.security.core.validate.code.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.base.security.core.constants.SecurityConstants;
import com.base.security.core.properties.SecurityProperties;
import com.base.security.core.properties.SmsCodeProperties;
import com.base.security.core.validate.code.ValidateCode;
import com.base.security.core.validate.code.ValidateCodeRepository;
import com.base.security.core.validate.code.ValidateCodeType;
import com.base.security.core.validate.code.exception.ValidateCodeException;

import lombok.extern.slf4j.Slf4j;

/**
 * 短信验证码 验证过滤器
 * @author ACID
 *
 */
@Slf4j
public class SmsCodeFilter  extends OncePerRequestFilter implements InitializingBean {
	
	
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
	
	private Set<String> urls=new HashSet<>();
	
	private SecurityProperties securityProperties;
	
	private ValidateCodeRepository validateCodeRepository;
	
	/**
	 * spring 工具
	 */
	private AntPathMatcher pathMatcher=new AntPathMatcher();

	
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		 SmsCodeProperties smsCodeProperties=securityProperties.getCode().getSms();
		 if(StringUtils.isNotBlank(smsCodeProperties.getUrl())){
			 String[] url=smsCodeProperties.getUrl().split(",");
				for(String config:url){
					urls.add(config);
				}
		 }
		
		urls.add(smsCodeProperties.getDealUrl());//处理发送的请求
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		
		boolean action =false;
		
		for(String url:urls){
			if(pathMatcher.match(url, request.getRequestURI())){
				action=true;
				break;
			}
		}
		if(action){
			try {
				log.info("短信验证url:"+request.getRequestURI());
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				//短信验证码异常
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return ;
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 验证验证码
	 * @param servletWebRequest
	 * @throws ServletRequestBindingException
	 */
	private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
		
//		ValidateCode codeInSession=(ValidateCode) sessionStrategy.getAttribute(servletWebRequest, ValidateCodeController.SESSION_SMS_KEY);
		
		ValidateCode codeInSession=(ValidateCode) 	validateCodeRepository.get(servletWebRequest, ValidateCodeType.SMS);
	
		String codeInRequest=ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS);
		
		if(null==codeInRequest||"".equals(codeInRequest.trim())){
			throw new ValidateCodeException("短信验证码的值不能为空");
		}
		
		if(null==codeInSession){
			throw new ValidateCodeException("短信验证码不存在");
		}
		
		if(codeInSession.isExpried()){
//			sessionStrategy.removeAttribute(servletWebRequest,  ValidateCodeController.SESSION_SMS_KEY);
			validateCodeRepository.remove(servletWebRequest, ValidateCodeType.SMS);
			throw new ValidateCodeException("短信验证码已过期");
		}
		
		if(!codeInSession.getCode().equalsIgnoreCase(codeInRequest)){
			throw new ValidateCodeException("短信验证码不匹配");
		}
//		sessionStrategy.removeAttribute(servletWebRequest,  ValidateCodeController.SESSION_SMS_KEY);
		
		validateCodeRepository.remove(servletWebRequest, ValidateCodeType.SMS);
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public SessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}

	public void setSessionStrategy(SessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	public ValidateCodeRepository getValidateCodeRepository() {
		return validateCodeRepository;
	}

	public void setValidateCodeRepository(ValidateCodeRepository validateCodeRepository) {
		this.validateCodeRepository = validateCodeRepository;
	}

	
}
