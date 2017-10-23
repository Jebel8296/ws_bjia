package com.chinamcom.framework.sport.dao;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.sport.model.HeartRateInfo;

public interface HeartRateInfoMapper {
	
	public List<HeartRateInfo> queryTodayHeartRate(Map<String,Object> map);

}
