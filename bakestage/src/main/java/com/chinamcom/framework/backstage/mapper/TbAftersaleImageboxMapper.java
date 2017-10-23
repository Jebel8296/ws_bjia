package com.chinamcom.framework.backstage.mapper;

import com.chinamcom.framework.backstage.model.TbAftersaleImagebox;
import com.chinamcom.framework.backstage.model.TbAftersaleImageboxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAftersaleImageboxMapper {
    int countByExample(TbAftersaleImageboxExample example);

    int deleteByExample(TbAftersaleImageboxExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbAftersaleImagebox record);

    int insertSelective(TbAftersaleImagebox record);

    List<TbAftersaleImagebox> selectByExample(TbAftersaleImageboxExample example);

    TbAftersaleImagebox selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAftersaleImagebox record, @Param("example") TbAftersaleImageboxExample example);

    int updateByExample(@Param("record") TbAftersaleImagebox record, @Param("example") TbAftersaleImageboxExample example);

    int updateByPrimaryKeySelective(TbAftersaleImagebox record);

    int updateByPrimaryKey(TbAftersaleImagebox record);
}