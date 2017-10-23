package com.chinamcom.framework.task.scheduler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import io.vertx.rx.java.RxHelper;
import rx.Scheduler;
import rx.functions.Action0;

/**
 * 将平台售后数据写入到数据表tb_code_relation中
 * 
 * @author xuxg
 * @since 20170505
 *
 */
@Component
public class AfterSaleToTbCodeRelationScheduler extends ZMAbstractorScheduler {

	private static Logger LOG = Logger.getLogger(AfterSaleToTbCodeRelationScheduler.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void start() throws Exception {
		// 任务延迟时间
		int initialDelay = Integer.valueOf(properties.getProperty("afterSaleToRelation.scheduler.delay", "15"));
		// 任务间隔时间
		int period = Integer.valueOf(properties.getProperty("afterSaleToRelation.scheduler.period", "600"));
		// 任务处理的数据条数
		int rows = Integer.valueOf(properties.getProperty("afterSaleToRelation.scheduler.rows", "10"));
		Scheduler scheduler = RxHelper.scheduler(vertx);
		scheduler.createWorker().schedulePeriodically(new Action0() {
			@Override
			public void call() {
				LOG.info("开始检索平台的售后信息......");
				StringBuilder orderSql = new StringBuilder();
				orderSql.append("SELECT a.aftercode platform_code ");
				orderSql.append("FROM tb_aftersale a INNER JOIN tb_aftersale_logistics b ");
				orderSql.append("ON b.after_code = a.aftercode ");
				orderSql.append("LEFT JOIN tb_code_relation d ON d.platform_code = a.aftercode ");
				orderSql.append("WHERE a.STATUS = 1 AND d.platform_code IS NULL");
				orderSql.append("ORDER BY a.id DESC LIMIT 1 , " + rows);
				List<Map<String, Object>> data = jdbcTemplate.queryForList(orderSql.toString());
				if (data != null && data.size() > 0) {
					data.forEach(item -> {
						StringBuilder insertSQL = new StringBuilder();
						insertSQL.append("insert into tb_code_relation");
						insertSQL.append("(platform_code,code_type,sync_flag) values(?,3,0)");
						jdbcTemplate.update(insertSQL.toString(), new Object[] { item.get("platform_code") });
						LOG.info("aftercode:" + item.get("platform_code") + ",已写入到表【tb_code_relation】中.");
					});
					LOG.info("本次处理结束.");
				} else {
					LOG.info("没检索到要写入到表【tb_code_relation】的售后,稍后继续处理.");
				}
			}
		}, initialDelay, period, TimeUnit.SECONDS);

		LOG.info(this.getClass().getName() + "is deployed successfully.");
	}

}
