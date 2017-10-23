package com.chinamcom.framework.user.service.impl;

//import javax.annotation.Resource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.AppUser;
import com.chinamcom.framework.user.dao.mysql.manager.AppUserMapper;
import com.chinamcom.framework.user.service.LoginService;


@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private AppUserMapper appUserMapper;
	
	@Override
	public AppUser getUserById(int userId) {
		// TODO Auto-generated method stub
		return this.appUserMapper.selectByPrimaryKey(userId);
	}

	@Override
	public Integer addUser(AppUser appUser) {
		// TODO Auto-generated method stub
		Integer row = appUserMapper.addUser(appUser);
		if(row>0){
			Integer uid = appUser.getUid();
			return uid;
		}else{
			return null;
		}
	}

	@Override
	public AppUser getUserByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return appUserMapper.getUserByMap(map);
	}

	@Override
	public AppUser queryByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		AppUser appUser = appUserMapper.queryByMap(map);
		return appUser;
	}

	@Override
	public boolean binding(Map<String, Object> map) {
		// TODO Auto-generated method stub
		boolean flag = appUserMapper.binding(map);
		return flag;
	}

	@Override
	public boolean unbundling(Map<String, Object> map) {
		// TODO Auto-generated method stub
		boolean flag = appUserMapper.unbundling(map);
		return flag;
	}
}
