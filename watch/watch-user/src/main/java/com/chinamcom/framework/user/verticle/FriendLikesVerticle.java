package com.chinamcom.framework.user.verticle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.ApplicationMybatis;
import com.chinamcom.framework.common.constants.ServerConstants;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.FriendLikesInfo;
import com.chinamcom.framework.user.service.FriendLikesService;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/09/27
 */
@Component
public class FriendLikesVerticle extends BaseVerticle {

	@Autowired
	private FriendLikesService friendsLikesService;
	
	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer(ServerConstants.USER_FRIEND_LIKES_LIST, message-> {
			log.info(ServerConstants.USER_FRIEND_LIKES_LIST + ": " + message.body());
			String sn = "";
			try{
				ZJSONObject request = Json.decode(message.body().toString(),ZJSONObject.class);
				sn = getSerialNumber(request);
				Integer uid = request.getInteger("uid");
				List<FriendLikesInfo> list = friendsLikesService.getFriendLikes(uid);
				Map<String,Object> result = new HashMap<String, Object>();
				if(list != null && list.size() > 0){
					if(list.size() == 6){
						for(int i = 0; i < list.size(); i++){
							FriendLikesInfo friendLikesInfo = list.get(i);
							if(friendLikesInfo.getHeadImg() != null){
								if(!friendLikesInfo.getHeadImg().contains("http://")){
									friendLikesInfo.setHeadImg(ApplicationMybatis.config.getProperty("headImageUrl") + friendLikesInfo.getHeadImg());
								}
							}
//							friendLikesInfo.setRank(i + 1);
							if(friendLikesInfo.getUid().intValue() == uid.intValue()){
								result.put("curUser", friendLikesInfo.clone());
							}
						}
						list.remove(5);
						result.put("list", list);
					}else{
						for(int i = 0; i < list.size(); i++){
							FriendLikesInfo friendLikesInfo = list.get(i);
							if(friendLikesInfo.getHeadImg() != null){
								if(!friendLikesInfo.getHeadImg().contains("http://")){
									friendLikesInfo.setHeadImg(ApplicationMybatis.config.getProperty("headImageUrl") + friendLikesInfo.getHeadImg());
								}
							}
//							friendLikesInfo.setRank(i + 1);
							if(friendLikesInfo.getUid().intValue() == uid.intValue()){
								result.put("curUser", friendLikesInfo.clone());
							}
						}
						result.put("list", list);
					}
				}
				message.reply(respWriter.toSuccess(sn, result));
			}catch(Exception ex){
				ex.printStackTrace();
				log.error(ex.getMessage(), ex);
				message.reply(respWriter.toError(sn, ex.getMessage()));
			}
		});
		
		vertx.eventBus().consumer(ServerConstants.USER_FRIEND_LIKES_OPEATER, message->{
			log.info(ServerConstants.USER_FRIEND_LIKES_OPEATER + ": " + message.body());
			String sn = "";
			try{
				ZJSONObject request = Json.decode(message.body().toString(),ZJSONObject.class);
				sn = getSerialNumber(request);
				Integer uid = request.getInteger("uid");
				Integer friendUid = request.getInteger("friendUid");
				Integer status = request.getInteger("status");
				friendsLikesService.clickFriendLikes(uid, friendUid, status);
				message.reply(respWriter.toSuccess(sn));
			}catch(Exception ex){
				ex.printStackTrace();
				log.error(ex.getMessage(), ex);
				message.reply(respWriter.toError(sn, ex.getMessage()));
			}
		});
	}
}
