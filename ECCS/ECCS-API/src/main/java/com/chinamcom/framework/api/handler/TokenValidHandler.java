package com.chinamcom.framework.api.handler;

import com.chinamcom.framework.api.handler.impl.TokenValidHandlerImpl;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

@VertxGen
public interface TokenValidHandler extends Handler<RoutingContext> {
	static TokenValidHandler create(Vertx vertx, JsonObject param) {
		return new TokenValidHandlerImpl(vertx, param);
	}
}
