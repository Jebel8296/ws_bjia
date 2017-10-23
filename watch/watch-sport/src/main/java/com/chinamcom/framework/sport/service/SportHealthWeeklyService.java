package com.chinamcom.framework.sport.service;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.sport.model.SportHealthWeekly;

public interface SportHealthWeeklyService {
	
	public List<SportHealthWeekly> weeklyHealthSportSummary(Map<String,Object> map);
	
	public boolean batchInsert(List<SportHealthWeekly> list);
	
	public List<SportHealthWeekly> queryRank(Map<String,Object> map);
	public Integer querySexRank(Map<String,Object> map);
	

}
