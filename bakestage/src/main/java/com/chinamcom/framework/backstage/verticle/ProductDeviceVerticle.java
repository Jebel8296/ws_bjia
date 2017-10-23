package com.chinamcom.framework.backstage.verticle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.backstage.common.BackstageConsumer;
import com.chinamcom.framework.backstage.service.IProductDeviceService;

import io.vertx.core.json.JsonObject;

/**
 * 产品管理
 * 
 * @author xuxg
 *
 */
@Component
public class ProductDeviceVerticle extends BackstageVerticle {

	@Autowired
	private IProductDeviceService productDeviceService;

	@Override
	public void start() throws Exception {
		super.start();
		eb.consumer(BackstageConsumer.LIST_PRODUCTDEVICE_BACKSTAGE, message -> {
			JsonObject reqData = (JsonObject) message.body();
			log.info("list request:" + reqData);
			message.reply(productDeviceService.queryTbProductDevices(reqData));
		});
		eb.consumer(BackstageConsumer.ADD_PRODUCTDEVICE_BACKSTAGE, message -> {
			JsonObject reqData = (JsonObject) message.body();
			log.info("add request:" + reqData);
			message.reply(productDeviceService.addTbProductDevices(reqData));
		});
		eb.consumer(BackstageConsumer.DEL_PRODUCTDEVICE_BACKSTAGE, message -> {
			JsonObject reqData = (JsonObject) message.body();
			log.info("del request:" + reqData);
			message.reply(productDeviceService.deleteTbProductDevices(reqData));
		});
	}

}
