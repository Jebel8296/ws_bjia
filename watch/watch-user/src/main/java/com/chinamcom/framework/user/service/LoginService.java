package com.chinamcom.framework.user.service;

import java.util.Map;

import com.chinamcom.framework.user.dao.AppUser;


public interface LoginService {
	public AppUser getUserById(int userId);

	public Integer addUser(AppUser appUser);
	
	public AppUser getUserByMap(Map<String , Object> map);
	
	public AppUser queryByMap(Map<String , Object> map);
	
	public boolean binding(Map<String , Object> map);
	
	public boolean unbundling(Map<String , Object> map);
	
}
