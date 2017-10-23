package com.chinamcom.framework.backstage.verticle;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.log4j.Logger;

import com.chinamcom.framework.backstage.reply.Reply;
import com.chinamcom.framework.backstage.util.ConfigUtil;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.redis.RedisClient;
import io.vertx.servicediscovery.ServiceDiscovery;

/**
 * 
 * @author xuxg
 *
 */
public abstract class BackstageVerticle extends AbstractVerticle {

	protected Logger log = Logger.getLogger(getClass());
	private Properties properties = ConfigUtil.getConfig("zookeeper.properties");
	protected CircuitBreaker breaker;
	protected ServiceDiscovery discovery;
	protected CuratorFramework curator;
	protected RedisClient redis;
	protected JWTAuth jwt;
	protected EventBus eb;

	@Override
	public void start() throws Exception {
		super.start();
		eb = vertx.eventBus();
		curator = CuratorFrameworkFactory.newClient(properties.getProperty("hosts.zookeeper", "localhost:2181"),
				Integer.valueOf(properties.getProperty("timeout.session", "20000")),
				Integer.valueOf(properties.getProperty("timeout.connect", "3000")),
				new RetryNTimes(Integer.valueOf(properties.getProperty("retry.maxTimes", "5")),
						Integer.valueOf(properties.getProperty("retry.intervalTimes", "10000"))));
		curator.start();
		jwt = JWTAuth.create(vertx, new JsonObject().put("keyStore",
				new JsonObject().put("path", "keystore.jceks").put("password", "secret")));
	}

	protected void enableCorsSupport(Router router) {
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
	}

	protected void checkHealth(String address) {
		eb.consumer(address, message -> {
			message.reply(new JsonObject().put("status", "UP"));
		});
	}

	protected JsonObject buildInternalServerError() {
		return new Reply(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "railed", new JsonObject()).toJson();
	}

	protected JsonObject buildOK(Object data) {
		return new Reply(Response.Status.OK.getStatusCode(), "successful", data).toJson();
	}

	/**
	 * Token检验
	 * 
	 * @param message
	 * @param resultHandler
	 */
	protected void checkToken(Message<Object> message, Handler<AsyncResult<JsonObject>> resultHandler) {
		JsonObject reqData = (JsonObject) message.body();
		log.info("request:" + reqData);
		String token = reqData.getString("token");
		Reply reply = new Reply(Response.Status.BAD_REQUEST.getStatusCode(), "登陆失效，请重新登陆.", null);
		if (token == null || token == "") {
			resultHandler.handle(Future.failedFuture(""));
			log.info("response:" + reply.toJson());
			message.reply(reply.toJson());
		} else {
			jwt.authenticate(new JsonObject().put("jwt", token), rs -> {
				if (rs.succeeded()) {
					resultHandler.handle(Future.succeededFuture(reqData));
				} else {
					resultHandler.handle(Future.failedFuture(""));
					log.info("response:" + reply.toJson());
					message.reply(reply.toJson());
				}
			});

		}
	}

}
