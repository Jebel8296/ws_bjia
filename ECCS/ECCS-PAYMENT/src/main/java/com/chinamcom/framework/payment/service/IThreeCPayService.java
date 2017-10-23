package com.chinamcom.framework.payment.service;

import java.util.List;

import com.chinamcom.framework.common.service.IService;
import com.chinamcom.framework.payment.model.TbOrderPayment;
import com.chinamcom.framework.payment.model.TbOrderPaymentQueryModel;

import io.vertx.core.json.JsonObject;

public interface IThreeCPayService extends IService {

	/**
	 * 根据条件分页查询
	 * 
	 * @param payment
	 * @return
	 */
	List<TbOrderPayment> selectByTbOrderPayment(TbOrderPaymentQueryModel payment);

	/**
	 * 新增支付相关信息
	 * 
	 * @param payment
	 */
	String insertTbOrderPayment(String sn, JsonObject param);

	/**
	 * 更新支付相关信息
	 * 
	 * @param payment
	 */
	String updateTbOrderPayment(String sn, JsonObject param);
}
