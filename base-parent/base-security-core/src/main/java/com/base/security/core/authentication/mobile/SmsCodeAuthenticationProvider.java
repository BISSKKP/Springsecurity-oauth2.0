package com.base.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider  {
	
	private UserDetailsService userDetailsService;

	/**
	 * 处理用户账号逻辑
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		SmsCodeAuthenticationToken authenticationToken=(SmsCodeAuthenticationToken) authentication;
		
		UserDetails user=userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
		
		if(user  ==null ){
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		
		
		SmsCodeAuthenticationToken authenticationResult=new 
				SmsCodeAuthenticationToken(user
						, user.getAuthorities());
		
		authenticationResult.setDetails(authenticationToken.getDetails());
		
		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
