package com.chinamcom.framework.user.services;

import io.vertx.core.json.JsonObject;

public interface IExpressService {

	/**
	 * 收货地址-新增
	 * 
	 * @param sn
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String addExpress(String sn, JsonObject param) throws Exception;

	/**
	 * 收货地址-修改
	 * 
	 * @param sn
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String modExpress(String sn, JsonObject param) throws Exception;

	/**
	 * 收货地址-删除
	 * 
	 * @param sn
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String delExpress(String sn, JsonObject param) throws Exception;

	/**
	 * 收货地址-默认
	 * 
	 * @param sn
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String defExpress(String sn, JsonObject param) throws Exception;

	/**
	 * 收货地址-列表
	 * 
	 * @param sn
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String listExpress(String sn, JsonObject param) throws Exception;
}
