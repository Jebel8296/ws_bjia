package com.chinamcom.framework.sport.dao;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.sport.model.SportInfoTotal;
import com.chinamcom.framework.sport.model.SportSummary;

public interface SportInfoTotalMapper {
	
	public List<SportInfoTotal> queryTodaySportTotal(Map<String , Object> map);
	
	public SportInfoTotal countTotal(Map<String , Object> map);
	
	//首页运动排名
	public Integer querySportRank(Map<String,Object> map);
	
	public int checkImie(Map<String,Object> map);
	
	public List<SportSummary> delaySportInfoTotal(Map<String ,Object> map);
	
}
