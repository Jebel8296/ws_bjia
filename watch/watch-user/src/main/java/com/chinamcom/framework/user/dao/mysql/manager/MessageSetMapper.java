package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.Map;

import com.chinamcom.framework.user.dao.MessageSet;

public interface MessageSetMapper {
	
	public MessageSet messagesetlist(Map<String , Object> map);
	
	public boolean updateMessageSet(MessageSet messageSet);

}
