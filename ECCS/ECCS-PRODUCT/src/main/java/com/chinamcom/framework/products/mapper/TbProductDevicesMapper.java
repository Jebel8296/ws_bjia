package com.chinamcom.framework.products.mapper;

import com.chinamcom.framework.products.model.TbProductDevices;
import com.chinamcom.framework.products.model.TbProductDevicesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbProductDevicesMapper {
    int countByExample(TbProductDevicesExample example);

    int deleteByExample(TbProductDevicesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbProductDevices record);

    int insertSelective(TbProductDevices record);

    List<TbProductDevices> selectByExample(TbProductDevicesExample example);

    TbProductDevices selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProductDevices record, @Param("example") TbProductDevicesExample example);

    int updateByExample(@Param("record") TbProductDevices record, @Param("example") TbProductDevicesExample example);

    int updateByPrimaryKeySelective(TbProductDevices record);

    int updateByPrimaryKey(TbProductDevices record);
}