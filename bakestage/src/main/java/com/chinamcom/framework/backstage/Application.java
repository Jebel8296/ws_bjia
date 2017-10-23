package com.chinamcom.framework.backstage;

import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinamcom.framework.backstage.util.ConfigUtil;
import com.chinamcom.framework.backstage.verticle.AfterSaleVerticle;
import com.chinamcom.framework.backstage.verticle.Gateway;
import com.chinamcom.framework.backstage.verticle.ProductDeviceVerticle;
import com.chinamcom.framework.backstage.verticle.ServiceMonitorVerticle;
import com.chinamcom.framework.backstage.verticle.UserManagerVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

/**
 * 必加后台启动类
 * 
 * @author xuxg
 */
public class Application {
	private static Properties properties = ConfigUtil.getDefaultConfig();
	private static ClassPathXmlApplicationContext applicationContext = null;

	public static void main(String[] args) throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Gateway gateway = applicationContext.getBean(Gateway.class);
		AfterSaleVerticle afterSaleVerticle = applicationContext.getBean(AfterSaleVerticle.class);
		ServiceMonitorVerticle serviceMonitorVerticle = applicationContext.getBean(ServiceMonitorVerticle.class);
		ProductDeviceVerticle productDeviceVerticle = applicationContext.getBean(ProductDeviceVerticle.class);
		UserManagerVerticle userManagerVerticle = applicationContext.getBean(UserManagerVerticle.class);

		ClusterManager mgr = new ZookeeperClusterManager();
		VertxOptions options = new VertxOptions().setClusterManager(mgr);
		options.setClusterHost(properties.getProperty("cluster.host", "localhost"));
		options.setClusterPublicHost(properties.getProperty("cluster.public.host", "localhost"));
		DeploymentOptions option = new DeploymentOptions();
		option.setWorker(true);
		Vertx.clusteredVertx(options, resultHandler -> {
			if (resultHandler.succeeded()) {
				Vertx vertx = resultHandler.result();
				vertx.deployVerticle(gateway);
				vertx.deployVerticle(afterSaleVerticle, option);
				vertx.deployVerticle(productDeviceVerticle, option);
				vertx.deployVerticle(serviceMonitorVerticle, option);
				vertx.deployVerticle(userManagerVerticle, option);
			}
		});
	}
}
