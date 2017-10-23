package com.chinamcom.framework.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinamcom.framework.order.model.TbOrderLogistics;
import com.chinamcom.framework.order.model.TbOrderLogisticsExample;

public interface TbOrderLogisticsMapper {
    int countByExample(TbOrderLogisticsExample example);

    int deleteByExample(TbOrderLogisticsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbOrderLogistics record);

    int insertSelective(TbOrderLogistics record);

    List<TbOrderLogistics> selectByExample(TbOrderLogisticsExample example);

    TbOrderLogistics selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbOrderLogistics record, @Param("example") TbOrderLogisticsExample example);

    int updateByExample(@Param("record") TbOrderLogistics record, @Param("example") TbOrderLogisticsExample example);

    int updateByPrimaryKeySelective(TbOrderLogistics record);

    int updateByPrimaryKey(TbOrderLogistics record);
}