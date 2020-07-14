package com.base.security.core.validate.code.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.base.security.core.constants.SecurityConstants;
import com.base.security.core.properties.ImageCodeProperties;
import com.base.security.core.properties.SecurityProperties;
import com.base.security.core.validate.code.ImageCode;
import com.base.security.core.validate.code.ValidateCodeRepository;
import com.base.security.core.validate.code.ValidateCodeType;
import com.base.security.core.validate.code.exception.ValidateCodeException;

import lombok.extern.slf4j.Slf4j;

/**
 * 验证码过滤器
 * 
 * @author ACID
 *
 */
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

	private SecurityProperties securityProperties;
	
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	
	private AntPathMatcher antPathMatcher=new AntPathMatcher();
	
	private ValidateCodeRepository validateCodeRepository;

	private Set<String> urls=new HashSet<>();

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();

		ImageCodeProperties imp = securityProperties.getCode().getImage();
		if (StringUtils.isNotBlank(imp.getUrl())) {
			String[] patterns = imp.getUrl().split(",");
			for (String url : patterns) {
				urls.add(url);
			}
		}

		urls.add("/dologin");

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 验证验证码是否正确
		boolean action = false;
		for (String url : urls) {
			if (antPathMatcher.match(url, request.getRequestURI())) {
				action = true;
				break;
			}
		}
		if (action) {
			try {
				log.info("验证码验证url:" + request.getRequestURI());
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				// 验证码异常
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
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
		
//		ImageCode codeInSession=(ImageCode) sessionStrategy.getAttribute(servletWebRequest, ValidateCodeController.SESSION_IMAGE_KEY);
		
		ImageCode codeInSession=(ImageCode)validateCodeRepository.get(servletWebRequest, ValidateCodeType.IMAGE);
		
		String codeInRequest=ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE);
		
		if(null==codeInRequest||"".equals(codeInRequest.trim())){
			throw new ValidateCodeException("验证码的值不能为空");
		}
		
		if(null==codeInSession){
			throw new ValidateCodeException("验证码不存在");
		}
		
		if(codeInSession.isExpried()){
			//sessionStrategy.removeAttribute(servletWebRequest,  ValidateCodeController.SESSION_IMAGE_KEY);
			validateCodeRepository.remove(servletWebRequest, ValidateCodeType.IMAGE);
			
			throw new ValidateCodeException("验证码已过期");
		}
		
		if(!codeInSession.getCode().equalsIgnoreCase(codeInRequest)){
			throw new ValidateCodeException("验证码不匹配");
		}
		//sessionStrategy.removeAttribute(servletWebRequest,  ValidateCodeController.SESSION_IMAGE_KEY);
		validateCodeRepository.remove(servletWebRequest, ValidateCodeType.IMAGE);
		
		
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	public ValidateCodeRepository getValidateCodeRepository() {
		return validateCodeRepository;
	}

	public void setValidateCodeRepository(ValidateCodeRepository validateCodeRepository) {
		this.validateCodeRepository = validateCodeRepository;
	}

	
	
	
}
