package com.chinamcom.framework.server;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.chinamcom.framework.common.container.Application;
import com.chinamcom.framework.server.verticle.ConnectionVerticle;

public class Runner {
	private static Logger log = Logger.getLogger(Runner.class);
	
    public static void main(String[] args)  throws Exception {
    	ClusterManager mgr = new ZookeeperClusterManager();
    	VertxOptions options = new VertxOptions().setClusterManager(mgr).setClustered(true);
    	Properties conf = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
        if (null == is) {
            is = Application.class.getClassLoader().getResourceAsStream("config.properties");
        }
        conf.load(is);
        options.setClusterHost(conf.getProperty("server.host","localhost"));
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                LocalMap<String, String> env = vertx.sharedData().getLocalMap("env");
                for (Map.Entry<Object, Object> entry : conf.entrySet()) {
                    env.put(entry.getKey().toString(), entry.getValue().toString());
                }
                vertx.deployVerticle(new ConnectionVerticle());
                log.info("server start success.");
            } else {
            	log.error("server start failed.");
            }
        });
	}
}
