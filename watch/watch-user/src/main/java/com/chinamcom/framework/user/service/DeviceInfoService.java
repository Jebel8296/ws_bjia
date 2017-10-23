package com.chinamcom.framework.user.service;

import java.util.Map;

import com.chinamcom.framework.user.dao.DeviceInfo;

public interface DeviceInfoService {
	
	public boolean imeiValid(Map<String,Object> map);

    DeviceInfo getInfoByImei(String imei);
}
