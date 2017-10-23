package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.Map;

import com.chinamcom.framework.user.dao.AppUserDevice;

public interface AppUserDeviceMapper {

	public AppUserDevice findOnlineDevice(Map<String , Object> map);
	
	public boolean appUserDeviceModify(Map<String , Object> map);
	
	public boolean appUserDeviceOnline(Map<String , Object> map);
	
	Integer addUserDevice(Map<String , Object> map);
}
