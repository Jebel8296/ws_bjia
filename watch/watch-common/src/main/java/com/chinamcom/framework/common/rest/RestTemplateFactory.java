package com.chinamcom.framework.common.rest;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

 
@SuppressWarnings("deprecation")
public class RestTemplateFactory {
	
	/**
	 * 每个ip地址上最大的链接数
	 */
	private static  int defaultMaxPerRoute = 500 ;
	/**
	 * 连接池中最大的链接条数
	 */
	private static  int maxTotle = 800;
	/**
	 * 创建连接最大时间
	 */
	private static  int connectTimeout = 10*1000 ;
	/**
	 * 读取数据最大时间
	 */
	private static  int readTimeout = 30*1000 ; 
	
	
	
	
	private static final RestTemplate restTemplate ;
	
	static {
		/**
		 * 读取数据库或者配置文件 修改参数
		 */
		PoolingClientConnectionManager  pccm = new PoolingClientConnectionManager();
		pccm.setDefaultMaxPerRoute(defaultMaxPerRoute);
		pccm.setMaxTotal(maxTotle);
		HttpClient httpClient = new DefaultHttpClient(pccm);
		HttpComponentsClientHttpRequestFactory hccchrf = new HttpComponentsClientHttpRequestFactory(httpClient);
		hccchrf.setConnectTimeout(connectTimeout);
		hccchrf.setReadTimeout(readTimeout);
		restTemplate = new RestTemplate(hccchrf);
		restTemplate.getMessageConverters().add( new MappingJackson2HttpMessageConverter());
		
		
	}
	
	/**返回restTemplate
	 * @return 
	 */
	public static RestTemplate getRestTemplate(){
		return restTemplate;
	}
	
	
	
	
}
