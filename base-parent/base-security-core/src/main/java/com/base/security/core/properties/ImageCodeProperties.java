package com.base.security.core.properties;

/**
 * 图片验证码
 * @author lqq
 *
 */
public class ImageCodeProperties {



	private int width = 100;
	private int height = 30;

	private int expireIn=120;
	
	private int length=4;
	
	private String url;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	

	
	
	
}
