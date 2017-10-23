package com.chinamcom.framework.device.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.chinamcom.framework.device.Runner;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

public class IOSUtil {
	
	private static Logger log = Logger.getLogger(IOSUtil.class);
	
	private static Map<Integer,ApnsService> apnsServiceMap = new ConcurrentHashMap<Integer,ApnsService>();
	static{
		apnsServiceMap = new HashMap<Integer, ApnsService>();
    	apnsServiceMap.put(1, APNS.newService()
				.withCert(Runner.config.get("apple.cert.path1"),Runner.config.get("apple.cert.pass")).withSandboxDestination().build());
    	apnsServiceMap.put(2, APNS.newService()
				.withCert(Runner.config.get("apple.cert.path2"),Runner.config.get("apple.cert.pass")).withProductionDestination().build());
    	apnsServiceMap.put(3, APNS.newService()
				.withCert(Runner.config.get("apple.cert.path3"),Runner.config.get("apple.cert.pass")).withSandboxDestination().build());
    	apnsServiceMap.put(4, APNS.newService()
				.withCert(Runner.config.get("apple.cert.path4"),Runner.config.get("apple.cert.pass")).withProductionDestination().build());
	}
	
	public static void postData(String token, Integer env, String data, String alertTitle, String alertBody){
    	log.info("ios appid:" + token + " data: " + data);
    	String payload = APNS.newPayload().badge(1).customField("cmd", data).alertTitle(alertTitle).alertBody(alertBody).build();
    	apnsServiceMap.get(env).push(token, payload);
	}
}
