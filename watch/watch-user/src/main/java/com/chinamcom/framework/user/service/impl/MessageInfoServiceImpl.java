package com.chinamcom.framework.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.MessageInfo;
import com.chinamcom.framework.user.dao.mysql.manager.MessageInfoMapper;
import com.chinamcom.framework.user.service.MessageInfoService;

@Service
public class MessageInfoServiceImpl implements MessageInfoService{
	
	@Autowired
	private MessageInfoMapper messageInfoMapper;

	@Override
	public List<MessageInfo> messageInfoList(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		List<MessageInfo> list = messageInfoMapper.messageInfoList(map_para);
		return list;
	}

	@Override
	public MessageInfo queryMessageInfo(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		MessageInfo messageInfo = messageInfoMapper.queryMessageInfo(map_para);
		return messageInfo;
	}

	@Override
	public boolean deleteMessageInfo(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		boolean flag = messageInfoMapper.deleteMessageInfo(map_para);
		return flag;
	}

	@Override
	public boolean batchdelete(Map<String, Object> map_para) {
		// TODO Auto-generated method stub
		boolean flag = messageInfoMapper.batchdelete(map_para);
		return flag;
	}

}
