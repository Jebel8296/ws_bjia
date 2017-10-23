package com.chinamcom.framework.common.response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinamcom.framework.common.json.Json;


/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/14
 */
public class RespBody {
	private Logger log = LoggerFactory.getLogger(getClass());
	private String cmd;		//请求命令
	private String sn;		//请求流水
	private int    code;	//响应码值
	private String msg;		//响应描述
	private Object respData;//响应结果
	private Long   ts;		//响应时间
	
	public RespBody(String sn) {
		this(sn, RespCode.CODE_200, null);
	}
	
	public RespBody(String sn, RespCode code) {
		this(sn, code, null);
	}

	public RespBody(String sn, RespCode code, Object responseData) {
		this(sn, code.getCode(), code.getDesp(), responseData);
	}
	
	public RespBody(String sn, RespCode code, String msg, Object responseData) {
		this(sn, code.getCode(), msg, responseData);
	}
	
	public RespBody(String sn, RespCode code, Throwable e) {
		this.sn = sn;
		this.code = code.getCode();
		this.msg = code.getDesp();
		this.respData = e;
		log.info(this.toJsonString());
	}
	
	private RespBody(String sn, int code, String msg, Object responseData) {
		this.sn = sn;
		this.code = code;
		this.msg = msg;
		if(responseData == null){
			
		} else if (responseData instanceof Map) {
			this.respData = responseData;
		} else {
			Map<String, Object> respMap = new HashMap<String, Object>();
			if(responseData.getClass().isPrimitive()){
				respMap.put("msg", responseData);
			}else{
				respMap.put(responseData.getClass().getSimpleName().toLowerCase(), responseData);
			}
			this.respData = respMap;
		}
		if(responseData instanceof List){
			if(log.isDebugEnabled()){
				log.debug(this.toJsonString());
			}
		}else{
			log.info(this.toJsonString());
		}
	}
	
	public RespBody(String sn, String cmd) {
		this(sn, cmd, RespCode.CODE_200, null);
	}
	
	public RespBody(String sn, String cmd, RespCode code) {
		this(sn, cmd, code, null);
	}

	public RespBody(String sn, String cmd, RespCode code, Object responseData) {
		this(sn, cmd, code.getCode(), code.getDesp(), responseData);
	}
	
	public RespBody(String sn, String cmd, RespCode code, String msg, Object responseData) {
		this(sn, cmd, code.getCode(), msg, responseData);
	}
	
	public RespBody(String sn, String cmd, RespCode code, Throwable e) {
		this.sn = sn;
		this.code = code.getCode();
		this.msg = code.getDesp();
		this.cmd = cmd;
		this.respData = e;
		log.info(this.toJsonString());
	}
	
	private RespBody(String sn, String cmd, int code, String msg, Object responseData) {
		this.sn = sn;
		this.code = code;
		this.msg = msg;
		this.cmd = cmd;
		if(responseData == null){
			
		} else if (responseData instanceof Map) {
			this.respData = responseData;
		} else {
			Map<String, Object> respMap = new HashMap<String, Object>();
			if(responseData.getClass().isPrimitive()){
				respMap.put("msg", responseData);
			}else{
				respMap.put(responseData.getClass().getSimpleName().toLowerCase(), responseData);
			}
			this.respData = respMap;
		}
		if(responseData instanceof List){
			if(log.isDebugEnabled()){
				log.debug(this.toJsonString());
			}
		}else{
			log.info(this.toJsonString());
		}
	}



	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getRespData() {
		return respData;
	}

	public void setRespData(Object respData) {
		this.respData = respData;
	}

	public Long getTs() {
		if(ts == null){
			return new Date().getTime();
		}
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public String toJsonString(){
		return Json.encode(this);
	}
}
