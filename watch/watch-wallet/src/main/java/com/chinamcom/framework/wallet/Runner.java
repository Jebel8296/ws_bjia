package com.chinamcom.framework.wallet;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinamcom.framework.common.container.Application;
import com.chinamcom.framework.common.utils.ConfigUtil;
import com.chinamcom.framework.wallet.verticle.BusErrorlogVerticle;
import com.chinamcom.framework.wallet.verticle.BusRechargeVerticle;
import com.chinamcom.framework.wallet.verticle.ComAccountVerticle;

public class Runner {
    private static Logger LOG = Logger.getLogger(Runner.class);
	static ApplicationContext applicationContext;
	public static Properties config;
    public static void main(String[] args) {
    	applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    	ClusterManager mgr = new ZookeeperClusterManager();
    	VertxOptions options = new VertxOptions().setClusterManager(mgr).setClustered(true);
    	config = ConfigUtil.getDefaultConfig();
    	options.setClusterHost(config.getProperty("cluster.host","localhost"));
    	ComAccountVerticle comAccountVerticle = applicationContext.getBean(ComAccountVerticle.class);
    	BusRechargeVerticle busRechargeVerticle = applicationContext.getBean(BusRechargeVerticle.class);
    	BusErrorlogVerticle busErrorlogVerticle = applicationContext.getBean(BusErrorlogVerticle.class);
    	DeploymentOptions deploymentOptions = new DeploymentOptions().setWorker(true);
    	List<Verticle> verticles = new ArrayList<Verticle>();
    	verticles.add(comAccountVerticle);
    	verticles.add(busRechargeVerticle);
    	verticles.add(busErrorlogVerticle);
    	Application.run(verticles, options, deploymentOptions);
    	
	}
}
