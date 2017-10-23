package com.chinamcom.framework.common.exception;

import com.chinamcom.framework.common.response.RespCode;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	private RespCode respCode;

	public RespCode getRespCode() {
		return respCode;
	}

	public void setRespCode(RespCode respCode) {
		this.respCode = respCode;
	}

	public ServiceException(RespCode respCode) {
		super();
		this.respCode = respCode;
	}
	
	public ServiceException(String message, RespCode respCode) {
		super(message);
		this.respCode = respCode;
	}
	
	public ServiceException(String message, RespCode respCode, Throwable cause) {
		super(message, cause);
		this.respCode = respCode;
	}
}
