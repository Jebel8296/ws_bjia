package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.FeedBackInfo;

public interface FeedBackInfoMapper {
	
		//查询
		public List<FeedBackInfo> feedBackInfoList(Map<String, Object> map);
		//新增
		public Integer addFeedBackInfo(FeedBackInfo feedBackInfo);
		//修改
		public boolean updateFeedBackInfo(FeedBackInfo feedBackInfo);
		//删除
		public boolean deleteFeedBackInfo(Integer id);
}