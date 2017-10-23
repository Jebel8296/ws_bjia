package com.chinamcom.framework.products.mapper;

import com.chinamcom.framework.products.model.TbProductImagebox;
import com.chinamcom.framework.products.model.TbProductImageboxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbProductImageboxMapper {
    int countByExample(TbProductImageboxExample example);

    int deleteByExample(TbProductImageboxExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbProductImagebox record);

    int insertSelective(TbProductImagebox record);

    List<TbProductImagebox> selectByExample(TbProductImageboxExample example);

    TbProductImagebox selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProductImagebox record, @Param("example") TbProductImageboxExample example);

    int updateByExample(@Param("record") TbProductImagebox record, @Param("example") TbProductImageboxExample example);

    int updateByPrimaryKeySelective(TbProductImagebox record);

    int updateByPrimaryKey(TbProductImagebox record);
}