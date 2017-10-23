package com.chinamcom.framework.api.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.RedisClient;

import com.chinamcom.framework.api.handler.impl.SignHandlerImpl;

public interface SignHandler extends Handler<RoutingContext>, BaseHandler {
	/**
	 * Create a handler
	 * @param redis  the private key to use
	 * @return the handler
	 */
	static SignHandler create(RedisClient redis) {
	    return new SignHandlerImpl(redis);
	}
}
