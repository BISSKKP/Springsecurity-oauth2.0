package com.base.security.core.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.base.security.core.validate.code.ImageCode;
import com.base.security.core.validate.code.ValidateCode;
import com.base.security.core.validate.code.ValidateCodeGenerator;
import com.base.security.core.validate.code.ValidateCodeRepository;
import com.base.security.core.validate.code.ValidateCodeType;
import com.base.security.core.validate.code.sms.SmsCodeSender;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/code")
@Slf4j
public class ValidateCodeController {

	@Resource
	private ValidateCodeGenerator imageCodeGenerator;
	
	public static final String SESSION_IMAGE_KEY="SESSION_KEY_IMAGE_CODE"; 
	
	public static final String SESSION_SMS_KEY="SESSION_KEY_SMS_CODE"; 
	
	
	
	@Resource
	private ValidateCodeGenerator smsCodeGenerator;
	
	@Resource
	private SmsCodeSender smsCodeSender;
	
	@Autowired
	private ValidateCodeRepository validateCodeRepository;
	
	@GetMapping("/image")
	public void createCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		//生成验证码
		ImageCode imageCode=(ImageCode) imageCodeGenerator.generateCode(request);
		
		//保存session
		ImageCode code=new ImageCode(null, imageCode.getCode(), imageCode.getExpireTime());//图片没有序列化，需要去除
//		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_IMAGE_KEY, code);
		validateCodeRepository.save(new ServletWebRequest(request), code, ValidateCodeType.IMAGE);	
		//写入
		ImageIO.write(imageCode.getImage(), "JPG", response.getOutputStream());
		
	}
	
	@GetMapping("/sms")
	public Object createSmsCode(@RequestParam(required=true)String mobile,
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		ValidateCode smsCode=smsCodeGenerator.generateCode(request);
//		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_SMS_KEY, smsCode);
		validateCodeRepository.save(new ServletWebRequest(request), smsCode, ValidateCodeType.SMS);	
		log.info("创建的sms："+smsCode.getCode());
		
		//发送
		smsCodeSender.send(mobile, smsCode.getCode());
		
		
		return "ok";
	}
	
	
}
