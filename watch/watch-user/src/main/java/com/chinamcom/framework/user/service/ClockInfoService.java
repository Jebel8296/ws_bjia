package com.chinamcom.framework.user.service;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.ClockInfo;

public interface ClockInfoService {
	//获取闹钟提醒列表
	public List<ClockInfo> getClockInfoList(Map<String, Object> map_para);
	
	//获取闹钟提醒
	public ClockInfo queryClockInfo(Map<String, Object> map_para);
	
	//打开、关闭闹钟提醒
	public boolean openAndCloseClock(Map<String, Object> map_para);
	
	//新增
	public Integer saveClock(ClockInfo clockInfo);
	
	//删除闹钟
	public boolean deleteClock(Map<String, Object> map_para);
	//修改闹钟
	public boolean updateClock(ClockInfo clockInfo);
	//推送闹钟
	public List<ClockInfo> getPushClockInfoList(Map<String, Object> map_para);
	
	//查询久坐提醒是否存在
	public ClockInfo querySitInfo(Map<String, Object> map);
	
	//查询闹钟个数
	public Integer countClock(Map<String, Object> map_para);
	
}
