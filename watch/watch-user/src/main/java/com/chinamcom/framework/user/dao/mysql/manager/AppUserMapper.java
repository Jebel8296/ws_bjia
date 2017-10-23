package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.Map;

import com.chinamcom.framework.user.dao.AppUser;

public interface AppUserMapper {
	public AppUser selectByPrimaryKey(int userId);
	
	public Integer addUser(AppUser appUser);
	
	public AppUser getUserByMap(Map<String , Object> map);
	
	public AppUser queryByMap(Map<String , Object> map);
	
	public boolean binding(Map<String , Object> map);
	
	public boolean unbundling(Map<String , Object> map);
	
}
  