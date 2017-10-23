package com.chinamcom.framework.sociality.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constants.Constants;
import com.chinamcom.framework.common.exception.ServiceException;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.sociality.dao.AppFriendshipMapper;
import com.chinamcom.framework.sociality.model.AppFriendship;
import com.chinamcom.framework.sociality.model.AppFriendshipExample;
import com.chinamcom.framework.sociality.service.AppFriendshipService;

@Component
public class AppFriendshipServiceImpl implements AppFriendshipService {

	static final Logger logger = Logger
			.getLogger(AppFriendshipServiceImpl.class);
	@Autowired
	private AppFriendshipMapper appFriendshipMapper;

	/* (non-Javadoc)
	 * @see com.chinamcom.framework.sociality.service.AppFriendshipService#deleteByIds(int, java.lang.String)
	 * delete friends
	 */
	@Override
	public int deleteByIds(int uid, String ids) throws ServiceException {
		// TODO Auto-generated method stub
		if (ids == null) {
			return 0;
		}
		AppFriendshipExample appFriendshipExample = new AppFriendshipExample();
		List<Integer> values = new ArrayList<Integer>();
		for (String idsItem : ids.split(",")) {
			int idsItemi = Integer.parseInt(idsItem);
			values.add(idsItemi);
		}
		appFriendshipExample.createCriteria().andUid1In(values).andUid0EqualTo(uid);
		
		AppFriendship appFriendship = new AppFriendship();
		appFriendship.setStatus(Constants.deleteFriend);
		int r = appFriendshipMapper.updateByExampleSelective(appFriendship,
				appFriendshipExample);	
		
		AppFriendshipExample example = new AppFriendshipExample();
		example.createCriteria().andUid0In(values).andUid1EqualTo(uid);
		
		List<AppFriendship> appFriendships = appFriendshipMapper.selectByExample(example);
		AppFriendship friendship = new AppFriendship();
		int t = 0 ;
		if(appFriendships.size()>0){
			AppFriendship appFriendshipItem = appFriendships.get(0);
			Integer appFriendshipItemStatus = appFriendshipItem.getStatus();
			if(appFriendshipItemStatus.intValue() == Constants.deleteFriend){
				friendship.setWasdeletedfriend(0);
				appFriendshipMapper.updateByExampleSelective(friendship,appFriendshipExample);
			}else{
				friendship.setWasdeletedfriend(1);
			}
			t = appFriendshipMapper.updateByExampleSelective(friendship,example);
		}else{
			throw new ServiceException(RespCode.CODE_500);
		}
		if(r>0 && t>0 ){
			return r;
		}else{
			return 0;
		}
//		return r;
	}

	/* (non-Javadoc)
	 * @see com.chinamcom.framework.sociality.service.AppFriendshipService#applyToBeFriend(int, int, java.lang.String)
	 */
	@Override
	public boolean applyToBeFriend(int uid0, int uid1, String alias,int watchstat)
			throws ServiceException {
		// TODO Auto-generated method stub
		AppFriendshipExample appFriendshipExample = new AppFriendshipExample();
		appFriendshipExample.createCriteria().andUid0EqualTo(uid0)
				.andUid1EqualTo(uid1);
		List<AppFriendship> appFriendships = appFriendshipMapper
				.selectByExample(appFriendshipExample);
		if (appFriendships.size() > 0) {
			AppFriendship appFriendshipItem = appFriendships.get(0);
			Integer appFriendshipItemStatus = appFriendshipItem.getStatus();
			Integer friendshipItemStatus = null;
			Integer wasDeletedFriend = appFriendshipItem.getWasdeletedfriend();
			if (appFriendshipItemStatus == null) {
				return false;
			}
			
			AppFriendshipExample friendshipexample = new AppFriendshipExample();
			friendshipexample.createCriteria().andUid1EqualTo(uid0)
					.andUid0EqualTo(uid1);
			List<AppFriendship> friendships = appFriendshipMapper
					.selectByExample(friendshipexample);
			if(friendships.size()>0){
				friendshipItemStatus = friendships.get(0).getStatus();
			}
			if (appFriendshipItemStatus.intValue() == Constants.agreeWithToBeFriend && friendshipItemStatus.intValue() == Constants.agreeWithToBeFriend) {
				throw new ServiceException(RespCode.CODE_4001);
			} else {
				// appFriendshipExample.createCriteria().
				AppFriendship appFriendship = new AppFriendship();
				
				//查询显示好友是否超过5个，超过5个的话修改手表端显示标识
				if(watchstat!=0){
					AppFriendshipExample example = new AppFriendshipExample();
					example.createCriteria().andUid0EqualTo(uid0).andWatchstatEqualTo(1);
					List<AppFriendship> friends = appFriendshipMapper
							.selectByExample(example);
					if(friends.size()>=5){
						appFriendship.setWatchstat(0);
					}
				}

				appFriendship.setStatus(Constants.applyToBeFriend);
				appFriendship.setUpdateTime(new Date());
				appFriendship.setStatus1(0);
				//申请添加好友的时候，如果被删除了，改成未被好友删除的状态
				if(wasDeletedFriend==1){
					appFriendship.setWasdeletedfriend(0);
				}
				if (alias != null && !"".equals(alias.trim())) {
					appFriendship.setAlias0(alias);
				}else{
					appFriendship.setAlias0("");
				}
				appFriendshipMapper.updateByExampleSelective(appFriendship,
						appFriendshipExample);
				//申请后修改自己在对方数据中的被删除状态为0（未删除）
				AppFriendshipExample friendshipExample = new AppFriendshipExample();
				friendshipExample.createCriteria().andUid1EqualTo(uid0)
						.andUid0EqualTo(uid1);
				AppFriendship friendship = new AppFriendship();
				friendship.setWasdeletedfriend(0);
				appFriendshipMapper.updateByExampleSelective(friendship,
						friendshipExample);
				
				return true;
			}
		} else {
			Date now = new Date();
			AppFriendship appFriendship = new AppFriendship();
			//查询显示好友是否超过5个，超过5个的话修改手表端显示标识
			if(watchstat!=0){
				AppFriendshipExample example = new AppFriendshipExample();
				example.createCriteria().andUid0EqualTo(uid0).andWatchstatEqualTo(1);
				List<AppFriendship> friends = appFriendshipMapper
						.selectByExample(example);
				if(friends.size()>=5){
					appFriendship.setWatchstat(0);
				}
			}
			appFriendship.setStatus(Constants.applyToBeFriend);
			appFriendship.setUpdateTime(now);
			appFriendship.setCreateTime(now);
			appFriendship.setUid0(uid0);
			appFriendship.setUid1(uid1);
			if (alias != null && !"".equals(alias.trim())) {
				appFriendship.setAlias0(alias);
			}
			appFriendshipMapper.insertSelective(appFriendship);
			return true;
		}
	}

	@Override
	public boolean agreeWithToBeFriend(int uid0, int uid1)
			throws ServiceException {
		// TODO Auto-generated method stub
		AppFriendshipExample appFriendshipExample = new AppFriendshipExample();
		appFriendshipExample.createCriteria().andUid1EqualTo(uid0)
				.andUid0EqualTo(uid1);
		List<AppFriendship> appFriendships = appFriendshipMapper
				.selectByExample(appFriendshipExample);
		if (appFriendships.size() > 0) {
			AppFriendship appFriendshipItem = appFriendships.get(0);
			Integer appFriendshipItemStatus = appFriendshipItem.getStatus();
			if (appFriendshipItemStatus == null) {
				return false;
			}
			if (appFriendshipItemStatus.intValue() != Constants.applyToBeFriend) {
				throw new ServiceException(RespCode.CODE_4002);
			} else {
				Date now = new Date();
				// appFriendshipExample.createCriteria().
				AppFriendship appFriendship = new AppFriendship();
				
				//查询显示好友是否超过5个，超过5个的话修改手表端显示标识
				AppFriendshipExample example = new AppFriendshipExample();
				example.createCriteria().andUid0EqualTo(uid1).andWatchstatEqualTo(1);
				List<AppFriendship> friends = appFriendshipMapper
						.selectByExample(example);
				if(friends.size()>=5){
					appFriendship.setWatchstat(0);
				}
				
				appFriendship.setStatus(Constants.agreeWithToBeFriend);
				appFriendship.setUpdateTime(now);
				appFriendship.setWasdeletedfriend(0);//接受好友申请的时候，申请人被删除状态变回0
				appFriendshipMapper.updateByExampleSelective(appFriendship,
						appFriendshipExample);
				//
				appFriendshipExample = new AppFriendshipExample();
				appFriendshipExample.createCriteria().andUid1EqualTo(uid1)
						.andUid0EqualTo(uid0);
				appFriendships = appFriendshipMapper
						.selectByExample(appFriendshipExample);
				if (appFriendships.size() > 0) {
					appFriendshipItem = appFriendships.get(0);
					appFriendshipItemStatus = appFriendshipItem.getStatus();
					/*if (appFriendshipItemStatus != Constants.applyToBeFriend) { 暂时先注释掉*/  
					
					AppFriendshipExample friendExample = new AppFriendshipExample();
					friendExample.createCriteria().andUid0EqualTo(uid0).andWatchstatEqualTo(1);
					List<AppFriendship> friendships = appFriendshipMapper
							.selectByExample(friendExample);
					if(friendships.size()>=5){
						appFriendship.setWatchstat(0);
					}	
						appFriendship = new AppFriendship();
						appFriendship.setStatus(Constants.agreeWithToBeFriend);
						appFriendship.setUpdateTime(now);
						appFriendship.setWasdeletedfriend(0);
						appFriendshipMapper.updateByExampleSelective(
								appFriendship, appFriendshipExample);
					/*}*/
				} else {
					appFriendship = new AppFriendship();
					AppFriendshipExample friendExample = new AppFriendshipExample();
					friendExample.createCriteria().andUid0EqualTo(uid0).andWatchstatEqualTo(1);
					List<AppFriendship> friendships = appFriendshipMapper
							.selectByExample(friendExample);
					if(friendships.size()>=5){
						appFriendship.setWatchstat(0);
					}
					appFriendship.setStatus(Constants.agreeWithToBeFriend);
					appFriendship.setUpdateTime(now);
					appFriendship.setCreateTime(now);
					appFriendship.setUid0(uid0);
					appFriendship.setUid1(uid1);
					appFriendshipMapper.insertSelective(appFriendship);
				}
				//
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean blueToothAgreeWithToBeFriend(int uid0, int uid1)
			throws ServiceException {
		// TODO Auto-generated method stub
		AppFriendshipExample appFriendshipExample = new AppFriendshipExample();
		appFriendshipExample.createCriteria().andUid1EqualTo(uid0)
		.andUid0EqualTo(uid1);
		List<AppFriendship> appFriendships = appFriendshipMapper
				.selectByExample(appFriendshipExample);
		if (appFriendships.size() > 0) {
			AppFriendship appFriendshipItem = appFriendships.get(0);
			Integer appFriendshipItemStatus = appFriendshipItem.getStatus();
			if (appFriendshipItemStatus != null && appFriendshipItemStatus.intValue() == Constants.agreeWithToBeFriend) {
				throw new ServiceException(RespCode.CODE_4001);
			} else {
				Date now = new Date();
				// appFriendshipExample.createCriteria().
				AppFriendship appFriendship = new AppFriendship();
				appFriendship.setStatus(Constants.agreeWithToBeFriend);
				appFriendship.setUpdateTime(now);
				appFriendshipMapper.updateByExampleSelective(appFriendship,
						appFriendshipExample);
				//
				appFriendshipExample = new AppFriendshipExample();
				appFriendshipExample.createCriteria().andUid1EqualTo(uid1)
				.andUid0EqualTo(uid0);
				appFriendships = appFriendshipMapper
						.selectByExample(appFriendshipExample);
				if (appFriendships.size() > 0) {
					appFriendshipItem = appFriendships.get(0);
					appFriendshipItemStatus = appFriendshipItem.getStatus();
					if (appFriendshipItemStatus != Constants.agreeWithToBeFriend) {
						appFriendship = new AppFriendship();
						appFriendship.setStatus(Constants.agreeWithToBeFriend);
						appFriendship.setUpdateTime(now);
						appFriendshipMapper.updateByExampleSelective(
								appFriendship, appFriendshipExample);
					}
				} else {
					appFriendship = new AppFriendship();
					appFriendship.setStatus(Constants.agreeWithToBeFriend);
					appFriendship.setUpdateTime(now);
					appFriendship.setCreateTime(now);
					appFriendship.setUid0(uid0);
					appFriendship.setUid1(uid1);
					appFriendshipMapper
							.insertSelective(appFriendship);
				}
				//
				return true;
			}
		}else{
			Date now = new Date();
			AppFriendship appFriendship = new AppFriendship();
			appFriendship = new AppFriendship();
			appFriendship.setStatus(Constants.agreeWithToBeFriend);
			appFriendship.setUpdateTime(now);
			appFriendship.setCreateTime(now);
			appFriendship.setUid0(uid1);
			appFriendship.setUid1(uid0);
			appFriendshipMapper
					.insertSelective(appFriendship);
			
			appFriendship = new AppFriendship();
			appFriendshipExample = new AppFriendshipExample();
			appFriendshipExample.createCriteria().andUid1EqualTo(uid1)
			.andUid0EqualTo(uid0);
			appFriendships = appFriendshipMapper
					.selectByExample(appFriendshipExample);
			if (appFriendships.size() > 0) {
				AppFriendship	appFriendshipItem = appFriendships.get(0);
				Integer appFriendshipItemStatus = appFriendshipItem.getStatus();
				if (appFriendshipItemStatus != Constants.agreeWithToBeFriend) {
					appFriendship = new AppFriendship();
					appFriendship.setStatus(Constants.agreeWithToBeFriend);
					appFriendship.setUpdateTime(now);
					appFriendshipMapper.updateByExampleSelective(
							appFriendship, appFriendshipExample);
				}
			} else {
				appFriendship = new AppFriendship();
				appFriendship.setStatus(Constants.agreeWithToBeFriend);
				appFriendship.setUpdateTime(now);
				appFriendship.setCreateTime(now);
				appFriendship.setUid0(uid0);
				appFriendship.setUid1(uid1);
				appFriendshipMapper.insertSelective(appFriendship);
			}
			return true;
		}
//		return false;
	}

	@Override
	public boolean refuseToBeFriend(int uid0, int uid1) throws ServiceException {
		// TODO Auto-generated method stub
		AppFriendshipExample appFriendshipExample = new AppFriendshipExample();
		appFriendshipExample.createCriteria().andUid1EqualTo(uid0)
				.andUid0EqualTo(uid1);
		List<AppFriendship> appFriendships = appFriendshipMapper
				.selectByExample(appFriendshipExample);
		if (appFriendships.size() > 0) {
			AppFriendship appFriendshipItem = appFriendships.get(0);
			Integer appFriendshipItemStatus = appFriendshipItem.getStatus();
			if (appFriendshipItemStatus == null) {
				return false;
			}
			if (appFriendshipItemStatus.intValue() != Constants.applyToBeFriend) {
				throw new ServiceException(RespCode.CODE_4002);
			} else {
				// appFriendshipExample.createCriteria().
				AppFriendship appFriendship = new AppFriendship();
				appFriendship.setStatus(Constants.refuseToBeFriend);
				appFriendship.setUpdateTime(new Date());
				appFriendshipMapper.updateByExampleSelective(appFriendship,
						appFriendshipExample);
				return true;
			}
		}
		return false;
	}

	@Override
	public int deleteApplyToBeFriendItem(int uid0, String ids)
			throws ServiceException {
		// TODO Auto-generated method stub
		
		List<Integer> values = new ArrayList<Integer>();
		for (String idsItem : ids.split(",")) {
			int idsItemi = Integer.parseInt(idsItem);
			values.add(idsItemi);
		}
		AppFriendshipExample appFriendshipExample = new AppFriendshipExample();
		appFriendshipExample.createCriteria().andUid1EqualTo(uid0)
				.andStatusEqualTo(Constants.applyToBeFriend).andUid0In(values);;
	// 	appFriendshipExample.createCriteria().andUid0In(values);
	// 	appFriendshipExample.createCriteria().andUid0EqualTo(10000158);
		AppFriendship appFriendship = new AppFriendship();
		appFriendship.setStatus(Constants.deleteApplyToBeFriend);
		appFriendship.setUpdateTime(new Date());
		int r = appFriendshipMapper.updateByExampleSelective(appFriendship,
				appFriendshipExample);
		if (r == 0) {
			throw new ServiceException(RespCode.CODE_4004);
		}
		return r;
	}

	@Override
	public List<AppFriendship> getNewApplyToBeFriendList(int uid,
			Integer pageNo, Integer pageSize) {
		AppFriendshipExample appFriendshipExample = new AppFriendshipExample();
		appFriendshipExample.createCriteria().andUid1EqualTo(uid)
				.andStatusEqualTo(Constants.applyToBeFriend);
		List<AppFriendship> appFriendships = appFriendshipMapper
				.selectByExampleNewApply(appFriendshipExample);
		return appFriendships;
	}

	@Override
	public List<AppFriendship> getIsFriendList(int uid, Integer gid,String nickname,
			Integer pageNo, Integer pageSize) {
		Map<String, Object> map_para = new HashMap<>();
		List<Integer> values = new ArrayList<Integer>();
		values.add(0,Constants.agreeWithToBeFriend);
//		values.add(1, Constants.wasDeletedFriend);
		map_para.put("status", values);
		map_para.put("uid", uid);
		map_para.put("wasdeletedfriend", 1);
		if (gid != null && gid != 0 ) {
			map_para.put("gid", gid);
		}
		if(nickname!=null && !nickname.equals("")){
			map_para.put("nickname", nickname);
		}
		List<AppFriendship> appFriendships = appFriendshipMapper
				.selectIsFriend(map_para);
		return appFriendships;
	}
	@Override
	public List<AppFriendship> getIsFriendListPassive(int uid, Integer gid,
			Integer pageNo, Integer pageSize) {
		Map<String, Object> map_para = new HashMap<>();
		map_para.put("status", Constants.agreeWithToBeFriend);
		map_para.put("uid", uid);
		if (gid != null && gid != 0 ) {
			map_para.put("gid", gid);
		}
		List<AppFriendship> appFriendships = appFriendshipMapper
				.selectIsFriendPassive(map_para);
		return appFriendships;
	}
	@Override
	public int getFriendAppnum(int uid){
		AppFriendshipExample example = new AppFriendshipExample();
		example.createCriteria().andUid1EqualTo(uid).andStatusEqualTo(0).andStatus1EqualTo(0);
		int num=appFriendshipMapper.countByExample(example);
		return num;
	}
	@Override
	public AppFriendship getNewFriendDetail(String imei, int uid) {
		// TODO Auto-generated method stub
		Map<String, Object> map_para = new HashMap<>();
		map_para.put("imei", imei);
		map_para.put("uid", uid);
		return appFriendshipMapper.selectNewFriend(map_para);
	}

	@Override
	public boolean modifyFriendAlias(int uid0, int uid1, String alias)
			throws ServiceException {
		// TODO Auto-generated method stub
		AppFriendshipExample appFriendshipExample = new AppFriendshipExample();
		appFriendshipExample.createCriteria().andUid0EqualTo(uid0)
				.andUid1EqualTo(uid1);
		List<AppFriendship> appFriendships = appFriendshipMapper
				.selectByExample(appFriendshipExample);
		if (appFriendships.size() > 0) {
			AppFriendship appFriendshipItem = appFriendships.get(0);
			Integer appFriendshipItemStatus = appFriendshipItem.getStatus();
			if (appFriendshipItemStatus == null) {
				return false;
			}
			if (appFriendshipItemStatus.intValue() != Constants.agreeWithToBeFriend) {
				throw new ServiceException(RespCode.CODE_4003);
			} else {
				// appFriendshipExample.createCriteria().
				AppFriendship appFriendship = new AppFriendship();
				appFriendship.setAlias0(alias);
				appFriendship.setUpdateTime(new Date());
				appFriendshipMapper.updateByExampleSelective(appFriendship,
						appFriendshipExample);
				return true;
			}
		} else {
			return false;
		}
	}
	@Override
	public void updatenewFriendShip(List<AppFriendship> list){
		if(list!=null){
			for(int i=0;i<list.size();i++){
				AppFriendship as = (AppFriendship)list.get(i);
				AppFriendshipExample appFriendshipExample = new AppFriendshipExample();
				appFriendshipExample.createCriteria().andIdEqualTo(as.getId());
				AppFriendship appFriendship = new AppFriendship();
				appFriendship.setStatus1(1);
				appFriendshipMapper.updateByExampleSelective(appFriendship,
						appFriendshipExample);
			}
		}
	}
}
