package com.chinamcom.framework.order.verticle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.order.services.ICartService;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 购物车服务
 * 
 * @author xuxg
 * @since 20160901
 *
 */
@Component
public class CartVerticle extends BaseVerticle {

	@Autowired
	private ICartService cartServiceImpl;

	@Override
	public void start() throws Exception {

		vertx.eventBus().consumer(ConsumerConstant.CART_INCR, message -> {
			LOG.info("cart_incr_received:" + message.body());
			JsonObject param = (JsonObject) message.body();
			String sn = param.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> instanceid = Optional.ofNullable(param.getInteger("instanceid"));// 购物车中产品的ID
				Optional<Integer> uid = Optional.ofNullable(param.getInteger("uid"));// 用户ID
				if (instanceid.isPresent() && uid.isPresent()) {
					reply = cartServiceImpl.incrCart(sn, param);
				} else {
					reply = respWriter.toError(sn, RespCode.CODE_505);
				}

			} catch (Exception e) {
				LOG.error("购物车数量增加异常：" + e);
				reply = respWriter.toError(sn, "操作失败，请重新提交.");
			}
			LOG.info("cart_incr_reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.CART_DECR, message -> {
			LOG.info("cart_decr_received:" + message.body());
			JsonObject param = (JsonObject) message.body();
			String sn = param.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> instanceid = Optional.ofNullable(param.getInteger("instanceid"));// 购物车中产品的ID
				Optional<Integer> uid = Optional.ofNullable(param.getInteger("uid"));// 用户ID
				if (instanceid.isPresent() && uid.isPresent()) {
					reply = cartServiceImpl.decrCart(sn, param);
				} else {
					reply = respWriter.toError(sn, RespCode.CODE_505);
				}

			} catch (Exception e) {
				LOG.error("购物车数量减少异常：" + e);
				reply = respWriter.toError(sn, "操作失败，请重新提交.");
			}
			LOG.info("cart_decr_reply:" + reply);
			message.reply(reply);
		});
		vertx.eventBus().consumer(ConsumerConstant.CART_DEL, message -> {
			LOG.info("cart_del_received:" + message.body());
			JsonObject param = (JsonObject) message.body();
			String sn = param.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> instanceid = Optional.ofNullable(param.getInteger("instanceid"));// 购物车中产品的ID
				Optional<Integer> uid = Optional.ofNullable(param.getInteger("uid"));// 用户ID
				if (instanceid.isPresent() && uid.isPresent()) {
					reply = cartServiceImpl.delCart(sn, param);
				} else {
					reply = respWriter.toError(sn, RespCode.CODE_505);
				}
			} catch (Exception e) {
				LOG.error("删除购物车异常：" + e);
				reply = respWriter.toError(sn, "操作失败，请重新提交.");
			}
			LOG.info("cart_del_reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.CART_ACCOUNT, message -> {
			LOG.info("cart_account_received:" + message.body());
			JsonObject param = (JsonObject) message.body();
			String sn = param.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				JsonObject buy = new JsonObject();
				Optional<Integer> uid = Optional.ofNullable(param.getInteger("uid"));// 用户ID
				if (uid.isPresent()) {
					cartServiceImpl.account(buy, param);
					if (buy.containsKey("product")) {
						vertx.eventBus().send(ConsumerConstant.EXPRESS_LIST, param, ctx -> {
							if (ctx.succeeded()) {
								JsonObject o = new JsonObject(ctx.result().body().toString());
								buy.put("address",
										o.containsKey("respData") ? o.getJsonArray("respData") : new JsonArray());
							} else {
								LOG.info("调用地址服务出现异常:" + ctx.cause());
							}
							String r = respWriter.toSuccess(buy, sn);
							LOG.info("cart_account_reply:" + new JsonObject(r).encode());
							message.reply(r);
						});
					} else {
						reply = respWriter.toError(sn, "请选择购物车中的商品.");
						LOG.info("cart_account_reply:" + reply);
						message.reply(reply);
					}
				} else {
					reply = respWriter.toError(sn, "请求参数不符合规范，请重新提交.");
					LOG.info("cart_account_reply:" + reply);
					message.reply(reply);
				}
			} catch (Exception e) {
				LOG.error("结算异常：" + e);
				reply = respWriter.toError(sn, "结算失败，请重新提交.");
				message.reply(reply);
			}
		});

		vertx.eventBus().consumer(ConsumerConstant.CART_CHECK, message -> {
			LOG.info("cart_account_received:" + message.body());
			JsonObject param = (JsonObject) message.body();
			String sn = param.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> instanceid = Optional.ofNullable(param.getInteger("instanceid"));// 购物车中产品的ID
				Optional<Integer> uid = Optional.ofNullable(param.getInteger("uid"));// 用户ID
				if (instanceid.isPresent() && uid.isPresent()) {
					reply = cartServiceImpl.checked(sn, param);
				} else {
					reply = respWriter.toError(sn, RespCode.CODE_505);
				}
			} catch (Exception e) {
				LOG.error("选中购物车产品异常：" + e);
				reply = respWriter.toError(sn, "操作失败，请重新提交.");
			}
			LOG.info("cart_account_reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.CART_LIST, message -> {
			LOG.info("cart_list_received:" + message.body());
			JsonObject param = (JsonObject) message.body();
			String sn = param.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> uid = Optional.ofNullable(param.getInteger("uid"));// 用户ID
				if (uid.isPresent()) {
					reply = cartServiceImpl.listCart(sn, param);
				} else {
					reply = respWriter.toError(sn, RespCode.CODE_505);
				}

			} catch (Exception e) {
				LOG.error("购物车列表异常：" + e);
				reply = respWriter.toError(sn, "操作失败，请重新提交.");
			}
			LOG.info("cart_list_reply:" + new JsonObject(reply).encode());
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.CART_ADD, message -> {
			LOG.info("cart_add_received:" + message.body());
			JsonObject param = (JsonObject) message.body();
			String sn = param.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> uid = Optional.ofNullable(param.getInteger("uid"));// 用户ID
				Optional<String> code = Optional.ofNullable(param.getString("code"));// 产品编号
				if (uid.isPresent() && code.isPresent()) {
					// 调用产品服务，检索产品相关信息
					JsonObject product = new JsonObject();
					product.put("sn", sn);
					product.put("prodcode", code.get());
					product.put("prod", 1);
					vertx.eventBus().send(ConsumerConstant.ZM3C_PRODUCT_QUERY, product, replyHandler -> {
						if (replyHandler.succeeded()) {
							JsonObject data = new JsonObject(replyHandler.result().body().toString());
							LOG.info(data);
							if (data.getInteger("code") == 200) {
								JsonArray prod = data.getJsonArray("respData");
								param.put("price", prod.getJsonObject(0).getFloat("prodprice"));
								param.put("name", prod.getJsonObject(0).getString("prodname"));
								param.put("id", prod.getJsonObject(0).getInteger("id"));
								message.reply(cartServiceImpl.addCart(sn, param));
							} else {
								LOG.info("未检索到产品信息，加入购物车失败.");
								message.reply(respWriter.toError(sn));
							}
						} else {
							LOG.info("产品服务异常，加入购物车失败.");
							message.reply(respWriter.toError(sn));
						}
					});
				} else {
					reply = respWriter.toError(sn, RespCode.CODE_505);
					message.reply(reply);
				}
			} catch (Exception e) {
				LOG.error("加入购物车异常：" + e);
				reply = respWriter.toError(sn, "操作失败，请重新提交.");
				message.reply(reply);
			}
		});
	}

}
