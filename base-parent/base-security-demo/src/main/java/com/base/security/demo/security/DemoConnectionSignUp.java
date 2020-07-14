package com.base.security.demo.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 在social 授权登录 没有查找到用户时默认会跳转至regist注册
 * 此方法可将授权的用户自动创建关联账号，不用注册
 * @author ACID
 *
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

	@Override
	public String execute(Connection<?> connection) {
		
		return connection.getDisplayName();
	}

}
