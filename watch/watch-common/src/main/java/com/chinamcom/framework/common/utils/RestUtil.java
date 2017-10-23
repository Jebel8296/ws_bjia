package com.chinamcom.framework.common.utils;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

public class RestUtil {
	
	private static Logger log = Logger.getLogger(RestUtil.class);
	
	@SuppressWarnings({"unchecked", "rawtypes" })
	public static Object postData(String server, JSONObject postData){
		SimpleClientHttpRequestFactory scrf = new SimpleClientHttpRequestFactory();
		scrf.setConnectTimeout(10 * 1000);
		scrf.setReadTimeout(10 * 1000);
		RestTemplate restTemplate = new RestTemplate(scrf);
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity reqEntity = new HttpEntity(postData.toString(), headers);
        log.info("postData: " + postData.toString());
        return restTemplate.postForObject("http://" + server, reqEntity, JSONObject.class);
	}
	
	
	public static String getData(String server, Map<String, String> params){
        SimpleClientHttpRequestFactory scrf = new SimpleClientHttpRequestFactory();
        scrf.setConnectTimeout(10 * 1000);
        scrf.setReadTimeout(10 * 1000);
        RestTemplate restTemplate = new RestTemplate(scrf);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        String queryString = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            queryString = queryString + entry.getKey() + "=" + entry.getValue() + "&";
        }
        return restTemplate.getForObject(server + "?" + queryString, String.class);
	}
}
