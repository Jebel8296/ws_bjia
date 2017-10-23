package com.chinamcom.framework.backstage.mapper;

import com.chinamcom.framework.backstage.model.TbAftersaleLogistics;
import com.chinamcom.framework.backstage.model.TbAftersaleLogisticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAftersaleLogisticsMapper {
    int countByExample(TbAftersaleLogisticsExample example);

    int deleteByExample(TbAftersaleLogisticsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbAftersaleLogistics record);

    int insertSelective(TbAftersaleLogistics record);

    List<TbAftersaleLogistics> selectByExample(TbAftersaleLogisticsExample example);
    /**
     * 切记，generated时，不要覆盖此接口.
     * @param example
     * @return
     */
    List<TbAftersaleLogistics> selectByExampleWithExpress(TbAftersaleLogisticsExample example);
    
    TbAftersaleLogistics selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAftersaleLogistics record, @Param("example") TbAftersaleLogisticsExample example);

    int updateByExample(@Param("record") TbAftersaleLogistics record, @Param("example") TbAftersaleLogisticsExample example);

    int updateByPrimaryKeySelective(TbAftersaleLogistics record);

    int updateByPrimaryKey(TbAftersaleLogistics record);
}