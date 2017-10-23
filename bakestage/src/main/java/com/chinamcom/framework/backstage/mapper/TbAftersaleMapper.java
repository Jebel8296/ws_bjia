package com.chinamcom.framework.backstage.mapper;

import com.chinamcom.framework.backstage.model.TbAftersale;
import com.chinamcom.framework.backstage.model.TbAftersaleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAftersaleMapper {
    int countByExample(TbAftersaleExample example);

    int deleteByExample(TbAftersaleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbAftersale record);

    int insertSelective(TbAftersale record);

    List<TbAftersale> selectByExample(TbAftersaleExample example);

    TbAftersale selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAftersale record, @Param("example") TbAftersaleExample example);

    int updateByExample(@Param("record") TbAftersale record, @Param("example") TbAftersaleExample example);

    int updateByPrimaryKeySelective(TbAftersale record);

    int updateByPrimaryKey(TbAftersale record);
}