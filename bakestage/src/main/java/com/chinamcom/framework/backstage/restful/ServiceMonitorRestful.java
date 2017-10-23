package com.chinamcom.framework.backstage.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.chinamcom.framework.backstage.common.BackstageConsumer;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * 服务监控
 * 
 * @author xuxg
 *
 */
@Path("/rest/monitor/service")
public class ServiceMonitorRestful extends Restful {

	@GET
	@Path("/health")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public void health(@Suspended final AsyncResponse asyncResponse, @Context Vertx vertx) {
		asyncResponse.resume(new JsonObject().put("status", "UP").encode());
	}

	@GET
	@Path("/{enviroment}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getAfterSaleList(@Suspended final AsyncResponse asyncResponse, @Context Vertx vertx,
			@PathParam("enviroment") String enviroment) {
		JsonObject message = new JsonObject();
		message.put("path", "real".equals(enviroment.trim()) ? "/bjia/real" : "/bjia/test");
		send(vertx, BackstageConsumer.SERVICE_MONITOR_BACKSTAGE, message, options, asyncResponse);
	}

}
