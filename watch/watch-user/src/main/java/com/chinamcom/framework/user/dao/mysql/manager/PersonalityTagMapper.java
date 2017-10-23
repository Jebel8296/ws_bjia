package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.List;

import com.chinamcom.framework.user.dao.PersonalityTag;

public interface PersonalityTagMapper {
	
	List<PersonalityTag> findPersonalTag(int uid);

	boolean batchDeleteByUid(int uid);
	
	boolean batchInsert(List<PersonalityTag> list);
	
}
