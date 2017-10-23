package com.chinamcom.framework.user.service;

import java.util.Map;
import com.chinamcom.framework.user.dao.SosSetting;

public interface SosSettingService {
	
	//查询急救信息
	public SosSetting getSosSettingList(Map<String,Object> map_para);
	
	//修改急救信息
	public boolean updateSosSetting(SosSetting sosSetting);
	
	//删除急救信息
	public boolean deleteSosSetting(Map<String,Object> map_para);

	//新增急救信息
	public Integer addSosSetting(SosSetting sosSetting);
	
	//查询sos推送电话号
	public SosSetting queryPhone(Map<String,Object> map);
}
