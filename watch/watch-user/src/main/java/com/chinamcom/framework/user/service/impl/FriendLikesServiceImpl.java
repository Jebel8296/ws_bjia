package com.chinamcom.framework.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.FriendLikes;
import com.chinamcom.framework.user.dao.FriendLikesInfo;
import com.chinamcom.framework.user.dao.FriendLikesRecord;
import com.chinamcom.framework.user.dao.mysql.manager.FriendLikesMapper;
import com.chinamcom.framework.user.dao.mysql.manager.FriendLikesRecordMapper;
import com.chinamcom.framework.user.service.FriendLikesService;

@Service
public class FriendLikesServiceImpl implements FriendLikesService {

	@Autowired
	private FriendLikesMapper friendLikesMapper;
	
	@Autowired
	private FriendLikesRecordMapper friendLikesRecordMapper;
	
	@Override
	public List<FriendLikesInfo> getFriendLikes(Integer uid) {
		return friendLikesMapper.selectFriendLikesList(uid);
	}

	@Override
	public void clickFriendLikes(Integer uid, Integer friendUid, Integer status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("uid", friendUid);
		map.put("status", status);
		if(friendLikesMapper.updateFriendLikes(map) <= 0){
			if(status == 1){
				FriendLikes record = new FriendLikes();
				record.setUid(friendUid);
				record.setLikesNum(1);
				friendLikesMapper.insert(record);
			}
		}
		Map<String,Object> mapRecord = new HashMap<String,Object>();
		mapRecord.put("uid", friendUid);
		mapRecord.put("from_uid", uid);
		mapRecord.put("status", status);
		if(friendLikesRecordMapper.updateFriendLikesRecord(mapRecord) <= 0){
			FriendLikesRecord record = new FriendLikesRecord();
			record.setUid(friendUid);
			record.setFromUid(uid);
			record.setStatus(status);
			friendLikesRecordMapper.insert(record);
		}
	}

}
