package com.chinamcom.framework.common.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import org.apache.log4j.Logger;

import com.chinamcom.framework.common.exception.ServiceException;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespBodyBuilder;
import com.chinamcom.framework.common.response.RespCode;

public abstract class BaseVerticle extends AbstractVerticle {
	protected RespBodyBuilder respWriter = new RespBodyBuilder();
	protected Logger log = Logger.getLogger(getClass());
	
	protected int checkResultAdd(int result) throws ServiceException{
		if(result < 0){
			throw new ServiceException(RespCode.CODE_602);
		}
		return result;
	}
	
	protected int checkResultUpdate(int result) throws ServiceException{
		if(result < 0){
			throw new ServiceException(RespCode.CODE_603);
		}
		return result;
	}
	
	protected int checkResultDelete(int result) throws ServiceException{
		if(result < 0){
			throw new ServiceException(RespCode.CODE_604);
		}
		return result;
	}
	
	protected String getSerialNumber(ZJSONObject reqData){
		return reqData.getString("sn");
	}
	
	protected String getCmd(ZJSONObject reqData){
		return reqData.getString("cmd");
	}
	
	protected String getCn(ZJSONObject reqData){
		return reqData.getString("cn");
	}
	
	protected String getSerialNumber(JsonObject reqData){
		return reqData.getString("sn");
	}
	
	public abstract void start() throws Exception;
}
