package com.base.security.core.social;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 * 通过bean名称来重写多个
 * @author lqq
 *
 */
public class ConnectViews extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String msg="";
		if(model.get("connections")==null){
			msg="解绑";
		}else{
			msg="绑定";
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("<h3>"+msg+"成功</h3>");
	}

}
