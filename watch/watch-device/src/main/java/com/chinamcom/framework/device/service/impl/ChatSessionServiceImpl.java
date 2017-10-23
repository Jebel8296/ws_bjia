package com.chinamcom.framework.device.service.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.common.constants.Constants;
import com.chinamcom.framework.device.service.ChatSessionService;

@Service
public class ChatSessionServiceImpl implements ChatSessionService {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer addChatSession(Integer fromUid, Integer to) {
		if(to < Constants.GROUP_ID){
			String _sql01 = "insert into chat_session(from_uid,to_id) values(?,?)";
	        KeyHolder keyHolder = new GeneratedKeyHolder();
	        jdbcTemplate.update(connection -> {
	            PreparedStatement ps = connection.prepareStatement(_sql01, Statement.RETURN_GENERATED_KEYS);
	            ps.setObject(1, fromUid);
	            ps.setObject(2, to);
	            return ps;
	        }, keyHolder);
	        Integer sid = keyHolder.getKey().intValue();
	        String uid0Sql = "insert into chat_session_user(sid,uid,type) values(?,?,0)";
	        String uid1Sql = "insert into chat_session_user(sid,uid,type) values(?,?,0)";
	        jdbcTemplate.update(uid0Sql, new Object[]{sid, fromUid});
	        jdbcTemplate.update(uid1Sql, new Object[]{sid, to});
	        return sid;
        }else{
        	String sql = "insert into chat_session_user select null, 1, t1.gid as sid, t1.uid, 1, 0, 0, null"
        			+ " from app_group_user t1, app_group t2 "
        			+ " where t1.gid = t2.id AND t1.gid = ? and t1.status = 1 and t2.status = 1";
        	jdbcTemplate.update(sql,new Object[]{to});
        	return to;
        }
	}

	@Override
	public boolean chatSessionExist(Integer fromUid, Integer to) {
		int count = 0;
		if(to < Constants.GROUP_ID){
			String sql = "SELECT count(1) FROM chat_session t WHERE (t.from_uid = ? and t.to_id = ?) or (t.from_uid = ? and t.to_id = ?)";
	        count = jdbcTemplate.queryForObject(sql, new Object[]{fromUid, to, to, fromUid}, Integer.class);
		}else{
			String sql = "SELECT count(1) FROM chat_session_user t WHERE t.uid = ? and t.sid = ?";
	        count = jdbcTemplate.queryForObject(sql, new Object[]{fromUid,to}, Integer.class);
		}
		return count > 0;
	}

	@Override
	public Integer updateChatSessionStatus(Integer to) {
		String sql = "update chat_session_user set status = 1 where sid = ? and status = 0";
		return jdbcTemplate.update(sql,new Object[]{to});
	}

	@Override
	public Integer getChatSessionId(Integer uid, Integer to) {
		if(to < Constants.GROUP_ID){
			String sql = "SELECT id  FROM chat_session t WHERE (t.from_uid = ? and t.to_id = ?) or (t.from_uid = ? and t.to_id = ?)";
			return jdbcTemplate.queryForObject(sql, new Object[]{uid,to,to,uid}, Integer.class);
		}else{
			return to;
		}
	}

	@Override
	public Integer getChatSessionScreenStatus(Integer uid, Integer id) {
		String sql = "SELECT screenstat  FROM chat_session_user t WHERE t.sid = ? and t.uid = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{id,uid}, Integer.class);
	}

}
