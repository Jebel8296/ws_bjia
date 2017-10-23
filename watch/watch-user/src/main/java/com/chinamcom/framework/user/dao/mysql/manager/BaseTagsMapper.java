package com.chinamcom.framework.user.dao.mysql.manager;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.user.dao.BaseTags;

public interface BaseTagsMapper {
	
	public List<BaseTags> basetagslist(Map<String,Object> map);
}
