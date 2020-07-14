package com.base.security.core.constants;

public class SecurityConstants {
	
	
	  /**
     * 当请求需要身份认证时，默认跳转的url
     */
	public static final  String DEFAULT_UNAUTHENTICATION_URL = "/authen/require";
	
    /**
     * 默认的用户名密码登录请求处理url
     */
	public static final  String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/dologin";
	
	
	/**
	 * 默认 注册处理请求
	 */
	public static final String DEFAULT_REGIST_PROCESSING_URL="/user/regist";
	
	  /**
     * 默认的注册url
     */
	public static final  String DEFAULT_SIGNUP_URL = "/signUp.html";
	
	/**
	 * app 社交登陆时处理注册的地址
	 */
	public static final  String DEFAULT_APP_SOCIAL_SIGNUP_URL = "/social/signUp";

	
	 /**
     * 默认登录页面
     */
	public static final  String DEFAULT_LOGIN_PAGE_URL = "/login.html";
	
	
	/**
     * 默认处理验证码的url前缀  
     */
    public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
    
    
    /**
     * 默认的手机验证码登录请求处理url
     */
    public static final   String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authen/mobile";
	/**
	 * Session失效之后的地址
	 */
	public static final String DEFAULT_SESSION_INVALID_URL="/session-invalid.html";
	
	/**
	 * 处理退出登录的url
	 */
	public static final String DEFAULT_LOGOUT_URL="/signout";
	
	
	/**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
	public static final  String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
	
	
	/**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
	public static final  String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

	
	  /**
     * openid参数名
     */
	public static final String DEFAULT_PARAMETER_NAME_OPENID = "openId";

	
	/**
     * providerId参数名
     */
	public static final String DEFAULT_PARAMETER_NAME_PROVIDERID = "providerId";
	
    /**
     * 默认的OPENID登录请求处理url
     */
	public static final String DEFAULT_LOGIN_PROCESSING_URL_OPENID = "/authentication/openid";
}
