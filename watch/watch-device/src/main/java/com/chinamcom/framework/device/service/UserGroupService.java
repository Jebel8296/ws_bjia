package com.chinamcom.framework.device.service;

public interface UserGroupService {
	Integer showGroupOnWatchByUidAndGid(Integer uid, Integer gid);
	
	Integer selectUserStatusOnGroup(Integer uid, Integer gid);
	
	Integer selectGroupScreenSatus(Integer uid, Integer gid);
	
	String selectGroupName(Integer gid);
}
