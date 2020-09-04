package com.base.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.ajax.AjaxJson;

/**
 * 	重写ouath 认证返回信息时  CustomExceptionTranslator 会失效，此时ExceptionController 开始生效
 *  但是自定义 登陆 还是会走 CustomExceptionTranslator
 * @author lhz
 *
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {

	
	@Autowired
	private TokenEndpoint tokenEndpoint;
	
	/**
	 * get方法返回自定义数据格式
	 * @param principal
	 * @param parameters
	 * @return
	 * @throws HttpRequestMethodNotSupportedException
	 */
	@GetMapping("/token")
	public AjaxJson getAccessToken(Principal principal,@RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
		return custom( tokenEndpoint.getAccessToken(principal, parameters).getBody());
	}
	
	/**
	 * post 方法返回自定义数据格式
	 * @param principal
	 * @param parameters
	 * @return
	 * @throws HttpRequestMethodNotSupportedException
	 */
	@PostMapping("/token")
	public AjaxJson postAccessToken(Principal principal,@RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
		
		return custom(tokenEndpoint.postAccessToken(principal, parameters).getBody());
	}
	
	
	
	/**
	 * 统一返回
	 * @param oAuth2AccessToken
	 * @return
	 */
	public AjaxJson custom(OAuth2AccessToken oAuth2AccessToken) {
		return AjaxJson.success("token", oAuth2AccessToken);
	}
	
	
	
}
