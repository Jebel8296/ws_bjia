package com.chinamcom.framework.payment.verticle;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bjcathay.pay.service.util.ClientSignHelper;
import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.util.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.payment.service.IThreeCPayService;

import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.rx.java.ObservableFuture;
import io.vertx.rx.java.RxHelper;

@Component
public class RequestUnifyPayVerticle extends BaseVerticle {

	@Autowired
	private IThreeCPayService threeCPayService;

	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.get("/gateway/acceptpay").handler(routingContext -> {
			JsonObject resp = new JsonObject().put("reply", "faild");
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "application/json;charset=UTF-8");
			String sn = StringUtil.getSn();
			String service = ConsumerConstant.PAYMENT_ACCEPTPAY;
			try {
				MultiMap multiMap = routingContext.request().params();
				LOG.info(routingContext.request().absoluteURI());
				LOG.info("支付平台返回的数据："+multiMap.toString());
				String money = multiMap.get("money");//支付的金额，单位为分
				String requestKey = multiMap.get("requestKey");//订单编号
				String payTime = multiMap.get("payTime");//支付时间
				String orderSn  = multiMap.get("orderSn");//支付中心对应的唯一码-方便后续对账中
				String payTypeCode = multiMap.get("payTypeCode");//支付渠道code-支持的值为：Wechat、Alipay、ChinapayOld、Paypal
				String payTypeName = multiMap.get("payTypeName");//支付渠道名称，支持的值为：微信支付、支付宝支付、银联支付、PayPal
				String currencyType = multiMap.get("currencyType");//业务系统传递的支付币种，支持：CNY 、 USD
				String payStatus = multiMap.get("payStatus");//支付成功:SUCCESSPAY;支付失败:FAILUREPAY
				int status = payStatus.equals("SUCCESSPAY") ? 2 : 3;
				String domainCode = multiMap.get("domainCode");//支付平台分配的domainCode
				String sign = multiMap.get("sign");
				//验签
				List<String> signParam = new ArrayList<String>();
				signParam.add(money);
				signParam.add(requestKey);
				signParam.add(payTime);
				signParam.add(orderSn);
				signParam.add(payTypeCode);
				signParam.add(payTypeName);
				signParam.add(currencyType);
				signParam.add(payStatus);
				signParam.add(domainCode);
				String sign2 = ClientSignHelper.getSign(config.getProperty("pay.domainSecret", "test"), signParam);
				if(sign.equals(sign2)){
					JsonObject reqData = new JsonObject();
					reqData.put("sn", sn);
					reqData.put("ordercode", requestKey);
					reqData.put("status", status);
					reqData.put("ext01", orderSn);
					reqData.put("ext02", money);
					reqData.put("ext03", payTypeCode);
					reqData.put("paychannel", payTypeName);
					reqData.put("pay_rsp", routingContext.request().absoluteURI());
					reqData.put("pay_msg", multiMap.toString());
					DeliveryOptions options = new DeliveryOptions();
					options.addHeader("command", service);
					options.setSendTimeout(Integer.valueOf(config.getProperty("server.http.port.send.timeout", "10000")));
					//更新支付状态
					vertx.eventBus().send(service, reqData, options, rep -> {
						if (rep.succeeded()) {
							JsonObject d = new JsonObject(rep.result().body().toString());
							resp.mergeIn(d);
							resp.put("reply", "success");
							//更新订单状态
							JsonObject ormessage = new JsonObject();
							ormessage.put("ordercode",requestKey).put("status", status==2?2:1).put("desc", payTypeName);
							vertx.eventBus().send(ConsumerConstant.ZM3C_ORDER_UPDATE, ormessage);
							LOG.info("已发送更新订单支付状态,Message=" + ormessage);
						} else {
							JsonObject d = new JsonObject(rep.result().body().toString());
							resp.mergeIn(d);
						}
						LOG.info(resp.toString());
						response.end(resp.getString("reply"));
					});
				}else{
					LOG.info("验签失败.");
					response.end(resp.getString("reply"));
				}
			} catch (Exception ex) {
				LOG.error(ex);
				response.end(resp.getString("reply"));
			}
		});
		vertx.eventBus().consumer(ConsumerConstant.PAYMENT_JUMPPAY, message -> {
			LOG.info("jumppay_received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));// 用户ID
				Optional<String> ordercode = Optional.ofNullable(reqData.getString("ordercode"));// 订单号
				Optional<Integer> orderid = Optional.ofNullable(reqData.getInteger("orderid"));// 订单号
				Optional<String> payfee = Optional.ofNullable(reqData.getString("payfee"));// 支付金额
				String channel = Optional.ofNullable(reqData.getString("channel")).orElse("pc");// 渠道
				if (uid.isPresent() && (ordercode.isPresent() || orderid.isPresent())) {
					ObservableFuture<Message<Object>> orderobservable = RxHelper.observableFuture();
					vertx.eventBus().send(ConsumerConstant.ZM3C_ORDER_INFO, reqData,orderobservable.toHandler());
					orderobservable.subscribe(o->{
						JsonObject orderinfo = new JsonObject(o.body().toString());
						LOG.info("订单信息：" + orderinfo);
						JsonObject order = orderinfo.getJsonObject("respData");
						String r = null;
						if (orderinfo.getInteger("code") == 200 && order != null && order.size()>0) {
							try {
								if (order.getInteger("status") != 1) {
									r = respWriter.toError(sn, "支付失败，请检查订单状态.");
								}else{
									StringBuilder url = new StringBuilder();
									if (channel.equals("pc")) {
										url.append(config.getProperty("pc.pay.url", "http://api6.wwoqu.com/pay.html#/?"));
									} else if (channel.equals("m")) {
										url.append(config.getProperty("h5.pay.url", "http://api6.wwoqu.com:80/mpay.html#/?"));
									}
									List<String> signParam = new ArrayList<String>();
									String domainCode = config.getProperty("pay.domainCode", "test");
									String money = (new BigDecimal(order.getFloat("pay")).multiply(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
									String info = order.getString("code");
									String requestChannel = "1";
									String requestKey = order.getString("code");
									String currencyType = "CNY";
									String pageUrl = URLEncoder.encode(config.getProperty("pay.pageUrl", "http://localhost/paysuccess/")+order.getString("code"),"UTF-8");
									String bgUrl = URLEncoder.encode(config.getProperty("pay.bgUrl", "http://localhost:60060/gateway/acceptpay"),"UTF-8");
									signParam.add(domainCode);
									signParam.add(requestChannel);
									signParam.add(money);
									signParam.add(info);
									signParam.add(requestKey);
									signParam.add(currencyType);
									signParam.add(pageUrl);
									signParam.add(bgUrl);
									signParam.add(pageUrl);
									signParam.add(pageUrl);
									String sign = ClientSignHelper.getSign(config.getProperty("pay.domainSecret", "test"),signParam);
									url.append("domainCode=" + domainCode).append("&");
									url.append("requestChannel=" + requestChannel).append("&");
									url.append("money=" + money).append("&");
									url.append("info=" + info).append("&");
									url.append("requestKey=" + requestKey).append("&");
									url.append("currencyType=" + currencyType).append("&");
									url.append("pageUrl=" + pageUrl).append("&");
									url.append("bgUrl=" + bgUrl).append("&");
									url.append("dialogOkUrl=" + pageUrl).append("&");
									url.append("dialogFailUrl=" + pageUrl).append("&");
									reqData.put("PAY_REQ", url.toString());
									reqData.put("ordercode", order.getString("code"));
									reqData.put("orderid", order.getInteger("oid"));
									threeCPayService.insertTbOrderPayment(sn, reqData);
									url.append("sign=" + sign);
									r = respWriter.toSuccess(new JsonObject().put("payurl", new String(url.toString().getBytes(), "UTF-8")),sn);
								}
							} catch (Exception e1) {
								LOG.error(e1);
								r = respWriter.toError(sn);
							}
						}else{
							LOG.info("不存在订单号为："+ordercode.get()+"的订单.");
							r = respWriter.toError(sn,RespCode.CODE_504);
						}
						LOG.info("jumppay_reply:"+r);
						message.reply(r);
					}, e -> {
						LOG.error("调用订单服务查询订单信息异常:" + e);
						message.reply(respWriter.toError(sn, "支付失败,请重新提交."));
					});
				} else {
					reply = respWriter.toError(sn,RespCode.CODE_505);
					message.reply(reply);
				}
			} catch (Exception e) {
				LOG.error("跳转支付统一平台出现异常:" + e);
				reply = respWriter.toError(sn, "支付失败,请重新提交.");
				message.reply(reply);
			}
		});
		vertx.eventBus().consumer(ConsumerConstant.PAYMENT_ACCEPTPAY, message -> {
			LOG.info("acceptpay_received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				threeCPayService.updateTbOrderPayment(sn, reqData);
				reply = respWriter.toSuccess(sn);
			} catch (Exception e) {
				LOG.error("支付出现异常:" + e);
				reply = respWriter.toError(sn, "支付失败,请重新提交.");
			}
			LOG.info("acceptpay_reply:" + reply);
			message.reply(reply);
		});

		vertx.createHttpServer().requestHandler(router::accept).listen(Integer.valueOf(config.getProperty("server.http.port", "60060")));
	}
}
