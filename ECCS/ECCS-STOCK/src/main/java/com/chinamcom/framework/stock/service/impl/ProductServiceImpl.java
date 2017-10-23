package com.chinamcom.framework.stock.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.common.ebspi.ProductService;
import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.stock.mapper.TbProductStockMapper;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@Service("StockService")
public class ProductServiceImpl extends BaseService implements ProductService {

	@Autowired
	private TbProductStockMapper dao;

	@Override
	public void deleteTbProductDevices(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler) {
	}

	@Override
	public void addTbProductDevices(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler) {
	}

	@Override
	public void queryTbProductDevices(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler) {
	}

}
