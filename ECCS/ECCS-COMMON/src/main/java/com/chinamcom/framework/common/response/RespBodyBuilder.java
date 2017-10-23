package com.chinamcom.framework.common.response;

import org.apache.log4j.Logger;

import com.chinamcom.framework.common.util.JsonUtil;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/15
 */
public class RespBodyBuilder {

	protected Logger LOG = Logger.getLogger(getClass());

	public String toSuccess(String sn) {
		String reply = null;
		RespBody body = new RespBody(sn);
		reply = JsonUtil.convertObject2Json(body);
		return reply;
	}

	public String toSuccess(String sn, String msg) {
		String reply = null;
		RespBody body = new RespBody(sn);
		body.setMsg(msg);
		reply = JsonUtil.convertObject2Json(body);
		return reply;
	}

	public String toPageSuccess(Object respdata, Object pageData, String sn) {
		String reply = null;
		PageBody body = new PageBody(sn);
		body.setRespData(respdata);
		body.setPageData(pageData);
		reply = JsonUtil.convertObject2Json(body);
		return reply;
	}

	public String toSuccess(Object respdata, String sn) {
		String reply = null;
		RespBody body = new RespBody(sn);
		body.setRespData(respdata);
		reply = JsonUtil.convertObject2Json(body);
		return reply;
	}

	public String toSuccess(String sn, String msg, Object respdata) {
		String reply = null;
		RespBody body = new RespBody(sn);
		body.setMsg(msg);
		body.setRespData(respdata);
		reply = JsonUtil.convertObject2Json(body);
		return reply;
	}

	public String toError(String sn) {
		String reply = null;
		RespBody body = new RespBody(sn, RespCode.CODE_500);
		reply = JsonUtil.convertObject2Json(body);
		return reply;
	}
	
	public String toError(String sn,RespCode code) {
		String reply = null;
		RespBody body = new RespBody(sn, code);
		reply = JsonUtil.convertObject2Json(body);
		return reply;
	}

	public String toError(String sn, String msg) {
		String reply = null;
		RespBody body = new RespBody(sn, RespCode.CODE_500);
		body.setMsg(msg);
		reply = JsonUtil.convertObject2Json(body);
		return reply;
	}

	public String toErrorWithLog(String sn, String msg) {
		String reply = null;
		RespBody body = new RespBody(sn, RespCode.CODE_500);
		body.setMsg(msg);
		reply = JsonUtil.convertObject2Json(body);
		LOG.info(reply);
		return reply;
	}
}
