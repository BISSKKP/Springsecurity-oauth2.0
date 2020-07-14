package com.base.security.core.properties;

/**
 * 登录请求相应结果展示方式
 * @author lqq
 *
 */
public enum LoginType {

	REDIRECT,//后端只返回页面
	
	
	JSON,//后端只返回json
	
	MIX,// head中包含 X-Requested-With:XMLHttpRequest
}
