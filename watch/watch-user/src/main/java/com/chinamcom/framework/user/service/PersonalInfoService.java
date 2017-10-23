package com.chinamcom.framework.user.service;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.AppUserInfo;

public interface PersonalInfoService {
	//根据uid查询个人资料
	public AppUserInfo getAppUserInfoByUid(int uid);
	
	//根据id修改个人资料
	public boolean updateById(AppUserInfo appUserInfo);
	
	//登陆新增头像、昵称、uid
//	public boolean addAppUserInfo(AppUserInfo appUserInfo);
	
	//登陆新增头像、昵称、uid
	public Integer insertAppUserInfo(AppUserInfo appUserInfo);
	
	//设置目标
	public boolean settarget(Map<String ,Object> map);
	//用於好友列表查詢好友信息
	public List<AppUserInfo> appUserInfoList(Map<String ,Object> map);
	
	//查询好友关系以及好友用户信息
	public AppUserInfo queryFriendinfo(Map<String ,Object> map);
	
	public AppUserInfo findFriendinfo(Map<String ,Object> map);
	
	//查询目标
	public Integer queryTarget(Map<String ,Object> map);
	
	//根据uid查询个人资料(充值用)
	public AppUserInfo queryAppUserInfoByUid(int uid);
	
	//查询显示的好友是否超过5个
	public boolean countFriend(Map<String ,Object> map);
	//修改好友显示状态
	public boolean updateFriendShow(Map<String ,Object> map);
	
	public List<AppUserInfo> queryAllFriends(Map<String ,Object> map);
}
