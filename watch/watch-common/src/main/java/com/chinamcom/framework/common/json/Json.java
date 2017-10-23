package com.chinamcom.framework.common.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Json {
	
	private static Logger log = LoggerFactory.getLogger(Json.class);

	public static String encode(Object obj){
		return JSON.toJSONString(obj);
	}
	
	public static <T> T decode(String json, Class<T> clazz){
		if(StringUtils.isEmpty(json)){
			return null;
		}
		try {
			return JSON.parseObject(json, clazz);
		} catch (Exception e) {
			log.error(" parse from json string error: ", e);
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception{
		String body = "{\"pageNo\":1,\"imei\":\"123456789\",\"pageSize\":10,\"type\":\"1\"}";
		JSONObject json = decode(body, JSONObject.class);
		System.out.println(json.toJSONString());
	}
}
