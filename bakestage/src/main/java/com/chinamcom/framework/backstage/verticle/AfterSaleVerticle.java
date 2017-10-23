package com.chinamcom.framework.backstage.verticle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.backstage.common.BackstageConsumer;
import com.chinamcom.framework.backstage.service.IAfterSaleService;

/**
 * 客服受理
 * 
 * @author xuxg
 * @since 20160926
 *
 */
@Component
public class AfterSaleVerticle extends BackstageVerticle {

	@Autowired
	private IAfterSaleService afterSaleService;

	@Override
	public void start() throws Exception {
		super.start();
		eb.consumer(BackstageConsumer.LIST_AFTERSALE_BACKSTAGE, message -> {
			checkToken(message, resultHandler -> {
				if (resultHandler.succeeded()) {
					message.reply(afterSaleService.selectTbAfterSale(resultHandler.result()));
				}
			});
		});
		eb.consumer(BackstageConsumer.INFO_AFTERSALE_BACKSTAGE, message -> {
			checkToken(message, resultHandler -> {
				if (resultHandler.succeeded()) {
					message.reply(afterSaleService.selectTbAfterSaleInfo(resultHandler.result()));
				}
			});
		});
		eb.consumer(BackstageConsumer.HANDLE_AFTERSALE_BACKSTAGE, message -> {
			checkToken(message, resultHandler -> {
				if (resultHandler.succeeded()) {
					message.reply(afterSaleService.handleTbAfterSale(resultHandler.result()));
				}
			});

		});
	}
}
