package com.chinamcom.framework.task.scheduler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.task.common.SchedulerContant;

import io.vertx.rx.java.RxHelper;
import rx.Scheduler;
import rx.functions.Action0;

/**
 * 订单自动取消
 * 
 * @author xuxg
 * @since 20170504
 *
 */
@Component
public class TradeAutoCancelScheduler extends ZMAbstractorScheduler {

	private static Logger LOG = Logger.getLogger(TradeAutoCancelScheduler.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void start() throws Exception {
		// 任务延迟处理时间
		int initialDelay = Integer.valueOf(properties.getProperty("autocancelorder.scheduler.delay", "5"));
		// 任务间隔处理时间
		int period = Integer.valueOf(properties.getProperty("autocancelorder.scheduler.period", "600"));
		Scheduler scheduler = RxHelper.scheduler(vertx);
		scheduler.createWorker().schedulePeriodically(new Action0() {
			@Override
			public void call() {
				LOG.info("开始处理超过24小时未支付的订单......");
				// 每次取10条数据来处理
				List<Map<String, Object>> data = jdbcTemplate.queryForList(SchedulerContant.ORDER_GREATER_THAN_SQL_);
				if (!data.isEmpty()) {
					data.forEach(item -> {
						jdbcTemplate.update(SchedulerContant.ORDER_ONEUPDATE_SQL, new Object[] { item.get("id") });
						LOG.info("ordercode:" + item.get("SERIAL_NUMBER") + ",下单时间：" + item.get("CREATE_TIME")
								+ ",已超过24小时未支付，自动取消.");
					});
					LOG.info("本次处理结束.");
				} else {
					LOG.info("不存在超过24小时未支付的订单,稍后继续处理.");
				}
				// 批量处理
				// int count =
				// jdbcTemplate.update(SchedulerContant.ORDER_BATHUPDATE_SQL);
				// LOG.info("update order count:" + count);
			}
		}, initialDelay, period, TimeUnit.SECONDS);
	}

}
