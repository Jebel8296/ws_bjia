package com.chinamcom.framework.api;

import com.chinamcom.framework.api.handler.TokenValidHandler;
import com.chinamcom.framework.common.util.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.servicediscovery.ServiceReference;
import io.vertx.servicediscovery.types.HttpEndpoint;
import org.springframework.stereotype.Component;

/**
 * 网关
 * 
 * @author xuxg
 */
@Component
public class GateWayVerticle extends BaseVerticle {

	@Override
	public void start() throws Exception {
		super.start();
		Router router = Router.router(vertx);
		enableCorsSupport(router);
		router.route().handler(LoggerHandler.create());
		router.route().handler(CookieHandler.create());
		router.route().handler(BodyHandler.create());
		// 统一登陆平台校验Token
		JsonObject param = new JsonObject();
		param.put("host", config.getProperty("tokenvalid.host", "http://api6.wwoqu.com"));
		param.put("uri", config.getProperty("tokenvalid.uri", "/login/api/local/tokenValid"));
		//router.route().handler(TokenValidHandler.create(vertx, param));
		router.route("/gateway.do").handler(this::doEventBusGateway);
		router.route("/gateway/rest/*").handler(this::doResufulGateway);

		router.route("/health").handler(this::doGatewayHealth);

		Integer port = Integer.valueOf(config.getProperty("server.http.port", "20020"));
		vertx.createHttpServer().requestHandler(router::accept).listen(port);
	}

	/**
	 * EventBus的Request/Response模式-实现网关
	 * 
	 * @param context
	 */
	private void doEventBusGateway(RoutingContext context) {
		HttpServerResponse response = context.response();
		response.putHeader("content-type", "application/json;charset=UTF-8");
		String sn = StringUtil.getSn();
		String channel = "pc";
		try {
			JsonObject request = context.getBodyAsJson();
			publishLogEvent("gateway", "request", request.encodePrettily());
			if (request.containsKey("channel")) {
				channel = request.getString("channel");
			}
			JsonObject reqData = request.getJsonObject("reqData");
			reqData.put("channel", channel);
			reqData.put("sn",request.getString("sn"));
			DeliveryOptions options = new DeliveryOptions();
			options.addHeader("cmd", "save");
			options.setSendTimeout(Integer.valueOf(config.getProperty("vertx.send.timeout", "10000")));
			breaker.executeWithFallback(future -> {
				vertx.eventBus().send(request.getString("service"), reqData, options, res -> {
					String reply = null;
					if (res.succeeded()) {
						reply = res.result().body().toString();	
						LOG.info("response:" + new JsonObject(reply).encode());
						publishLogEvent("gateway", "success", reply);
						future.complete(reply);
					} else {
						LOG.error(res.cause().getMessage(), res.cause());
						reply = respWriter.toError(reqData.getString("sn"), res.cause().getMessage());
						publishLogEvent("gateway", "error", reply);
						future.fail(reply);
					}
				});
			}, error -> {
				String reply = respWriter.toError(reqData.getString("sn"), "网络繁忙，请稍后再试");
				publishLogEvent("gateway", "error", reply);
				return reply;
			}).setHandler(ar -> {
				response.end(ar.result().toString());
			});
		} catch (Exception e) {
			LOG.error("请求网关出现异常：" + e);
			publishLogEvent("gateway", "error", respWriter.toError(sn, e.getMessage()));
			response.end(respWriter.toError(sn));
		}
	}

	/**
	 * Restful实现网关
	 * 
	 * @param context
	 */
	private void doResufulGateway(RoutingContext context) {
		int restOffset = 14;// length of '/gateway/rest/'
		String path = context.request().uri();
		if (path.length() <= restOffset) {
			context.response().setStatusCode(404).putHeader("content-type", "application/json").end();
			return;
		}
		String restname = (path.substring(restOffset).split("/"))[0];
		String restpath = path.substring(restOffset + restname.length());
		breaker.execute(future -> {
			discovery.getRecord(r -> r.getName().equals(restname) && r.getType().equals(HttpEndpoint.TYPE), ar -> {
				if (ar.succeeded()) {
					HttpClient client = ((ServiceReference) discovery.getReference(ar.result())).get();
					HttpClientRequest req = client.request(context.request().method(), restpath, response -> {
						response.bodyHandler(body -> {
							HttpServerResponse r = context.response().setStatusCode(response.statusCode());
							response.headers().forEach(head -> {
								r.putHeader(head.getKey(), head.getValue());
							});
							r.end(body);
							future.succeeded();
						});
					});
					context.request().headers().forEach(head -> {
						req.putHeader(head.getKey(), head.getValue());
					});
					if (context.user() != null) {
						req.putHeader("user-principal", context.user().principal().encode());
					}
					if (context.getBody() == null) {
						req.end();
					} else {
						req.end(context.getBody());
					}
				} else {
					future.fail(ar.cause());
				}
			});
		}).setHandler(ar -> {
			if (ar.failed()) {
				badGateway(context);
			}
		});
	}
}
