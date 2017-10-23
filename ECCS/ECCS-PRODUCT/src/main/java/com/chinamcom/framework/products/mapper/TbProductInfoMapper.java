package com.chinamcom.framework.products.mapper;

import com.chinamcom.framework.products.model.TbProductInfo;
import com.chinamcom.framework.products.model.TbProductInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbProductInfoMapper {
    int countByExample(TbProductInfoExample example);

    int deleteByExample(TbProductInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbProductInfo record);

    int insertSelective(TbProductInfo record);

    List<TbProductInfo> selectByExample(TbProductInfoExample example);

    TbProductInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProductInfo record, @Param("example") TbProductInfoExample example);

    int updateByExample(@Param("record") TbProductInfo record, @Param("example") TbProductInfoExample example);

    int updateByPrimaryKeySelective(TbProductInfo record);

    int updateByPrimaryKey(TbProductInfo record);
}