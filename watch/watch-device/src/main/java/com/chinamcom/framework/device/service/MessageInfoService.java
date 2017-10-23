package com.chinamcom.framework.device.service;

import com.chinamcom.framework.device.model.MessageInfo;

public interface MessageInfoService {
	String getDidByMessageId(Integer mid);
	
	int addMessage(MessageInfo messageInfo);
}
