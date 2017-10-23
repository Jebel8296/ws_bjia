package com.chinamcom.framework.sociality.service;

import java.util.List;

import com.chinamcom.framework.sociality.model.CallInfo;

public interface CallInfoService {
	
	/**
	 * 根据IMEI获取通话记录
	 * @param imei
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<CallInfo> getList(String imei, String type, Integer pageNo,  Integer pageSize);
	
	
	int delete(Integer id);
	
	public int deleteByids(String ids) ;
	
	List<CallInfo> selectByids(String ids);
}
