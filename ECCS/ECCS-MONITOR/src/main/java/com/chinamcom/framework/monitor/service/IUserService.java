package com.chinamcom.framework.monitor.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;

public interface IUserService {

	/**
	 * 登陆
	 * 
	 * @param param
	 * @param resultHandler
	 */
	void login(JsonObject param, JWTAuth jwt, Handler<AsyncResult<JsonObject>> resultHandler);

	/**
	 * 创建用户
	 * 
	 * @param param
	 * @param resultHandler
	 */
	void addBackstageUser(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler);

	/**
	 * 更新用户
	 * 
	 * @param param
	 * @param resultHandler
	 */
	void updateBackstageUser(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler);

	/**
	 * 删除用户
	 * 
	 * @param param
	 * @param resultHandler
	 */
	void deleteBackstageUser(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler);

	/**
	 * 用户列表
	 * 
	 * @param param
	 * @param resultHandler
	 */
	void queryBackstageUser(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler);
}
