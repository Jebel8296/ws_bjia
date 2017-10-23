package com.chinamcom.framework.sport.dao;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.sport.model.LocationInfo;

public interface LocationInfoMapper {
	
	public List<LocationInfo> sportInfoPath(Map<String,Object> map);
	
	public List<LocationInfo> locationBreakPoints(Map<String,Object> map); 

}
