package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.Map;

import com.chinamcom.framework.user.dao.TargetCount;

public interface TargetCountMapper {
	
	public TargetCount queryTargetCount(Map<String , Object> map_para);
	
	public boolean updateTargetCount(Map<String , Object> map_para);
	
	public boolean addTargetCount(TargetCount targetCount);
}
