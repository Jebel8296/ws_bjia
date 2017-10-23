package com.chinamcom.framework.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.AppMessageSend;
import com.chinamcom.framework.user.dao.mysql.manager.AppMessageSendMapper;
import com.chinamcom.framework.user.service.AppMessageSendService;

@Service
public class AppMessageSendServiceImpl implements AppMessageSendService{

	@Autowired
	private AppMessageSendMapper appMessageSendMapper;
	
	
	@Override
	public List<AppMessageSend> sysMessageList(Map<String, Object> map) {
		List<AppMessageSend> list =  appMessageSendMapper.sysMessageList(map);
		return list;
	}


	@Override
	public boolean deleteSysMessage(Map<String, Object> map) {
		boolean flag = appMessageSendMapper.deleteSysMessage(map);
		return flag;
	}

}
