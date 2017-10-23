package com.chinamcom.framework.sport.service;

import java.util.List;
import java.util.Map;
import com.chinamcom.framework.sport.model.SportInfo;

public interface SportInfoService {

	public int insert(SportInfo sportInfo);
	
	public SportInfo getTodaySportInfo(String imei, Integer sportType);
	
	public List<SportInfo> queryTodaySportInfo(Map<String,Object> map);
	
	public SportInfo countTotal(Map<String,Object> map);
	
	public Integer queryTargetStep(Map<String,Object> map);
	
	public List<SportInfo> sportInfoModel(Map<String,Object> map);
	
	//首页运动排名
	public Integer querySportRank(Map<String,Object> map);
	
	SportInfo queryTotalInfo(Map<String,Object> map);
	
}
