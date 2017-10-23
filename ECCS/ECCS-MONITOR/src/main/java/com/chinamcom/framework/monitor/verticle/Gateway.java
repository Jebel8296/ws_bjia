package com.chinamcom.framework.monitor.verticle;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.util.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.MetricsService;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * 官网后台网关
 * 
 * @author xuxg
 * @since 20161123
 *
 */
@Component
public class Gateway extends BaseVerticle {

	@Override
	public void start() throws Exception {
		super.start();
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		enableCorsSupport(router);
		BridgeOptions bridgeOptions = new BridgeOptions();
		bridgeOptions.addOutboundPermitted(new PermittedOptions().setAddress(ConsumerConstant.MONITOR_LOG));
		bridgeOptions.addOutboundPermitted(new PermittedOptions().setAddress(ConsumerConstant.MONITOR_METRICS));
		SockJSHandler sockJSHandler = SockJSHandler.create(vertx).bridge(bridgeOptions);
		router.route("/monitor/*").handler(sockJSHandler);
		router.route("/backstage").handler(this::doGateway);
		router.route("/health").handler(this::doGatewayHealth);
		int port = Integer.valueOf(config.getProperty("server.http.port", "9999"));
		vertx.createHttpServer().requestHandler(router::accept).listen(port);

		// 每隔5秒发送一次监控数据
		int metricsInterval = Integer.valueOf(config.getProperty("monitor.metrics.interval", "5000"));
		MetricsService service = MetricsService.create(vertx);
		vertx.setPeriodic(metricsInterval, t -> {
			JsonObject metrics = service.getMetricsSnapshot(vertx);
			vertx.eventBus().publish(ConsumerConstant.MONITOR_METRICS, metrics);
		});
	}

	private void doGateway(RoutingContext context) {
		HttpServerResponse response = context.response();
		response.putHeader("content-type", "application/json;charset=UTF-8");
		String sn = StringUtil.getSn();
		String channel = "pc";
		String service = "zm3c.service.no";
		try {
			JsonObject request = context.getBodyAsJson();
			if (StringUtils.isEmpty(request)) {
				response.end(respWriter.toError(sn, "ReqBody is null."));
				return;
			}
			LOG.info("request:" + request);
			publishLogEvent("gateway", "request", request.encodePrettily());
			if (request.containsKey("service")) {
				service = request.getString("service");
			}
			request.put("service", service);
			if (request.containsKey("sn")) {
				sn = request.getString("sn");
			}
			if (request.containsKey("channel")) {
				channel = request.getString("channel");
			}
			if (!request.containsKey("reqData")) {
				response.end(respWriter.toError(sn, "ReqData is null."));
				return;
			}
			JsonObject reqData = request.getJsonObject("reqData");
			reqData.put("sn", sn);
			reqData.put("channel", channel);
			breaker.execute(future -> {
				DeliveryOptions deliveryOptions = new DeliveryOptions();
				deliveryOptions.setSendTimeout(10000L);
				vertx.eventBus().send(request.getString("service"), reqData, deliveryOptions, res -> {
					String reply = null;
					if (res.succeeded()) {
						reply = res.result().body().toString();
						LOG.info("response:" + reply);
						publishLogEvent("gateway", "success", reply);
						future.complete(reply);
					}
				});
			}).setHandler(ar -> {
				if (ar.succeeded()) {
					response.end(ar.result().toString());
				} else {
					String reply = respWriter.toError(reqData.getString("sn"), "网络异常，请重新提交.");
					publishLogEvent("gateway", "error", reply);
					response.end(reply);
				}
			});
		} catch (Exception e) {
			LOG.error("请求网关出现异常：" + e);
			publishLogEvent("gateway", "error", respWriter.toError(sn, e.getMessage()));
			response.end(respWriter.toError(sn));
		}
	}
}
