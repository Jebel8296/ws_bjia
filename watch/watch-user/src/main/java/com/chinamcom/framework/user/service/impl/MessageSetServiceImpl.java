package com.chinamcom.framework.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.MessageSet;
import com.chinamcom.framework.user.dao.mysql.manager.MessageSetMapper;
import com.chinamcom.framework.user.service.MessageSetService;

@Service
public class MessageSetServiceImpl implements MessageSetService{

	@Autowired
	private MessageSetMapper messageSetMapper;
	
	@Override
	public MessageSet messagesetlist(Map<String, Object> map) {
		
		MessageSet messageSet = messageSetMapper.messagesetlist(map);
		
		return messageSet;
	}

	@Override
	public boolean updateMessageSet(MessageSet messageSet) {
		// TODO Auto-generated method stub
		boolean flag = messageSetMapper.updateMessageSet(messageSet);
		return flag;
	}

}
