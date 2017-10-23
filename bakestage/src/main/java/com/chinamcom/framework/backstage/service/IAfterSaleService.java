package com.chinamcom.framework.backstage.service;

import io.vertx.core.json.JsonObject;

public interface IAfterSaleService {

	/**
	 * 查询售后服务
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	JsonObject selectTbAfterSale(JsonObject param);

	/**
	 * 售后服务详情
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	JsonObject selectTbAfterSaleInfo(JsonObject param);

	/**
	 * 客服处理售后服务
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	JsonObject handleTbAfterSale(JsonObject param);
}
