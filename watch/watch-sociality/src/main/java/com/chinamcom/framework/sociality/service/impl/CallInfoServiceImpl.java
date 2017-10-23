package com.chinamcom.framework.sociality.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.sociality.dao.CallInfoMapper;
import com.chinamcom.framework.sociality.model.CallInfo;
import com.chinamcom.framework.sociality.model.CallInfoExample;
import com.chinamcom.framework.sociality.service.CallInfoService;

@Service
public class CallInfoServiceImpl implements CallInfoService {

	@Autowired
	private CallInfoMapper callInfoMapper;
	
	@Override
	public List<CallInfo> getList(String imei, String type, Integer pageNo, Integer pageSize) {
		CallInfoExample example = new CallInfoExample();
		if(StringUtil.isEmpty(type)){
			example.createCriteria().andDeviceImeiEqualTo(imei);
		}else{
			example.createCriteria().andDeviceImeiEqualTo(imei).andTypeEqualTo(type);
		}
		example.setBeforeCount(pageNo);
		example.setPageSize(pageSize);
		example.setOrderByClause(" begin_time desc ");
		return callInfoMapper.selectByExample(example);
	}

	@Override
	public int delete(Integer id) {
		return callInfoMapper.deleteByPrimaryKey(id);
	}
	@Override
	public int deleteByids(String ids) {
		List<String> list=new ArrayList<String>();
		if(ids==null){
			return 0;
		}
		for(String idItem:ids.split(",")){
			list.add(idItem);
		}
		return callInfoMapper.deleteByIDS(list);
	}

	@Override
	public List<CallInfo> selectByids(String ids) {
		List<String> list=new ArrayList<String>();
		if(ids==null){
			return null;
		}
		for(String idItem:ids.split(",")){
			list.add(idItem);
		}
		return callInfoMapper.selectByPrimaryKeys(list);
	}

	
}
