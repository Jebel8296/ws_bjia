package com.chinamcom.framework.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.FeedBackInfo;
import com.chinamcom.framework.user.dao.mysql.manager.FeedBackInfoMapper;
import com.chinamcom.framework.user.service.FeedBackInfoService;

@Service
public class FeedBackInfoServiceImpl implements FeedBackInfoService{

	@Autowired
	private FeedBackInfoMapper feedBackInfoMapper;
	
	@Override
	public List<FeedBackInfo> feedBackInfoList(Map<String, Object> map) {
		
		List<FeedBackInfo> list = feedBackInfoMapper.feedBackInfoList(map);
		
		return list;
	}
	
	@Override
	public Integer addFeedBackInfo(FeedBackInfo feedBackInfo) {
		Integer row = feedBackInfoMapper.addFeedBackInfo(feedBackInfo);
		if(row>0){
			Integer id = feedBackInfo.getId();
			return id;
		}else{
			return -1;
		}
	}

	@Override
	public boolean updateFeedBackInfo(FeedBackInfo feedBackInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteFeedBackInfo(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
