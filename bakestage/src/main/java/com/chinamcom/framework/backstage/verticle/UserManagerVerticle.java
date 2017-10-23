package com.chinamcom.framework.backstage.verticle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.backstage.common.BackstageConsumer;
import com.chinamcom.framework.backstage.service.IUserService;

import io.vertx.core.json.JsonObject;

/**
 * 用户管理
 * 
 * @author xuxg
 *
 */
@Component
public class UserManagerVerticle extends BackstageVerticle {

	@Autowired
	private IUserService userService;

	@Override
	public void start() throws Exception {
		super.start();
		eb.consumer(BackstageConsumer.LOGIN_BACKSTAGE, message -> {
			JsonObject reqData = (JsonObject) message.body();
			log.info("request:" + reqData);
			message.reply(userService.login(reqData, jwt));
		});
		eb.consumer(BackstageConsumer.ADD_USER_BACKSTAGE, message -> {
			JsonObject reqData = (JsonObject) message.body();
			log.info("request:" + reqData);
			message.reply(userService.addBackstageUser(reqData));
		});
	}
}
