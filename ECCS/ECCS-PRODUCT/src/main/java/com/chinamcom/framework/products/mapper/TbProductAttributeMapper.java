package com.chinamcom.framework.products.mapper;

import com.chinamcom.framework.products.model.TbProductAttribute;
import com.chinamcom.framework.products.model.TbProductAttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbProductAttributeMapper {
    int countByExample(TbProductAttributeExample example);

    int deleteByExample(TbProductAttributeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbProductAttribute record);

    int insertSelective(TbProductAttribute record);

    List<TbProductAttribute> selectByExample(TbProductAttributeExample example);

    TbProductAttribute selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProductAttribute record, @Param("example") TbProductAttributeExample example);

    int updateByExample(@Param("record") TbProductAttribute record, @Param("example") TbProductAttributeExample example);

    int updateByPrimaryKeySelective(TbProductAttribute record);

    int updateByPrimaryKey(TbProductAttribute record);
}