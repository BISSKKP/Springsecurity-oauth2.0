package com.base.security.core.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCodeProperties {

	private ImageCodeProperties image=new ImageCodeProperties();
	
	private SmsCodeProperties sms=new SmsCodeProperties();
	
	
}
