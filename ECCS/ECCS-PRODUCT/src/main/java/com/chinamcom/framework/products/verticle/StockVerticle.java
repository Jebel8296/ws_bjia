package com.chinamcom.framework.products.verticle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.products.services.ITbProductStockService;

import io.vertx.core.json.JsonObject;

/**
 * 库存服务
 * 
 * @author xuxg
 * @since 20160718
 */
@Component
public class StockVerticle extends BaseVerticle {
	@Autowired
	private ITbProductStockService tbProductStockService;

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer(ConsumerConstant.ZM3C_STOCK_QUERY, message -> {
			JsonObject reqData = (JsonObject) message.body();
			LOG.info(ConsumerConstant.ZM3C_STOCK_QUERY + ".received:" + reqData.toString());
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				if (reqData.containsKey("product")) {
					reply = tbProductStockService.selectTbProductStockList(sn, reqData);
				} else {
					reply = respWriter.toError(sn, RespCode.CODE_505);
				}
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
				if (reqData.containsKey("product") && reqData.containsKey("type")
						&& (reqData.getInteger("type") == 1 || reqData.getInteger("type") == 2)) {// product:待更新的库存产品type:更新类型1增加2減少
					reply = tbProductStockService.updateTbProductStockList(sn, reqData);
				} else {
					reply = respWriter.toError(sn, RespCode.CODE_505);
				}
			} catch (Exception e) {
				LOG.error("更新库存异常：" + e);
				reply = respWriter.toError(sn, "更新库存失败，请重新提交.");
			}
			LOG.info(ConsumerConstant.ZM3C_STOCK_UPDATE + ".reply:" + reply);
			message.reply(reply);
		});
	}

}
