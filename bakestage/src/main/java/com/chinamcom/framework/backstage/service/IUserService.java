package com.chinamcom.framework.backstage.service;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;

public interface IUserService {

	/**
	 * 登陆
	 * 
	 * @param param
	 * @param resultHandler
	 */
	JsonObject login(JsonObject param, JWTAuth jwt);

	/**
	 * 创建用户
	 * 
	 * @param param
	 * @param resultHandler
	 */
	JsonObject addBackstageUser(JsonObject param);

	/**
	 * 更新用户
	 * 
	 * @param param
	 * @param resultHandler
	 */
	JsonObject updateBackstageUser(JsonObject param);

	/**
	 * 删除用户
	 * 
	 * @param param
	 * @param resultHandler
	 */
	JsonObject deleteBackstageUser(JsonObject param);

	/**
	 * 用户列表
	 * 
	 * @param param
	 * @param resultHandler
	 */
	JsonObject queryBackstageUser(JsonObject param);
}
