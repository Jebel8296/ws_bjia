package com.chinamcom.framework.stock.verticle;

import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.verticle.BaseVerticle;

import io.vertx.core.json.JsonObject;

/**
 * 库存服务
 * 
 * @author xuxg
 * @since 20160718
 */
@Component
public class StockVerticle extends BaseVerticle {
	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer(ConsumerConstant.ZM3C_STOCK_QUERY, message -> {
			LOG.info(ConsumerConstant.ZM3C_STOCK_QUERY + ".received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
			} catch (Exception e) {
				LOG.error("检索库存异常：" + e);
				reply = respWriter.toError(sn, "检索库存失败，请重新提交.");
			}
			LOG.info(ConsumerConstant.ZM3C_STOCK_QUERY + ".reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.ZM3C_STOCK_UPDATE, message -> {
			LOG.info(ConsumerConstant.ZM3C_STOCK_UPDATE + ".received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
			} catch (Exception e) {
				LOG.error("更新库存异常：" + e);
				reply = respWriter.toError(sn, "更新库存失败，请重新提交.");
			}
			LOG.info(ConsumerConstant.ZM3C_STOCK_UPDATE + ".reply:" + reply);
			message.reply(reply);
		});
	}

}
