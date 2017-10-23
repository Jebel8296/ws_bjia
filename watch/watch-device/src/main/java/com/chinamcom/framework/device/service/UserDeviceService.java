package com.chinamcom.framework.device.service;

import java.util.List;

import com.chinamcom.framework.device.model.UserDevice;

public interface UserDeviceService {
	
	UserDevice getUserDeviceByDid(String did);
	
	List<UserDevice> getUserDeviceByStatus(String deviceType);
	
	UserDevice getUserDeviceByUid(Integer uid, String deviceType);
	
	int delUserDevice(Integer uid, String did);
	
	List<UserDevice> getUserDeviceExcludeWatchByUid(Integer uid, String did);
	
	int getUserDeviceStatus(Integer uid, String did);
}
