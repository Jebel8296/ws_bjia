package com.chinamcom.framework.sport.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.sport.dao.LocationInfoMapper;
import com.chinamcom.framework.sport.model.LocationInfo;
import com.chinamcom.framework.sport.service.LocationInfoService;


@Service
public class LocationInfoServiceImpl implements LocationInfoService{

	@Autowired
	private LocationInfoMapper locationInfoMapper;
	
	@Override
	public List<LocationInfo> sportInfoPath(Map<String, Object> map) {
		
		List<LocationInfo> list = locationInfoMapper.sportInfoPath(map);
		return list;
	}

	@Override
	public List<LocationInfo> locationBreakPoints(Map<String, Object> map) {
		
		List<LocationInfo> list = locationInfoMapper.locationBreakPoints(map);
		return list;
	}

}
