package com.chinamcom.framework.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.device.model.UserDevice;
import com.chinamcom.framework.device.service.UserDeviceService;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public UserDevice getUserDeviceByDid(String did) {
		String sql = "select id,uid,did,status,server,device_type,apple_id,env from app_user_device where did = ? and status = 1";
        List<UserDevice> userDevices = jdbcTemplate.query(sql, new Object[]{did}, (rs, i) -> {
            UserDevice ud = new UserDevice();
            ud.setId(rs.getInt("id"));
            ud.setUid(rs.getInt("uid"));
            ud.setDid(rs.getString("did"));
            ud.setStatus(rs.getInt("status"));
            ud.setServer(rs.getString("server"));
            ud.setDevice_type(rs.getString("device_type"));
            ud.setApple_id(rs.getString("apple_id"));
            ud.setEnv(rs.getString("env"));
            return ud;
        });
        if(userDevices != null && userDevices.size() > 0){
        	return userDevices.get(0);
        }
		return null;
	}

	@Override
	public List<UserDevice> getUserDeviceByStatus(String deviceType) {
		String sql = "select * from app_user_device where status = 1 and device_type = ?";
        List<UserDevice> userDevices = jdbcTemplate.query(sql, new Object[]{deviceType}, (rs, i) -> {
            UserDevice ud = new UserDevice();
            ud.setId(rs.getInt("id"));
            ud.setUid(rs.getInt("uid"));
            ud.setDid(rs.getString("did"));
            ud.setStatus(rs.getInt("status"));
            ud.setServer(rs.getString("server"));
            ud.setDevice_type(rs.getString("device_type"));
            ud.setApple_id(rs.getString("apple_id"));
            ud.setEnv(rs.getString("env"));
            return ud;
        });
		return userDevices;
	}

	@Override
	public UserDevice getUserDeviceByUid(Integer uid, String deviceType) {
		String sql = "select * from app_user_device where uid = ? and status = 1 and device_type = ?";
        List<UserDevice> userDevices = jdbcTemplate.query(sql, new Object[]{uid,deviceType}, (rs, i) -> {
            UserDevice ud = new UserDevice();
            ud.setId(rs.getInt("id"));
            ud.setUid(rs.getInt("uid"));
            ud.setDid(rs.getString("did"));
            ud.setStatus(rs.getInt("status"));
            ud.setServer(rs.getString("server"));
            ud.setDevice_type(rs.getString("device_type"));
            ud.setApple_id(rs.getString("apple_id"));
            return ud;
        });
        if(userDevices != null && userDevices.size() > 0){
        	return userDevices.get(0);
        }
		return null;
	}

	@Override
	public int delUserDevice(Integer uid, String did) {
		String sql = "delete from app_user_device where uid = ? and did = ?";
		return jdbcTemplate.update(sql, new Object[]{uid,did});
	}

	@Override
	public List<UserDevice> getUserDeviceExcludeWatchByUid(Integer uid, String did) {
		String sql = null;
		Object[] params = null;
		if(StringUtil.isNotEmpty(did)){
			sql = "select uid,did,server,device_type,apple_id,env from app_user_device where uid = ? and did != ? and status = 1 and device_type != 'watch'";
			params = new Object[]{uid, did};
		}else{
			sql = "select uid,did,server,device_type,apple_id,env from app_user_device where uid = ? and status = 1 and device_type != 'watch'";
			params = new Object[]{uid};
		}
        List<UserDevice> userDevices = jdbcTemplate.query(sql, params, (rs, i) -> {
            UserDevice ud = new UserDevice();
            ud.setUid(rs.getInt("uid"));
            ud.setDid(rs.getString("did"));
            ud.setServer(rs.getString("server"));
            ud.setDevice_type(rs.getString("device_type"));
            ud.setApple_id(rs.getString("apple_id"));
            ud.setEnv(rs.getString("env"));
            return ud;
        });
        return userDevices;
	}

	@Override
	public int getUserDeviceStatus(Integer uid, String did) {
		String sql = "select status from app_user_device where uid = ? and did = ? and status = 1";
        List<UserDevice> userDevices = jdbcTemplate.query(sql, new Object[]{uid,did}, (rs, i) -> {
            UserDevice ud = new UserDevice();
            ud.setStatus(rs.getInt("status"));
            return ud;
        });
        if(userDevices != null && userDevices.size() > 0){
        	return userDevices.get(0).getStatus();
        }
		return 0;
	}

}
