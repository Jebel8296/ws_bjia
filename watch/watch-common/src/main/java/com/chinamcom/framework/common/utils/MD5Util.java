package com.chinamcom.framework.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/20
 */

public class MD5Util {


	/**
	 * 
	 * @param text
	 *            加密串
	 * @return
	 */
	public static String getStringMD5(String text) {
		
		byte[] hash = null;
		try {
			hash = MessageDigest.getInstance("MD5").digest(text.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10){
				hex.append("0");
			}
			hex.append(Integer.toHexString(b & 0xFF));
		}

		return hex.toString();
	}

	public static void main(String[] args) {
		System.out.println(getStringMD5("123321"));
	}
}
