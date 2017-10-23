package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.MessageInfo;

public interface MessageInfoMapper {

		public List<MessageInfo> messageInfoList(Map<String, Object> map_para);
		
		public MessageInfo queryMessageInfo(Map<String, Object> map_para);
		
		public boolean deleteMessageInfo(Map<String, Object> map_para);
		
		public boolean batchdelete(Map<String, Object> map_para);
		
}
