package com.chinamcom.framework.common.ebspi;

import java.util.concurrent.TimeUnit;

import as.leap.vertx.rpc.RequestProp;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * 产品服务
 * 
 * @author xuxg
 *
 */
public interface ProductService {

	String SERVICE_ADDRESS = "service.product";

	/**
	 * 设备编号与产品编号对应关系查询
	 * 
	 * @param param
	 *            {"pageNum":xx,"pageSize":xx,"prodcode":xx,"devicode":xx}
	 * @param resultHandler
	 * @return
	 */
	@RequestProp(timeout = 2, timeUnit = TimeUnit.SECONDS, retry = 2)
	void queryTbProductDevices(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler);

	/**
	 * 设备编号与产品编号对应关系新增
	 * 
	 * @param param
	 *            {"prodcode":xx,"devicode":xx}
	 * @param resultHandler
	 * @return
	 */
	@RequestProp(timeout = 2, timeUnit = TimeUnit.SECONDS, retry = 2)
	void addTbProductDevices(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler);

	/**
	 * 设备编号与产品编号对应关系删除
	 * 
	 * @param param
	 *            {"id":xx}
	 * @param resultHandler
	 * @return
	 */
	@RequestProp(timeout = 2, timeUnit = TimeUnit.SECONDS, retry = 2)
	void deleteTbProductDevices(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler);
}
