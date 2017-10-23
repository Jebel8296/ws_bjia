package com.chinamcom.framework.products.mapper;

import com.chinamcom.framework.products.model.TbProductColor;
import com.chinamcom.framework.products.model.TbProductColorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbProductColorMapper {
    int countByExample(TbProductColorExample example);

    int deleteByExample(TbProductColorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbProductColor record);

    int insertSelective(TbProductColor record);

    List<TbProductColor> selectByExample(TbProductColorExample example);

    TbProductColor selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProductColor record, @Param("example") TbProductColorExample example);

    int updateByExample(@Param("record") TbProductColor record, @Param("example") TbProductColorExample example);

    int updateByPrimaryKeySelective(TbProductColor record);

    int updateByPrimaryKey(TbProductColor record);
}