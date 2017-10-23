package com.chinamcom.framework.user.services;

import io.vertx.core.json.JsonObject;

/**
 * 地区维护Service
 * 
 * @author xuxg
 * @since 20161009
 *
 */
public interface IRegionService {
	/**
	 * 地区查询
	 * 
	 * @param param
	 * @param reply
	 * @throws Exception
	 */
	public String select(String sn, JsonObject param) throws Exception;

	
}
