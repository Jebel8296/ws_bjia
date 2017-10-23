package com.chinamcom.framework.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.device.service.UserGroupService;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer showGroupOnWatchByUidAndGid(Integer uid, Integer gid) {
		String sql = "SELECT count(0) FROM app_group_user t WHERE t.uid=? and t.gid=? and t.watchstat=1 and t.status=1 ";
        return jdbcTemplate.queryForObject(sql, new Object[]{uid,gid}, Integer.class);
	}

	@Override
	public Integer selectUserStatusOnGroup(Integer uid, Integer gid) {
		String sql = "SELECT count(1) FROM app_group a "
				+ "LEFT JOIN app_group_user b on a.id = b.gid "
				+ "where a.id = ? and b.uid = ? and a.`status` = 1 and b.`status` = 1 ";
        return jdbcTemplate.queryForObject(sql, new Object[]{gid,uid}, Integer.class);
	}

	@Override
	public Integer selectGroupScreenSatus(Integer uid, Integer gid) {
		String sql = "SELECT screenstat FROM app_group_user t WHERE t.uid=? and t.gid=? and t.status=1 ";
        return jdbcTemplate.queryForObject(sql, new Object[]{gid,uid}, Integer.class);
	}

	@Override
	public String selectGroupName(Integer gid) {
		String sql = "SELECT name FROM app_group a where a.id = ? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{gid}, String.class);
	}

}
