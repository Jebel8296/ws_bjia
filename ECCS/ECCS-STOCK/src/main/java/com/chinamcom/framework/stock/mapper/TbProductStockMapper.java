package com.chinamcom.framework.stock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinamcom.framework.stock.model.TbProductStock;
import com.chinamcom.framework.stock.model.TbProductStockExample;

public interface TbProductStockMapper {
	int countByExample(TbProductStockExample example);

	int deleteByExample(TbProductStockExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(TbProductStock record);

	int insertSelective(TbProductStock record);

	List<TbProductStock> selectByExample(TbProductStockExample example);

	TbProductStock selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") TbProductStock record,
			@Param("example") TbProductStockExample example);

	int updateByExample(@Param("record") TbProductStock record, @Param("example") TbProductStockExample example);

	int updateByPrimaryKeySelective(TbProductStock record);

	int updateByPrimaryKey(TbProductStock record);
}