package com.base.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

public class ValidateCode  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5297396299354470351L;

	private String code;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime expireTime;
	
	
	public ValidateCode() {
	}

	public ValidateCode( String code, int expireIn) {
		super();
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}

	public ValidateCode(String code, LocalDateTime exDateTime) {
		super();
		this.code = code;
		this.expireTime = exDateTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}


	public boolean isExpried() {
//		return LocalDateTime.now().compareTo(this.expireTime)<=0?false:true;
		return LocalDateTime.now().isAfter(this.expireTime);
	}
	
	
	
}
