package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.Map;

import com.chinamcom.framework.user.dao.SosSetting;

public interface SosSettingMapper {
	
	public SosSetting getSosSettingList(Map<String,Object> map_para);
		
	public boolean updateSosSetting(SosSetting sosSetting);
		
	public boolean deleteSosSetting(Map<String,Object> map_para);

	public Integer addSosSetting(SosSetting sosSetting);
	
	public SosSetting queryPhone(Map<String,Object> map);
}
