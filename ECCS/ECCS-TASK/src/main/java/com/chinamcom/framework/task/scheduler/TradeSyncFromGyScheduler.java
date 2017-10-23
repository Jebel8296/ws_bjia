package com.chinamcom.framework.task.scheduler;

import com.sun.corba.se.spi.ior.ObjectKey;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 订单信息从管易同步到平台
 * 
 * @author xuxg
 * @since 20160805
 *
 */
@Component
public class TradeSyncFromGyScheduler extends ZMAbstractorScheduler {

	private static Logger LOG = Logger.getLogger(TradeSyncFromGyScheduler.class);
	private static String DEFAULT_URI = "http://localhost:2001/gy/trade";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void start() throws Exception {
		// 任务延迟时间
		int initialDelay = Integer.valueOf(properties.getProperty("tradeSyncFromGy.scheduler.delay", "20"));
		// 任务间隔时间
		int period = Integer.valueOf(properties.getProperty("tradeSyncFromGy.scheduler.period", "600"));
		// 任务处理的数据条数
		int rows = Integer.valueOf(properties.getProperty("tradeSyncFromGy.scheduler.rows", "10"));
		Scheduler scheduler = RxHelper.scheduler(vertx);
		scheduler.createWorker().schedulePeriodically(new Action0() {
			@Override
			public void call() {
				LOG.info("开始处理未同步到平台的订单信息[管易已发货状态同步]......");
				StringBuilder querySql = new StringBuilder();
				querySql.append("SELECT a.SERIAL_NUMBER platform_code ");
				querySql.append("FROM tb_order a ");
				querySql.append("WHERE a.STATUS = 1 AND a.SEND_TIME IS NULL ");
				querySql.append("ORDER BY a.id DESC LIMIT 0 , " + rows);
				List<Map<String, Object>> data = jdbcTemplate.queryForList(querySql.toString());
				if (data != null && data.size() > 0) {
					data.forEach(item -> {
						String platform_code = (String) item.get("platform_code");
						// 根据管易接口要求，拼装参数，从管易取订单信息
						JsonObject param = new JsonObject();
						Map<String,Object> p = new HashMap<String,Object>();
						p.put("shop_code", properties.getProperty("guanyi.shop_code", "001"));// 店铺code
						p.put("warehouse_code", properties.getProperty("guanyi.warehouse_code", "01"));// 仓库code
						p.put("platform_code", "123654789987654");// 平台单号
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
						HttpEntity<String> reqEntity = new HttpEntity<String>(param.toString(), headers);
						String ret = null;
						try {
							ret = restTemplate.getForObject(DEFAULT_URI, String.class, p);
						} catch (Exception e) {
							LOG.info("调用[guanyiapi]失败,本次任务不做处理直接结束，等待下次处理.");
							LOG.error(e.getMessage());
							return;
						}
						LOG.info("result of guanyiapi:" + ret);
						JsonObject checkResult = new JsonObject(ret);
						Boolean success = Optional.ofNullable(checkResult.getBoolean("success")).orElse(false);
						Integer total = Optional.ofNullable(checkResult.getInteger("total")).orElse(0);
						if (success && total > 0) {
							JsonObject order = checkResult.getJsonArray("orders").getJsonObject(0);// 管易系统中订单信息
							JsonArray deliverys = order.getJsonArray("deliverys");// 订单中的物流信息
							String delivery_code = deliverys.getJsonObject(0).getString("code");// 物流单号
							// 更新到本平台的订单表中
							StringBuilder updateSql1 = new StringBuilder();
							updateSql1.append("update tb_order ");
							updateSql1.append("set STATUS=3,SEND_TIME=NOW() ");
							updateSql1.append("where SERIAL_NUMBER=?");
							jdbcTemplate.update(updateSql1.toString(), new Object[] { platform_code });
							StringBuilder updateSql2 = new StringBuilder();
							updateSql2.append("update tb_order_logistics ");
							updateSql2.append("set LOGISTICS_SERIAL=?,UPDATE_TIME=NOW() ");
							updateSql2.append("where ORDER_CODE=?");
							jdbcTemplate.update(updateSql2.toString(), new Object[] { delivery_code, platform_code });
							LOG.info("订单号：[" + item.get("platform_code") + "]同步成功，继续下一个数据处理.");
						} else {
							LOG.info("未从管易平台取得订单编号为【" + item.get("platform_code") + "】数据，继续下一个数据处理." + ret);
						}
					});
					LOG.info("本次处理结束.");
				} else {
					LOG.info("平台没有需要从管易同步的订单的数据,稍后继续处理.");
				}
			}
		}, initialDelay, period, TimeUnit.SECONDS);

		LOG.info(this.getClass().getName() + "is deployed successfully.");
	}

}
