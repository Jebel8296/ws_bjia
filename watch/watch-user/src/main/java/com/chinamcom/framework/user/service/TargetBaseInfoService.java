package com.chinamcom.framework.user.service;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.TargetBaseInfo;

public interface TargetBaseInfoService {
	//查询目标设置list
	public List<TargetBaseInfo> getTargetBaseInfoList(Map<String , Object> map_para);
	
	public List<TargetBaseInfo> getTargetBaseList();
}
