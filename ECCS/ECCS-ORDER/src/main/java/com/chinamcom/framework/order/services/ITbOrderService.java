package com.chinamcom.framework.order.services;

import com.chinamcom.framework.common.service.IService;

import io.vertx.core.json.JsonObject;

public interface ITbOrderService extends IService {

	/**
	 * 查询订单列表-分页
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	String selectTbOrderList(String sn, JsonObject param);

	/**
	 * 提交订单
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	String submitTbOrderInfo(String sn, JsonObject param);
	
	/**
	 * 从购物车提交订单
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	String submitTbOrderInfoByCart(String sn, JsonObject param);

	/**
	 * 订单详情
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	void selectTbOrderInfo(JsonObject param,JsonObject result);

	/**
	 * 更新订单信息
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	String updateTbOrderInfo(String sn, JsonObject param);
	
}
