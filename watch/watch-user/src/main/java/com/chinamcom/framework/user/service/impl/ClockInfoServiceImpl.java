package com.chinamcom.framework.user.service.impl;

import java.util.List;
import java.util.Map;





//import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.ClockInfo;
import com.chinamcom.framework.user.dao.mysql.manager.ClockInfoMapper;
import com.chinamcom.framework.user.service.ClockInfoService;

@Service
public class ClockInfoServiceImpl implements ClockInfoService{
	
	@Autowired
	private ClockInfoMapper clockInfoMapper;

	@Override
	public List<ClockInfo> getClockInfoList(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		List<ClockInfo> list = clockInfoMapper.getClockInfoList(map_para);
		return list;
	}

	@Override
	public boolean openAndCloseClock(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		boolean flag = clockInfoMapper.openAndCloseClock(map_para);
		return flag;
	}

	@Override
	public Integer saveClock(ClockInfo clockInfo) {
		// TODO Auto-generated method stub
		Integer row =  clockInfoMapper.saveClock(clockInfo);
		if(row>0){
			Integer id = clockInfo.getId();
			return id;
		}else{
			return null;
		}
	}
	
	@Override
	public boolean deleteClock(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		boolean flag = clockInfoMapper.deleteClock(map_para);
		return flag;
	}

	@Override
	public boolean updateClock(ClockInfo clockInfo) {
		// TODO Auto-generated method stub
		boolean flag = clockInfoMapper.updateClock(clockInfo);
		return flag;
	}

	@Override
	public List<ClockInfo> getPushClockInfoList(Map<String, Object> map_para) {
		List<ClockInfo> list = clockInfoMapper.getPushClockInfoList(map_para);
		return list;
	}

	@Override
	public ClockInfo querySitInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		ClockInfo clockInfo = clockInfoMapper.querySitInfo(map);
		return clockInfo;
	}

	@Override
	public Integer countClock(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		Integer count = clockInfoMapper.countClock(map_para);
		return count;
	}

	@Override
	public ClockInfo queryClockInfo(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		ClockInfo clockInfo = clockInfoMapper.queryClockInfo(map_para);
		return clockInfo;
	}


}
