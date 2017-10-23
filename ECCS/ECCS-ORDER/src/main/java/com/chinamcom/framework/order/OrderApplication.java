package com.chinamcom.framework.order;

import java.util.Properties;

import com.chinamcom.framework.order.scheduler.OrderScheduler;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinamcom.framework.common.util.ConfigUtil;
import com.chinamcom.framework.order.verticle.CartVerticle;
import com.chinamcom.framework.order.verticle.OrderVerticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

/**
 * Created by Administrator on 2016/5/13.
 */
public class OrderApplication {
    private static Logger LOG = Logger.getLogger(OrderApplication.class);
    private static Properties config = ConfigUtil.getDefaultConfig();
    private static ClassPathXmlApplicationContext applicationContext = null;

    public static void main(String[] args) throws Exception {

        ClusterManager mgr = new ZookeeperClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        options.setClusterHost(config.getProperty("cluster.host", "localhost"));
        options.setClusterPublicHost(config.getProperty("cluster.public.host", "localhost"));

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
                OrderVerticle order = applicationContext.getBean(OrderVerticle.class);
                CartVerticle cart = applicationContext.getBean(CartVerticle.class);
                OrderScheduler orderScheduler = applicationContext.getBean(OrderScheduler.class);
                Vertx vertx = res.result();
                DeploymentOptions option = new DeploymentOptions();
                option.setWorker(true);
                vertx.deployVerticle(order, option);
                vertx.deployVerticle(cart, option);
                vertx.deployVerticle(orderScheduler, option);
                LOG.info("server start success.");
            } else {
                LOG.error("server start failed.");
            }
        });
    }
}
