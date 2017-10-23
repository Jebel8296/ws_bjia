package com.chinamcom.framework.user.dao.mysql.manager;


import java.util.Map;

import com.chinamcom.framework.user.dao.DeviceWearInfo;

public interface DeviceWearInfoMapper {

	public DeviceWearInfo getDeviceWearInfoByImei(Map<String , Object> map);

	public boolean saveDeviceWearInfo(DeviceWearInfo deviceActiveInfo);
	
	public Integer insertDeviceWearInfo(DeviceWearInfo deviceActiveInfo);
}
