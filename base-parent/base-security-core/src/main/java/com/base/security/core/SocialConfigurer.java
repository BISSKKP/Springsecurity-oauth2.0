package com.base.security.core;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import com.base.security.core.social.SocialAuthenticationFilterPostProcessor;


/**
 *  重写 social 处理请求的路径
 * @author lqq
 *
 */
public class SocialConfigurer extends SpringSocialConfigurer {

	private String filterProcessesUrl;
	
	private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

	public SocialConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl=filterProcessesUrl;
	}
	
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter authenticationFilter=(SocialAuthenticationFilter) super.postProcess(object);
		authenticationFilter.setFilterProcessesUrl(filterProcessesUrl);
		
		//不同环境处理不同
		if(socialAuthenticationFilterPostProcessor!=null){
			socialAuthenticationFilterPostProcessor.process(authenticationFilter);
		}
		
		return super.postProcess(object);
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	public SocialAuthenticationFilterPostProcessor getSocialAuthenticationFilterPostProcess() {
		return socialAuthenticationFilterPostProcessor;
	}

	public void setSocialAuthenticationFilterPostProcess(
			SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor) {
		this.socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessor;
	}
	
	
	
	
}
