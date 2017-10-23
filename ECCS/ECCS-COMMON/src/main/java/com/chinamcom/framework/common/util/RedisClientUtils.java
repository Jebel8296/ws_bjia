package com.chinamcom.framework.common.util;

import java.util.Properties;

import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;

/**
 * 获取Redis客户端
 * 
 * @author xuxg
 *
 */
public class RedisClientUtils {

	private static Properties config = ConfigUtil.getDefaultConfig();

	// 若返回NULL，则验证码保存到DB中
	public static RedisClient getRedisClient(Vertx vertx) {
		RedisClient client = null;
		/** 暂时不用
		if (config.containsKey("redis.host")) {
			RedisOptions options = new RedisOptions();
			options.setHost(config.getProperty("redis.host", "localhost"));
			options.setPort(Integer.valueOf(config.getProperty("redis.port", "6379")));
			options.setSelect(Integer.valueOf(config.getProperty("redis.db", "3")));
			client = RedisClient.create(vertx, options);
		}
		*/
		return client;
	}
}
