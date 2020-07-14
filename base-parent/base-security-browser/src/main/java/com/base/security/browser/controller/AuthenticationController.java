package com.base.security.browser.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.ajax.AjaxJson;
import com.base.common.ajax.AjaxUtils;
import com.base.security.core.properties.LoginType;
import com.base.security.core.properties.SecurityProperties;
import com.base.security.core.social.support.SocialUserInfo;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AuthenticationController {

	
	@Resource
	private SecurityProperties securityProperties;
	
	private RequestCache requestCache=new HttpSessionRequestCache();
	
	private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
	
//	@Autowired
//	private ProviderSignInUtils providerSignInUtils;
	
	

	@RequestMapping("/authen/require")
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public AjaxJson requireAuthentication(HttpServletResponse response,HttpServletRequest request) throws IOException{
		SavedRequest savedRequest=requestCache.getRequest(request	, response);
		
		if(savedRequest != null){
			String target=savedRequest.getRedirectUrl();
			log.info("引发跳转的请求是："+target);
//			if(StringUtils.endsWithIgnoreCase(target, ".html")){
//				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
//			}
			LoginType loginType=securityProperties.getBrowser().getLoginType();
			if(LoginType.REDIRECT.equals(loginType)||(LoginType.MIX.equals(loginType)&&!AjaxUtils.isAjaxRequest(request))){
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}
		}
		return AjaxJson.error("401", "访问的服务需要身份认证");
	}
	
	/**
	 * 获取到第三方信息
	 * @param request
	 * @param authenticationToken
	 * @return
	 */
	@GetMapping("/social/user")
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request,SocialAuthenticationToken authenticationToken){
		SocialUserInfo userInfo=new SocialUserInfo();
//      Connection<?> connection =providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
//	//	旧版方法无法获取connection
//      if(connection!=null){
//    	  userInfo.setHeadimg(connection.getImageUrl());
//    	  userInfo.setProviderId(connection.getKey().getProviderId());
//    	  userInfo.setNickname(connection.getDisplayName());
//    	  userInfo.setProviderUserId(connection.getKey().getProviderUserId());
//      }
      
//		//session 回话丢失时报错
		 Connection<?> connection =authenticationToken.getConnection();
		  if(authenticationToken.isAuthenticated()&&connection!=null){
	    	  userInfo.setHeadimg(connection.getImageUrl());
	    	  userInfo.setProviderId(connection.getKey().getProviderId());
	    	  userInfo.setNickname(connection.getDisplayName());
	    	  userInfo.setProviderUserId(connection.getKey().getProviderUserId());
	      }
//		
		return userInfo;
	}
	
	@GetMapping("/session/invaild")
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public AjaxJson  sessionInvaild(){
		
		return AjaxJson.error(HttpStatus.UNAUTHORIZED.value()+"", "session失效");
	}
	
	
}
