package com.base.security.core.validate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode extends ValidateCode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5095052871958265463L;
	private BufferedImage image;
	
	
	public ImageCode() {
	}


	public ImageCode(BufferedImage image, String code, int expireIn) {
		super(code,expireIn);
		this.image = image;
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime exDateTime) {
		super(code,exDateTime);
		this.image = image;
	}




	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	
	
}
