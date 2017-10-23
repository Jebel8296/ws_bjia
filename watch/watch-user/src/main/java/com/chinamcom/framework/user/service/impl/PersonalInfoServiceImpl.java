package com.chinamcom.framework.user.service.impl;

//import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.AppUserInfo;
import com.chinamcom.framework.user.dao.mysql.manager.AppUserInfoMapper;
import com.chinamcom.framework.user.service.PersonalInfoService;

@Service
public class PersonalInfoServiceImpl implements PersonalInfoService{
	
	@Autowired
	private AppUserInfoMapper appUserInfoMapper;
	
	@Override
	public AppUserInfo getAppUserInfoByUid(int uid) {
		
		return this.appUserInfoMapper.getAppUserInfoByUid(uid);
	}

	@Override
	public boolean updateById(AppUserInfo appUserInfo) {
		
		return appUserInfoMapper.updateById(appUserInfo);
	}

	/*@Override
	public boolean addAppUserInfo(AppUserInfo appUserInfo) {
		
		return appUserInfoMapper.addAppUserInfo(appUserInfo);
	}*/

	@Override
	public Integer insertAppUserInfo(AppUserInfo appUserInfo) {
		int row = appUserInfoMapper.insertAppUserInfo(appUserInfo);
		if(row>0){
			Integer id = appUserInfo.getId();
			return id;
		}else{
			return null;
		}
	}
	@Override
	public boolean settarget(Map<String, Object> map) {
		boolean flag = appUserInfoMapper.setTarget(map);
		return flag;
	}

	@Override
	public List<AppUserInfo> appUserInfoList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<AppUserInfo> list = appUserInfoMapper.appUserInfoList(map);
		return list;
	}

	@Override
	public Integer queryTarget(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer target = appUserInfoMapper.queryTarget(map);
		return target;
	}

	@Override
	public AppUserInfo queryAppUserInfoByUid(int uid) {
		// TODO Auto-generated method stub
		AppUserInfo appUserInfo =  appUserInfoMapper.queryAppUserInfoByUid(uid);
		return appUserInfo;
	}

	@Override
	public AppUserInfo queryFriendinfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		AppUserInfo appUserInfo =  appUserInfoMapper.queryFriendinfo(map);
		return appUserInfo;
	}

	@Override
	public AppUserInfo findFriendinfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		AppUserInfo appUserInfo =  appUserInfoMapper.findFriendinfo(map);
		return appUserInfo;
	}

	@Override
	public boolean countFriend(Map<String, Object> map) {
		Integer count = appUserInfoMapper.countFriend(map);
		return count<5;
	}
	
	@Override
	public boolean updateFriendShow(Map<String, Object> map) {
		boolean flag = appUserInfoMapper.updateFriendShow(map);
		return flag;
	}
	@Override
	public List<AppUserInfo> queryAllFriends(Map<String, Object> map){
		List<AppUserInfo> list = appUserInfoMapper.queryAllFriends(map);
		return list;
	}
}
