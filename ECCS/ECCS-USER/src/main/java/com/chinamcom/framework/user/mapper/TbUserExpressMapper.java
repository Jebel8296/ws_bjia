package com.chinamcom.framework.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinamcom.framework.common.model.user.TbUserExpress;
import com.chinamcom.framework.common.model.user.TbUserExpressExample;

public interface TbUserExpressMapper {
    int countByExample(TbUserExpressExample example);

    int deleteByExample(TbUserExpressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbUserExpress record);

    int insertSelective(TbUserExpress record);

    List<TbUserExpress> selectByExample(TbUserExpressExample example);

    TbUserExpress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbUserExpress record, @Param("example") TbUserExpressExample example);

    int updateByExample(@Param("record") TbUserExpress record, @Param("example") TbUserExpressExample example);

    int updateByPrimaryKeySelective(TbUserExpress record);

    int updateByPrimaryKey(TbUserExpress record);
}