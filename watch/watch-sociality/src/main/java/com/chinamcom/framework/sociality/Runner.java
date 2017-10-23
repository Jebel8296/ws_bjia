package com.chinamcom.framework.sociality;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinamcom.framework.common.container.Application;
import com.chinamcom.framework.common.utils.ConfigUtil;
import com.chinamcom.framework.sociality.verticle.CallInfoVerticle;
import com.chinamcom.framework.sociality.verticle.ContactsVerticle;
import com.chinamcom.framework.sociality.verticle.SocialityVerticle;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/03
 */
public class Runner {
	static ApplicationContext applicationContext;
    
	public static Properties config;
	
    public static void main(String[] args) {
    	applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    	ClusterManager mgr = new ZookeeperClusterManager();
    	VertxOptions options = new VertxOptions().setClusterManager(mgr).setClustered(true);
    	config = ConfigUtil.getDefaultConfig();
    	options.setClusterHost(config.getProperty("cluster.host","localhost"));
    	CallInfoVerticle callInfoVerticle = applicationContext.getBean(CallInfoVerticle.class);
    	SocialityVerticle socialityVerticle = applicationContext.getBean(SocialityVerticle.class);
    	ContactsVerticle contactsVerticle = applicationContext.getBean(ContactsVerticle.class);
    	DeploymentOptions deploymentOptions = new DeploymentOptions().setWorker(true);
    	List<Verticle> verticles = new ArrayList<Verticle>();
    	verticles.add(callInfoVerticle);
    	verticles.add(socialityVerticle);
    	verticles.add(contactsVerticle);
    	Application.run(verticles, options, deploymentOptions);		
	}
}
