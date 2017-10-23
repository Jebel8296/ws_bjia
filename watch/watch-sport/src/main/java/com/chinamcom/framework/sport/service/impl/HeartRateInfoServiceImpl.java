package com.chinamcom.framework.sport.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.sport.dao.HeartRateInfoMapper;
import com.chinamcom.framework.sport.model.HeartRateInfo;
import com.chinamcom.framework.sport.service.HeartRateInfoService;

@Service
public class HeartRateInfoServiceImpl implements HeartRateInfoService{

	@Autowired
	private HeartRateInfoMapper heartRateInfoMapper;

	@Override
	public List<HeartRateInfo> queryTodayHeartRate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<HeartRateInfo> list= heartRateInfoMapper.queryTodayHeartRate(map);
		return list;
	}
	
}
