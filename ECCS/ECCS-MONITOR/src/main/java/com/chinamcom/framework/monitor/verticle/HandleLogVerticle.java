package com.chinamcom.framework.monitor.verticle;

import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.verticle.BaseVerticle;

import io.vertx.core.json.JsonObject;

/**
 * 日志操作
 * 
 * @author xuxg
 * @since 20170112
 *
 */
@Component
public class HandleLogVerticle extends BaseVerticle {

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer(ConsumerConstant.MONITOR_LOG, message -> {
			JsonObject logs = (JsonObject) message.body();
			try {
				// 收到日志 进行处理，可存入数据库或大数据平台，进而进行统计分析......
				LOG.info(logs.encode());

			} catch (Exception e) {
				LOG.error(e);
			}
		});
	}
}
