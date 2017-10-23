package com.chinamcom.framework.api.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.RedisClient;

import com.chinamcom.framework.api.handler.impl.BlackWhiteListHandlerImpl;

public interface BlackWhiteListHandler extends Handler<RoutingContext>,
		BaseHandler {
	/**
	 * Create a handler
	 * @param redis  the private key to use
	 * @return the handler
	 */
	static BlackWhiteListHandler create(RedisClient redis) {
	    return new BlackWhiteListHandlerImpl(redis);
	}
}
