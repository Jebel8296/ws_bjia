package com.chinamcom.framework.user.service;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.MessageInfo;

public interface MessageInfoService {
	
	//查询消息列表
	public List<MessageInfo> messageInfoList(Map<String, Object> map_para);
	
	//查询消息详情
	public MessageInfo queryMessageInfo(Map<String, Object> map_para);
	
	//删除消息
	public boolean deleteMessageInfo(Map<String, Object> map_para);
	
	//批量删除消息
	public boolean batchdelete(Map<String, Object> map_para);

	
}
