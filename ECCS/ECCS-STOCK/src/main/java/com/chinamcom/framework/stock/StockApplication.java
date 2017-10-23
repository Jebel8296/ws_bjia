package com.chinamcom.framework.stock;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinamcom.framework.common.ebspi.ProductService;
import com.chinamcom.framework.common.util.ConfigUtil;
import com.chinamcom.framework.stock.api.RestStockAPIVerticle;
import com.chinamcom.framework.stock.verticle.StockVerticle;

import as.leap.vertx.rpc.impl.RPCServerOptions;
import as.leap.vertx.rpc.impl.VertxRPCServer;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

/**
 * Created by Administrator on 2016/5/13.
 */
public class StockApplication {
	private static Logger LOG = Logger.getLogger(StockApplication.class);
	private static Properties config = ConfigUtil.getDefaultConfig();

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		ClusterManager mgr = new ZookeeperClusterManager();
		VertxOptions options = new VertxOptions().setClusterManager(mgr)
				.setMetricsOptions(new DropwizardMetricsOptions().setEnabled(true));
		options.setClusterHost(config.getProperty("cluster.host", "localhost"));
		options.setClusterPublicHost(config.getProperty("cluster.public.host", "localhost"));

		Vertx.clusteredVertx(options, res -> {
			if (res.succeeded()) {
				ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
						"classpath:applicationContext.xml");
				StockVerticle stock = applicationContext.getBean(StockVerticle.class);
				RestStockAPIVerticle api = applicationContext.getBean(RestStockAPIVerticle.class);
				Vertx vertx = res.result();

				RPCServerOptions serverOption = new RPCServerOptions(vertx)
						.setBusAddress(ProductService.SERVICE_ADDRESS)
						.addService(applicationContext.getBean(ProductService.class));
				new VertxRPCServer(serverOption);

				DeploymentOptions option = new DeploymentOptions();
				option.setWorker(true);

				vertx.deployVerticle(stock, option);
				vertx.deployVerticle(api);
				LOG.info("server start success.");
			} else {
				LOG.error("server start failed.");
			}
		});
	}
}
