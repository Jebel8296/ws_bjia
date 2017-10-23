package com.chinamcom.framework.common.utils;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * AES 加密 
 * need Hex.class from apache commons codec 
 * @author Z
 * @version 2015年6月10日
 *
 */
public class AESUtil {
	
	static final String KEY_ALGORITHM="AES";
	static final String ENCRIPT_ALGORITHM="AES";
	
	public static byte[] encode(String signKey,String data){
		try {
			Key key=getKey(signKey);
			Cipher cipher=Cipher.getInstance(ENCRIPT_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(data.getBytes());
		} catch (Exception e) {e.printStackTrace();}
		
		return null;
	}
	
	public static String decode(String signKey,byte[] data){
		Key key=getKey(signKey);
		try {
			Cipher cipher=Cipher.getInstance(ENCRIPT_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] bytes=cipher.doFinal(data);
			return new String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/***
	 * 转16进制字符串
	 * @param key 加密key
	 * @param data 加密串
	 * @return
	 */
	public static String encode2Str(String key,String data){
		return Hex.encodeHexString(encode(key, data));
	}
	
	/***
	 * 16进制字符串解密
	 * @param key 解密key
	 * @param data 解密串
	 * @return
	 */
	public static String decode4Str(String key,String data){
		try {
			byte[] datas = Hex.decodeHex(data.toCharArray());
			return decode(key, datas);
		} catch (Exception e) {}
		return null;
	}
	
	private static Key getKey(String key){
		try {
			return new SecretKeySpec(md5(key.getBytes("UTF-8")), KEY_ALGORITHM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] md5(byte[] bytes) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(bytes);
			return md5.digest();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) {
		String key="63e3f2af0503ac0c9c0e131174fc6cee";
		String ecnode=encode2Str(key, "陶晓阳");
		System.out.println("加密 :"+ecnode);
		String dec=decode4Str(key, ecnode);
		System.out.println("解密: "+dec);
	}
}

