package com.chinamcom.framework.user.service.impl;

import java.util.List;

//import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.PersonalityTag;
import com.chinamcom.framework.user.dao.mysql.manager.PersonalityTagMapper;
import com.chinamcom.framework.user.service.PersonalityTagService;

@Service
public class PersonalityTagServiceImpl implements PersonalityTagService{

	@Autowired
	private PersonalityTagMapper personalityTagMapper;
	
	@Override
	public List<PersonalityTag> findPersonalTag(int uid) {
		
		List<PersonalityTag> personalityTag = personalityTagMapper.findPersonalTag(uid);
		
		return personalityTag;
	}

	@Override
	public boolean batchDeleteByUid(int uid) {
		boolean flag = personalityTagMapper.batchDeleteByUid(uid);
		return flag;
	}

	@Override
	public boolean batchInsert(List<PersonalityTag> list) {
		boolean flag = personalityTagMapper.batchInsert(list);
		return flag;
	}

}
