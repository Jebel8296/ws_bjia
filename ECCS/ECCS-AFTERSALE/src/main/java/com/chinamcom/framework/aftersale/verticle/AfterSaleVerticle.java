package com.chinamcom.framework.aftersale.verticle;

import java.util.Optional;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.aftersale.service.IAfterSaleService;
import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.verticle.BaseVerticle;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.rx.java.ObservableFuture;
import io.vertx.rx.java.RxHelper;
import rx.Observable;

/**
 * 售后服务
 *
 * @author xuxg
 * @since 20160926
 */
@Component
public class AfterSaleVerticle extends BaseVerticle {

    @Autowired
    private IAfterSaleService afterSaleService;

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer(ConsumerConstant.AFTERSALE_LIST, message -> {
            LOG.info("received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
                if (uid.isPresent()) {
                    reply = afterSaleService.selectTbAfterSale(sn, reqData);
                } else {
                    reply = respWriter.toError(sn, RespCode.CODE_505);
                }
            } catch (Exception e) {
                LOG.error(e);
                reply = respWriter.toError(sn, "检索售后服务信息异常,请重新提交.");
            }
            LOG.info("reply:" + reply);
            message.reply(reply);
        });

        vertx.eventBus().consumer(ConsumerConstant.AFTERSALE_UPLOAD, message -> {
            LOG.info("upload_received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
                Optional<Integer> afterid = Optional.ofNullable(reqData.getInteger("afterid"));
                Optional<String> aftercode = Optional.ofNullable(reqData.getString("aftercode"));
                if (uid.isPresent() && (afterid.isPresent() || aftercode.isPresent())) {
                    reply = afterSaleService.uploadLogistics(sn, reqData);
                } else {
                    reply = respWriter.toError(sn, RespCode.CODE_505);
                }
            } catch (Exception e) {
                LOG.error("上传售后物流信息出现异常：" + e);
                reply = respWriter.toError(sn, "上传售后物流信息失败.");
            }
            LOG.info("reply:" + reply);
            message.reply(reply);
        });

        vertx.eventBus().consumer(ConsumerConstant.AFTERSALE_CANCEL, message -> {
            LOG.info("upload_received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
                Optional<Integer> afterid = Optional.ofNullable(reqData.getInteger("afterid"));
                Optional<String> aftercode = Optional.ofNullable(reqData.getString("aftercode"));
                if (uid.isPresent() && (afterid.isPresent() || aftercode.isPresent())) {
                    reply = afterSaleService.cancelAfterSale(sn, reqData);
                } else {
                    reply = respWriter.toError(sn, RespCode.CODE_505);
                }
            } catch (Exception e) {
                LOG.error("上传售后物流信息出现异常：" + e);
                reply = respWriter.toError(sn, "上传售后物流信息失败.");
            }
            LOG.info("reply:" + reply);
            message.reply(reply);
        });

        vertx.eventBus().consumer(ConsumerConstant.AFTERSALE_INFO, message -> {
            LOG.info("received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
                Optional<Integer> afterid = Optional.ofNullable(reqData.getInteger("afterid"));
                Optional<String> aftercode = Optional.ofNullable(reqData.getString("aftercode"));
                JsonObject result = new JsonObject();
                if (uid.isPresent() && (afterid.isPresent() || aftercode.isPresent())) {
                    afterSaleService.selectTbAfterSaleInfo(sn, reqData, result);
                } else {
                    reply = respWriter.toError(sn, RespCode.CODE_505);
                }
                if (result.containsKey("aftertype")) {
                    if (result.getInteger("aftertype") != 1) {
                        JsonObject exressParam = new JsonObject();
                        exressParam.put("uid", uid.get());
                        exressParam.put("express", result.getJsonObject("logistics").getInteger("express"));
                        vertx.eventBus().send(ConsumerConstant.EXPRESS_LIST, exressParam, handlerData -> {
                            if (handlerData.succeeded()) {
                                JsonObject r = new JsonObject(handlerData.result().body().toString());
                                if (r.getInteger("code") == 200 && r.containsKey("respData") && r.getJsonArray("respData").size() > 0) {
                                    JsonObject ex = r.getJsonArray("respData").getJsonObject(0);
                                    JsonObject logistics = result.getJsonObject("logistics");
                                    logistics.put("name", ex.getString("name"));
                                    logistics.put("phone", ex.getString("phone"));
                                    logistics.put("zipcode", ex.getString("zipcode"));
                                    logistics.put("address", ex.getString("address"));
                                    logistics.put("provname", ex.getString("provname"));
                                    logistics.put("cityname", ex.getString("cityname"));
                                    logistics.put("areaname", ex.getString("areaname"));
                                    result.put("logistics", logistics);
                                }
                                message.reply(respWriter.toSuccess(result, sn));
                            } else {
                                LOG.error("调用收货地址服务出现异常：" + handlerData.cause().getMessage());
                                message.reply(respWriter.toError(sn, "系统异常."));
                            }
                        });
                    } else {
                        message.reply(respWriter.toSuccess(result, sn));
                    }
                } else {
                    reply = respWriter.toError(sn, RespCode.CODE_504);
                    message.reply(reply);
                }
            } catch (Exception e) {
                LOG.error("检索售后服务详情出现异常：" + e);
                reply = respWriter.toError(sn, "检索售后服务详情.");
                message.reply(reply);
            }
        });

        vertx.eventBus().consumer(ConsumerConstant.AFTERSALE_DEVICECHECK, message -> {
            LOG.info(ConsumerConstant.AFTERSALE_DEVICECHECK + ".received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            try {
                Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
                Optional<String> devicescode = Optional.ofNullable(reqData.getString("devicescode"));// 设备编号
                Optional<Long> date = Optional.ofNullable(reqData.getLong("date"));// 购买时期
                Optional<String> code = Optional.ofNullable(reqData.getString("code"));// 验证码
                Optional<String> smssn = Optional.ofNullable(reqData.getString("smssn"));
                if (!uid.isPresent() || !devicescode.isPresent() || !date.isPresent() || !code.isPresent()
                        || !smssn.isPresent()) {
                    message.reply(respWriter.toError(sn, RespCode.CODE_505));
                } else {
                    ObservableFuture<Message<Object>> authobservable = RxHelper.observableFuture();
                    ObservableFuture<Message<Object>> prodobservable = RxHelper.observableFuture();
                    vertx.eventBus().send(ConsumerConstant.ZM3C_DEVICES_QUERY, reqData, prodobservable.toHandler());// 通设备编号查询产品信息
                    vertx.eventBus().send(ConsumerConstant.VERIFY_CODE, reqData, authobservable.toHandler());// 校验验证码是否正确
                    Observable.zip(authobservable, prodobservable, (b1, b2) -> new Object[]{b1.body(), b2.body()})
                            .subscribe(o -> {
                                JsonObject checked = new JsonObject(o[0].toString());
                                JsonObject prods = new JsonObject(o[1].toString());
                                checked.put("checked", true);// 测试
                                LOG.info(checked);
                                if (!checked.getBoolean("checked")) {
                                    message.reply(respWriter.toError(sn, RespCode.CODE_507));
                                }
                                if (prods.getInteger("code") != 200) {
                                    message.reply(respWriter.toError(sn, RespCode.CODE_504));
                                } else {
                                    message.reply(respWriter.toSuccess(sn));
                                }
                            }, err -> {
                                message.reply(respWriter.toError(sn, "通过设备编号申请售后失败,请重新提交."));
                            });
                }
            } catch (Exception e) {
                LOG.error("通过设备编号申请售后异常：" + e);
                message.reply(respWriter.toError(sn, "通过设备编号申请售后失败,请重新提交."));
            }
        });

        vertx.eventBus().consumer(ConsumerConstant.AFTERSALE_DEVICE, message -> {
            LOG.info(ConsumerConstant.AFTERSALE_DEVICE + ".received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            try {
                Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
                Optional<String> devicescode = Optional.ofNullable(reqData.getString("devicescode"));// 设备编号
                Optional<Long> date = Optional.ofNullable(reqData.getLong("date"));// 购买时期
                if (!uid.isPresent() || !devicescode.isPresent() || !date.isPresent()) {
                    message.reply(respWriter.toError(sn, RespCode.CODE_505));
                } else {
                    ObservableFuture<Message<Object>> prodobservable = RxHelper.observableFuture();
                    ObservableFuture<Message<Object>> expressobservable = RxHelper.observableFuture();
                    vertx.eventBus().send(ConsumerConstant.ZM3C_DEVICES_QUERY, reqData, prodobservable.toHandler());// 通设备编号查询产品信息
                    vertx.eventBus().send(ConsumerConstant.EXPRESS_LIST, reqData, expressobservable.toHandler());// 通过用户ID取地址信息
                    Observable.zip(prodobservable, expressobservable, (b1, b2) -> new Object[]{b1.body(), b2.body()})
                            .subscribe(o -> {
                                JsonObject prods = new JsonObject(o[0].toString());
                                JsonObject exprs = new JsonObject(o[1].toString());
                                if (prods.getInteger("code") == 200) {
                                    prods.getJsonObject("respData").put("express", exprs.getJsonArray("respData"));
                                    message.reply(respWriter.toSuccess(prods, sn));
                                } else {
                                    message.reply(respWriter.toError(sn, RespCode.CODE_504));
                                }
                            }, err -> {
                                message.reply(respWriter.toError(sn, "通过设备编号申请售后失败,请重新提交."));
                            });
                }
            } catch (Exception e) {
                LOG.error("通过设备编号申请售后异常：" + e);
                message.reply(respWriter.toError(sn, "通过设备编号申请售后失败,请重新提交."));
            }
        });


        vertx.eventBus().consumer(ConsumerConstant.AFTERSALE_UPDATE, message -> {
            LOG.info("received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                if (reqData.containsKey("uid") && reqData.containsKey("aftercode")) {
                    reply = afterSaleService.updateSecond(sn, reqData);
                } else {
                    LOG.info("申请售后，参数不符合规范.");
                    reply = respWriter.toError(sn, RespCode.CODE_505);
                }
            } catch (Exception e) {
                LOG.error(e);
                reply = respWriter.toError(sn, "申请售后失败,请重新提交.");
            }
            message.reply(reply);
        });

        vertx.eventBus().consumer(ConsumerConstant.AFTERSALE_APPLEY, message -> {
            LOG.info("received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
                Optional<Integer> type = Optional.ofNullable(reqData.getInteger("type"));// 1退货2换货3维修
                Optional<String> ordercode = Optional.ofNullable(reqData.getString("ordercode"));// 订单编号
                Optional<String> prodcode = Optional.ofNullable(reqData.getString("prodcode"));// 产品编号
                Optional<Integer> express = Optional.ofNullable(reqData.getInteger("express"));// 收货ID
                Optional<String> reason = Optional.ofNullable(reqData.getString("reason"));// 售后原因
                Optional<String> remark = Optional.ofNullable(reqData.getString("remark"));// 售后原因
                Optional<String> devicecode = Optional.ofNullable(reqData.getString("devicescode"));// 设备编号
                Optional<Long> signtime = Optional.ofNullable(reqData.getLong("signtime"));// 签收日期
                if (!uid.isPresent() || !type.isPresent() || !remark.isPresent() || ((type.get() == 1 && !reason.isPresent()))) {
                    reply = respWriter.toError(sn, RespCode.CODE_505);
                    message.reply(reply);
                    return;
                }
                if (type.isPresent() && (type.get() != 1 && !express.isPresent())) {
                    LOG.info("非退货的售后申请，收货地址不能为空.");
                    reply = respWriter.toError(sn, RespCode.CODE_505);
                    message.reply(reply);
                    return;
                }
                if (devicecode.isPresent() && signtime.isPresent()) {// 通过设备编号提交售后
                    vertx.eventBus().send(ConsumerConstant.ZM3C_DEVICES_QUERY,
                            new JsonObject().put("devicescode", devicecode.get()), replyHandler -> {
                                if (replyHandler.succeeded()) {
                                    JsonObject data = new JsonObject(replyHandler.result().body().toString());
                                    LOG.info("order repley :" + data);
                                    if (data.getInteger("code") == 200) {
                                        JsonObject o = data.getJsonObject("respData");
                                        reqData.put("prods", o);
                                        message.reply(afterSaleService.submitTbAfterSaleByDevice(sn, reqData));
                                    } else {
                                        LOG.info("未取得相应的订单信息，申请售后失败.");
                                        message.reply(respWriter.toError(sn));
                                    }
                                } else {
                                    LOG.info("调用订单服务异常，返回申请售后失败.");
                                    message.reply(respWriter.toError(sn));
                                }
                            });

                } else if (ordercode.isPresent() && prodcode.isPresent()) {// 通过订单提交售后
                    JsonObject queryProduct = new JsonObject();
                    queryProduct.put("ordercode", ordercode.get()).put("prodcode", prodcode.get()).put("uid", uid.get());
                    vertx.eventBus().send(ConsumerConstant.ZM3C_ORDER_INFO, queryProduct,
                            replyHandler -> {
                                if (replyHandler.succeeded()) {
                                    JsonObject data = new JsonObject(replyHandler.result().body().toString());
                                    LOG.info("order repley :" + data);
                                    if (data.getInteger("code") == 200) {
                                        JsonObject o = data.getJsonObject("respData");
                                        reqData.put("prods", o.getJsonArray("prods"));
                                        reqData.put("orderid", o.getInteger("oid"));//订单ID
                                        reqData.put("ordercode", o.getString("code"));//订单编号
                                        reqData.put("express_pay", o.getFloat("express_pay"));//物流费用
                                        reqData.put("product_pay", o.getFloat("product_pay"));//订单费用
                                        if (express.isPresent()) {
                                            reqData.put("express", express.get());
                                        }
                                        message.reply(afterSaleService.submitTbAfterSale(sn, reqData));
                                    } else {
                                        LOG.info("未取得相应的订单信息，申请售后失败.");
                                        message.reply(respWriter.toError(sn));
                                    }
                                } else {
                                    LOG.info("调用订单服务异常，返回申请售后失败.");
                                    message.reply(respWriter.toError(sn));
                                }
                            });
                } else {
                    LOG.info("申请售后，参数不符合规范.");
                    reply = respWriter.toError(sn, RespCode.CODE_505);
                    message.reply(reply);
                }
            } catch (Exception e) {
                LOG.error(e);
                reply = respWriter.toError(sn, "申请售后失败,请重新提交.");
                message.reply(reply);
            }
        });
        vertx.eventBus().consumer(ConsumerConstant.AFTERSALE_ACCEPT, message -> {
            LOG.info("received:" + message.body());
            JsonObject reqData = (JsonObject) message.body();
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                Optional<Integer> afterid = Optional.ofNullable(reqData.getInteger("afterid"));// 售后服务ID
                Optional<String> aftercode = Optional.ofNullable(reqData.getString("aftercode"));
                Optional<Integer> type = Optional.ofNullable(reqData.getInteger("type"));// 值为1时客服受理售后服务；值为2时售后完成；值为3客服取消售后服务
                if ((!afterid.isPresent() && !aftercode.isPresent()) || !type.isPresent()) {
                    message.reply(respWriter.toError(sn, RespCode.CODE_505));
                } else {
                    switch (type.get()) {
                        case 1:
                            reply = afterSaleService.handleTbAfterSale(sn, reqData);
                            break;
                        case 2:
                            reply = afterSaleService.handleTbAfterSale(sn, reqData);
                            break;
                        case 3:
                            reply = afterSaleService.handleTbAfterSale(sn, reqData);
                            break;
                        default:
                            reply = respWriter.toError(sn, RespCode.CODE_505);
                            break;
                    }
                }
            } catch (Exception e) {
                LOG.error(e);
                reply = respWriter.toError(sn, "申请售后失败,请重新提交.");
            }
            LOG.info("reply:" + reply);
            message.reply(reply);
        });
    }

}
