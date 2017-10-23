package com.chinamcom.framework.user.service;

import java.util.Map;

import com.chinamcom.framework.user.dao.AppUserDevice;

public interface AppUserDeviceService {
	
	public AppUserDevice findOnlineDevice(Map<String , Object> map);
	
	public boolean appUserDeviceModify(Map<String , Object> map);
	
	//根据uid、did,修改设备在线状态
	public boolean appUserDeviceOnline(Map<String , Object> map);
	
	Integer addUserDevice(Map<String, Object> map);

}
