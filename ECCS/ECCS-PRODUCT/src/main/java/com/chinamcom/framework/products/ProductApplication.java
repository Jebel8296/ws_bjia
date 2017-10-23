package com.chinamcom.framework.products;

import com.chinamcom.framework.common.util.ConfigUtil;
import com.chinamcom.framework.products.verticle.ProductVerticle;
import com.chinamcom.framework.products.verticle.StockVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

/**
 * Created by Administrator on 2016/5/13.
 */
public class ProductApplication {
    private static Logger LOG = Logger.getLogger(ProductApplication.class);
    private static Properties config = ConfigUtil.getDefaultConfig();
    private static ClassPathXmlApplicationContext applicationContext = null;

    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {

        ClusterManager mgr = new ZookeeperClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        options.setClusterHost(config.getProperty("cluster.host", "localhost"));
        options.setClusterPublicHost(config.getProperty("cluster.public.host", "localhost"));

        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
                ProductVerticle product = applicationContext.getBean(ProductVerticle.class);
                StockVerticle stock = applicationContext.getBean(StockVerticle.class);
                Vertx vertx = res.result();

                DeploymentOptions option = new DeploymentOptions();
                option.setWorker(true);

                vertx.deployVerticle(product, option);
                vertx.deployVerticle(stock, option);
                LOG.info("server start success.");
            } else {
                LOG.error("server start failed.");
            }
        });
    }
}
