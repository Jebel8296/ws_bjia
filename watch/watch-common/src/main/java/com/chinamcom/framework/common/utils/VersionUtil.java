package com.chinamcom.framework.common.utils;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/27
 */
public class VersionUtil {
	
	/**
	 * 
	 * @param v1
	 * @param v2
	 * @return 
	 */
	public static int compareVersion(String v1, String v2) {
		int diff = 0;
		if (v1 == null || v2 == null) {
			return diff;
		}
		String[] vs1 = v1.split("\\.");
		String[] vs2 = v2.split("\\.");
		int size = vs1.length < vs2.length ? vs1.length : vs2.length;
		for (int i = 0; i < size; i++) {
			String vs1i = vs1[i];
			String vs2i = vs2[i];
			if ((diff = vs1i.length() - vs2i.length()) != 0) {
				break;
			}
			if ((diff = vs1i.compareTo(vs2i)) != 0) {
				break;
			}
		}
		// return if it has result,otherwise who has subversion is bigger.
		return diff != 0 ? diff : (vs1.length - vs2.length);
	}
}
