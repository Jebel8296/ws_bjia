package com.chinamcom.framework.task;

import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinamcom.framework.common.util.ConfigUtil;
import com.chinamcom.framework.task.scheduler.AfterSaleToTbCodeRelationScheduler;
import com.chinamcom.framework.task.scheduler.OrderToTbCodeRelationScheduler;
import com.chinamcom.framework.task.scheduler.ReturnTradeSyncFromGyScheduler;
import com.chinamcom.framework.task.scheduler.ReturnTradeSyncToGyScheduler;
import com.chinamcom.framework.task.scheduler.TradeAutoCancelScheduler;
import com.chinamcom.framework.task.scheduler.TradeSyncFromGyScheduler;
import com.chinamcom.framework.task.scheduler.TradeSyncToGyScheduler;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

/**
 * 启动类
 * 
 * @author xuxg
 */
public class Application {
	private static Properties properties = ConfigUtil.getDefaultConfig();
	private static ClassPathXmlApplicationContext applicationContext = null;

	public static void main(String[] args) throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		TradeAutoCancelScheduler orderAutoCancel = applicationContext.getBean(TradeAutoCancelScheduler.class);
		TradeSyncFromGyScheduler orderSyncFromGy = applicationContext.getBean(TradeSyncFromGyScheduler.class);
		TradeSyncToGyScheduler orderSyncToGy = applicationContext.getBean(TradeSyncToGyScheduler.class);
		ReturnTradeSyncToGyScheduler returnTo = applicationContext.getBean(ReturnTradeSyncToGyScheduler.class);
		ReturnTradeSyncFromGyScheduler returnFrom = applicationContext.getBean(ReturnTradeSyncFromGyScheduler.class);
		AfterSaleToTbCodeRelationScheduler afterSale = applicationContext.getBean(AfterSaleToTbCodeRelationScheduler.class);
		OrderToTbCodeRelationScheduler order = applicationContext.getBean(OrderToTbCodeRelationScheduler.class);
		ClusterManager mgr = new ZookeeperClusterManager();
		VertxOptions options = new VertxOptions().setClusterManager(mgr);
		options.setClusterHost(properties.getProperty("cluster.host", "localhost"));
		options.setClusterPublicHost(properties.getProperty("cluster.public.host", "localhost"));
		DeploymentOptions option = new DeploymentOptions();
		option.setWorker(true);
		Vertx.clusteredVertx(options, resultHandler -> {
			if (resultHandler.succeeded()) {
				Vertx vertx = resultHandler.result();
				vertx.deployVerticle(orderAutoCancel, option);
				//vertx.deployVerticle(orderSyncFromGy, option);
				//vertx.deployVerticle(orderSyncToGy, option);
				//vertx.deployVerticle(afterSale, option);
				//vertx.deployVerticle(order, option);
				//vertx.deployVerticle(returnTo, option);
				//vertx.deployVerticle(returnFrom, option);
			}
		});
	}
}
