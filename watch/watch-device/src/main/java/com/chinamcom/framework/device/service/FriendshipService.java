package com.chinamcom.framework.device.service;

import java.util.List;

public interface FriendshipService {
	List<Integer> addFriend(String imei, String imeif);
	
	String getUserName(Integer uid, Integer fromUid);
	
	boolean isFriend(Integer uid, Integer to);
	
	Integer isShowWatch(Integer uid, Integer to);
}
