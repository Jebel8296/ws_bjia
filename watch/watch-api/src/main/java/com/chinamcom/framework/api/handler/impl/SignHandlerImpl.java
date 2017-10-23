package com.chinamcom.framework.api.handler.impl;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.RedisClient;

import com.chinamcom.framework.api.handler.SignHandler;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.MD5Util;

public class SignHandlerImpl implements SignHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private RedisClient redis;
	public SignHandlerImpl(RedisClient redis){
		this.redis = redis;
	}
	
	@Override
	public void handle(RoutingContext context) {
		JsonObject jsonBody  = context.getBodyAsJson().copy();
		log.info(jsonBody.toString());
		String pn = jsonBody.getString("pn");
		String client_sign = jsonBody.getString("sign");
		log.info(client_sign);
		jsonBody.remove("sign");
		jsonBody.remove("st");
		String serial_number = jsonBody.getString("sn");
		redis.get(pn,  result -> {
		  if(result.succeeded()){
	          String primaryKey = MD5Util.getStringMD5(result.result());
	          log.info(primaryKey);
	          if(primaryKey == null){
	        	 context.response().end(respWriter.toError(serial_number, RespCode.CODE_600).toString());
	          }
	          String reqMD5 = MD5Util.getStringMD5(jsonBody.toString());
	          String server_sign = MD5Util.getStringMD5(reqMD5 + primaryKey);
	          log.info("server_sign:{}", server_sign);
	          if(!client_sign.equals(server_sign)){
	        	  context.response().end(respWriter.toError(serial_number, RespCode.CODE_600).toString());
	          }
		  }else{
			  log.error(result.cause().getMessage(), result.cause());
			  context.response().end(respWriter.toError(serial_number, RespCode.CODE_1000).toString());
		  }
	    });
	}
}
