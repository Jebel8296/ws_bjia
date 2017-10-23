package com.chinamcom.framework.backstage.verticle;

import org.jboss.resteasy.plugins.server.vertx.VertxRegistry;
import org.jboss.resteasy.plugins.server.vertx.VertxRequestHandler;
import org.jboss.resteasy.plugins.server.vertx.VertxResteasyDeployment;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.backstage.common.BackstageConsumer;
import com.chinamcom.framework.backstage.restful.AftersaleRestful;
import com.chinamcom.framework.backstage.restful.ProductDeviceRestful;
import com.chinamcom.framework.backstage.restful.ServiceMonitorRestful;
import com.chinamcom.framework.backstage.restful.USerManagerRestful;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * 
 * 必加后台管理系统网关
 * 
 * @author xxug
 *
 */
@Component
public class Gateway extends BackstageVerticle {
	@Override
	public void start() throws Exception {
		super.start();
		VertxResteasyDeployment vertxResteasyDeployment = new VertxResteasyDeployment();
		vertxResteasyDeployment.start();
		VertxRegistry vertxRegistry = vertxResteasyDeployment.getRegistry();
		vertxRegistry.addPerInstanceResource(AftersaleRestful.class);
		vertxRegistry.addPerInstanceResource(ProductDeviceRestful.class);
		vertxRegistry.addPerInstanceResource(ServiceMonitorRestful.class);
		vertxRegistry.addPerInstanceResource(USerManagerRestful.class);
		Router router = Router.router(vertx);
		router.route().handler(CookieHandler.create()); 
		enableCorsSupport(router);
		router.route("/rest/*").handler(routerHandler -> {
			new VertxRequestHandler(vertx, vertxResteasyDeployment).handle(routerHandler.request());
		});

		BridgeOptions bridgeOptions = new BridgeOptions();
		PermittedOptions permittedOptions = new PermittedOptions();
		permittedOptions.setAddress(BackstageConsumer.LOG_EB_MONITOR_BACKSTAGE);
		bridgeOptions.addOutboundPermitted(permittedOptions);
		SockJSHandler sockJSHandler = SockJSHandler.create(vertx).bridge(bridgeOptions);
		router.route("/eb/*").handler(sockJSHandler);
		
		vertx.createHttpServer().requestHandler(router::accept).listen(9900);
		log.info(this.getClass().getName() + "is deployed successfully.");
	}

}