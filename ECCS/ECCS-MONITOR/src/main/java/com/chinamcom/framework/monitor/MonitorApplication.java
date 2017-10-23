package com.chinamcom.framework.monitor;

import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinamcom.framework.common.util.ConfigUtil;
import com.chinamcom.framework.monitor.verticle.Gateway;
import com.chinamcom.framework.monitor.verticle.HandleLogVerticle;
import com.chinamcom.framework.monitor.verticle.ServiceVerticle;
import com.chinamcom.framework.monitor.verticle.UserVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

/**
 * 官网后台系统启动类
 * 
 * @author xuxg
 *
 */
public class MonitorApplication {

	private static Properties config = ConfigUtil.getDefaultConfig();
	private static ClassPathXmlApplicationContext applicationContext = null;

	public static void main(String[] args) throws Exception {
		ClusterManager mgr = new ZookeeperClusterManager();
		VertxOptions vertxOptions = new VertxOptions();
		vertxOptions.setClusterManager(mgr).setMetricsOptions(new DropwizardMetricsOptions().setEnabled(true));
		vertxOptions.setClusterHost(config.getProperty("cluster.host", "localhost"));
		vertxOptions.setClusterPublicHost(config.getProperty("cluster.public.host", "localhost"));

		applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Gateway gateway = applicationContext.getBean(Gateway.class);// 网关
		ServiceVerticle serviceVerticle = applicationContext.getBean(ServiceVerticle.class);// 服务监控
		HandleLogVerticle handleLogVerticle = applicationContext.getBean(HandleLogVerticle.class);//日志处理
		UserVerticle userVerticle = applicationContext.getBean(UserVerticle.class);//用户管理
		Vertx.clusteredVertx(vertxOptions, res -> {
			if (res.succeeded()) {
				Vertx vertx = res.result();
				DeploymentOptions deploymentOptions = new DeploymentOptions();
				deploymentOptions.setWorker(true);
				vertx.deployVerticle(gateway);
				vertx.deployVerticle(serviceVerticle, deploymentOptions);
				vertx.deployVerticle(handleLogVerticle, deploymentOptions);
				vertx.deployVerticle(userVerticle, deploymentOptions);
			} else {
			}
		});
	}
}
