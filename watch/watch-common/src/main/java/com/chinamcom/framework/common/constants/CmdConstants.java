package com.chinamcom.framework.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/18
 */
public class CmdConstants {
	
	public static String S1 = "data.clock.get";
	public static String S2 = "data.location.post";
	public static String S3 = "data.message.post";
	public static String S4 = "data.di.post";
	public static String S5 = "data.sport.post";
	public static String S6 = "data.callinfo.post";
	public static String S7 = "data.heartrate.post";
	public static String S8 = "data.contact.post";
	public static String S9 = "data.message.get";
	public static String S10 = "wallet.comaccount.query";
	public static String S11 = "wallet.comaccount.recharge.history";
	public static String S12 = "data.acc1.balance.get";
	public static String S13 = "data.acc1.consume.get";
	public static String S14 = "data.chatGroup.get";
	public static String S15 = "data.deviceInfo.get";
	public static String S16 = "data.sporttotal.post";
	
	
	public static Map<String,String> cmdShortMap = new HashMap<String,String>();
	static{
		cmdShortMap.put("S1", S1);
		cmdShortMap.put("S2", S2);
		cmdShortMap.put("S3", S3);
		cmdShortMap.put("S4", S4);
		cmdShortMap.put("S5", S5);
		cmdShortMap.put("S6", S6);
		cmdShortMap.put("S7", S7);
		cmdShortMap.put("S8", S8);
		cmdShortMap.put("S9", S9);
		cmdShortMap.put("S10", S10);
		cmdShortMap.put("S11", S11);
		cmdShortMap.put("S12", S12);
		cmdShortMap.put("S13", S13);
		cmdShortMap.put("S14", S14);
		cmdShortMap.put("S15", S15);
		cmdShortMap.put("S16", S16);
	}
}
