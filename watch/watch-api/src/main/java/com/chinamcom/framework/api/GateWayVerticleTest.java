package com.chinamcom.framework.api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constants.CmdConstants;
import com.chinamcom.framework.common.constants.Constants;
import com.chinamcom.framework.common.container.Application;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.request.ReqBody;
import com.chinamcom.framework.common.response.RespBodyBuilder;
import com.chinamcom.framework.common.utils.ConfigUtil;
import com.chinamcom.framework.common.utils.StringUtil;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/15
 */
@Component
public class GateWayVerticleTest extends AbstractVerticle {

    private static Logger log = Logger.getLogger(GateWayVerticleTest.class);
    
    private RespBodyBuilder respWriter = new RespBodyBuilder();
    
//    private LocalUserRealm localUserRealm;

    static ApplicationContext applicationContext;
    
    private static Properties config;
    
    public static void main(String[] args) {
    	applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    	config = ConfigUtil.getDefaultConfig();
    	ClusterManager mgr = new ZookeeperClusterManager();
    	VertxOptions options = new VertxOptions().setClusterManager(mgr).setClustered(true);
    	options.setClusterHost(config.getProperty("server.host","localhost"));
//		Application.run(applicationContext.getBean(GateWayVerticleTest.class), options, null);
    	String exampleDir = "watch-api/src/main/java/"+GateWayVerticleTest.class.getPackage().getName().replace(".", "/");
    	String verticleID = GateWayVerticleTest.class.getName();
    	Application.run(exampleDir, verticleID, options, null);
    }
    
    @Override
    public void start() throws Exception {
//    	RedisClient redis = RedisClient.create(vertx, new RedisOptions()
//		.setHost(config.getProperty("redis.host", "localhost"))
//		.setPort(Integer.parseInt(config.getProperty("redis.port")))
//		.setTcpKeepAlive(true)
//		.setSelect(Integer.valueOf(config.getProperty("redis.db", "8"))));
        Router router = Router.router(vertx);
        router.route().handler(LoggerHandler.create());
        router.route().handler(CookieHandler.create());
        router.route().handler(BodyHandler.create());
        router.route("/static/*").handler(StaticHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)).setSessionTimeout(Long.valueOf(config.getProperty("shiro.timeout", "2592000000"))));
       
//        router.route().handler(SignHandler.create(redis));
//        router.route().handler(UpGradeHandler.create(redis));
        
        router.route("/gateway.do").handler(context -> {
        	String contentType = context.request().getHeader("content-type");
        	if(contentType == null || !contentType.contains("json")){
        		return;
        	}
            HttpServerResponse response = context.response();
            response.putHeader("content-type", "application/json");
            String serial_number = null;
            String cn = null;
            try {
                String strReqBody = context.getBodyAsString();
                Session session = context.session();
                String ip = context.request().remoteAddress().host();
                log.info("Request IP:" +  ip + " Session ID:"  + session.id() + "Original reqBody:" + strReqBody);
                if(StringUtil.isEmpty(strReqBody)){
                	response.end(respWriter.toError(serial_number, "ReqBody is null."));
                	return;
                }
                ReqBody reqBody = Json.decode(strReqBody, ReqBody.class);
                if(reqBody == null){
                	response.end(respWriter.toError(serial_number, "ReqBody is null."));
                	return;
                }
                reqBody.setTs(new Date().getTime());
                serial_number = reqBody.getSn();
                cn = reqBody.getCn();
                reqBody.setIp(ip);
                String strReqData = reqBody.getReqData();
                ZJSONObject  reqData = Json.decode(strReqData, ZJSONObject.class);
                reqData.put("sn", serial_number);
                DeliveryOptions options = new DeliveryOptions();
                options.setSendTimeout(Integer.parseInt(config.getProperty("server.port.http.send.timeout","10000")));
                String address = null;
                String cmd = reqBody.getCmd();
                reqData.put("cn", cn);
                if(Constants.CHANNEL_WATCH.equals(reqBody.getCn())){
                	reqData.put("cmd", cmd);
                	address = CmdConstants.cmdShortMap.get(cmd);
                }else{
                	address = reqBody.getService();
                }
                vertx.eventBus().send(address, reqData.toString(), options, res -> {
                	String respBody = null;
                    if (res.succeeded()) {
                    	respBody = res.result().body().toString();
                    } else {
                    	log.error(res.cause().getMessage(), res.cause());
                    	if(StringUtil.isNotEmpty(res.cause().getMessage())){
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
                    	}
                    }
                    log.info(respBody);
                    response.end(respBody);
                });
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                if(StringUtil.isNotEmpty(ex.getMessage())){
                	response.end(respWriter.toError(serial_number, ex.getMessage()));
                }else{
                	response.end(respWriter.toError(serial_number));
                }
            }
        });
        
        HandlebarsTemplateEngine engine = HandlebarsTemplateEngine.create();
        router.get("/index.html").handler(ctx -> {
            // we define a hardcoded title for our application
        	String uid = ctx.request().getParam("uid");
        	String imei = ctx.request().getParam("imei");
        	DeliveryOptions options = new DeliveryOptions();
            options.setSendTimeout(Integer.parseInt(config.getProperty("server.port.http.send.timeout","10000")));
            ZJSONObject reqData = new ZJSONObject();
            reqData.put("uid", uid);
            reqData.put("imei", imei);
        	vertx.eventBus().send("sport.sportinfo.healthweekly", reqData.toString(), options, res -> {
        		String respBody = null;
                if (res.succeeded()) {
                	respBody = res.result().body().toString();
                    ctx.put("name", "Vert.x Web");
                    ctx.put("respBody", respBody);
                    engine.render(ctx, "templates/h5OfHealth.hbs", resd -> {
                      if (resd.succeeded()) {
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
//                response.end(respBody);
            });
          });
        
        HttpClientOptions options = new HttpClientOptions();
        options.setIdleTimeout(Integer.parseInt(config.getProperty("server.port.http.timeout","20")));
        vertx.createHttpServer().requestHandler(router::accept).listen(Integer.parseInt(config.getProperty("server.port.http","10020")));
    }
}
