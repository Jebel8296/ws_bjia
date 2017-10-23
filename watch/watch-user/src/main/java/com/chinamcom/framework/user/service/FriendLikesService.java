package com.chinamcom.framework.user.service;

import java.util.List;

import com.chinamcom.framework.user.dao.FriendLikesInfo;

public interface FriendLikesService {
	List<FriendLikesInfo> getFriendLikes(Integer uid);
	
	void clickFriendLikes(Integer uid, Integer friend_uid, Integer status);
}
