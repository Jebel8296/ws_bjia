package com.chinamcom.framework.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.constants.CmdConstants;
import com.chinamcom.framework.common.constants.Constants;
import com.chinamcom.framework.common.container.Application;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.request.ReqBody;
import com.chinamcom.framework.common.response.RespBodyBuilder;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.ConfigUtil;
import com.chinamcom.framework.common.utils.DateUtil;
import com.chinamcom.framework.common.utils.StringUtil;
/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/15
 */
@Component
public class GateWayVerticle extends AbstractVerticle {

    private static Logger log = Logger.getLogger(GateWayVerticle.class);
    
    private RespBodyBuilder respWriter = new RespBodyBuilder();
    
    private static Properties config;
    
    public static String secretKey;
    
    public static void main(String[] args) {
    	config = ConfigUtil.getDefaultConfig();
    	secretKey = config.getProperty("jwt.secretKey", "056538af5de907cf43cdb535f9eb016437b69a14");
    	ClusterManager mgr = new ZookeeperClusterManager();
    	VertxOptions options = new VertxOptions().setClusterManager(mgr).setClustered(true);
    	options.setClusterHost(config.getProperty("server.host","localhost"));
//    	String exampleDir = "watch-api/src/main/resources/"+GateWayVerticle.class.getPackage().getName().replace(".", "/");
    	String exampleDir = config.getProperty("exampleDir");
    	String verticleID = GateWayVerticle.class.getName();
    	Application.run(exampleDir, verticleID, options, null);
//		Application.run(GateWayVerticle.class.getName(), options, null);
	}
    
    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(LoggerHandler.create());
//        router.route().handler(CookieHandler.create());
        router.route().handler(BodyHandler.create());
//        router.route().handler(StaticHandler.create());
        Set<String> allowHeaders = new HashSet<>();
        allowHeaders.add("X-PINGARUNER");
		allowHeaders.add("Content-Type");
		Set<HttpMethod> allowMethods = new HashSet<>();
		allowMethods.add(HttpMethod.GET);
		allowMethods.add(HttpMethod.POST);
		allowMethods.add(HttpMethod.DELETE);
		allowMethods.add(HttpMethod.PATCH);
		allowMethods.add(HttpMethod.OPTIONS);
		router.route().handler(CorsHandler.create("*").allowedHeaders(allowHeaders).allowedMethods(allowMethods));

        router.route("/static/*").handler(StaticHandler.create());
//        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)).setSessionTimeout(Long.valueOf(config.getProperty("shiro.timeout", "2592000000"))));
        
        router.route("/gateway.do").handler(context -> {
        	HttpServerRequest request = context.request();
        	String contentType = request.getHeader("content-type");
        	if(contentType == null || !contentType.contains("json")){
        		return;
        	}
            HttpServerResponse response = context.response();
            response.putHeader("content-type", "application/json");
            String sn = null;
            String cn = null;
            String cmd = null;
            String address = null;
            try {
                String strReqBody = context.getBodyAsString();
                String ip = context.request().remoteAddress().host();
                log.info("Request IP: " +  ip + " Original reqBody: " + strReqBody);
                if(StringUtil.isEmpty(strReqBody)){
                	response.end(respWriter.toError(sn, "ReqBody is null."));
                	return;
                }
                ReqBody reqBody = Json.decode(strReqBody, ReqBody.class);
                if(reqBody == null){
                	response.end(respWriter.toError(sn, "ReqBody is null."));
                	return;
                }
                
                reqBody.setTs(new Date().getTime());
                sn = reqBody.getSn();
                if(StringUtil.isEmpty(sn)){
                	response.end(respWriter.toError(sn, "sn is null."));
                	return;
                }
                cn = reqBody.getCn();
                reqBody.setIp(ip);
                String strReqData = reqBody.getReqData();
                ZJSONObject  reqData = Json.decode(strReqData, ZJSONObject.class);
                reqData.put("sn", sn);
                DeliveryOptions sendOptions = new DeliveryOptions();
                sendOptions.setSendTimeout(Integer.parseInt(config.getProperty("server.port.http.send.timeout", "10000")));
                cmd = reqBody.getCmd();
                reqData.put("cn", cn);
                if(Constants.CHANNEL_WATCH.equals(cn)){
                	reqData.put("cmd", cmd);
                	address = CmdConstants.cmdShortMap.get(cmd);
                }else{
                	address = reqBody.getService();
                }
                if("user.loginverticle.login".equals(address) || "user.loginverticle.logout".equals(address)){  //登陆不需要校验token
                	doService(response, reqBody, reqData, sendOptions, address, cmd);
                	return;
        		}
        		if(Constants.CHANNEL_WATCH.equals(cn)){  //手表没有登陆，调用接口不需要token
        			doService(response, reqBody, reqData, sendOptions, address, cmd);
        			return;
        		}
        		try {
        			String token = request.getHeader("token");
        			log.debug("Current User token: " +  token);
        			if(StringUtil.isEmpty(token)){
        				response.end(respWriter.toError(sn, RespCode.CODE_605, "会话异常"));
        				return;
        			}
        			Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        			String uid = claims.getSubject();
        			String did = claims.get("did").toString();
        			log.debug("Current User Id: " +  uid + " did: " + did);
        			ZJSONObject userDeviceGetReqData = new ZJSONObject();
        			userDeviceGetReqData.put("uid", uid);
        			userDeviceGetReqData.put("did", did);
        			final String addressFinal = address;
        			final String snFinal = sn;
        			final String cmdFinal = cmd;
        			vertx.eventBus().send("user.device.get", userDeviceGetReqData.toString(), sendOptions, res -> {
                    	String respBody = null;
                        if (res.succeeded()) {
                        	respBody = res.result().body().toString();
                        	JSONObject respData = ZJSONObject.parseObject(respBody).getJSONObject("respData");
                        	log.debug("user.device.get" + respData);
                        	if(respData != null && respData.get("status") != null && "1".equals(respData.get("status").toString())){
                        		doService(response, reqBody, reqData, sendOptions, addressFinal, cmdFinal);
                        	}else{
                        		log.error("did: " + did + " uid : " + uid + " Status is error.");
                    			response.end(respWriter.toError(snFinal, RespCode.CODE_501));
                        	}
                        } else {
                        	log.error(res.cause().getMessage(), res.cause());
                			response.end(respWriter.toError(snFinal, RespCode.CODE_501, "获取在线状态失败"));
                        }
                    });
        		} catch (ExpiredJwtException ex) {
                    response.end(respWriter.toError(sn, RespCode.CODE_605, "会话过期"));
                } catch (MalformedJwtException ex) {
                    response.end(respWriter.toError(sn, RespCode.CODE_605, "会话无效"));
                } catch (Exception ex) {
        			log.error("[" + address + "] " + ex.getMessage(), ex);
        			response.end(respWriter.toError(sn, RespCode.CODE_605, "会话异常"));
        		}
            } catch (Exception ex) {
                response.end(respWriter.toCmdError(sn, cmd));
                log.error("[" + address + "] " + ex.getMessage(), ex);
            }
        });
        
      //访问路径：
        HandlebarsTemplateEngine engine = HandlebarsTemplateEngine.create();
        router.get("/healthReport.html").handler(ctx -> {
        	String uid = ctx.request().getParam("uid");
        	String imei = ctx.request().getParam("imei");
        	long date = Long.parseLong(ctx.request().getParam("date"));
        	
        	Properties config = GateWayVerticle.config;
			RedisOptions redisConfig = new RedisOptions();  
			redisConfig.setHost(config.getProperty("redis.host"));  
			redisConfig.setPort(Integer.parseInt(config.getProperty("redis.port")));
			redisConfig.setSelect(Integer.parseInt(config.getProperty("redis.db","3")));
			redisConfig.setTcpKeepAlive(true);
			RedisClient client = RedisClient.create(Vertx.vertx(), redisConfig);
			String redisKey =imei+"_healthReport"+"_"+uid+"_"+date;
        	
        	if(StringUtil.isEmpty(uid) || StringUtil.isEmpty(imei) || StringUtil.isEmpty(date)){
        		ctx.response().end("there has something rong with the paramaters!");
				return;
        	}
        	if(!StringUtil.isEmpty(date)){
        		try {
					Date day =new Date(date);
					int betweenDays = DateUtil.daysBetween(day, new Date()); 
					if(Math.abs(betweenDays)>30){
						ctx.response().end("sorry ,the link failed!");
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
        	DeliveryOptions options = new DeliveryOptions();
            options.setSendTimeout(Integer.parseInt(config.getProperty("server.port.http.send.timeout","10000")));
            ZJSONObject reqData = new ZJSONObject();
            reqData.put("uid", uid);
            reqData.put("imei", imei);
            log.info("分享页面访问请求："+reqData.toJSONString());
            client.get(redisKey, getRes ->{
            	Object ret = null;
    			if(getRes.succeeded()){
    				ret = getRes.result();
    				if(ret==null){
    					vertx.eventBus().send("sport.sportinfo.healthweekly", reqData.toString(), options, res -> {
    		        		String respBody = null;
    		                if (res.succeeded()) {
    		                	respBody = res.result().body().toString();
    		                    ctx.put("name", "Vert.x Web");
    		                    ctx.put("respBody", respBody);
    		                    engine.render(ctx, "templates/index.hbs", resd -> {
    		                      if (resd.succeeded()) {
    		                    	  
    		  						client.setex(redisKey,60*60*24*7,resd.result().toString(), setRes->{
    									if(!setRes.succeeded()){  
    								         setRes.cause().printStackTrace();  
    								         } 
    									});
    		                    	  
    		                        ctx.response().end(resd.result());
    		                      } else {
    		                        ctx.fail(resd.cause());
    		                      }
    		                    });
    		                } else {
    		                	log.error(res.cause().getMessage(), res.cause());
    		                	/*if(StringUtil.isNotEmpty(res.cause().getMessage())){
    		                		if(Constants.CHANNEL_WATCH.equals(reqBody.getCn())){
    		                			respBody = respWriter.toError(reqBody.getSn(), res.cause().getMessage());
    		                		}else{
    		                			respBody = respWriter.toCmdError(reqBody.getSn(), cmd, res.cause().getMessage());
    		                		}
    		                	}else{
    		                		if(Constants.CHANNEL_WATCH.equals(reqBody.getCn())){
    		                			respBody = respWriter.toError(reqBody.getSn());
    		                		}else{
    		                			respBody = respWriter.toCmdError(reqBody.getSn(), cmd);
    		                		}
    		                	}*/
    		                }
    		                log.info(respBody);
    		            });
    				}else{
    					ctx.response().end(ret.toString());
    				}
    			}
            });
          });
        
        
        HttpServerOptions httpServerOptions = new HttpServerOptions();
        httpServerOptions.setIdleTimeout(Integer.parseInt(config.getProperty("server.port.http.timeout","20")));
        vertx.createHttpServer(httpServerOptions).requestHandler(router::accept).listen(Integer.parseInt(config.getProperty("server.port.http","10020")));
    }

	private void doService(HttpServerResponse response, ReqBody reqBody, ZJSONObject reqData, DeliveryOptions sendOptions,
			String address, String cmd) {
		vertx.eventBus().send(address, reqData.toString(), sendOptions, res -> {
			String respBody = null;
		    if (res.succeeded()) {
		    	respBody = res.result().body().toString();
		    } else {
		    	respBody = respWriter.toCmdError(reqBody.getSn(), cmd);
		    	log.error("[" + address + "] " + res.cause().getMessage(), res.cause());
		    }
		    log.info(respBody);
		    response.end(respBody);
		});
	}
}
