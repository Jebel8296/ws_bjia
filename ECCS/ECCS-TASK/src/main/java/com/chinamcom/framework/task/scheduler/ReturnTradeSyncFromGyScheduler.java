package com.chinamcom.framework.task.scheduler;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.vertx.rx.java.RxHelper;
import rx.Scheduler;
import rx.functions.Action0;

/**
 * 退换货订单信息从管易同步到平台
 * 
 * @author xuxg
 * @since 20160805
 *
 */
@Component
public class ReturnTradeSyncFromGyScheduler extends ZMAbstractorScheduler {

	private static Logger LOG = Logger.getLogger(ReturnTradeSyncFromGyScheduler.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void start() throws Exception {
		// 任务延迟处理时间
		int initialDelay = Integer.valueOf(properties.getProperty("order.scheduler.delay", "5"));
		// 任务间隔处理时间
		int period = Integer.valueOf(properties.getProperty("order.scheduler.period", "600"));
		Scheduler scheduler = RxHelper.scheduler(vertx);
		scheduler.createWorker().schedulePeriodically(new Action0() {
			@Override
			public void call() {
				// 先查询未同步
				LOG.info("开始处理未同步到平台的退换货订单信息......");

				LOG.info("本次处理结束.");
			}
		}, initialDelay, period, TimeUnit.SECONDS);

		LOG.info(this.getClass().getName() + "is deployed successfully.");
	}

}
