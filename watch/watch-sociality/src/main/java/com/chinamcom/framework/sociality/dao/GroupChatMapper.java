package com.chinamcom.framework.sociality.dao;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.sociality.model.AppGroup;
import com.chinamcom.framework.sociality.model.AppGroupUser;

public interface GroupChatMapper {

	Integer findAppGroupCountByUid(Integer uid);

	void insertAppGroup(AppGroup group);

	void insertAppGroupUser(AppGroupUser user);

	void insertBatchAppGroupUser(List<AppGroupUser> list);

	void deleteBatchAppGroupUser(List<AppGroupUser> list);

	void deleteBatchWatchstatAppGroupUser(List<AppGroupUser> list);

	void deleteBatchScreenstatAppGroupUser(List<AppGroupUser> list);

	List<AppGroup> findAppGroupByUid(AppGroup group);

	void deleteAppGroup(AppGroup group);

	void updateAppGroupName(AppGroup group);

	void updateAppGroupUserAlias(AppGroupUser user);

	List<AppGroupUser> selectAppGroupUsers(AppGroupUser user);
	
	String selectFriendAlias(Map<String , Object> map);
	
	String selectUserNickname(Integer uid);

	List<AppGroup> selectAppGroups(AppGroup group);

	List<AppGroup> selectAllAppGroups(AppGroup group);

	List<AppGroup> selectAllAppGroupsByUID(AppGroup group);

	Integer selectAppGroupCountByWatchstat(AppGroupUser user);

	String selectHeadByUid(Integer uid);
}
