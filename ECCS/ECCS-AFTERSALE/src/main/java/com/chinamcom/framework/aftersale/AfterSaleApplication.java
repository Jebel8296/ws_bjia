package com.chinamcom.framework.aftersale;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinamcom.framework.aftersale.verticle.AfterSaleVerticle;
import com.chinamcom.framework.common.util.ConfigUtil;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

/**
 * Created by Administrator on 2016/5/13.
 */
public class AfterSaleApplication {
	private static Logger LOG = Logger.getLogger(AfterSaleApplication.class);
	private static Properties config = ConfigUtil.getDefaultConfig();


	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ClusterManager mgr = new ZookeeperClusterManager();
		VertxOptions options = new VertxOptions().setClusterManager(mgr).setMetricsOptions(new DropwizardMetricsOptions().setEnabled(true));
		options.setClusterHost(config.getProperty("cluster.host", "localhost"));
		options.setClusterPublicHost(config.getProperty("cluster.public.host", "localhost"));

		Vertx.clusteredVertx(options, res -> {
			if (res.succeeded()) {
				ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
				AfterSaleVerticle afterSaleVerticle = applicationContext.getBean(AfterSaleVerticle.class);
				Vertx vertx = res.result();
				DeploymentOptions option = new DeploymentOptions();
				option.setWorker(true);
				vertx.deployVerticle(afterSaleVerticle, option);
				LOG.info("server start success.");
			} else {
				LOG.error("server start failed.");
			}
		});
	}
}
