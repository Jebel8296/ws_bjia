package com.chinamcom.framework.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.DeviceInfo;
import com.chinamcom.framework.user.dao.mysql.manager.DeviceInfoMapper;
import com.chinamcom.framework.user.service.DeviceInfoService;

@Service
public class DeviceInfoServiceImpl implements DeviceInfoService{

	@Autowired
	private DeviceInfoMapper deviceInfoMapper;
	
	@Override
	public boolean imeiValid(Map<String, Object> map) {
		int count = deviceInfoMapper.imeiValid(map);
		return count>0;
	}

	@Override
	public DeviceInfo getInfoByImei(String imei) {
		List<DeviceInfo> list = deviceInfoMapper.selectByImei(imei);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
