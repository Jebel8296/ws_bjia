package com.chinamcom.framework.common.verticle;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.log4j.Logger;

import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.response.RespBodyBuilder;
import com.chinamcom.framework.common.util.ConfigUtil;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.backend.redis.RedisBackendService;
import io.vertx.servicediscovery.types.EventBusService;
import io.vertx.servicediscovery.types.HttpEndpoint;
import io.vertx.servicediscovery.types.MessageSource;

public abstract class BaseVerticle extends AbstractVerticle {

	private static final String LOG_EVENT_ADDRESS = ConsumerConstant.MONITOR_LOG;

	protected Logger LOG = Logger.getLogger(getClass());
	protected Properties config = ConfigUtil.getDefaultConfig();
	protected Properties zkConfig = ConfigUtil.getConfig("zookeeper.properties");
	protected RespBodyBuilder respWriter = new RespBodyBuilder();
	protected String zkRootPath = zkConfig.getProperty("path.root", "/");
	protected CircuitBreaker breaker;
	protected ServiceDiscovery discovery;
	protected CuratorFramework curator;
	protected RedisClient redis;
	protected JWTAuth jwt;

	@Override
	public void start() throws Exception {
		super.start();
		discovery = ServiceDiscovery.create(vertx,
				new ServiceDiscoveryOptions().setBackendConfiguration(
						new JsonObject().put("backend-name", RedisBackendService.class.getName())
								.put("host", config.getProperty("redis.host", "localhost"))
								.put("port", config.getProperty("redis.port", "6379"))
								.put("select", config.getProperty("redis.db", "3"))));
		breaker = CircuitBreaker.create("circuit-breaker", vertx,
				new CircuitBreakerOptions()
						.setMaxFailures(Integer.valueOf(config.getProperty("circuit.maxfailures", "5")))
						.setTimeout(Integer.valueOf(config.getProperty("circuit.timeout", "10000")))
						.setFallbackOnFailure(Boolean.valueOf(config.getProperty("circuit.fallbackOnfailure", "true")))
						.setResetTimeout(Integer.valueOf(config.getProperty("circuit.resetTtimeout", "10000"))));
		curator = CuratorFrameworkFactory.newClient(zkConfig.getProperty("hosts.zookeeper", "localhost:2181"),
				Integer.valueOf(config.getProperty("timeout.session", "20000")),
				Integer.valueOf(config.getProperty("timeout.connect", "3000")),
				new RetryNTimes(Integer.valueOf(config.getProperty("retry.maxTimes", "5")),
						Integer.valueOf(config.getProperty("retry.intervalTimes", "10000"))));
		curator.start();

		redis = RedisClient.create(vertx,
				new RedisOptions().setHost(config.getProperty("redis.host", "localhost"))
						.setPort(Integer.valueOf(config.getProperty("redis.port", "6379")))
						.setSelect(Integer.valueOf(config.getProperty("redis.db", "3"))));
		jwt = JWTAuth.create(vertx, new JsonObject().put("keyStore",
				new JsonObject().put("path", "keystore.jceks").put("password", "secret")));
	}

	protected void doGatewayHealth(RoutingContext context) {
		HttpServerResponse res = context.response();
		res.putHeader("content-type", "application/json;charset=UTF-8");
		res.end(new JsonObject().put("version", config.getProperty("version", "v1")).put("status", "running").encode());
	}

	protected Future<Void> publishHttpEndpoint(String name, String host, int port) {
		Record record = HttpEndpoint.createRecord(name, host, port, "/", new JsonObject().put("api.name", name));
		return publish(record);
	}

	protected Future<Void> publishApiGateway(String host, int port) {
		Record record = HttpEndpoint.createRecord("api-gateway", true, host, port, "/", null).setType("api-gateway");
		return publish(record);
	}

	protected Future<Void> publishMessageSource(String name, String address) {
		Record record = MessageSource.createRecord(name, address);
		return publish(record);
	}

	protected Future<Void> publishEventBusService(String name, String address, Class<?> serviceClass) {
		Record record = EventBusService.createRecord(name, address, serviceClass);
		return publish(record);
	}

	private Future<Void> publish(Record record) {
		if (discovery == null) {
			try {
				start();
			} catch (Exception e) {
				throw new IllegalStateException("Cannot create discovery service");
			}
		}
		Future<Void> future = Future.future();
		discovery.publish(record, ar -> {
			if (ar.succeeded()) {
				future.complete();
			} else {
				future.fail(ar.cause());
			}
		});
		return future;
	}

	protected Future<Void> createHttpServer(Router router, String host, int port) {
		Future<HttpServer> httpServerFuture = Future.future();
		vertx.createHttpServer().requestHandler(router::accept).listen(port, host, httpServerFuture.completer());
		return httpServerFuture.map(r -> null);
	}

	protected void enableCorsSupport(Router router) {
		Set<String> allowHeaders = new HashSet<>();
		allowHeaders.add("X-PINGARUNER");
		allowHeaders.add("Content-Type");
		allowHeaders.add("Access-Control-Allow-Headers");
		allowHeaders.add("Authorization");
		allowHeaders.add("X-Requested-With");
		Set<HttpMethod> allowMethods = new HashSet<>();
		allowMethods.add(HttpMethod.GET);
		allowMethods.add(HttpMethod.POST);
		allowMethods.add(HttpMethod.DELETE);
		allowMethods.add(HttpMethod.PATCH);
		allowMethods.add(HttpMethod.OPTIONS);

		router.route().handler(CorsHandler.create("*").allowedHeaders(allowHeaders).allowedMethods(allowMethods));
	}

	protected <T> Handler<AsyncResult<T>> resultHandler(RoutingContext context, Function<T, String> converter) {
		return ar -> {
			if (ar.succeeded()) {
				T res = ar.result();
				if (res == null) {
					reply500Response(context, "");
				} else {
					reply200Response(context, converter.apply(res));
				}
			} else {
				LOG.error(ar);
				reply500Response(context, ar.cause().getMessage());
			}
		};
	}

	protected JsonObject multiMap2Json(MultiMap map) {
		JsonObject reply = new JsonObject();
		map.forEach(entry -> {
			reply.put(entry.getKey(), entry.getValue());
		});
		return reply;
	}

	protected void reply500Response(RoutingContext context, String _msg) {
		LOG.info("response=" + _msg);
		context.response().setStatusCode(500).putHeader("content-type", "application/json").end(_msg);
	}

	protected void reply200Response(RoutingContext context, String _msg) {
		LOG.info("response=" + _msg);
		context.response().setStatusCode(200).putHeader("content-type", "application/json").end(_msg);
	}

	protected void reply204Response(RoutingContext context) {
		context.response().setStatusCode(204).putHeader("content-type", "application/json").end();
	}

	protected void badGateway(RoutingContext context) {
		context.response().setStatusCode(502).putHeader("content-type", "application/json").end();
	}

	protected void publishLogEvent(String type, String flag, String data) {
		// flag=request/success/error
		JsonObject msg = new JsonObject().put("host", config.getProperty("cluster.host", "localhost")).put("type", type)
				.put("flag", flag).put("message", new JsonObject(data)).put("time", System.currentTimeMillis());
		vertx.eventBus().publish(LOG_EVENT_ADDRESS, msg);
	}

}
