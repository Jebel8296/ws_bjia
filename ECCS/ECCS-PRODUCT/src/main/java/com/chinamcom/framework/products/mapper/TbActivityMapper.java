package com.chinamcom.framework.products.mapper;

import com.chinamcom.framework.products.model.TbActivity;
import com.chinamcom.framework.products.model.TbActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbActivityMapper {
    int countByExample(TbActivityExample example);

    int deleteByExample(TbActivityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbActivity record);

    int insertSelective(TbActivity record);

    List<TbActivity> selectByExample(TbActivityExample example);

    TbActivity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbActivity record, @Param("example") TbActivityExample example);

    int updateByExample(@Param("record") TbActivity record, @Param("example") TbActivityExample example);

    int updateByPrimaryKeySelective(TbActivity record);

    int updateByPrimaryKey(TbActivity record);
}