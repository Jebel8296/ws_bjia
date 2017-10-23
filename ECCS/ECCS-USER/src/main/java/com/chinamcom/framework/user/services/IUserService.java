package com.chinamcom.framework.user.services;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;

/**
 * 收货地址维护Service
 * 
 * @author xuxg
 * @since 20161009
 *
 */
public interface IUserService {
	/**
	 * 注册
	 * 
	 * @param param
	 * @param reply
	 * @throws Exception
	 */
	public String register(String sn, JsonObject param) throws Exception;

	/**
	 * 登陆
	 * 
	 * @param param
	 * @param reply
	 * @throws Exception
	 */
	public String loginCn(JWTAuth jwt, String sn, String phone, String password) throws Exception;

	/**
	 * 微信登陆
	 * 
	 * @param param
	 * @param reply
	 * @throws Exception
	 */
	public void loginWx(JsonObject param, JsonObject reply) throws Exception;

	/**
	 * 微播登陆
	 * 
	 * @param param
	 * @param reply
	 * @throws Exception
	 */
	public void loginWb(JsonObject param, JsonObject reply) throws Exception;

	/**
	 * QQ登陆
	 * 
	 * @param param
	 * @param reply
	 * @throws Exception
	 */
	public void loginQq(JsonObject param, JsonObject reply) throws Exception;

	/**
	 * 修改密码
	 * 
	 * @param sn
	 * @param param
	 * @throws Exception
	 */
	public String modifyPass(String sn, JsonObject param) throws Exception;

	/**
	 * 发送短信验证码
	 * 
	 * @param param
	 * @param sn
	 * @param ex
	 *            过期时间
	 * @throws Exception
	 */
	public String sendSMS(String sn, JsonObject param, Integer ex) throws Exception;

	/**
	 * 更新短信验证信息
	 * 
	 * @param param
	 * @param sn
	 * @throws Exception
	 */
	public String updateSMS(String sn, JsonObject param);

	/**
	 * 退出登陆
	 * 
	 * @param param
	 * @param reply
	 * @throws Exception
	 */
	public void unLogin(JsonObject param, JsonObject reply) throws Exception;

	/**
	 * 验证验证码是否输入正确
	 * 
	 * @param smssn
	 * @throws Exception
	 */
	public boolean verifyCode(String smssn, String code) throws Exception;
}
