package com.chinamcom.framework.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinamcom.framework.order.model.TbOrderInstance;
import com.chinamcom.framework.order.model.TbOrderInstanceExample;

public interface TbOrderInstanceMapper {
	int countByExample(TbOrderInstanceExample example);

	int deleteByExample(TbOrderInstanceExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(TbOrderInstance record);

	int insertSelective(TbOrderInstance record);

	List<TbOrderInstance> selectByExample(TbOrderInstanceExample example);

	TbOrderInstance selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") TbOrderInstance record,@Param("example") TbOrderInstanceExample example);

	int updateByExample(@Param("record") TbOrderInstance record, @Param("example") TbOrderInstanceExample example);

	int updateByPrimaryKeySelective(TbOrderInstance record);

	int updateByPrimaryKey(TbOrderInstance record);
}