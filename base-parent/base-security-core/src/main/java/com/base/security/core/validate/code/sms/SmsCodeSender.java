package com.base.security.core.validate.code.sms;

/**
 * 短信发送总接口
 * @author ACID
 *
 */
public interface SmsCodeSender {

	public void send(String mobile,String code);
}
