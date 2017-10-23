package com.chinamcom.framework.sociality.service;

import com.chinamcom.framework.common.json.ZJSONObject;

public interface IContactsService {

	/**
	 * 通讯录列表
	 * 
	 * @param serialNumber
	 * @param reqData
	 * @return
	 * @throws Exception
	 */
	String selectContacts(String serialNumber, ZJSONObject reqData) throws Exception;

	/**
	 * 添加通讯录
	 * 
	 * @param serialNumber
	 * @param reqData
	 * @throws Exception
	 */
	String addContactInfo(String serialNumber, ZJSONObject reqData) throws Exception;

	/**
	 * 编辑通讯录
	 * 
	 * @param serialNumber
	 * @param reqData
	 * @throws Exception
	 */
	String updateContactInfo(String serialNumber, ZJSONObject reqData) throws Exception;

	/**
	 * 通讯录在手表显示
	 * 
	 * @param serialNumber
	 * @param reqData
	 * @throws Exception
	 */
	String updateWatchstatByContact(String serialNumber, ZJSONObject reqData) throws Exception;

	/**
	 * 删除通讯录
	 * 
	 * @param serialNumber
	 * @param reqData
	 * @throws Exception
	 */
	String deleteContactInfo(String serialNumber, ZJSONObject reqData) throws Exception;

	/**
	 * 同步通讯录
	 * 
	 * @param serialNumber
	 * @param reqData
	 * @throws Exception
	 */
	String syncContactInfo(Integer uid, String did);

}
