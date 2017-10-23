package com.chinamcom.framework.backstage.restful;

import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chinamcom.framework.backstage.common.BackstageConsumer;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * 产品设备管理
 * 
 * @author xuxg
 *
 */
@Path("/rest/prodevice")
public class ProductDeviceRestful extends Restful {

	@GET
	@Path("/health")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public void health(@Suspended final AsyncResponse asyncResponse, @Context Vertx vertx) {
		asyncResponse.resume(new JsonObject().put("status", "UP").encode());
	}

	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void delProductDevice(@Suspended final AsyncResponse asyncResponse, @Context Vertx vertx,
			@PathParam("id") Integer id) {
		JsonObject message = new JsonObject();
		if (Pattern.matches("[0-9]+", id.toString())) {
			message.put("id", id);
			send(vertx, BackstageConsumer.DEL_PRODUCTDEVICE_BACKSTAGE, message, options, asyncResponse);
		} else {
			asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
			return;
		}
	}

	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getAfterSaleList(@Suspended final AsyncResponse asyncResponse, @Context Vertx vertx,
			@QueryParam("prodcode") String prodcode, @QueryParam("devicode") String devicode,
			@QueryParam("pageSize") Integer pageSize, @QueryParam("pageNum") Integer pageNum) {
		JsonObject message = new JsonObject();
		message.put("pageSize", pageSize == null ? 10 : pageSize);
		message.put("pageNum", pageNum == null ? 1 : pageNum);
		if (prodcode != null) {
			message.put("prodcode", prodcode);
		}
		if (devicode != null) {
			message.put("devicode", devicode);
		}
		send(vertx, BackstageConsumer.LIST_PRODUCTDEVICE_BACKSTAGE, message, options, asyncResponse);
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void handleAfterSaleInfo(@Suspended final AsyncResponse asyncResponse, @Context Vertx vertx, String param) {
		JsonObject message = new JsonObject(param);
		if (!message.containsKey("devicode") || !message.containsKey("prodcode")) {
			asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
			return;
		}
		String prodcode = message.getString("prodcode");
		boolean match = Pattern.matches("3C4[1|0][A|B|C]1000[1|2|3]0001", prodcode.trim());
		if (match) {
			send(vertx, BackstageConsumer.ADD_PRODUCTDEVICE_BACKSTAGE, message, options, asyncResponse);
		} else {
			asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
			return;
		}
	}

}
