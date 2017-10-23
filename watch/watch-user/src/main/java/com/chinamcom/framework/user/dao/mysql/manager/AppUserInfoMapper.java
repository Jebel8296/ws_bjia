package com.chinamcom.framework.user.dao.mysql.manager;


import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.AppUserInfo;

public interface AppUserInfoMapper {
	
	public AppUserInfo getAppUserInfoByUid(int uid);

	public boolean updateById(AppUserInfo appUserInfo);
	
	public boolean addAppUserInfo(AppUserInfo appUserInfo);
	
	public Integer insertAppUserInfo(AppUserInfo appUserInfo);
	
	public boolean setTarget(Map<String,Object> map);
	
	public List<AppUserInfo> appUserInfoList(Map<String ,Object> map);
	
	public Integer queryTarget(Map<String, Object> map);
	
	public AppUserInfo queryAppUserInfoByUid(int uid);
	
	public AppUserInfo queryFriendinfo(Map<String ,Object> map);
	
	public AppUserInfo findFriendinfo(Map<String ,Object> map);
	
	public Integer countFriend(Map<String ,Object> map);
	
	public boolean updateFriendShow(Map<String ,Object> map);
	
	public List<AppUserInfo> queryAllFriends(Map<String ,Object> map);
}
