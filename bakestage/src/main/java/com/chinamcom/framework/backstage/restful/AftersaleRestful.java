package com.chinamcom.framework.backstage.restful;

import com.chinamcom.framework.backstage.common.BackstageConsumer;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

/**
 * 客服受理
 *
 * @author xuxg
 */
@Path("/rest/aftersale")
public class AftersaleRestful extends Restful {

    @GET
    @Path("/health")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public void health(@Suspended final AsyncResponse asyncResponse, @Context Vertx vertx) {
        asyncResponse.resume(new JsonObject().put("status", "UP").encode());
    }

    @GET
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void getAfterSaleList(@Suspended final AsyncResponse asyncResponse, @Context Vertx vertx,
                                 @QueryParam("uid") Integer uid, @QueryParam("code") String code, @QueryParam("type") Integer type,
                                 @QueryParam("status") Integer status, @QueryParam("pageSize") Integer pageSize,
                                 @QueryParam("pageNum") Integer pageNum, @QueryParam("token") String token, @QueryParam("beginDate") String beginDate, @QueryParam("endDate") String endDate) {
        JsonObject message = new JsonObject();
        message.put("uid", uid == null ? 0 : uid);
        message.put("status", status == null ? "0" : status.toString());
        message.put("type", type == null ? "9" : type.toString());
        message.put("pageSize", pageSize == null ? 10 : pageSize);
        message.put("pageNum", pageNum == null ? 1 : pageNum);
        message.put("token", token);
        message.put("beginDate", beginDate != null ? beginDate : LocalDate.now().toString());
        message.put("endDate", endDate != null ? endDate : LocalDate.now().toString());
        if (code != null && code.length() > 0) {
            message.put("code", code);
        }
        send(vertx, BackstageConsumer.LIST_AFTERSALE_BACKSTAGE, message, options, asyncResponse);
    }

    @GET
    @Path("/{aftercode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public void getAfterSaleInfo(@Suspended final AsyncResponse asyncResponse, @Context Vertx vertx,
                                 @PathParam("aftercode") Integer aftercode, @QueryParam("token") String token) {
        JsonObject message = new JsonObject();
        message.put("aftercode", aftercode.toString());
        message.put("token", token);
        send(vertx, BackstageConsumer.INFO_AFTERSALE_BACKSTAGE, message, options, asyncResponse);
    }

    @POST
    @Path("/handle")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void handleAfterSaleInfo(@Suspended final AsyncResponse asyncResponse, @Context Vertx vertx, String param) {
        JsonObject message = new JsonObject(param);
        if (!(message.containsKey("result") || message.containsKey("aftercode") || message.containsKey("reply"))) {
            asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
            return;
        }
        Integer result = Integer.valueOf(message.getString("result"));
        if (!(result == 1 || result == 3 || result == 4 || result == 5)) {
            asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
            return;
        }
        send(vertx, BackstageConsumer.HANDLE_AFTERSALE_BACKSTAGE, message, options, asyncResponse);
    }

}
