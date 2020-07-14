package com.base.security.core.validate.code.sms;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.security.core.properties.SecurityProperties;
import com.base.security.core.validate.code.ValidateCode;
import com.base.security.core.validate.code.ValidateCodeGenerator;


/**
 * 产生短信数字类
 * @author ACID
 *
 */
@Component
public class SmsCodeGenerator implements ValidateCodeGenerator{

	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public ValidateCode generateCode(HttpServletRequest request) {
		
		System.out.println(request.getRequestURI());
		return new ValidateCode(getNumber(securityProperties.getCode().getSms().getLength()), 
				securityProperties.getCode().getSms().getExpireIn());
	}
	
	private String getNumber(int size){
		String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String number="";
		Random r=new Random();
		for(int i=0;i<size;i++){
		number+=str.charAt(r.nextInt(str.length()));
		}
		return number;
		}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	

}
