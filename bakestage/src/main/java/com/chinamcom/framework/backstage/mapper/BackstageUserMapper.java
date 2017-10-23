package com.chinamcom.framework.backstage.mapper;

import com.chinamcom.framework.backstage.model.BackstageUser;
import com.chinamcom.framework.backstage.model.BackstageUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BackstageUserMapper {
	int countByExample(BackstageUserExample example);

	int deleteByExample(BackstageUserExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(BackstageUser record);

	int insertSelective(BackstageUser record);

	List<BackstageUser> selectByExample(BackstageUserExample example);

	BackstageUser selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") BackstageUser record, @Param("example") BackstageUserExample example);

	int updateByExample(@Param("record") BackstageUser record, @Param("example") BackstageUserExample example);

	int updateByPrimaryKeySelective(BackstageUser record);

	int updateByPrimaryKey(BackstageUser record);
}