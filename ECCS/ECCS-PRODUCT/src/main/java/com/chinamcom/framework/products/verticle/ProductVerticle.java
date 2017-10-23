package com.chinamcom.framework.products.verticle;

import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.products.services.ITbProductInfoService;
import com.chinamcom.framework.products.services.ITbProductStockService;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 产品服务
 *
 * @author xuxg
 * @since 20160718
 */
@Component
public class ProductVerticle extends BaseVerticle {

    @Autowired
    private ITbProductInfoService tbProductInfoService;
    @Autowired
    private ITbProductStockService iTbProductStockService;

    @Override
    public void start() throws Exception {
        /**
         vertx.eventBus().consumer(ConsumerConstant.ZM3C_PRODUCT_QUERY, message -> {
         LOG.info("received:" + message.body());
         JsonObject reqData = (JsonObject) message.body();
         String sn = reqData.getString("sn");
         String reply = respWriter.toError(sn);
         try {
         Optional<String> code = Optional.ofNullable(reqData.getString("code"));
         Optional<Integer> category = Optional.ofNullable(reqData.getInteger("category"));
         if (code.isPresent()) {
         reply = productServiceImpl.searchProductByCode(sn, code.get());
         } else if (category.isPresent()) {
         reply = productServiceImpl.searchProductByCategory(sn, category.get());
         } else {
         reply = productServiceImpl.searchProductByCategory(sn, null);
         }
         } catch (Exception e) {
         reply = respWriter.toError(sn, "product query faild.");
         }
         LOG.info("reply:" + reply);
         message.reply(reply);
         });
         */
        vertx.eventBus().consumer(ConsumerConstant.ZM3C_PRODUCT_QUERY, message -> {
            JsonObject reqData = (JsonObject) message.body();
            LOG.info("received:" + reqData.toString());
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                if (reqData.containsKey("prod")) {
                    reply = tbProductInfoService.selectTbProductInfoList(sn, reqData);
                } else {
                    reply = tbProductInfoService.selectTbProductTypeList(sn, reqData);
                }
            } catch (Exception e) {
                LOG.error("检索产品信息异常：" + e);
                reply = respWriter.toError(sn, "检索产品信息失败，请重新提交.");

            }
            LOG.info("reply:" + reply.toString());
            message.reply(reply);
        });

        vertx.eventBus().consumer(ConsumerConstant.ZM3C_DEVICES_QUERY, message -> {
            JsonObject reqData = (JsonObject) message.body();
            LOG.info(ConsumerConstant.ZM3C_DEVICES_QUERY + ".received:" + reqData.toString());
            String sn = reqData.getString("sn");
            String reply = respWriter.toError(sn);
            try {
                reply = tbProductInfoService.selectTbProductDevicesList(sn, reqData);
            } catch (Exception e) {
                LOG.error("检索设备信息异常：" + e);
                reply = respWriter.toError(sn, "检索设备信息失败，请重新提交.");
            }
            LOG.info(ConsumerConstant.ZM3C_DEVICES_QUERY + ".reply:" + reply.toString());
            message.reply(reply);
        });
    }

}
