package com.base.security.demo.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class WeiXinConnectionView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String msg="";
		log.info("绑定或解绑");
		if(model.get("connections")==null){
			msg="解绑";
		}else{
			msg="绑定";
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("<h3>demo-重写"+msg+" ---"+msg+"成功</h3>");
	}

}
