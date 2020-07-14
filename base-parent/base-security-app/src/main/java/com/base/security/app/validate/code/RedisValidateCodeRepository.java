package com.base.security.app.validate.code;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.base.security.core.validate.code.ValidateCode;
import com.base.security.core.validate.code.ValidateCodeType;
import com.base.security.core.validate.code.exception.ValidateCodeException;

import java.util.concurrent.TimeUnit;

/**
 * Description：使用redis+deviceId的方式进行验证码的存、取、删
 */
@Component
public class RedisValidateCodeRepository implements com.base.security.core.validate.code.ValidateCodeRepository {
    
	
	@Autowired
    private RedisTemplate<Object, Object> redisTemplate;

	

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
    	System.out.println(buildKey(request, type));
        redisTemplate.opsForValue().set(buildKey(request, type), code, 30, TimeUnit.MINUTES);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
    	System.out.println(buildKey(request, type));
        Object value = redisTemplate.opsForValue().get(buildKey(request, type));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(buildKey(request, type));
    }

    /**
     * 构建验证码在redis中的key ---- 该key类似与cookie
     * @param request
     * @param type
     * @return
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }
}
