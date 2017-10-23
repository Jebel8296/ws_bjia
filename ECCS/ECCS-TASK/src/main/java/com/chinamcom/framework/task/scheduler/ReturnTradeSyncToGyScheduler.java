package com.chinamcom.framework.task.scheduler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rx.java.RxHelper;
import rx.Scheduler;
import rx.functions.Action0;

/**
 * 将退换货订单信息同步到管易
 * 
 * @author xuxg
 * @since 20160805
 *
 */
@Component
public class ReturnTradeSyncToGyScheduler extends ZMAbstractorScheduler {

	private static Logger LOG = Logger.getLogger(TradeSyncToGyScheduler.class);
	private static String DEFAULT_URI = "http://localhost:20011/gy/return/add";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void start() throws Exception {
		// 任务延迟时间
		int initialDelay = Integer.valueOf(properties.getProperty("afterSaleToGy.scheduler.delay", "30"));
		// 任务间隔时间
		int period = Integer.valueOf(properties.getProperty("afterSaleToGy.scheduler.period", "600"));
		// 任务处理的数据条数
		int rows = Integer.valueOf(properties.getProperty("afterSaleToGy.scheduler.rows", "10"));
		Scheduler scheduler = RxHelper.scheduler(vertx);
		scheduler.createWorker().schedulePeriodically(new Action0() {
			@Override
			public void call() {
				// 先查询未同步的订单数据
				LOG.info("开始处理未同步到管易的退换货信息......");
				StringBuilder builder = new StringBuilder();
				builder.append("SELECT id,platform_code,guanyi_code,code_type,sync_flag,sync_date ");
				builder.append("FROM tb_code_relation ");
				builder.append("where code_type=2 and sync_flag=0");
				builder.append("ORDER BY id DESC LIMIT 1 , " + rows);
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
								String uri = properties.getProperty("guanyiapi.trade.add.api", DEFAULT_URI);
								JsonObject param = new JsonObject();
								param.put("shop_code", properties.getProperty("guanyi.shop_code", "111111"));// 店铺code
								param.put("warehouse_code", properties.getProperty("guanyi.warehouse_code", "222222"));// 仓库code
								param.put("vip_code", "000000");// 会员code
								param.put("platform_code", order.get("platform_code"));// 平台单号
								param.put("express_code", order.get("express_code"));// 物流公司code
								param.put("receiver_name", order.get("receiver_name"));// 收货人
								param.put("receiver_address", order.get("receiver_address"));// 收货地址
								param.put("receiver_zip", order.get("receiver_zip"));// 收货邮编
								param.put("receiver_mobile", order.get("receiver_mobile"));// 收货人手机
								param.put("receiver_province", order.get("receiver_province"));// 收货人省份
								param.put("receiver_city", order.get("receiver_city"));// 收货人城市
								param.put("receiver_district", order.get("receiver_district"));// 收货人区域
								param.put("deal_datetime", order.get("deal_datetime"));// 拍单时间
								param.put("pay_datetime", order.get("pay_datetime"));// 付款时间
								param.put("post_fee", order.get("post_fee"));// 物流费用
								// 支付信息
								JsonArray payments = new JsonArray();
								JsonObject payment = new JsonObject();
								payment.put("payment", order.get("payment"));
								payment.put("paytime", order.get("paytime"));
								payment.put("pay_type_code", order.get("pay_type_code"));
								payment.put("pay_code", order.get("pay_code"));
								payments.add(payment);
								param.put("payments", payments);
								// 发票信息
								JsonArray invoices = new JsonArray();
								JsonObject invoice = new JsonObject();
								invoice.put("invoice_type", 1);// 待讨论?
								invoice.put("invoice_title", "");// 待讨论?
								invoice.put("invoice_content", "");// 待讨论?
								invoice.put("invoice_amount", "");// 待讨论?
								invoice.put("bill_amount", "");// 待讨论?
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
								if (checkResult.getBoolean("success")) {
									String trade_code = checkResult.getString("code");// 管易系统中生成订单编号
									Integer trade_id = checkResult.getInteger("id");// 管易系统中生成订单ID
									// 更新到本平台的数据中
									StringBuilder updateSql = new StringBuilder();
									updateSql.append("update tb_code_relation ");
									updateSql.append("set guanyi_code=?,sync_flag=1,sync_date=NOW(),ext01=? ");
									updateSql.append("where platform_code=?");
									jdbcTemplate.update(updateSql.toString(),
											new Object[] { trade_code, platform_code, trade_id });
									LOG.info("售后编号：[" + platform_code + "],同步成功，继续下一个数据处理.");
								} else {
									LOG.info("同步失败，继续下一个数据处理." + ret);
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
