package com.chinamcom.framework.order.verticle;

import java.math.BigDecimal;
import java.util.Optional;

import com.chinamcom.framework.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.order.services.ITbOrderService;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 订单服务
 *
 * @author xuxg
 * @since 20160901
 */
@Component
public class OrderVerticle extends BaseVerticle {

    @Autowired
    private ITbOrderService tbOrderService;

    @Override
    public void start() throws Exception {

        vertx.eventBus().consumer(ConsumerConstant.ZM3C_ORDER_ADD, message -> {
            LOG.info("received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));// 用户ID
                Optional<Integer> expressid = Optional.ofNullable(reqData.getInteger("expressid"));// 收货地址
                String logistics = Optional.ofNullable(reqData.getString("logistics")).orElse("EMS");//物流公司代码
                BigDecimal logisticsPay = StringUtil.getExpressPay(logistics);
                if (logisticsPay == null) {
                    LOG.info("物流编号有误，不能正常提交订单.");
                    message.reply(respWriter.toError(sn));
                    return;
                }
                if (uid.isPresent() && expressid.isPresent()) {
                    if (reqData.containsKey("product")) {// 直接下订单
                        // 调用产品服务，检索产品相关信息
                        JsonObject product = reqData.getJsonObject("product");
                        product.put("sn", sn);
                        product.put("prodcode", product.getString("code"));
                        product.put("prod", 1);
                        vertx.eventBus().send(ConsumerConstant.ZM3C_PRODUCT_QUERY, product, replyHandler -> {
                            if (replyHandler.succeeded()) {
                                JsonObject data = new JsonObject(replyHandler.result().body().toString());
                                LOG.info(data);
                                Integer code = data.getInteger("code");
                                if (code == 200) {
                                    JsonArray prod = data.getJsonArray("respData");
                                    reqData.getJsonObject("product").put("price",
                                            prod.getJsonObject(0).getFloat("prodprice"));
                                    reqData.getJsonObject("product").put("name",
                                            prod.getJsonObject(0).getString("prodname"));
                                    reqData.getJsonObject("product").put("id", prod.getJsonObject(0).getInteger("id"));
                                    message.reply(tbOrderService.submitTbOrderInfo(sn, reqData));
                                } else {
                                    LOG.info("未检索到产品信息，不能正常提交订单.");
                                    message.reply(respWriter.toError(sn));
                                }
                            } else {
                                LOG.info("产品服务异常，未检索到产品信息，不能正常提交订单.");
                                message.reply(respWriter.toError(sn));
                            }
                        });
                    } else {// 从购物车下订单
                        reply = tbOrderService.submitTbOrderInfoByCart(sn, reqData);
                        LOG.info("reply:" + reply);
                        message.reply(reply);
                    }
                } else {
                    reply = respWriter.toError(sn, "请核查请求参数是否符合规范.");
                    LOG.info("reply:" + reply);
                    message.reply(reply);
                }
            } catch (Exception e) {
                LOG.error("提交订单异常：" + e);
                reply = respWriter.toError(sn, "提交订单失败，请重新提交.");
                message.reply(reply);
            }
        });
        vertx.eventBus().consumer(ConsumerConstant.ZM3C_ORDER_LIST, message -> {
            LOG.info("received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));// 用户ID
                if (uid.isPresent()) {
                    reply = tbOrderService.selectTbOrderList(sn, reqData);
                } else {
                    reply = respWriter.toError(sn, "请核查请求参数是否符合规范.");
                }
            } catch (Exception e) {
                LOG.error("检索订单异常：" + e);
                reply = respWriter.toError(sn, "检索订单失败，请重新提交.");
            }
            LOG.info("reply:" + new JsonObject(reply).encode());
            message.reply(reply);
        });

        vertx.eventBus().consumer(ConsumerConstant.ZM3C_ORDER_INFO, message -> {
            LOG.info("received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                JsonObject result = new JsonObject();
                Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));// 用户ID
                Optional<Integer> orderid = Optional.ofNullable(reqData.getInteger("orderid"));// 订单ID
                Optional<String> ordercode = Optional.ofNullable(reqData.getString("ordercode"));// 订单编号
                if (uid.isPresent() && (orderid.isPresent() || ordercode.isPresent())) {
                    tbOrderService.selectTbOrderInfo(reqData, result);
                    if (result.containsKey("express")) {
                        vertx.eventBus().send(ConsumerConstant.EXPRESS_LIST, new JsonObject()
                                        .put("express", result.getInteger("express")).put("sn", sn).put("uid", uid.get()),
                                replyHandler -> {
                                    if (replyHandler.succeeded()) {
                                        JsonObject data = new JsonObject(replyHandler.result().body().toString());
                                        LOG.info("express repley :" + data);
                                        if (data.getInteger("code") == 200) {
                                            JsonArray respData = data.getJsonArray("respData");
                                            JsonObject ex = respData.getJsonObject(0);
                                            result.remove("express");
                                            ex.remove("userid");
                                            ex.remove("provid");
                                            ex.remove("cityid");
                                            ex.remove("areaid");
                                            ex.remove("createtime");
                                            ex.remove("updatetime");
                                            ex.remove("status");
                                            result.put("express", ex);
                                            message.reply(respWriter.toSuccess(result, sn));
                                        } else {
                                            LOG.info("没有取得地址信息，直接返回订单信息.");
                                            message.reply(respWriter.toSuccess(result, sn));
                                        }
                                    } else {
                                        LOG.info("调用地址服务异常，直接返回订单信息.");
                                        message.reply(respWriter.toSuccess(result, sn));
                                    }
                                });
                    } else {
                        reply = respWriter.toSuccess(result, sn);
                        LOG.info("reply:" + reply);
                        message.reply(reply);
                    }
                } else {
                    reply = respWriter.toError(sn, RespCode.CODE_505);
                    message.reply(reply);
                }
            } catch (Exception e) {
                LOG.error("检索订单明细异常:" + e);
                reply = respWriter.toError(sn, "检索订单明细失败.");
                message.reply(reply);
            }
        });

        vertx.eventBus().consumer(ConsumerConstant.ZM3C_ORDER_UPDATE, message -> {
            LOG.info("update_received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> orderid = Optional.ofNullable(reqData.getInteger("orderid"));// 订单编号
                Optional<String> ordercode = Optional.ofNullable(reqData.getString("ordercode"));// 订单编号
                Integer status = Optional.ofNullable(reqData.getInteger("status")).orElse(0);
                if ((ordercode.isPresent() || orderid.isPresent()) && status != 0) {
                    reply = tbOrderService.updateTbOrderInfo(sn, reqData);
                } else {
                    reply = respWriter.toError(sn, "请核查请求参数是否符合规范.");
                }
            } catch (Exception e) {
                LOG.error("更新订单异常：" + e);
                reply = respWriter.toError(sn, "更新订单失败，请重新提交.");
            }
            LOG.info("update_reply:" + reply);
            message.reply(reply);

        });
        vertx.eventBus().consumer(ConsumerConstant.ZM3C_ORDER_CANCEL, message -> {
            LOG.info("cancel_received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> orderid = Optional.ofNullable(reqData.getInteger("orderid"));// 订单编号
                Optional<String> ordercode = Optional.ofNullable(reqData.getString("ordercode"));// 订单编号
                if (ordercode.isPresent() || orderid.isPresent()) {
                    reqData.put("status", 5);
                    reply = tbOrderService.updateTbOrderInfo(sn, reqData);
                } else {
                    reply = respWriter.toError(sn, "请核查请求参数是否符合规范.");
                }
            } catch (Exception e) {
                LOG.error("更新订单异常：" + e);
                reply = respWriter.toError(sn, "更新订单失败，请重新提交.");
            }
            LOG.info("reply:" + reply);
            message.reply(reply);

        });

        vertx.eventBus().consumer(ConsumerConstant.ZM3C_ORDER_CLOSE, message -> {
            LOG.info("close_received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> orderid = Optional.ofNullable(reqData.getInteger("orderid"));// 订单编号
                Optional<String> ordercode = Optional.ofNullable(reqData.getString("ordercode"));// 订单编号
                if (ordercode.isPresent() || orderid.isPresent()) {
                    reqData.put("status", 7);
                    reply = tbOrderService.updateTbOrderInfo(sn, reqData);
                } else {
                    reply = respWriter.toError(sn, "请核查请求参数是否符合规范.");
                }
            } catch (Exception e) {
                LOG.error("更新订单异常：" + e);
                reply = respWriter.toError(sn, "更新订单失败，请重新提交.");
            }
            LOG.info("reply:" + reply);
            message.reply(reply);

        });
        vertx.eventBus().consumer(ConsumerConstant.ZM3C_ORDER_RECEIPT, message -> {
            LOG.info("receipt_received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> orderid = Optional.ofNullable(reqData.getInteger("orderid"));// 订单编号
                Optional<String> ordercode = Optional.ofNullable(reqData.getString("ordercode"));// 订单编号
                if (ordercode.isPresent() || orderid.isPresent()) {
                    reqData.put("status", 4);
                    reply = tbOrderService.updateTbOrderInfo(sn, reqData);
                } else {
                    reply = respWriter.toError(sn, "请核查请求参数是否符合规范.");
                }
            } catch (Exception e) {
                LOG.error("更新订单异常：" + e);
                reply = respWriter.toError(sn, "更新订单失败，请重新提交.");
            }
            LOG.info("reply:" + reply);
            message.reply(reply);

        });
        vertx.eventBus().consumer(ConsumerConstant.ZM3C_ORDER_REFUSE, message -> {
            LOG.info("refuse_received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> orderid = Optional.ofNullable(reqData.getInteger("orderid"));// 订单编号
                Optional<String> ordercode = Optional.ofNullable(reqData.getString("ordercode"));// 订单编号
                if (ordercode.isPresent() || orderid.isPresent()) {
                    reqData.put("status", 6);
                    reply = tbOrderService.updateTbOrderInfo(sn, reqData);
                } else {
                    reply = respWriter.toError(sn, "请核查请求参数是否符合规范.");
                }
            } catch (Exception e) {
                LOG.error("更新订单异常：" + e);
                reply = respWriter.toError(sn, "更新订单失败，请重新提交.");
            }
            LOG.info("reply:" + reply);
            message.reply(reply);

        });
    }
}
