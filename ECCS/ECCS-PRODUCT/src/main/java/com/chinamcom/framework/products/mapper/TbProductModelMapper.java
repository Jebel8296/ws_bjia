package com.chinamcom.framework.products.mapper;

import com.chinamcom.framework.products.model.TbProductModel;
import com.chinamcom.framework.products.model.TbProductModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbProductModelMapper {
    int countByExample(TbProductModelExample example);

    int deleteByExample(TbProductModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbProductModel record);

    int insertSelective(TbProductModel record);

    List<TbProductModel> selectByExample(TbProductModelExample example);

    TbProductModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProductModel record, @Param("example") TbProductModelExample example);

    int updateByExample(@Param("record") TbProductModel record, @Param("example") TbProductModelExample example);

    int updateByPrimaryKeySelective(TbProductModel record);

    int updateByPrimaryKey(TbProductModel record);
}