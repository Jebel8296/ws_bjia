package com.chinamcom.framework.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.util.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.payment.sdk.union.AcpService;
import com.chinamcom.framework.payment.sdk.union.SDKConstants;

import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;

/**
 * 支付服务
 * 
 * @author xuxg
 * @since 20160901
 *
 */
@Component
public class PaymentGateway extends BaseVerticle {

	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		router.route().handler(LoggerHandler.create());
		router.route().handler(BodyHandler.create());
		// 支付调用接口
		router.route("/zmpay.do").handler(context -> {
			HttpServerRequest request = context.request();
			HttpServerResponse response = context.response();
			response.putHeader("content-type", "text/html; charset=utf-8");
			response.putHeader("Access-Control-Allow-Origin", "*");
			response.putHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,OPTIONS");
			response.putHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
			String method = context.request().method().name();
			if (method.equals("OPTIONS")) {
				response.end();
				return;
			}
			String sn = StringUtil.getSn();
			try {
				JsonObject reqData = new JsonObject();
				String service = request.getParam("payType");
				reqData.put("service", service);
				String orderId = request.getParam("orderId");
				reqData.put("orderId", orderId);
				String txnAmt = request.getParam("txnAmt");
				reqData.put("txnAmt", txnAmt);
				reqData.put("sn", sn);
				reqData.put("channel", "pc");

				DeliveryOptions options = new DeliveryOptions();
				options.addHeader("cmd", "save");
				options.setSendTimeout(Integer.valueOf(config.getProperty("server.http.port.send.timeout", "10000")));
				vertx.eventBus().send(service, reqData, res -> {
					String reply = null;
					if (res.succeeded()) {
						reply = res.result().body().toString();
					} else {
						LOG.error(res.cause().getMessage(), res.cause());
						reply = respWriter.toError(sn, res.cause().getMessage());
					}
					LOG.info(reply);
					response.end(reply);
				});
			} catch (Exception ex) {
				LOG.error(ex);
				response.end(respWriter.toError(sn));
			}
		});
		// 支付后台通知
		router.route("/backUnionRcvResp.do").handler(context -> {
			HttpServerRequest request = context.request();
			JsonObject reply = new JsonObject();
			try {
				MultiMap result = request.params();
				result.forEach(item -> {
					reply.put(item.getKey(), item.getValue());
				});
				LOG.info("backUnionRcvResp:" + reply.toString());
			} catch (Exception e) {
				LOG.error(e);
			}
		});

		// 支付前台通知
		router.route("/frontUnionRcvResp.do").handler(context -> {
			HttpServerRequest request = context.request();
			HttpServerResponse response = context.response();
			response.putHeader("content-type", "text/html; charset=utf-8");
			JsonObject mess = new JsonObject();
			try {
				String encoding = request.getParam(SDKConstants.param_encoding);
				LOG.info("返回报文encoding:" + encoding);
				Map<String,String> validata = new HashMap<String,String>();
				MultiMap result = request.params();
				result.forEach(item -> {
					try {
						String key = item.getKey();
						String val = new String(item.getValue().getBytes(encoding), encoding);
						validata.put(key, val);
						mess.put(key, val);
					} catch (Exception e) {
					}
				});
				LOG.info("frontUnionRcvResp:" + mess.toString());
				if(!AcpService.validate(validata, encoding)){
				}else{
				}
			} catch (Exception e) {
				LOG.error(e);
			}
			response.end(mess.toString());
		});
		vertx.createHttpServer().requestHandler(router::accept).listen(Integer.valueOf(config.getProperty("server.http.port", "60060")));
	}
}
