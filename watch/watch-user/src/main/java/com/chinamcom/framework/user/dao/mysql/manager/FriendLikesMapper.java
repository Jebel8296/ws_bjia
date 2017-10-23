package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.FriendLikes;
import com.chinamcom.framework.user.dao.FriendLikesInfo;

public interface FriendLikesMapper {
	List<FriendLikesInfo> selectFriendLikesList(Integer uid);
	int insert(FriendLikes record);
	int updateFriendLikes(Map<String,Object> map);
}
