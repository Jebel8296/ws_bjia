package com.chinamcom.framework.user.service.impl;

import java.util.Map;

//import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.SosSetting;
import com.chinamcom.framework.user.dao.mysql.manager.SosSettingMapper;
import com.chinamcom.framework.user.service.SosSettingService;

@Service
public class SosSettingServiceImpl implements SosSettingService{

	@Autowired
	private SosSettingMapper sosSettingMapper;
	
	@Override
	public SosSetting getSosSettingList(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		SosSetting sosSetting = sosSettingMapper.getSosSettingList(map_para);
		return sosSetting;
	}

	@Override
	public boolean updateSosSetting(SosSetting sosSetting) {
		// TODO Auto-generated method stub
		boolean flag =sosSettingMapper.updateSosSetting(sosSetting);
		return flag;
	}

	@Override
	public boolean deleteSosSetting(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		boolean flag = sosSettingMapper.deleteSosSetting(map_para);
		return flag;
	}

	@Override
	public Integer addSosSetting(SosSetting sosSetting) {
		// TODO Auto-generated method stub
		Integer row = sosSettingMapper.addSosSetting(sosSetting);
		if(row>0){
			Integer id = sosSetting.getId();
			return id;
		}else{
			return null;
		}
	}

	@Override
	public SosSetting queryPhone(Map<String,Object> map) {
		SosSetting sosPhone= sosSettingMapper.queryPhone(map);
		return sosPhone;
	}

}
