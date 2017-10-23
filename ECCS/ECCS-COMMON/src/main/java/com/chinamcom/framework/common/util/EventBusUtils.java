package com.chinamcom.framework.common.util;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

/**
 * 服务间调用工具类
 * 
 * @author xuxg
 *
 */
public class EventBusUtils {

	/**
	 * 无需处理返回结果
	 * 
	 * @param vertx
	 * @param address
	 *            接收服务地址
	 * @param message
	 *            接收服务所需参数，具体参数见其规范
	 */
	public static void sendWithoutDOReplyData(Vertx vertx, String address, JsonObject message) {
		vertx.eventBus().send(address, message);
	}

	/**
	 * 处理返回结果
	 * 
	 * @param vertx
	 * @param address
	 *            接收服务地址
	 * @param message
	 *            接收服务参数，具体参数见其规范
	 * @param handler
	 *            需要自己实现
	 */
	public static void sendWithDOReplyData(Vertx vertx, String address, JsonObject message,Handler<AsyncResult<Message<Object>>> handler) {
		vertx.eventBus().send(address, message, handler);
	}

}
