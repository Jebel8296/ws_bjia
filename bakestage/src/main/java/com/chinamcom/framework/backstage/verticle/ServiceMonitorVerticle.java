package com.chinamcom.framework.backstage.verticle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.chinamcom.framework.backstage.common.BackstageConsumer;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 服务监控
 * 
 * @author xuxg
 *
 */
@Component
public class ServiceMonitorVerticle extends BackstageVerticle {

	@Override
	public void start() throws Exception {
		super.start();
		eb.consumer(BackstageConsumer.SERVICE_MONITOR_BACKSTAGE, message -> {
			JsonObject reqData = (JsonObject) message.body();
			log.info("service-monitor request:" + reqData);
			List<JsonObject> services = new ArrayList<JsonObject>();
			String zkRootPath = reqData.getString("path");
			JsonObject result = new JsonObject();
			try {
				String zkPath = zkRootPath + "/asyncMultiMap/__vertx.subs";
				List<String> nodeList = curator.getChildren().forPath(zkPath);
				if (!nodeList.isEmpty()) {
					nodeList.forEach(item -> {
						JsonObject node = new JsonObject();
						node.put("service", item);
						try {
							List<String> hostList = curator.getChildren().forPath(zkPath + "/" + item);
							if (!hostList.isEmpty()) {
								JsonArray hosts = new JsonArray();
								node.put("stat", 1);
								hostList.forEach(host -> {
									hosts.add(new String(host.getBytes()));
								});
								node.put("host", hosts);
							} else {
								node.put("stat", 0);
							}
						} catch (Exception e) {
						}
						services.add(node);
					});
				}
				result = buildOK(services);
			} catch (Exception e) {
				e.printStackTrace();
				result = buildInternalServerError();
			}
			log.info("service-monitor reponse:" + result);
			message.reply(result);
		});
	}

}
