package com.chinamcom.framework.sport.dao;

import com.chinamcom.framework.sport.model.SportInfo;
import com.chinamcom.framework.sport.model.SportInfoExample;

import java.util.List;
import java.util.Map;

public interface SportInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SportInfo record);

    int insertSelective(SportInfo record);

    List<SportInfo> selectByExample(SportInfoExample example);

    SportInfo selectByPrimaryKey(Integer id);
    
    public List<SportInfo> queryTodaySportInfo(Map<String,Object> map);
    
    public SportInfo countTotal(Map<String,Object> map);
    
    public Integer queryTargetStep(Map<String,Object> map);
    
    public List<SportInfo> sportInfoModel(Map<String,Object> map);
    
    public Integer querySportRank(Map<String,Object> map);
    
    SportInfo queryTotalInfo(Map<String,Object> map);
}