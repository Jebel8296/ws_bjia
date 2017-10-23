package com.chinamcom.framework.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.user.dao.BaseTags;
import com.chinamcom.framework.user.dao.mysql.manager.BaseTagsMapper;
import com.chinamcom.framework.user.service.BaseTagsService;

@Service
public class BaseTagsServiceImpl implements BaseTagsService{

	@Autowired
	private BaseTagsMapper baseTagsMapper;
	
	@Override
	public List<BaseTags> basetagslist(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<BaseTags> list = baseTagsMapper.basetagslist(map);
		return list;
	}

}
