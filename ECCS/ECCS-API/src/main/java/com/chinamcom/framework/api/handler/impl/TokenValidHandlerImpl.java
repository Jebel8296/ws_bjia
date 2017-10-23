package com.chinamcom.framework.api.handler.impl;

import com.chinamcom.framework.api.handler.TokenValidHandler;
import com.chinamcom.framework.common.response.RespBodyBuilder;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.util.SecurityUtil;
import com.chinamcom.framework.common.util.StringUtil;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class TokenValidHandlerImpl implements TokenValidHandler {

    private final Logger logger = Logger.getLogger(TokenValidHandlerImpl.class);
    private final RespBodyBuilder respWriter = new RespBodyBuilder();

    protected final Vertx vertx;
    protected final JsonObject param;

    public TokenValidHandlerImpl(Vertx vertx, JsonObject param) {
        this.vertx = vertx;
        this.param = param;
    }

    @Override
    public void handle(RoutingContext ctx) {
        HttpServerResponse response = ctx.response();
        JsonObject request = ctx.getBodyAsJson();
        if (StringUtils.isEmpty(request)) {
            response.end(respWriter.toError(StringUtil.getSn(), "ReqBody is null."));
            return;
        }
        logger.info("request:" + request);
        String sn = request.containsKey("sn") ? request.getString("sn") : StringUtil.getSn();
        request.put("sn", sn);
        if (!request.containsKey("reqData")) {
            response.end(respWriter.toError(sn, "ReqData is null."));
            return;
        }
        JsonObject reqData = request.getJsonObject("reqData");
        if (!request.containsKey("service")) {
            response.end(respWriter.toError(sn, "service is null."));
            return;
        }
        String service = request.getString("service");
        /**
         //签名校验
         String sign = SecurityUtil.md5(reqData.encode());
         if (sign.equals(request.getString("sign"))) {
         response.end(respWriter.toError(sn, "验签失败"));
         return;
         }
         */
        // service=ConsumerConstant.transform.get(request.getString("service"));//获取真正的服务地址
        if (service.contains("product") || service.contains("user") || service.contains("build")) {
            ctx.next();
        } else {
            if (service.contains("user.") || service.contains("build.") || service.contains("product.")) {
                ctx.next();
            } else {
                if (!reqData.containsKey("uid")) {
                    response.end(respWriter.toError(sn, RespCode.CODE_506));
                    return;
                }
                /**
                 //token
                 if (!ctx.request().headers().contains("Authorization")) {
                 response.end(respWriter.toError(sn, RespCode.CODE_506));
                 return;
                 }
                 String[] bearer = ctx.request().headers().get("Authorization").split(" ");
                 if (bearer.length != 2 || !bearer[0].equals("Bearer")) {
                 response.end(respWriter.toError(sn, RespCode.CODE_506));
                 return;
                 }
                 */
                Integer uid = reqData.getInteger("uid");
                String token = request.getString("sign");// bearer[1]; //
                vertx.createHttpClient().get(8080, param.getString("host"),
                        param.getString("uri") + "?ui=" + uid + "&token=" + token, responseHandler -> {
                            int status = responseHandler.statusCode();
                            if (status == 200) {
                                responseHandler.bodyHandler(body -> {
                                    JsonObject result = body.toJsonObject();
                                    logger.info("调用【验证登陆状态接口】,返回值：" + result);
                                    if (result.getString("code").equals("0") && result.getBoolean("obj")) {// 已登陆,继续下面的业务
                                        ctx.next();
                                    } else {
                                        response.end(respWriter.toError(sn, RespCode.CODE_506));
                                    }
                                });
                            } else {
                                logger.error("调用【验证登陆状态接口】失败.");
                                response.end(respWriter.toError(sn, RespCode.CODE_506));
                            }
                        }).exceptionHandler(exec -> {
                    logger.error("调用【验证登陆状态接口】异常，请确认相应服务是否正常.");
                    response.end(respWriter.toError(sn, RespCode.CODE_506));
                }).end();
            }
        }

    }
}
