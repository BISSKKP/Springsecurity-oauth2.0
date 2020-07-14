package com.base.common.crypto;


import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

import sun.misc.BASE64Encoder;
 
@SuppressWarnings("restriction")
public class MD5Util {
	/**
	 * 
	 * @param pwd
	 *            需要加密的字符串
	 * @param type
	 *            字母大小写(false为默认小写，true为大写)
	 * @param bit
	 *            加密的类型（16,32,64）
	 * @return
	 */
	public static String getMD5(String pwd, boolean isUpper, Integer bit) {
		String md5 = new String();
		try {
			// 创建加密对象
			MessageDigest md = MessageDigest.getInstance("md5");
			if (bit == 64) {
				BASE64Encoder bw = new BASE64Encoder();
				String bsB64 = bw.encode(md.digest(pwd.getBytes("utf-8")));
				md5 = bsB64;
			} else {
				// 计算MD5函数
				md.update(pwd.getBytes());
				byte b[] = md.digest();
				int i;
				StringBuffer sb = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						sb.append("0");
					sb.append(Integer.toHexString(i));
				}
				md5 = sb.toString();
				if(bit == 16) {
					//截取32位md5为16位
					String md16 = md5.substring(8, 24).toString();
					md5 = md16;
					if (isUpper)
						md5 = md5.toUpperCase();
					return md5;
				}
			}
			//转换成大写
			if (isUpper)
				md5 = md5.toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("md5加密抛出异常！");
		}
 
		return md5;
	}
	
	/**
	 * 转md5
	 * @param data
	 * @param up true 转大写，false小写
	 * @return
	 */
	public static String md5(String data,boolean up){
		return up?DigestUtils.md5Hex(data).toUpperCase():DigestUtils.md5Hex(data).toLowerCase();
	}
 
 
	public static void main(String[] args) throws Exception {
		String a = "123";
		String md5a = getMD5(a, true, 64);
		System.out.println(md5a);
		System.out.println(md5a.length());
	}
}
