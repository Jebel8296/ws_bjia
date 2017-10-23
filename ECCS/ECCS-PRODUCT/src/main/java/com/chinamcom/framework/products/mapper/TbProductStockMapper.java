package com.chinamcom.framework.products.mapper;

import com.chinamcom.framework.products.model.TbProductStock;
import com.chinamcom.framework.products.model.TbProductStockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbProductStockMapper {
    int countByExample(TbProductStockExample example);

    int deleteByExample(TbProductStockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbProductStock record);

    int insertSelective(TbProductStock record);

    List<TbProductStock> selectByExample(TbProductStockExample example);

    TbProductStock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProductStock record, @Param("example") TbProductStockExample example);

    int updateByExample(@Param("record") TbProductStock record, @Param("example") TbProductStockExample example);

    int updateByPrimaryKeySelective(TbProductStock record);

    int updateByPrimaryKey(TbProductStock record);
}