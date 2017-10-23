package com.chinamcom.framework.user.service;

import java.util.Map;

import com.chinamcom.framework.user.dao.DeviceWearInfo;

public interface DeviceWearInfoService {
	//根据imei号获取佩戴者信息
	public DeviceWearInfo getDeviceWearInfoByImei(Map<String , Object> map);
	
	//修改佩戴者信息
	public boolean saveDeviceWearInfo(DeviceWearInfo deviceWearInfo);
	
	//佩戴者信息插入返回主键
	public Integer insertDeviceWearInfo(DeviceWearInfo deviceWearInfo);

}
