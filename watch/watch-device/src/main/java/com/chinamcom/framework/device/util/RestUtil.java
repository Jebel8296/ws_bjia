package com.chinamcom.framework.device.util;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.rest.RestTemplateFactory;

public class RestUtil {
	
	private static Logger log = Logger.getLogger(RestUtil.class);
	
	public static Object postData(String server, JSONObject postData){
		RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> reqEntity = new HttpEntity<String>(postData.toString(), headers);
        log.info("postData: " + postData.toString());
        return restTemplate.postForObject("http://" + server + "/push-m.do", reqEntity, Object.class);
	}
	
	
	public static String getData(String server, Map<String, String> params){
		RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();
        String queryString = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            queryString = queryString + entry.getKey() + "=" + entry.getValue() + "&";
        }
        return restTemplate.getForObject(server + "?" + queryString, String.class);
	}
}
