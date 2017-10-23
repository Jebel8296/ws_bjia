package com.chinamcom.framework.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.TargetCount;
import com.chinamcom.framework.user.dao.mysql.manager.TargetCountMapper;
import com.chinamcom.framework.user.service.TargetCountService;

@Service
public class TargetCountServiceImpl implements TargetCountService{
	
	@Autowired
	private TargetCountMapper targetCountMapper;
	@Override
	public TargetCount queryTargetCount(Map<String, Object> map_para) {
		
		return targetCountMapper.queryTargetCount(map_para);
	}

	@Override
	public boolean updateTargetCount(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		boolean flag = targetCountMapper.updateTargetCount(map_para);
		return flag;
	}

	@Override
	public boolean addTargetCount(Map<String, Object> map_para) {
		TargetCount targetCount = new TargetCount();
//		targetCount.setUid((Integer)map_para.get("uid"));//根据imei统计设置目标
		targetCount.setTarget((Integer)map_para.get("target"));
		targetCount.setImei((String)map_para.get("imei"));
		boolean flag = targetCountMapper.addTargetCount(targetCount);
		return flag;
	}

}
