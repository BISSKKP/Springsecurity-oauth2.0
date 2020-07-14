package com.base.security.core.social.qq.api;
/**
 * access_token	可通过使用Authorization_Code获取Access_Token 或来获取。 
	access_token有3个月有效期。
	oauth_consumer_key	申请QQ登录成功后，分配给应用的appid
	openid	用户的ID，与QQ号码一一对应。 
	可通过调用https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN 来获取。
 * @author ACID
 *
 */
public interface QQ {
	/**
	 * 获取用户信息
	 * @return
	 * @throws Exception 
	 */
	//https://graph.qq.com/user/get_user_info
	QQUserInfo getQQUserInfo();
}
