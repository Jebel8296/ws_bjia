package com.chinamcom.framework.sport.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.sport.dao.SportInfoTotalMapper;
import com.chinamcom.framework.sport.model.SportInfoTotal;
import com.chinamcom.framework.sport.model.SportSummary;
import com.chinamcom.framework.sport.service.SportInfoTotalService;

@Service
public class SportInfoTotalServiceImpl implements SportInfoTotalService{

	@Autowired
	private SportInfoTotalMapper sportInfoTotalMapper;
	
	@Override
	public List<SportInfoTotal> queryTodaySportTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportInfoTotal> list = sportInfoTotalMapper.queryTodaySportTotal(map);
		return list;
	}

	@Override
	public SportInfoTotal countTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		SportInfoTotal sportInfoTotal = sportInfoTotalMapper.countTotal(map);
		return sportInfoTotal;
	}

	@Override
	public Integer querySportRank(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer rownum = sportInfoTotalMapper.querySportRank(map);
		return rownum==null?0:rownum;
	}

	@Override
	public boolean checkImie(Map<String, Object> map) {
		// TODO Auto-generated method stub
		int rownum =sportInfoTotalMapper.checkImie(map); 
		return rownum>0;
	}
	
	@Override
	public List<SportSummary> delaySportInfoTotal(Map<String, Object> map) {
		
		List<SportSummary> list = sportInfoTotalMapper.delaySportInfoTotal(map);
		
		return list;
	}
	

}
