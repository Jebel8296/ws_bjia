package com.chinamcom.framework.user.service.impl;

import java.util.List;
import java.util.Map;

//import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.TargetBaseInfo;
import com.chinamcom.framework.user.dao.mysql.manager.TargetBaseInfoMapper;
import com.chinamcom.framework.user.service.TargetBaseInfoService;

@Service
public class TargetBaseInfoServiceImpl implements TargetBaseInfoService{

	@Autowired
	private TargetBaseInfoMapper targetBaseInfoMapper;
	
	@Override
	public List<TargetBaseInfo> getTargetBaseInfoList(
			Map<String, Object> map_para) {
		List<TargetBaseInfo> list = targetBaseInfoMapper.getTargetBaseInfoList(map_para);
		return list;
	}

	@Override
	public List<TargetBaseInfo> getTargetBaseList() {
		// TODO Auto-generated method stub
		List<TargetBaseInfo> list = targetBaseInfoMapper.getTargetBaseList();
		return list;
	}

}
