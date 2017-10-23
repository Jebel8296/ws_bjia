package com.chinamcom.framework.common.utils;

import org.springframework.util.StringUtils;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/02
 */
public class StringUtil extends StringUtils {
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
}
