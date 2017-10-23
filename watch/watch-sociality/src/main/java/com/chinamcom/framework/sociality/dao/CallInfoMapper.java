package com.chinamcom.framework.sociality.dao;

import com.chinamcom.framework.sociality.model.CallInfo;
import com.chinamcom.framework.sociality.model.CallInfoExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CallInfoMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByIDS(@Param("array") List<String> ids);

    int insert(CallInfo record);

    int insertSelective(CallInfo record);

    List<CallInfo> selectByExample(CallInfoExample example);

    CallInfo selectByPrimaryKey(Integer id);
    
    List<CallInfo> selectByPrimaryKeys(@Param("array") List<String> ids);

    int updateByPrimaryKeySelective(CallInfo record);

    int updateByPrimaryKey(CallInfo record);
}