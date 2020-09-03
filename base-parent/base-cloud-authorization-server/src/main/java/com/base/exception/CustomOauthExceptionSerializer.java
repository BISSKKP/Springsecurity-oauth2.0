package com.base.exception;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.base.common.ajax.AjaxJson;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * 	oauth2.  异常处理类重写
 * @author vsupa
 *
 */
public class CustomOauthExceptionSerializer  extends StdSerializer<CustomOauthException> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5436883017589134974L;

	public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        AjaxJson j=new AjaxJson();
        
        j.setSuccess(false);
        j.setErrorCode(String.valueOf(value.getHttpErrorCode()));
        j.setMsg(value.getMessage());
        j.put("path", request.getServletPath());
        j.put("timestamp", String.valueOf(new Date().getTime()));
        gen.writeObject(j);
        
    }
}

