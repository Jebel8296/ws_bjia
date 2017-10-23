package com.chinamcom.framework.sport.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.sport.dao.SportInfoMapper;
import com.chinamcom.framework.sport.model.SportInfo;
import com.chinamcom.framework.sport.model.SportInfoExample;
import com.chinamcom.framework.sport.service.SportInfoService;

@Service
public class SportInfoServiceImpl implements SportInfoService {

	@Autowired
	private SportInfoMapper sportInfoMapper;
	
	@Override
	public int insert(SportInfo sportInfo) {
		return sportInfoMapper.insertSelective(sportInfo);
	}

	@Override
	public SportInfo getTodaySportInfo(String imei, Integer sportType) {
		SportInfoExample example = new SportInfoExample();
        example.createCriteria().andImeiEqualTo(imei).andSportTypeEqualTo(sportType);
        example.setOrderByClause(" create_time desc ");
        example.setBeforeCount(0);
        example.setPageSize(1);
        List<SportInfo> sportInfo = sportInfoMapper.selectByExample(example);
        if(sportInfo != null && sportInfo.size() > 0){
        	return sportInfo.get(0);
        }
		return null;
	}

	@Override
	public List<SportInfo> queryTodaySportInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportInfo> list = sportInfoMapper.queryTodaySportInfo(map);
		return list;
	}

	@Override
	public SportInfo countTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		SportInfo totalStep = sportInfoMapper.countTotal(map);
		return totalStep;
	}

	@Override
	public Integer queryTargetStep(Map<String, Object> map) {
		Integer target = sportInfoMapper.queryTargetStep(map);
		return target;
	}

	@Override
	public List<SportInfo> sportInfoModel(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		List<SportInfo> list = sportInfoMapper.sportInfoModel(map);
		return list;
	}

	@Override
	public Integer querySportRank(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		Integer rownum = sportInfoMapper.querySportRank(map);
		return rownum==null?0:rownum;
	}

	@Override
	public SportInfo queryTotalInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		SportInfo sportInfo = sportInfoMapper.queryTotalInfo(map);
		return sportInfo;
	}

}
