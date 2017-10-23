package com.chinamcom.framework.user.service;

import java.util.List;

import com.chinamcom.framework.user.dao.PersonalityTag;

public interface PersonalityTagService {
	//查询个人标签
	List<PersonalityTag> findPersonalTag(int uid);
	
	//批量删除个人标签
	boolean batchDeleteByUid(int uid);
	
	//批量新增
	boolean batchInsert(List<PersonalityTag> list);
}
