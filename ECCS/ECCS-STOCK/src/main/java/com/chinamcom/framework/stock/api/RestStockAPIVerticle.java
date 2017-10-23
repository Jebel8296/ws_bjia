package com.chinamcom.framework.stock.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.ebspi.ProductService;
import com.chinamcom.framework.common.verticle.BaseVerticle;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;

/**
 * 库存服务（Rest)
 * 
 * @author xuxg
 *
 */
@Component
public class RestStockAPIVerticle extends BaseVerticle {

	public static final String SERVICE_NAME = "stock";

	private static final String API_HEARTBEAT = "/health";
	private static final String API_PRODUCT_DEVICES_ADD = "/stock/add";
	private static final String API_PRODUCT_DEVICES_QUERY = "/stock/query";
	private static final String API_PRODUCT_DEVICES_DELETE = "/stock/:id";

	@Autowired
	private ProductService service;

	@Override
	public void start() throws Exception {
		super.start();
		final Router router = Router.router(vertx);
		enableCorsSupport(router);
		router.route().handler(CookieHandler.create());
		router.route().handler(BodyHandler.create());
		router.get(API_HEARTBEAT).handler(context -> {
			JsonObject checkResult = new JsonObject().put("status", "up");
			LOG.info(checkResult);
			context.response().end(checkResult.encodePrettily());
		});
		router.post(API_PRODUCT_DEVICES_ADD).handler(this::apiProductDevicesAdd);
		router.get(API_PRODUCT_DEVICES_QUERY).handler(this::apiProductDevicesQuery);
		router.delete(API_PRODUCT_DEVICES_DELETE).handler(this::apiProductDevicesDelete);
		String host = config.getProperty("product.http.host", "localhost");
		String port = config.getProperty("product.http.port", "38082");
		createHttpServer(router, host, Integer.valueOf(port));
	}

	private void apiProductDevicesDelete(RoutingContext context) {
		try {
			service.deleteTbProductDevices(multiMap2Json(context.request().params()),
					resultHandler(context, Json::encodePrettily));
		} catch (Exception e) {
			LOG.error(e.getCause());
			reply500Response(context, e.getMessage());
		}
	}

	private void apiProductDevicesAdd(RoutingContext context) {
		try {
			service.addTbProductDevices(context.getBodyAsJson(), resultHandler(context, Json::encodePrettily));
		} catch (Exception e) {
			LOG.error(e.getCause());
			reply500Response(context, e.getMessage());
		}
	}

	private void apiProductDevicesQuery(RoutingContext context) {
		try {
			service.queryTbProductDevices(multiMap2Json(context.request().params()),
					resultHandler(context, Json::encodePrettily));
		} catch (Exception e) {
			LOG.error(e.getCause());
			reply500Response(context, e.getMessage());
		}
	}
}
