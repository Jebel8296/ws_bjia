package com.chinamcom.framework.aftersale.mapper;

import com.chinamcom.framework.aftersale.model.TbAftersaleProduct;
import com.chinamcom.framework.aftersale.model.TbAftersaleProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAftersaleProductMapper {
    int countByExample(TbAftersaleProductExample example);

    int deleteByExample(TbAftersaleProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbAftersaleProduct record);

    int insertSelective(TbAftersaleProduct record);

    List<TbAftersaleProduct> selectByExample(TbAftersaleProductExample example);

    TbAftersaleProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAftersaleProduct record, @Param("example") TbAftersaleProductExample example);

    int updateByExample(@Param("record") TbAftersaleProduct record, @Param("example") TbAftersaleProductExample example);

    int updateByPrimaryKeySelective(TbAftersaleProduct record);

    int updateByPrimaryKey(TbAftersaleProduct record);
}