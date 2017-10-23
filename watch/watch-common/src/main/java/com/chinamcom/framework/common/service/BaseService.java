package com.chinamcom.framework.common.service;

import org.apache.log4j.Logger;

import com.chinamcom.framework.common.response.RespBodyBuilder;

public abstract class BaseService {
	protected RespBodyBuilder respWriter = new RespBodyBuilder();
	protected Logger log = Logger.getLogger(getClass());
}
