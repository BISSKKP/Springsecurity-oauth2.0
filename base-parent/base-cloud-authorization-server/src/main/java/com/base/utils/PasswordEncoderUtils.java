package com.base.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.base.common.crypto.Sha1HashUtils;

/**
 * 自定义加密
 * @author ACID
 *
 */
public class PasswordEncoderUtils implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		
		return Sha1HashUtils.entryptPassword(rawPassword.toString());
//		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return Sha1HashUtils.validatePassword(rawPassword.toString(), encodedPassword);
	}
}
