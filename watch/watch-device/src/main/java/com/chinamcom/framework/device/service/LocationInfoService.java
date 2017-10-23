package com.chinamcom.framework.device.service;

import com.chinamcom.framework.device.model.LocationInfo;

public interface LocationInfoService {

	String getadCodeByImei(String imei);
	
	LocationInfo getLocationByImei(String imei);
}
