package com.chinamcom.framework.api.handler.impl;

import java.util.Optional;

import com.chinamcom.framework.api.handler.LoginAuthHandler;
import com.chinamcom.framework.common.response.RespBodyBuilder;
import com.chinamcom.framework.common.response.RespCode;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.RoutingContext;

public class LoginAuthHandlerImpl implements LoginAuthHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final RespBodyBuilder respWriter = new RespBodyBuilder();

	protected final Vertx vertx;
	protected final JWTAuth jwt;

	public LoginAuthHandlerImpl(Vertx vertx, JWTAuth jwt) {
		this.vertx = vertx;
		this.jwt = jwt;
	}

	@Override
	public void handle(RoutingContext ctx) {
		JsonObject request = ctx.getBodyAsJson();
		String sn = request.getString("sn");
		HttpServerResponse response = ctx.response();
		Optional<String> service = Optional.ofNullable(request.getString("service"));
		Optional<String> token = Optional.ofNullable(ctx.request().headers().get("token"));
		if (service.isPresent() && (service.get().contains("user.") || service.get().contains("product."))) {
			ctx.next();
		} else {
			if (!token.isPresent()) {
				logger.info("Token check faild.");
				response.end(respWriter.toError(sn, RespCode.CODE_506));
			} else {
				jwt.authenticate(new JsonObject().put("jwt", token.get()), handler -> {
					if (handler.succeeded()) {
						User user = handler.result();
						logger.info(user.principal());
						logger.info("Token check succeed.");
						ctx.next();
					} else {
						logger.info("Token check faild.");
						response.end(respWriter.toError(sn, RespCode.CODE_506));
					}
				});
			}
		}
	}
}
