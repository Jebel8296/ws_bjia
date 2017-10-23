package com.chinamcom.framework.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/11
 */
public class GenerateKeyUtil {

	static final Logger logger = Logger.getLogger(GenerateKeyUtil.class);

	public static String getKeyByTime(){
		SimpleDateFormat df4ms  = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		return df4ms.format(now) + (RandomUtils.nextInt(1, 100000) + 100000 + "");
	}
	
	 public static String getCrmTimeString(){
		 Date now = new Date();
		 SimpleDateFormat df4crm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 return df4crm.format(now);
	 }
	 
	 public static String getUUID(){
		 return UUID.randomUUID().toString().replaceAll("-", "");
	 }
	 
	 public static Date get2100Date(){
		 SimpleDateFormat df4ms  = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		 try {
			return df4ms.parse("20300101000000000");
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.set(2100, 0, 0,0,0,0);
			return calendar.getTime();
		}
	 }
}
