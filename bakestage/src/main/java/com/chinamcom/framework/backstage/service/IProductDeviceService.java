package com.chinamcom.framework.backstage.service;

import io.vertx.core.json.JsonObject;

/**
 * 产品服务
 * 
 * @author xuxg
 *
 */
public interface IProductDeviceService {

	/**
	 * 设备编号与产品编号对应关系查询
	 * 
	 * @param param
	 *            {"pageNum":xx,"pageSize":xx,"prodcode":xx,"devicode":xx}
	 * @return
	 */
	JsonObject queryTbProductDevices(JsonObject param);

	/**
	 * 设备编号与产品编号对应关系新增
	 * 
	 * @param param
	 *            {"prodcode":xx,"devicode":xx}
	 * @return
	 */
	JsonObject addTbProductDevices(JsonObject param);

	/**
	 * 设备编号与产品编号对应关系删除
	 * 
	 * @param param
	 *            {"id":xx}
	 * @return
	 */
	JsonObject deleteTbProductDevices(JsonObject param);
}
