package com.chinamcom.framework.device;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinamcom.framework.common.container.Application;
import com.chinamcom.framework.common.utils.PropertyConfigLoader;
import com.chinamcom.framework.device.verticle.CallHisService;
import com.chinamcom.framework.device.verticle.DeviceVerticle;
import com.chinamcom.framework.device.verticle.HeartRateVerticle;
import com.chinamcom.framework.device.verticle.LocationService;
import com.chinamcom.framework.device.verticle.MessageService;
import com.chinamcom.framework.device.verticle.PushService;
import com.chinamcom.framework.device.verticle.SportService;
import com.chinamcom.framework.device.verticle.TaskVerticle;
import com.chinamcom.framework.device.verticle.UserVerticle;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/17
 */
public class Runner {

	public static ApplicationContext applicationContext;
	public static PropertyConfigLoader config;
    public static void main(String[] args) {
    	applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        MessageService ms = applicationContext.getBean(MessageService.class);
        CallHisService cs = applicationContext.getBean(CallHisService.class);
        SportService ss = applicationContext.getBean(SportService.class);
        LocationService ls = applicationContext.getBean(LocationService.class);
        PushService ps = applicationContext.getBean(PushService.class);
        UserVerticle us = applicationContext.getBean(UserVerticle.class);
        DeviceVerticle dv = applicationContext.getBean(DeviceVerticle.class);
        HeartRateVerticle hv = applicationContext.getBean(HeartRateVerticle.class);
        TaskVerticle tv  = applicationContext.getBean(TaskVerticle.class);
        ClusterManager mgr = new ZookeeperClusterManager();
    	VertxOptions options = new VertxOptions().setClusterManager(mgr).setClustered(true);
    	DeploymentOptions deploymentOptions = new DeploymentOptions().setWorker(true);
    	config = applicationContext.getBean(PropertyConfigLoader.class);
    	options.setClusterHost(config.get("cluster.host","localhost"));
    	List<Verticle> verticles = new ArrayList<Verticle>();
    	verticles.add(ms);
    	verticles.add(cs);
    	verticles.add(ss);
    	verticles.add(ls);
    	verticles.add(ps);
    	verticles.add(us);
    	verticles.add(dv);
    	verticles.add(hv);
    	verticles.add(tv);
		Application.run(verticles, options, deploymentOptions);
    }
}
