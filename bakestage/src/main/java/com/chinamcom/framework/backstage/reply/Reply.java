package com.chinamcom.framework.backstage.reply;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * 返回数据
 * 
 * @author xuxg
 *
 */
public class Reply {
	private int code;
	private String msg;
	private Object respData;
	private Long ts = System.currentTimeMillis();

	public String encodePrettily() {
		return Json.encodePrettily(this);
	}

	public JsonObject toJson() {
		return new JsonObject(encodePrettily());
	}

	public Reply(int code) {
		super();
		this.code = code;
	}

	public Reply(int code, String msg, Object respData) {
		super();
		this.code = code;
		this.msg = msg;
		this.respData = respData;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getRespData() {
		return respData;
	}

	public void setRespData(Object respData) {
		this.respData = respData;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

}
