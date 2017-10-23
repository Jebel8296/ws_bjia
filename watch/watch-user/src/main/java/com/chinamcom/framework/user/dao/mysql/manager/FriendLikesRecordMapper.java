package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.Map;

import com.chinamcom.framework.user.dao.FriendLikesRecord;

public interface FriendLikesRecordMapper {
    int insert(FriendLikesRecord record);
    int updateFriendLikesRecord(Map<String,Object> map);
}