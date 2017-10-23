package com.chinamcom.framework.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日期上增加分钟
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMinutes(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, amount);
		return c.getTime();
	}

	/**
	 * 将日期转字符
	 * 
	 * @param date
	 * @return
	 */
	public static String parseDate2String(Date date) {
		return new SimpleDateFormat(DEFAULT_PATTERN).format(date);
	}
	
	public static Long paraseString2Long(String date){
		Long d = 0L;
		try {
			d = new SimpleDateFormat(DEFAULT_PATTERN).parse(date).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

}
