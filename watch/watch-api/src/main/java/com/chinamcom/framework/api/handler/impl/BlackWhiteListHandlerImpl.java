package com.chinamcom.framework.api.handler.impl;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.RedisClient;

import com.chinamcom.framework.api.handler.BlackWhiteListHandler;

public class BlackWhiteListHandlerImpl implements BlackWhiteListHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private RedisClient redis;
	public BlackWhiteListHandlerImpl(RedisClient redis){
		this.redis = redis;
	}
	
	@Override
	public void handle(RoutingContext event) {
		log.info(event.getBodyAsString());
		redis.get("", result->{});
	}

}
