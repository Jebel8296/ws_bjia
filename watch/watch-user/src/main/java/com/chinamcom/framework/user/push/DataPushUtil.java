package com.chinamcom.framework.user.push;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinamcom.framework.common.json.ZJSONObject;

public class DataPushUtil {
	private Logger log = LoggerFactory.getLogger(getClass());
	/*private String cmd;		//请求命令
	private Integer uid;		//请求用户uid
	private String did;		//手表imei
	private Object data;//推送数据
	private String pushUrl;	//推送地址
	private String to;*/
	
	public String pushData(String cmd, Integer uid,String did,String to,Object data){
		ZJSONObject params =new ZJSONObject();
		ZJSONObject param =new ZJSONObject();
		/*if(data == null){
			
		} else if (data instanceof Map) {
			this.data = data;
		}*/
		params.put("ack", 0);
		params.put("to", to);
		params.put("cmd", cmd); 
		params.put("uid", uid); 
		params.put("did", did);
		if(data instanceof List){
			param.put("data", data);
			params.put("body", param);
		}else{
			params.put("body", data);
		}
		String paraStr = params.toString();
		log.info("推送json串："+paraStr);
		return paraStr;
	} 
}
