package com.chinamcom.framework.user.service.impl;

//import javax.annotation.Resource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.DeviceWearInfo;
import com.chinamcom.framework.user.dao.mysql.manager.DeviceWearInfoMapper;
import com.chinamcom.framework.user.service.DeviceWearInfoService;

@Service
public class DeviceWearInfoServiceImpl implements DeviceWearInfoService{

	@Autowired
	private DeviceWearInfoMapper deviceWearInfoMapper;
	@Override
	public DeviceWearInfo getDeviceWearInfoByImei(Map<String , Object> map) {
		// TODO Auto-generated method stub
		DeviceWearInfo deviceActiveInfo = deviceWearInfoMapper.getDeviceWearInfoByImei(map);
		return deviceActiveInfo;
	}
	@Override
	public boolean saveDeviceWearInfo(DeviceWearInfo deviceWearInfo) {
		// TODO Auto-generated method stub
		boolean flag = deviceWearInfoMapper.saveDeviceWearInfo(deviceWearInfo);
		return flag;
	}
	@Override
	public Integer insertDeviceWearInfo(DeviceWearInfo deviceWearInfo) {
		// TODO Auto-generated method stub
		Integer row = deviceWearInfoMapper.insertDeviceWearInfo(deviceWearInfo);
		if(row>0){
			Integer id = deviceWearInfo.getId();
			return id;
		}else{
			return null;
		}
	}
}
