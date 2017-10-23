package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.DeviceInfo;

public interface DeviceInfoMapper {

	public int imeiValid(Map<String,Object> map);
	
	List<DeviceInfo> selectByImei(String imei);
}
