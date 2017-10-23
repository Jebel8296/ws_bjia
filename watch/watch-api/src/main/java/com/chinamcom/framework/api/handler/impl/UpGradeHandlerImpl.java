package com.chinamcom.framework.api.handler.impl;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.RedisClient;

import java.util.Date;

import com.chinamcom.framework.api.handler.UpGradeHandler;
import com.chinamcom.framework.api.model.UpGradeConfig;
import com.chinamcom.framework.common.constants.Constants;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.VersionUtil;


public class UpGradeHandlerImpl implements UpGradeHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private RedisClient redis;
	public UpGradeHandlerImpl(RedisClient redis){
		this.redis = redis;
	}
	
	@Override
	public void handle(RoutingContext context) {
		JsonObject jsonBody  = context.getBodyAsJson();
		log.info(jsonBody.toString());
		String service = jsonBody.getString("service");
		String cli_version = jsonBody.getString("cv");
		redis.get(Constants.UP_GRADE_KEY_PREFIX + service,  result -> {
		  String serial_number = jsonBody.getString("sn");
		  if(result.succeeded()){
			  log.info("UpGradeHandler:" + result.result());
			  if(result.result() != null){
				  UpGradeConfig config = Json.decodeValue(result.result(), UpGradeConfig.class);
				  if(config != null){
					  Date now = new Date();
					  if (now.getTime() <= config.getUpEndTime().getTime() && now.getTime() >= config.getUpStartTime().getTime()) {
						  context.response().end(respWriter.toError(serial_number, RespCode.CODE_601).toString());
					  }
					  if(VersionUtil.compareVersion(cli_version, config.getUpVersion()) <= 0){
						  context.response().end(respWriter.toError(serial_number, RespCode.CODE_601).toString());
					  }
				  }
			  }
		  }else{
			  log.error(result.cause().getMessage(), result.cause());
			  context.response().end(respWriter.toError(serial_number, RespCode.CODE_601).toString());
			  
		  }
		});
	}

}
