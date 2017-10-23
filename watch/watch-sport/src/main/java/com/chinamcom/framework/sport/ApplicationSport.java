package com.chinamcom.framework.sport;

import java.util.Properties;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinamcom.framework.common.utils.ConfigUtil;
import com.chinamcom.framework.sport.verticle.HealthWeeklyVerticle;
import com.chinamcom.framework.sport.verticle.SportInfoVerticle;
import com.chinamcom.framework.sport.verticle.SportSummaryVerticle;

public class ApplicationSport {
    private static Logger LOG = Logger.getLogger(ApplicationSport.class);
    
    public static Properties config;
    
	private static ClassPathXmlApplicationContext applicationContext;

    public static void main(String[] args) {
    	applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    	Verticle sportInfoVerticle = applicationContext.getBean(SportInfoVerticle.class);
    	Verticle sportSummaryVerticle = applicationContext.getBean(SportSummaryVerticle.class);
    	Verticle healthWeeklyVerticle = applicationContext.getBean(HealthWeeklyVerticle.class);
        ClusterManager mgr = new ZookeeperClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
//        Properties config = ConfigUtil.getDefaultConfig();
        config = ConfigUtil.getDefaultConfig();
        options.setClusterHost(config.getProperty("cluster.host","localhost"));
        options.setClusterPublicHost(config.getProperty("cluster.public.host","localhost"));
//        options.setClusterHost("10.12.12.153");
//        options.setClusterPublicHost("10.12.12.153");
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                LOG.debug("service run in cluster model.");
                Vertx vertx = res.result();
                //cluster.
                DeploymentOptions option = new DeploymentOptions();
                option.setWorker(true);
                //option.setInstances(2);
                option.setMultiThreaded(true);
                vertx.deployVerticle(sportInfoVerticle,option);
                vertx.deployVerticle(sportSummaryVerticle,option);
                vertx.deployVerticle(healthWeeklyVerticle,option);
                
            } else {
                //local.
                LOG.debug("service run in local model.");
            }
        });
    }
}
