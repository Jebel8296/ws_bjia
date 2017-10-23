package com.chinamcom.framework.sport.service;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.sport.model.SportInfoTotal;
import com.chinamcom.framework.sport.model.SportSummary;

public interface SportInfoTotalService {
	
	//全量表当天运动数据查询
	public List<SportInfoTotal> queryTodaySportTotal(Map<String , Object> map);
	
	public SportInfoTotal countTotal(Map<String , Object> map);
	
	//首页运动排名
	public Integer querySportRank(Map<String,Object> map);
	
	//校验uid与imei是否存在绑定关系
	public boolean checkImie(Map<String,Object> map);
	
	//运动（全量表）
	public List<SportSummary> delaySportInfoTotal(Map<String ,Object> map);

}
