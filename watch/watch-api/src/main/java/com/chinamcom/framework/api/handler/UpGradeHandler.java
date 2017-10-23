package com.chinamcom.framework.api.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.RedisClient;

import com.chinamcom.framework.api.handler.impl.UpGradeHandlerImpl;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/28
 */
public interface UpGradeHandler extends Handler<RoutingContext>, BaseHandler {

	/**
	 * Create a handler
	 * @param redis  the upgrade data service to use
	 * @return the handler
	 */
	static UpGradeHandler create(RedisClient redis) {
	    return new UpGradeHandlerImpl(redis);
	}
}
