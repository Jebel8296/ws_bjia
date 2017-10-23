package com.chinamcom.framework.products.services;

import io.vertx.core.json.JsonObject;

public interface ITbProductStockService {

	/**
	 * 查询库存信息
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	String selectTbProductStockList(String sn, JsonObject param) throws Exception;

	/**
	 * 更新库存信息
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	String updateTbProductStockList(String sn, JsonObject param) throws Exception;
}
