package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.TargetBaseInfo;

public interface TargetBaseInfoMapper {
	
	public List<TargetBaseInfo> getTargetBaseInfoList(Map<String , Object> map_para);
	
	public List<TargetBaseInfo> getTargetBaseList();
}
