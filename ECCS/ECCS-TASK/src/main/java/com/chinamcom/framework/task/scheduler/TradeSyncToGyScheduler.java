package com.chinamcom.framework.task.scheduler;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rx.java.RxHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rx.Scheduler;
import rx.functions.Action0;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 订单信息同步到管易
 *
 * @author xuxg
 * @since 20160805
 */
@Component
public class TradeSyncToGyScheduler extends ZMAbstractorScheduler {

    private static Logger LOG = Logger.getLogger(TradeSyncToGyScheduler.class);
    private static String DEFAULT_URI = "http://localhost:20011/gy/trade/add";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void start() throws Exception {
        // 任务延迟时间
        int initialDelay = Integer.valueOf(properties.getProperty("tradeSyncToGy.scheduler.delay", "15"));
        // 任务间隔时间
        int period = Integer.valueOf(properties.getProperty("tradeSyncToGy.scheduler.period", "600"));
        // 任务处理的数据条数
        int rows = Integer.valueOf(properties.getProperty("tradeSyncToGy.scheduler.rows", "10"));
        Scheduler scheduler = RxHelper.scheduler(vertx);
        scheduler.createWorker().schedulePeriodically(new Action0() {
            @Override
            public void call() {
                // 先查询未同步的订单数据
                LOG.info("开始处理未同步到管易的订单信息......");
                StringBuilder builder = new StringBuilder();
                builder.append("SELECT id,platform_code,guanyi_code,code_type,sync_flag,sync_date ");
                builder.append("FROM tb_code_relation ");
                builder.append("where code_type=2 and sync_flag=0 ");
                builder.append("ORDER BY id DESC LIMIT 0 , " + rows);
                List<Map<String, Object>> data = jdbcTemplate.queryForList(builder.toString());
                if (data != null && data.size() > 0) {
                    data.forEach(relation -> {
                        String platform_code = (String) relation.get("platform_code");// 订单编号
                        // 取相应的订单信息
                        StringBuilder orderSql = new StringBuilder();
                        orderSql.append("SELECT b.LOGISTICS_COMPANY_CDOE express_code,a.CREATE_TIME deal_datetime, ");
                        orderSql.append("b.NAME receiver_name,b.ADDRESS receiver_address,b.ZIPCODE receiver_zip, ");
                        orderSql.append("b.PHONE receiver_mobile,b.PROVINCE receiver_province,b.CITY receiver_city, ");
                        orderSql.append("b.AREA receiver_district,a.PAY_TIME pay_datetime,a.EXPRESS_PAY post_fee, ");
                        orderSql.append("a.SERIAL_NUMBER platform_code ,a.PAY_FEE payment,c.EXT02 pay_type_code, ");
                        orderSql.append("c.EXT01 pay_code,a.SERIAL_NUMBER platform_code FROM tb_order a ");
                        orderSql.append("INNER JOIN tb_order_logistics b ON b.ORDER_ID = a.ID ");
                        orderSql.append("INNER JOIN tb_order_payment c ON c.ORDER_ID = a.ID ");
                        orderSql.append("WHERE a.SERIAL_NUMBE=? and a.STATUS = 2 ");
                        orderSql.append("ORDER BY a.id DESC LIMIT 1 , " + rows);
                        List<Map<String, Object>> orderInfo = jdbcTemplate.queryForList(builder.toString());
                        if (orderInfo != null && orderInfo.size() > 0) {
                            orderInfo.forEach(order -> {
                                // 根据管易接口要求，拼装参数，并发送到管易
                                String d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                String uri = properties.getProperty("guanyiapi.trade.add.api", DEFAULT_URI);
                                JsonObject param = new JsonObject();
                                param.put("platform_code", order.get("platform_code"));// 平台单号
                                param.put("shop_code", properties.getProperty("guanyi.shop_code", "001"));// 店铺code
                                param.put("warehouse_code", properties.getProperty("guanyi.warehouse_code", "01"));// 仓库code
                                param.put("express_code", "SF");// 物流公司code
                                param.put("deal_datetime", d);// 拍单时间
                                param.put("receiver_mobile", "17090020984");// 收货人手机
                                param.put("receiver_phone", "17090020984");// 收货人手机

                                param.put("vip_code", "01");// 会员code
                                param.put("vip_name", "客户01");// 会员code
                                param.put("receiver_name", "王小二");// 收货人
                                param.put("receiver_address", "北京大兴区");// 收货地址
                                param.put("receiver_zip", "100020");// 收货邮编
                                param.put("receiver_province", "北京市");// 收货人省份
                                param.put("receiver_city", "大兴区");// 收货人城市
                                param.put("receiver_district", "西红门镇");// 收货人区域

                                param.put("pay_datetime", d);// 付款时间
                                param.put("post_fee", 12);// 物流费用
                                // 商品信息
                                JsonArray details = new JsonArray();
                                JsonObject product = new JsonObject();
                                product.put("item_code", "001.02.02");
                                product.put("price", 100);
                                product.put("qty", 1);
                                product.put("refund", 0);
                                details.add(product);
                                param.put("details", details);
                                // 支付信息
                                JsonArray payments = new JsonArray();
                                JsonObject payment = new JsonObject();
                                payment.put("payment", 100);
                                payment.put("paytime", "2016-05-31");
                                payment.put("pay_type_code", "alipay");
                                payment.put("pay_code", "0000011");
                                payment.put("account", "0000011");
                                payments.add(payment);
                                param.put("payments", payments);
                                // 发票信息
                                JsonArray invoices = new JsonArray();
                                JsonObject invoice = new JsonObject();
                                invoice.put("invoice_type", 1);// 发票类型1普通发票2增值发票
                                invoice.put("invoice_title", "a");// 发票抬头
                                invoice.put("invoice_content", "b");// 发票内容
                                invoice.put("invoice_amount", 1);// 发票金额
                                invoices.add(invoice);
                                param.put("invoices", invoices);

                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
                                HttpEntity<String> reqEntity = new HttpEntity<String>(param.toString(), headers);
                                String ret = null;
                                try {
                                    ret = restTemplate.postForObject(uri, reqEntity, String.class);
                                } catch (Exception e) {
                                    LOG.info("调用[guanyiapi]失败,本次任务不做处理直接完成，等待下次处理.");
                                    LOG.error(e.getMessage());
                                    return;
                                }
                                LOG.info("result of guanyiapi:" + ret);
                                JsonObject checkResult = new JsonObject(ret);
                                int code = checkResult.getInteger("code");
                                if (code == 200) {
                                    JsonObject result = checkResult.getJsonObject("result");
                                    String trade_code = result.getString("code");// 管易系统中生成订单编号
                                    Integer trade_id = result.getInteger("id");// 管易系统中生成订单ID
                                    // 更新到本平台的数据中
                                    StringBuilder updateSql = new StringBuilder();
                                    updateSql.append("update tb_code_relation ");
                                    updateSql.append("set guanyi_code=?,sync_flag=1,sync_date=NOW(),ext01=? ");
                                    updateSql.append("where platform_code=?");
                                    jdbcTemplate.update(updateSql.toString(),
                                            new Object[]{trade_code, trade_id, platform_code});
                                    LOG.info("订单号：[" + platform_code + "],同步成功，继续下一个数据处理.");
                                } else {
                                    LOG.info("同步失败，继续下一个数据处理.");
                                }
                            });
                        }
                    });
                    LOG.info("本次处理结束.");
                } else {
                    LOG.info("暂时没有可处理的订单信息,稍后继续处理.");
                }
            }
        }, initialDelay, period, TimeUnit.SECONDS);

        LOG.info(this.getClass().getName() + "is deployed successfully.");
    }

}
