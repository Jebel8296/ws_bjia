package com.chinamcom.framework.device.service;

public interface ChatSessionService {

	Integer addChatSession(Integer fromUid, Integer to);
	
	Integer getChatSessionId(Integer uid, Integer to);
	
	boolean chatSessionExist(Integer fromUid, Integer to);
	
	Integer updateChatSessionStatus(Integer to);
	
	Integer getChatSessionScreenStatus(Integer uid, Integer id);
}
