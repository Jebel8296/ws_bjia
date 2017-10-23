package com.chinamcom.framework.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.AppUserDevice;
import com.chinamcom.framework.user.dao.mysql.manager.AppUserDeviceMapper;
import com.chinamcom.framework.user.service.AppUserDeviceService;

@Service
public class AppUserDeviceServiceImpl implements AppUserDeviceService{
	
	@Autowired
	private AppUserDeviceMapper appUserDeviceMapper;
	
	@Override
	public boolean appUserDeviceModify(Map<String, Object> map) {
		boolean flag = appUserDeviceMapper.appUserDeviceModify(map);
		return flag;
	}

	@Override
	public AppUserDevice findOnlineDevice(Map<String, Object> map) {
		AppUserDevice appUserDevice = appUserDeviceMapper.findOnlineDevice(map);
		return appUserDevice;
	}

	@Override
	public boolean appUserDeviceOnline(Map<String, Object> map) {
		// TODO Auto-generated method stub
		boolean flag = appUserDeviceMapper.appUserDeviceOnline(map);
		return flag;
	}

	@Override
	public Integer addUserDevice(Map<String, Object> map) {
		return appUserDeviceMapper.addUserDevice(map);
	}
}
