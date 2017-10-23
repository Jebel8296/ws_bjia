package com.chinamcom.framework.monitor.verticle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.monitor.contant.Address;
import com.chinamcom.framework.monitor.service.IUserService;

import io.vertx.core.json.JsonObject;

/**
 * 用户管理
 * 
 * @author xuxg
 *
 */
@Component
public class UserVerticle extends BaseVerticle {

	@Autowired
	private IUserService userService;

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer(Address.LOGIN, message -> {
			JsonObject msg = (JsonObject) message.body();
			Optional<String> username = Optional.ofNullable(msg.getString("username"));
			Optional<String> password = Optional.ofNullable(msg.getString("password"));
			if (username.isPresent() && password.isPresent()) {
				userService.login(msg, jwt, rs -> {
					if (rs.succeeded()) {
						message.reply(rs.result());
					} else {
						message.reply(respWriter.toError(msg.getString("sn")));
					}
				});
			} else {
				message.reply(respWriter.toError(msg.getString("sn"), RespCode.CODE_505));

			}
		});
		vertx.eventBus().consumer(Address.USER_ADD, message -> {
			JsonObject msg = (JsonObject) message.body();
			Optional<String> username = Optional.ofNullable(msg.getString("username"));
			Optional<String> password = Optional.ofNullable(msg.getString("password"));
			if (username.isPresent() && password.isPresent()) {
				userService.addBackstageUser(msg, rs -> {
					if (rs.succeeded()) {
						message.reply(rs.result());
					} else {
						message.reply(respWriter.toError(msg.getString("sn")));
					}
				});
			} else {
				message.reply(respWriter.toError(msg.getString("sn"), RespCode.CODE_505));
			}
		});
	}
}
