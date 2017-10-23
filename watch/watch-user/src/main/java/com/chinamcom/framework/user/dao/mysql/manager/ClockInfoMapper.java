package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.ClockInfo;

public interface ClockInfoMapper {
	
	public List<ClockInfo> getClockInfoList(Map<String, Object> map_para);

	public boolean openAndCloseClock(Map<String, Object> map_para);
	
	public Integer saveClock(ClockInfo clockInfo);
		
	public boolean deleteClock(Map<String, Object> map_para);
	
	public boolean updateClock(ClockInfo clockInfo);
	
	public List<ClockInfo> getPushClockInfoList(Map<String, Object> map_para);
	
	public ClockInfo querySitInfo(Map<String, Object> map);
	
	public Integer countClock(Map<String, Object> map_para);
	
	public ClockInfo queryClockInfo(Map<String, Object> map_para);
	
}
