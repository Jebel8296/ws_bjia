package com.chinamcom.framework.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.device.model.MessageInfo;
import com.chinamcom.framework.device.service.MessageInfoService;

@Service
public class MessageInfoServiceImpl implements MessageInfoService{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getDidByMessageId(Integer mid) {
		String sql = "select device_id from app_message_send where id = ?";
		List<String> didList = jdbcTemplate.query(sql, new Object[]{mid}, (rs, i) -> {
            return rs.getString("device_id");
        });
		if(didList != null && didList.size() > 0){
			return didList.get(0);
		}
		return null;
	}

	@Override
	public int addMessage(MessageInfo messageInfo) {
		String sql = " insert into message_info(uid,type,title,content,status,remark) values (?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, 
				messageInfo.getUid(), 
				messageInfo.getType(), 
				messageInfo.getTitle(), 
				messageInfo.getContent(),
				messageInfo.getStatus(),
				messageInfo.getRemark());
	}

}
