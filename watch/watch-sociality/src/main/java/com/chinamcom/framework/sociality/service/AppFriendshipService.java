package com.chinamcom.framework.sociality.service;

import java.util.List;

import com.chinamcom.framework.common.exception.ServiceException;
import com.chinamcom.framework.sociality.model.AppFriendship;

public interface AppFriendshipService {

	public int deleteByIds(int uid,String ids) throws ServiceException;

	public boolean applyToBeFriend(int uid0, int uid1,String alias,int watchstat) throws ServiceException;

	public boolean modifyFriendAlias(int uid0, int uid1,String alias) throws ServiceException;
	
	public boolean agreeWithToBeFriend(int uid0, int uid1)
			throws ServiceException;
	public boolean blueToothAgreeWithToBeFriend(int uid0, int uid1)
			throws ServiceException;

	public boolean refuseToBeFriend(int uid0, int uid1) throws ServiceException;

	public int deleteApplyToBeFriendItem(int uid0, String ids)
			throws ServiceException;
	
	public AppFriendship getNewFriendDetail(String imei,int uid);
	
	

	List<AppFriendship> getNewApplyToBeFriendList(int uid, Integer pageNo, Integer pageSize);
	
	List<AppFriendship> getIsFriendList(int uid,Integer gid,String nickname, Integer pageNo, Integer pageSize);
	List<AppFriendship> getIsFriendListPassive(int uid,Integer gid, Integer pageNo, Integer pageSize);
	/**
	 * 获取好友申请个数
	 * @param uid
	 * @return
	 */
	public int getFriendAppnum(int uid);
	/**
	 * 看过新好友之后修改已看过状态
	 * @param list
	 */
	public void updatenewFriendShip(List<AppFriendship> list);

}
