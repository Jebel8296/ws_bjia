package com.chinamcom.framework.products.services;

import io.vertx.core.json.JsonObject;

public interface ITbProductInfoService {

	/**
	 * 查询产品
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	String selectTbProductInfoList(String sn, JsonObject param) throws Exception;

	/**
	 * 查询产品
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	String selectTbProductTypeList(String sn, JsonObject param) throws Exception;

	/**
	 * 设备产品查询
	 * 
	 * @param sn
	 * @param param
	 * @return
	 */
	String selectTbProductDevicesList(String sn, JsonObject param) throws Exception;
}
