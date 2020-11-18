package com.base.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

/**
 * 自定义token 额外参数
 * @author vsupa
 *
 */
@Component("tokenEnhancer")
public class CustomeTokenEnhancer implements TokenEnhancer{

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		DefaultOAuth2AccessToken token=(DefaultOAuth2AccessToken) accessToken;
		User user= (User) authentication.getPrincipal();
		Map<String, Object> map=new LinkedHashMap<>();
		map.put("user", user);
		//重写返回信息，添加自定义的返回信息
		token.setAdditionalInformation(map);
		
		return accessToken;
	}

}
