package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.AppMessageSend;

public interface AppMessageSendMapper {
	
	public List<AppMessageSend> sysMessageList(Map<String,Object> map);
	
	public boolean deleteSysMessage(Map<String,Object> map);

}
