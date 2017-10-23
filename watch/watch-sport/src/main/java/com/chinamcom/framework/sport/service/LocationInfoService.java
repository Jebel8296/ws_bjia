package com.chinamcom.framework.sport.service;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.sport.model.LocationInfo;

public interface LocationInfoService {
	
	public List<LocationInfo> sportInfoPath(Map<String,Object> map);
	
	public List<LocationInfo> locationBreakPoints(Map<String,Object> map); 
	
}
