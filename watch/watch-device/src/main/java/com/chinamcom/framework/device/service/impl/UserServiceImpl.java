package com.chinamcom.framework.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.device.model.ClockInfo;
import com.chinamcom.framework.device.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public Integer getUserIdByImei(String imei) {
		String sql = "SELECT UID FROM app_user t WHERE t.imei = ? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{imei}, Integer.class);
	}


	@Override
	public String getSosMessageByUid(Integer uid) {
		String sql = "SELECT sms_content FROM sos_setting t WHERE t.user_id = ? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{uid}, String.class);
	}


	@Override
	public ClockInfo getClockInfoById(Integer id) {
		String sql = "select id, voice_body from clock_info where id = ?";
        List<ClockInfo> userClocks = jdbcTemplate.query(sql, new Object[]{id}, (rs, i) -> {
        	ClockInfo ci = new ClockInfo();
            ci.setId(rs.getInt("id"));
            ci.setVoiceBody(rs.getString("voice_body"));
            return ci;
        });
        if(userClocks != null && userClocks.size() > 0){
        	return userClocks.get(0);
        }
		return null;
	}


	@Override
	public Integer getUserTarget(Integer uid) {
		String sql = "SELECT target FROM app_userinfo t WHERE t.uid = ? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{uid}, Integer.class);
	}

}
