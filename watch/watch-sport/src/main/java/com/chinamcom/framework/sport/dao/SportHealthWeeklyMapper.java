package com.chinamcom.framework.sport.dao;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.sport.model.SportHealthWeekly;

public interface SportHealthWeeklyMapper {
	
	public List<SportHealthWeekly> weeklyHealthSportSummary(Map<String,Object> map);
	
	public boolean batchInsert(List<SportHealthWeekly> list);
	
	public List<SportHealthWeekly> queryRank(Map<String,Object> map);
	public Integer querySexRank(Map<String,Object> map);
}
