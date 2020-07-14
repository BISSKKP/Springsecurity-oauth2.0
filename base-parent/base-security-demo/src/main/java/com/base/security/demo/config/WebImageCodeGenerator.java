package com.base.security.demo.config;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import com.base.security.core.properties.SecurityProperties;
import com.base.security.core.validate.code.ImageCode;
import com.base.security.core.validate.code.ValidateCode;
import com.base.security.core.validate.code.ValidateCodeGenerator;

import lombok.extern.slf4j.Slf4j;

@Component("imageCodeGenerator")
@Slf4j
public class WebImageCodeGenerator  implements ValidateCodeGenerator{

	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public ValidateCode generateCode(HttpServletRequest request) {
		
		log.info("web----自定义验证码生成类");
		int width=ServletRequestUtils.getIntParameter(request, "width", securityProperties.getCode().getImage().getWidth());
	    int height=ServletRequestUtils.getIntParameter(request, "height", securityProperties.getCode().getImage().getHeight());
	    int length=securityProperties.getCode().getImage().getLength();
		
		//此方法生成一张图片
		Random r=new Random();
		
		//1.创建空白图片
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//2.获取图片的画笔
		Graphics g=image.getGraphics();
		//3.设置画笔的颜色
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),
				r.nextInt(255)));
		//4.绘制矩形的背景
		g.fillRect(0,0,width,height);
		//5.调用自定义的方法,获取长度为5的字母和数字的字符串					
		String number=getNumber(length);
//		//6.把随机生存的验证码存储在session中
//		HttpSession session=request.getSession();
//		session.setAttribute("code",number);
		//7.重新设置画笔,准备画验证码
		g.setColor(new Color(0,0,0));
		g.setFont(new Font(null,Font.BOLD,24));
		//8.绘制验证码
		g.drawString(number,5,25);
		//9.绘制8条干扰线
		for(int i=0;i<8;i++){
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		int x1=r.nextInt(width);
		int y1=r.nextInt(height);
		int x2=r.nextInt(width);
		int y2=r.nextInt(height);
		g.drawLine(x1,
				y1,
				x2,
				y2);
		}
		
		g.dispose();
		
		return new ImageCode(image, number, securityProperties.getCode().getImage().getExpireIn());
	
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

	
	
	
}
