package com.chinamcom.framework.common.request;

import java.io.IOException;

import com.chinamcom.framework.common.util.JsonUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ReqBody {

	private String ip; // 客户端IP
	private String service; // 服务名称 sport.center [company].[product].[business].[module]
	private String cn; // 渠道名称 web|ios|android|wap|wx
	private String sv; // 服务版本
	private String cv; // 客户版本
	private String pn; // 渠道私钥
	private String sign; // 加密结果
	private String st; // 加密类型
	private String sn; // 请求流水
	private String reqData; // 请求数据
	private Long ts; // 请求时间

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getSv() {
		return sv;
	}

	public void setSv(String sv) {
		this.sv = sv;
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getReqData() {
		return reqData;
	}

	public void setReqData(String reqData) {
		this.reqData = reqData;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public String toJsonString() throws JsonGenerationException, JsonMappingException, IOException {
		return JsonUtil.convertObject2Json(this);
	}
}
