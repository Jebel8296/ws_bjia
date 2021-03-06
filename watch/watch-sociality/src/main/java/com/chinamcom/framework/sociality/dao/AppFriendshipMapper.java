package com.chinamcom.framework.sociality.dao;

import com.chinamcom.framework.sociality.model.AppFriendship;
import com.chinamcom.framework.sociality.model.AppFriendshipExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AppFriendshipMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_friendship
     *
     * @mbggenerated
     */
    int countByExample(AppFriendshipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_friendship
     *
     * @mbggenerated
     */
    int deleteByExample(AppFriendshipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_friendship
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_friendship
     *
     * @mbggenerated
     */
    int insert(AppFriendship record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_friendship
     *
     * @mbggenerated
     */
    int insertSelective(AppFriendship record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_friendship
     *
     * @mbggenerated
     */
    List<AppFriendship> selectByExample(AppFriendshipExample example);

    List<AppFriendship> selectByExampleNewApply(AppFriendshipExample example);

    AppFriendship selectNewFriend(@Param("record") Map<String, Object> map_para);
    List<AppFriendship> selectIsFriend(@Param("record") Map<String, Object> map_para);
    List<AppFriendship> selectIsFriendPassive(@Param("record") Map<String, Object> map_para);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_friendship
     *
     * @mbggenerated
     */
    AppFriendship selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_friendship
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") AppFriendship record, @Param("example") AppFriendshipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_friendship
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") AppFriendship record, @Param("example") AppFriendshipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_friendship
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(AppFriendship record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_friendship
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(AppFriendship record);
}