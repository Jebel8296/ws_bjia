package com.chinamcom.framework;

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

public class ApplicationMybatis {
    private static Logger LOG = Logger.getLogger(ApplicationMybatis.class);
    
    public static Properties config;

	private static ClassPathXmlApplicationContext applicationContext;

    public static void main(String[] args) {
    	applicationContext = new ClassPathXmlApplicationContext("classpath:app-db.xml");
//    	Verticle loginHandler = (Verticle)applicationContext.getBean("loginHandler");
    	Verticle personalInfoHandler = (Verticle)applicationContext.getBean("personalInfoHandler");
    	Verticle managerWatchHandler = (Verticle)applicationContext.getBean("managerWatchHandler");
    	Verticle clockInfoVerticle = (Verticle)applicationContext.getBean("clockInfoVerticle");
    	Verticle sosSettingVerticle = (Verticle)applicationContext.getBean("sosSettingVerticle");
    	Verticle targetBaseInfoVerticle = (Verticle)applicationContext.getBean("targetBaseInfoVerticle");
    	Verticle feedBackInfoVerticle = (Verticle)applicationContext.getBean("feedBackInfoVerticle");
    	Verticle classModelVerticle = (Verticle)applicationContext.getBean("classModelVerticle");
//    	Verticle appMessageSendVerticle = (Verticle)applicationContext.getBean("appMessageSendVerticle");
    	Verticle messageSetVerticle = (Verticle)applicationContext.getBean("messageSetVerticle");
    	Verticle loginVerticle = (Verticle)applicationContext.getBean("loginVerticle");
    	Verticle deviceVerticle = (Verticle)applicationContext.getBean("deviceInfoVerticle");
    	Verticle messageInfoVerticle = (Verticle)applicationContext.getBean("messageInfoVerticle");
    	Verticle friendLikesVerticle = (Verticle)applicationContext.getBean("friendLikesVerticle");
    	ClusterManager mgr = new ZookeeperClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        
        config = ConfigUtil.getDefaultConfig();
        options.setClusterHost(config.getProperty("cluster.host","localhost"));
        options.setClusterPublicHost(config.getProperty("cluster.public.host","localhost"));
//        options.setClusterHost("10.12.12.129");
//        options.setClusterPublicHost("10.12.12.129");

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                LOG.debug("service run in cluster model.");
                Vertx vertx = res.result();
                //cluster.
                DeploymentOptions option = new DeploymentOptions();
                option.setWorker(true);
                //option.setInstances(2);
                option.setMultiThreaded(false);
//                vertx.deployVerticle(loginHandler,option);
                vertx.deployVerticle(personalInfoHandler,option);
                vertx.deployVerticle(managerWatchHandler,option);
                vertx.deployVerticle(clockInfoVerticle,option);
                vertx.deployVerticle(sosSettingVerticle,option);
                vertx.deployVerticle(targetBaseInfoVerticle,option);
                vertx.deployVerticle(feedBackInfoVerticle,option);
                vertx.deployVerticle(classModelVerticle,option);
//                vertx.deployVerticle(appMessageSendVerticle,option);
                vertx.deployVerticle(messageSetVerticle,option);
                vertx.deployVerticle(loginVerticle,option);
                vertx.deployVerticle(deviceVerticle,option);
                vertx.deployVerticle(messageInfoVerticle,option);
                vertx.deployVerticle(friendLikesVerticle,option);
            } else {
                //local.
                LOG.debug("service run in local model.");
            }
        });
    }
}
