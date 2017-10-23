package com.chinamcom.framework.monitor.verticle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.verticle.BaseVerticle;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * 服务监控
 * 
 * @author xuxg
 * @since 20161123
 *
 */
@Component
public class ServiceVerticle extends BaseVerticle {

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer(ConsumerConstant.MONITOR_SERVICE, message -> {
			JsonObject param = (JsonObject) message.body();
			String sn = param.getString("sn");
			String reply = null;
			List<JsonObject> services = new ArrayList<JsonObject>();
			try {
				String zkPath = "/" + zkRootPath + "/asyncMultiMap/__vertx.subs";
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
				reply = respWriter.toSuccess(services, sn);
			} catch (Exception e) {
				LOG.error(e);
				reply = respWriter.toError(sn);
			}
			message.reply(reply);
		});
	}
}
