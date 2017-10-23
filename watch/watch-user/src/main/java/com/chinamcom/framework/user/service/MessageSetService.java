package com.chinamcom.framework.user.service;

import java.util.Map;

import com.chinamcom.framework.user.dao.MessageSet;

public interface MessageSetService {
	
	public MessageSet messagesetlist(Map<String , Object> map);
	
	public boolean updateMessageSet(MessageSet messageSet);

}
