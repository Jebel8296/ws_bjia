package com.chinamcom.framework.common.response;

import java.util.Date;

public class RespBody {

	private String sn; // 序列号
	private int code; // 响应码值
	private String msg; // 响应描述
	private Object respData;// 响应结果
	private Long ts; // 响应时间

	public RespBody(String sn) {
		this(sn, RespCode.CODE_200, null);
	}

	public RespBody(String sn, RespCode code) {
		this(sn, code, null);
	}

	public RespBody(String sn, RespCode code, Object responseData) {
		this(sn, code.getCode(), code.getDesp(), responseData);
	}

	public RespBody(String sn, RespCode code, String msg, Object responseData) {
		this(sn, code.getCode(), msg, responseData);
	}

	private RespBody(String sn, int code, String msg, Object responseData) {
		this.sn = sn;
		this.ts = new Date().getTime();
		this.code = code;
		this.msg = msg;
		this.respData = responseData;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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
		if (ts == null) {
			return new Date().getTime();
		}
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}
}
