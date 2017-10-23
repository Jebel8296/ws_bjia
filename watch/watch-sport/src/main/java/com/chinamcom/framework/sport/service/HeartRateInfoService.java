package com.chinamcom.framework.sport.service;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.sport.model.HeartRateInfo;

public interface HeartRateInfoService {
	//查当天所有心率，最多35条
	public List<HeartRateInfo> queryTodayHeartRate(Map<String,Object> map);
	
	

}
