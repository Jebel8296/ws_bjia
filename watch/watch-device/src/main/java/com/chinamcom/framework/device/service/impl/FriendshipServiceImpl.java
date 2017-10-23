package com.chinamcom.framework.device.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.device.service.FriendshipService;

@Service
public class FriendshipServiceImpl implements FriendshipService {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Integer> addFriend(String imei, String imeif) {
		String sql = "SELECT MAX(UID) FROM app_user t WHERE t.IMEI = ? ";
        Integer uid = jdbcTemplate.queryForObject(sql, new Object[]{imei}, Integer.class);
//        if(uid == null){
//        	String _sql01 = "insert into app_user(IMEI,STATUS) values(?,?)";
//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            jdbcTemplate.update(connection -> {
//                PreparedStatement ps = connection.prepareStatement(_sql01, Statement.RETURN_GENERATED_KEYS);
//                ps.setObject(1, imei);
//                ps.setObject(2, 1);
//                return ps;
//            }, keyHolder);
//            uid = keyHolder.getKey().intValue();
//        }
        
        Integer uidf = jdbcTemplate.queryForObject(sql, new Object[]{imeif}, Integer.class);
//        if(uidf == null){
//        	String _sql01 = "insert into app_user(IMEI,STATUS) values(?,?)";
//            KeyHolder keyHolder = new GeneratedKeyHolder();
//            jdbcTemplate.update(connection -> {
//                PreparedStatement ps = connection.prepareStatement(_sql01, Statement.RETURN_GENERATED_KEYS);
//                ps.setObject(1, imeif);
//                ps.setObject(2, 1);
//                return ps;
//            }, keyHolder);
//            uidf = keyHolder.getKey().intValue();
//        }
        List<Integer> userList = new ArrayList<Integer>();
        userList.add(uid);
        userList.add(uidf);
        return userList;
	}

	@Override
	public String getUserName(Integer uid, Integer fromUid) {
		String sql = "SELECT IFNULL(b.ALIAS_0,a.nickname) gname FROM app_userinfo a  LEFT JOIN app_friendship b on a.uid = b.UID_1 AND b.UID_0 = ? AND b.UID_1 = ? where a.uid = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{uid, fromUid, uid}, String.class);
	}

	@Override
	public boolean isFriend(Integer uid, Integer to) {
		String sql = "SELECT COUNT(1) FROM app_friendship a where ((a.UID_0 = ? and a.UID_1 = ?)  OR  (a.UID_0 = ? and a.UID_1 = ?)) and `STATUS` = 1";
		return jdbcTemplate.queryForObject(sql, new Object[]{uid, to, to, uid}, Integer.class) == 2 ? true : false;
	}

	@Override
	public Integer isShowWatch(Integer uid, Integer to) {
		String sql = "SELECT watchstat FROM app_friendship a where a.UID_0 = ? and a.UID_1 = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{uid, to}, Integer.class);
	}

}
