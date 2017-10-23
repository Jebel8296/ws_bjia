package com.chinamcom.framework.common.service;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.chinamcom.framework.common.response.RespBodyBuilder;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public abstract class BaseService {

	protected Logger LOG = Logger.getLogger(getClass());
	protected RespBodyBuilder respWriter = new RespBodyBuilder();

	protected JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * for query with result
	 */
	protected JsonObject reply200Result(Object obj) {
		JsonObject msg = new JsonObject();
		msg.put("code", 200);
		msg.put("msg", "succeed");
		msg.put("data", new JsonObject(Json.encode(obj))).encodePrettily();
		return msg;
	}

	/**
	 * for delete/modify/query without result
	 */
	protected JsonObject reply204Result() {
		JsonObject msg = new JsonObject();
		msg.put("code", 204);
		msg.put("msg", "succeed");
		return msg;
	}

	/**
	 * for error
	 */
	protected String reply500Result(String _msg) {
		JsonObject msg = new JsonObject();
		msg.put("code", 500);
		msg.put("msg", _msg);
		return msg.encodePrettily();
	}
}
