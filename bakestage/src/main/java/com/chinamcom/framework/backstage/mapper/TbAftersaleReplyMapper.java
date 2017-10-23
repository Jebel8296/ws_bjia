package com.chinamcom.framework.backstage.mapper;

import com.chinamcom.framework.backstage.model.TbAftersaleReply;
import com.chinamcom.framework.backstage.model.TbAftersaleReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbAftersaleReplyMapper {
    int countByExample(TbAftersaleReplyExample example);

    int deleteByExample(TbAftersaleReplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbAftersaleReply record);

    int insertSelective(TbAftersaleReply record);

    List<TbAftersaleReply> selectByExample(TbAftersaleReplyExample example);

    TbAftersaleReply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbAftersaleReply record, @Param("example") TbAftersaleReplyExample example);

    int updateByExample(@Param("record") TbAftersaleReply record, @Param("example") TbAftersaleReplyExample example);

    int updateByPrimaryKeySelective(TbAftersaleReply record);

    int updateByPrimaryKey(TbAftersaleReply record);
}