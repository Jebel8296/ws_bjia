package com.chinamcom.framework.user.service;

import java.util.Map;

import com.chinamcom.framework.user.dao.TargetCount;

public interface TargetCountService {

	//查询当天的目标是否设置了
	public TargetCount queryTargetCount(Map<String , Object> map_para);
	//修改
	public boolean updateTargetCount(Map<String , Object> map_para);
	//新增
	public boolean addTargetCount(Map<String , Object> map_para);
}
