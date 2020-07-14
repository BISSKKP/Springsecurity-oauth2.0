package com.base.security.core.validate.code;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeGenerator {
	
	/**
	 * 创建图形二维码
	 * @param request
	 * @return
	 */
	ValidateCode generateCode(HttpServletRequest request);

}
