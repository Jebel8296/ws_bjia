package com.chinamcom.framework.backstage.util;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * 安全组件类
 * 
 * @author xuxg
 * @since 20160929
 */
public class SecurityUtil {

	/**
	 * Base64编码
	 * 
	 * @param data
	 * @return
	 */
	public static String encoderWithBase64(String data) {
		String result = null;
		try {
			result = Base64.getEncoder().encodeToString(data.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Base64解码
	 * 
	 * @param data
	 * @return
	 */
	public static String decodeWithBase64(String data) {
		String result = null;
		try {
			result = new String(Base64.getDecoder().decode(data), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * MD5加盐加密
	 * 
	 * @param salt
	 * @param data
	 * @return
	 */
	public static String encryptWithSaltMD5(String salt, String data) {
		return encryptWithMD5("ZM." + salt + "." + data);
	}
	
	/**
	 * MD5加密
	 * @param data
	 * @return
	 */
	public  static String encryptWithMD5(String data) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] input = data.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(input);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
   	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
