package com.chinamcom.framework.sociality.service;

import java.util.List;

import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.sociality.model.AppGroupUser;

import io.vertx.core.Vertx;

public interface IGroupChatService {

	/**
	 * 创建群组
	 * 
	 * @param serialNumber
	 *            序列号
	 * @param o
	 *            reqData
	 * @return
	 */
	String insertCreateAppGroup(String headuri,String serialNumber, ZJSONObject o) throws Exception;

	/**
	 * 添加群组成员
	 * 
	 * @param list
	 *            成员信息
	 */
	String insertBatchAppGroupUser(String serialNumber, ZJSONObject reqData);

	/**
	 * 退群
	 * 
	 * @param serialNumber
	 *            序列号
	 * @param reqData
	 * @return
	 */
	String updateQuitAppGroup(Vertx vertx,String serialNumber, ZJSONObject reqData);

	/**
	 * 屏蔽
	 * 
	 * @param serialNumber
	 *            序列号
	 * @param reqData
	 * @return
	 */
	String updateScreenAppGroup(String serialNumber, ZJSONObject reqData);

	/**
	 * 删除群组成员
	 * 
	 * @param serialNumber
	 *            序列号
	 * @param reqData
	 * @return
	 */
	String updateDelAppGroupUser(String serialNumber, ZJSONObject reqData);

	/**
	 * 编辑群组名称
	 * 
	 * @param serialNumber
	 *            序列号
	 * @param reqData
	 * @return
	 */
	String updateModifyAppGroupName(String serialNumber, ZJSONObject reqData);

	/**
	 * 修改群组成员昵称
	 * 
	 * @param serialNumber
	 *            序列号
	 * @param reqData
	 * @return
	 */
	String updateModifyAppGroupUserAlias(String serialNumber, ZJSONObject reqData);

	/**
	 * 群聊成员列表
	 * 
	 * @param serialNumber
	 *            序列号
	 * @param reqData
	 * @return
	 */
	String selectSearchAppGroupUser(String serialNumber, ZJSONObject reqData);

	/**
	 * 设置手表接收群聊消息
	 * 
	 * @param serialNumber
	 *            序列号
	 * @param reqData
	 * @return
	 */
	String updateSetWatchReceiveAppGroup(String serialNumber, ZJSONObject reqData);

	/**
	 * 同步群消息到手表端
	 * 
	 * @param uid
	 *            用户ID
	 * @param did
	 *            设备ID
	 */
	public String postToWatchApp(Integer uid, String did);
	
	/**
	 * 根据UID推送群信息
	 * 
	 * @param uid
	 *            用户ID
	 */
	public String postToWatchAppByUID(Integer uid);
	
	/**
	 * 根据UI查询用户头像
	 * @param uid
	 * @return
	 */
	public String selectHeadByUid(Integer uid);
	
	public List<AppGroupUser> selectAppGroupUser(Integer gid);

}
