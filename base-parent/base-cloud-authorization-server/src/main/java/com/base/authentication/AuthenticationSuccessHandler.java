package com.base.authentication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.base.common.ajax.AjaxJson;
import com.base.properties.BaseProperties;
import com.base.properties.LoginMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * app登录成功处理器
 * 
 * @author lqq
 *
 */
@Slf4j
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;
	
	@Autowired
	private PasswordEncoder passwordEncoderUtils;
	
	@Autowired
	private BaseProperties baseProperties;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException{

		log.info("登录成功：" + objectMapper.writeValueAsString(authentication));
		String[] tokens=null;
		
		if(baseProperties.getLoginMethod().equals(LoginMethods.Authen)) {
			try {
				tokens= getClientAndSecret(request);
			} catch (Exception e) {
				writeResponse(response, objectMapper.writeValueAsString(AjaxJson.error("403", e.getMessage())));
				return;
			}
		}else{
			//表单登陆时取默认
			tokens=new  String[]{baseProperties.getDefAppId(),baseProperties.getDefSecret()};
		}
		
		// 创建 oAuth2Request
		OAuth2Request oAuth2Request= convert(tokens,false);
		

		// 创建 oAuth2Authentication
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

		// 创建本次成功的token
		OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
		//返回json
		
		writeResponse(response, objectMapper.writeValueAsString(AjaxJson.success("token", token)));
	}
	
	// 只返回json
	private void writeResponse(HttpServletResponse response,String responseData) throws JsonProcessingException, IOException  {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseData);
	}
	
	

	/**
	 * 创建 OAuth2Request
	 * 
	 * @param tokens
	 * @return
	 */
	private OAuth2Request convert(String[] tokens,boolean getDefaultTokens) {
		String clientId = tokens[0];
		String secret = tokens[1];
   
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

		if (clientDetails == null) {
			throw new UnapprovedClientAuthenticationException("clientid 不存在  " + clientId);
		} else if (
				
				!checkSecret(secret,clientDetails.getClientSecret())
				
				) {
			throw new UnapprovedClientAuthenticationException("clientsecret 不一致 " + secret);

		}
		// 自定义认证流程
		@SuppressWarnings("unchecked")
		TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

		return tokenRequest.createOAuth2Request(clientDetails);
	}

	/**
	 * 获取client  clientsecret
	 * @param request
	 * @return
	 */
	public String[] getClientAndSecret(HttpServletRequest request) {
		String header = request.getHeader("Authorization");

		if (null ==header ||!header.startsWith("Basic ")/*&&header.startsWith("bearer ")*/) {
			throw new UnapprovedClientAuthenticationException("请求头部没有client信息");
		}

		byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, StandardCharsets.UTF_8);

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}

		return new String[] { token.substring(0, delim), token.substring(delim + 1) };

	}
	
	/**
	 * 检测密钥
	 * @param requestSecret
	 * @param detailSecret
	 * @return
	 */
	private boolean checkSecret(String requestSecret,String detailSecret){
		
		try {
			if(requestSecret.equals(detailSecret)){
				return false;
			}else if(passwordEncoderUtils.matches(requestSecret, detailSecret)){
				return true;
			}
		} catch (Exception e) {
			
		}
		return false;
		
	}
	
	

}
