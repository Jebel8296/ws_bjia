package com.chinamcom.framework.sport.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.sport.dao.SportHealthWeeklyMapper;
import com.chinamcom.framework.sport.model.SportHealthWeekly;
import com.chinamcom.framework.sport.service.SportHealthWeeklyService;

@Service
public class SportHealthWeeklyServiceImpl implements SportHealthWeeklyService{

	@Autowired
	private SportHealthWeeklyMapper mapper;
	
	@Override
	public List<SportHealthWeekly> weeklyHealthSportSummary(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportHealthWeekly> list = mapper.weeklyHealthSportSummary(map);
		return list;
	}

	@Override
	public boolean batchInsert(List<SportHealthWeekly> list) {
		// TODO Auto-generated method stub
		boolean flag = mapper.batchInsert(list);
		return flag;
	}

	@Override
	public List<SportHealthWeekly> queryRank(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportHealthWeekly> list = mapper.queryRank(map);
		return list;
	}

	@Override
	public Integer querySexRank(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer sexRank = mapper.querySexRank(map);
		return sexRank;
	}

}
