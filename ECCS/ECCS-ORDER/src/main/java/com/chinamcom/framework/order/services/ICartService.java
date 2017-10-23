package com.chinamcom.framework.order.services;

import io.vertx.core.json.JsonObject;

public interface ICartService {
	/**
	 * 加入购物车
	 * 
	 * @param sn
	 * @param param
	 * @return
	 * @throws Exception
	 */
	String addCart(String sn, JsonObject param);

	/**
	 * 增加数量
	 * 
	 * @param sn
	 * @param param
	 * @return
	 * @throws Exception
	 */
	String incrCart(String sn, JsonObject param);

	/**
	 * 减少数量
	 * 
	 * @param sn
	 * @param param
	 * @return
	 * @throws Exception
	 */
	String decrCart(String sn, JsonObject param);

	/**
	 * 删除购物车
	 * 
	 * @param sn
	 * @param param
	 * @return
	 * @throws Exception
	 */
	String delCart(String sn, JsonObject param);
	
	/**
	 * 去结算
	 * 
	 * @param buy
	 * @param param
	 * @return
	 * @throws Exception
	 */
	void account(JsonObject buy, JsonObject param);
	
	/**
	 * 购买
	 * 
	 * @param sn
	 * @param param
	 * @return
	 * @throws Exception
	 */
	String checked(String sn, JsonObject param);

	/**
	 * 购物车列表--分页
	 * 
	 * @param sn
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String listCart(String sn, JsonObject param);
}
