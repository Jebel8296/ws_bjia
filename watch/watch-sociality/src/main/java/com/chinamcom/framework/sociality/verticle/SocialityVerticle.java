package com.chinamcom.framework.sociality.verticle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import sun.misc.BASE64Encoder;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.constants.ServerConstants;
import com.chinamcom.framework.common.exception.ServiceException;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.UploadFileUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.sociality.Runner;
import com.chinamcom.framework.sociality.model.AppFriendship;
import com.chinamcom.framework.sociality.model.AppGroupUser;
import com.chinamcom.framework.sociality.service.AppFriendshipService;
import com.chinamcom.framework.sociality.service.impl.GroupChatServiceImpl;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/03
 */
@Component
public class SocialityVerticle extends BaseVerticle {

	@Autowired
	private AppFriendshipService appFriendshipService;
	@Autowired
	private GroupChatServiceImpl groupChatServiceImpl;

	@Override
	public void start() throws Exception {
		// 添加好友申请（添加到通讯录操作）
		vertx.eventBus().consumer(ServerConstants.SERVER_APPLYTOBEFRIEND_ADD, message -> {
			log.info("message received:" + message.body());
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			Integer uid0 = reqData.getInteger("uid0");
			Integer uid1 = reqData.getInteger("uid1");
			String alias = reqData.getString("alias");
			Integer watchstat = reqData.getInteger("watchstat");//手表端好友显示状态
			String serial_number = getSerialNumber(reqData);
			try {
				if(Integer.parseInt(uid0+"")==Integer.parseInt(uid1+"")){
					String result = respWriter.toError(serial_number,RespCode.CODE_4005);
					message.reply(result);
					return;
				}
				boolean returncode = appFriendshipService.applyToBeFriend(uid0, uid1, alias,watchstat);
				if (returncode) {
					String result = respWriter.toSuccess(serial_number);
					message.reply(result);
				} else {
					String result = respWriter.toError(serial_number);
					message.reply(result);
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				message.reply(respWriter.toError(serial_number, e.getRespCode()));
			}
		});
		// 获取好友申请（新的朋友）列表
		vertx.eventBus().consumer(ServerConstants.SERVER_APPLYTOBEFRIEND_LIST, message -> {
			log.info("message received:" + message.body());
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			Integer uid = reqData.getInteger("uid");
			List<AppFriendship> appFriendships = appFriendshipService.getNewApplyToBeFriendList(uid, null, null);
			/**
			 *遍历list拼接头像地址
			 */
			String ipurl = Runner.config.getProperty("headImageUrl");
			for(int i=0;i<appFriendships.size();i++){
				AppFriendship appship =(AppFriendship)appFriendships.get(i);
				if(appship.getHeadimage() != null && !appship.getHeadimage().contains("http:")){
					appship.setHeadimage(ipurl+appship.getHeadimage());
				}
			}
			appFriendshipService.updatenewFriendShip(appFriendships);
			String result = respWriter.toSuccess(serial_number, appFriendships);
			message.reply(result);
		});
		// （批量）删除好友申请记录
		vertx.eventBus().consumer(ServerConstants.SERVER_APPLYTOBEFRIEND_DEL, message -> {
			log.info("message received:" + message.body());
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			Integer uid0 = reqData.getInteger("uid0");
			String ids = reqData.getString("ids");
			try {
				int r = appFriendshipService.deleteApplyToBeFriendItem(uid0, ids);
				if (r == 0) {
					String result = respWriter.toError(serial_number);
					message.reply(result);
				} else {
					String result = respWriter.toSuccess(serial_number);
					message.reply(result);
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				message.reply(respWriter.toError(serial_number, e.getRespCode()));
			}
		});
		// 接受好友申请（新的朋友）
		vertx.eventBus().consumer(ServerConstants.SERVER_AGREEWITH2FRIEND_OPT, message -> {
			log.info("message received:" + message.body());
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			Integer uid0 = reqData.getInteger("uid0");
			Integer uid1 = reqData.getInteger("uid1");
			try {
				appFriendshipService.agreeWithToBeFriend(uid0, uid1);
				
				String text = "我已添加你为好友，快来畅聊一番";
				ZJSONObject params =new ZJSONObject();
				params.put("uid", uid0);
				params.put("cn", getCn(reqData));
				params.put("to", uid1);
				params.put("type", "MSG.TEXT");
				params.put("body", new BASE64Encoder().encode(text.getBytes()));
				params.put("msgType", "notify");
				//获取当前用户头像
				vertx.eventBus().send("user.appuserinfo.queryappuserinfo", params.toString(),res ->{
					String respBody = null;
					String head = null;
					if(res.succeeded()){
						respBody = res.result().body().toString();
						JSONObject respData = JSON.parseObject(respBody);
						JSONObject userInfo = JSON.parseObject(respData.get("respData").toString());
						JSONObject js = JSON.parseObject(userInfo.get("appuserinfo").toString());
						log.info("个人信息返回："+js);
						head = Runner.config.getProperty("headImageUrl")+js.get("headimage");
						params.put("head", head);
						log.info("添加好友后，推送添加成功通知："+params.toString());
						vertx.eventBus().send("data.message.post", params.toString());
					}else{
						params.put("head", head);
						log.info("添加好友后，推送添加成功通知："+params.toString());
						log.error(res.cause().getMessage(), res.cause());
						vertx.eventBus().send("data.message.post", params.toString());
					}
				});
				//同意成为好友以后修改手表端好友列表显示
				ZJSONObject params1 =new ZJSONObject();
				params.put("uid", uid0);
				vertx.eventBus().send("user.friendshow.push", params1.toString());
				ZJSONObject params2 =new ZJSONObject();
				params2.put("uid", uid1);
				vertx.eventBus().send("user.friendshow.push", params2.toString());
				String result = respWriter.toSuccess(serial_number);
				message.reply(result);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				message.reply(respWriter.toError(serial_number, e.getRespCode()));
			}
		});
		// 手表蓝牙添加新的好友
		vertx.eventBus().consumer(ServerConstants.SERVER_AGREEWITH2FRIEND_BLUETOOTH_OPT, message -> {
			log.info("手表蓝牙好友申请:" + message.body());
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			Integer uid0 = reqData.getInteger("uid0");
			Integer uid1 = reqData.getInteger("uid1");
			try {
				appFriendshipService.blueToothAgreeWithToBeFriend(uid0, uid1);
				ZJSONObject params =new ZJSONObject();
				params.put("uid", uid0);
				vertx.eventBus().send("user.friendshow.push", params.toString());
				ZJSONObject params2 =new ZJSONObject();
				params2.put("uid", uid1);
				vertx.eventBus().send("user.friendshow.push", params2.toString());
				String result = respWriter.toSuccess(serial_number);
				message.reply(result);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				message.reply(respWriter.toError(serial_number, e.getRespCode()));
			}
		});
		// 拒绝好友申请
		vertx.eventBus().consumer(ServerConstants.SERVER_REFUSE2FRIEND_OPT, message -> {
			log.info("message received:" + message.body());
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			Integer uid0 = reqData.getInteger("uid0");
			Integer uid1 = reqData.getInteger("uid1");
			try {
				boolean r = appFriendshipService.refuseToBeFriend(uid0, uid1);
				log.info("r\t" + r);
				if (r) {
					String result = respWriter.toSuccess(serial_number);
					message.reply(result);
				} else {
					String result = respWriter.toError(serial_number, "根据请求参数未查询到相关数据");
					message.reply(result);
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				message.reply(respWriter.toError(serial_number, e.getRespCode()));
			}
		});
		// 搜索好友
		vertx.eventBus().consumer(ServerConstants.SERVER_FRIEND_SEARCH, message -> {
			log.info("message received:" + message.body());
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			String imei = reqData.getString("imei");
			Integer uid = reqData.getInteger("uid");
			AppFriendship appFriendship = appFriendshipService.getNewFriendDetail(imei, uid);
			/*System.out.println(appFriendship == null);*/
			if (appFriendship == null) {
				String result = respWriter.toError(serial_number, "找不到记录");
				message.reply(result);
			} else {
				if(appFriendship.getHeadimage() != null && !appFriendship.getHeadimage().contains("http:")){
					String ipurl = Runner.config.getProperty("headImageUrl");
					appFriendship.setHeadimage(ipurl+appFriendship.getHeadimage());
				}
				String result = respWriter.toSuccess(serial_number, appFriendship);
				message.reply(result);
			}
		});
		// 修改好友备注
		vertx.eventBus().consumer(ServerConstants.SERVER_MODIFYALIAS_OPT, message -> {
			log.info("message received:" + message.body());
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			String alias = reqData.getString("alias");
			Integer uid0 = reqData.getInteger("uid0");
			Integer uid1 = reqData.getInteger("uid1");
			boolean r;
			try {
				r = appFriendshipService.modifyFriendAlias(uid0, uid1, alias);
				if (r) {
					ZJSONObject params =new ZJSONObject();
					params.put("uid", uid0);
					vertx.eventBus().send("user.friendshow.push", params.toString());
					String result = respWriter.toSuccess(serial_number);
					message.reply(result);
				} else {
					String result = respWriter.toError(serial_number, "根据请求参数未查询到相关数据");
					message.reply(result);
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				message.reply(respWriter.toError(serial_number, e.getRespCode()));
			}

		});
		//删除好友
		vertx.eventBus().consumer(ServerConstants.SERVER_FRIEND_DEL, message -> {
			log.info("message received:" + message.body());
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			Integer uid = reqData.getInteger("uid");
			String ids = reqData.getString("ids");
			int r;
			try {
				r = appFriendshipService.deleteByIds(uid, ids);
				if (r > 0) {
					ZJSONObject params =new ZJSONObject();
					params.put("uid", uid);
					vertx.eventBus().send("user.friendshow.push", params.toString());
					String result = respWriter.toSuccess(serial_number);
					message.reply(result);
				} else {
					String result = respWriter.toError(serial_number);
					message.reply(result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// 获取好友列表
		vertx.eventBus().consumer(ServerConstants.SERVER_FRIEND_LIST, message -> {
			log.info("message received:" + message.body());
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			Integer uid0 = reqData.getInteger("uid");
			Integer gid = reqData.getInteger("gid");//群id、好有列表不传
			String  nickname = reqData.getString("nickname");
			/*List<com.chinamcom.framework.sociality.model.AppFriendship> list = appFriendshipService
					.getIsFriendList(uid0,gid, null, null);
			if (list == null || list.size() == 0) {
				String result = respWriter.toError(serial_number, "无好友");
				message.reply(result);
			} else {
				String result = respWriter.toSuccess(serial_number, list);
				message.reply(result);
			}*/
			try{
				List<com.chinamcom.framework.sociality.model.AppFriendship> list = appFriendshipService
						.getIsFriendList(uid0,gid,nickname, null, null);
				/*List<com.chinamcom.framework.sociality.model.AppFriendship> listpassive = appFriendshipService
						.getIsFriendListPassive(uid0,gid, null, null);
			    list.addAll(listpassive);*/
				/**
				 *遍历list拼接头像地址
				 */
				String ipurl = Runner.config.getProperty("headImageUrl");
				for(int i=0;i<list.size();i++){
					AppFriendship appship =(AppFriendship)list.get(i);
					if(appship.getHeadimage() != null && !appship.getHeadimage().contains("http:")){
						appship.setHeadimage(ipurl+appship.getHeadimage());
					}
				}
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("arraylist", list);
				map.put("friendRequest", appFriendshipService.getFriendAppnum(uid0));
				//map.put("fileUrl", Runner.config.getProperty("uploadFile.host", "127.0.0.1")+"/"+Runner.config.getProperty("uploadFile.port", "40020")+"/");
				String result = respWriter.toSuccess(serial_number, map); /* list*/
				message.reply(result);
			}catch(Exception e){
				log.error(e.getMessage(), e);
				String result = respWriter.toError(serial_number, RespCode.CODE_500);
				message.reply(result);
			}

		});
		/**   此接口调换到ContactsVerticle.java中
		//同步到通讯录
		vertx.eventBus()
		.consumer(
				ServerConstants.MOLDEL_SYNCHRONIZE_COMMUNICATION,
				message -> {
					log.info(message.body());
					ZJSONObject params = Json.decode(message.body().toString(), ZJSONObject.class);
					String serial_number = getSerialNumber(params);
					try {
						Integer uid = params.getInteger("uid");
						AppFriendship imeid=appFriendshipService.getNewFriendDetail(null, uid);
						if(imeid.getImei() == null){
							String result = respWriter.toError(
									serial_number, "根据uid，查询不到imei");
							message.reply(result);
							return;
						}
						List<AppFriendship> list = appFriendshipService.getIsFriendList(uid, 0,null,null, null);
						List<Contact> listContact = new ArrayList<Contact>();
						for(AppFriendship af : list){
							Contact c = new Contact();
							c.setId(af.getUid1());
							c.setName(af.getAlias0());
							c.setIcon(0);
							c.setPhone(af.getPhonenum());
							listContact.add(c);
						}
						JSONObject p = new JSONObject();
						p.put("cmd", "L5");// sociality.synchronize.communitcation
						p.put("uid", uid);
						p.put("to", imeid);
						JSONObject jsonBody = new JSONObject();
						jsonBody.put("data", listContact);
						p.put("body", jsonBody);
						vertx.eventBus().send("device.data.push", p.toString());
						String result = respWriter.toSuccess(serial_number);
						message.reply(result);
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println(ex == null);
						System.out.println(ex.getMessage());
						log.error(ex.getMessage());
						String result = respWriter.toError(serial_number);
						message.reply(result);
					}
				});
		**/
		// 创建群聊
		vertx.eventBus().consumer(ServerConstants.SERVER_GROUPCHAT_GROUP_CREATE, message -> {
			log.info(">>>>groupchat group create received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			try {
				StringBuilder uris = new StringBuilder();
				JSONArray users = reqData.getJSONArray("user");// 群聊成员
				Integer uid = reqData.getInteger("uid");// 创建者ID
//				String did = reqData.containsKey("did") ? reqData.getString("did") : "";
				//String head = reqData.containsKey("head") ? reqData.getString("head") : "download/default";// 创建者头像
				//根据UI查询用户头像
				String head = groupChatServiceImpl.selectHeadByUid(uid);
				//head = head.substring(head.indexOf("download/") + 9);
				uris.append(head);
				if (users != null && uid != null) {
					for(int i=0;i<users.size();i++){
						if(i>2){
							break;
						}
						JSONObject u = users.getJSONObject(i);
						String h = groupChatServiceImpl.selectHeadByUid(u.getInteger("uid"));
						//h = h.substring(h.indexOf("download/") + 9);
						uris.append(",").append(h);
					}
					Map<String, String> param = new HashMap<String, String>();
					param.put("uris",uris.toString());
					log.info("param:" + param);
					UploadFileUtil.combineAvatar(vertx, Runner.config.getProperty("uploadFile.host", "127.0.0.1"),
							new Integer(Runner.config.getProperty("uploadFile.port", "40020")), param, resp -> {
								if(resp.statusCode() == 200){
									resp.bodyHandler(body -> {
										ZJSONObject uploadFileData = Json.decode(body.toString(), ZJSONObject.class);
										log.info("uploadFile:" + uploadFileData);
										String code = uploadFileData.getString("code");
										if("0".equals(code)){
											ZJSONObject uploadFileDataRespData = Json.decode(uploadFileData.getString("obj"), ZJSONObject.class);
											String headuri = uploadFileDataRespData.getString("uri");
											String reply = null;
											try {
												reply = groupChatServiceImpl.insertCreateAppGroup(headuri,serial_number, reqData);
												// 同步群信息 因创建时手表默认不接收此群信息，暂时不同步
												/**
												ZJSONObject mess = Json.decode(reply, ZJSONObject.class);
												if (mess.containsKey("code") && mess.getInteger("code") == 200) {
													vertx.eventBus().send("device.data.push", groupChatServiceImpl.postToWatchApp(uid, did));
												}
												*/
												message.reply(reply);
											} catch (Exception e) {
												log.info("创建群聊失败,详细信息如下：");
												log.error(e);
												message.reply(respWriter.toError(serial_number));
											}
										}else{
											log.info("调用头像接口时，生成群组头像失败。");
											message.reply(respWriter.toError(serial_number));
										}
									});
								}else{
									log.info("调用头像接口失败，请检查头像服务是否启用。");
									log.info(resp.statusCode() + ":" + resp.statusMessage());
									message.reply(respWriter.toError(serial_number));
								}
							});
				}else{
					log.info("请求参数不符合规范，请重新提交。");
					message.reply(respWriter.toError(serial_number));
				}
			} catch (Exception e) {
				log.error(e);
				message.reply(respWriter.toError(serial_number));
			}
		});
		// 添加群聊成员
		vertx.eventBus().consumer(ServerConstants.SERVER_GROUPCHAT_MEMBER_ADD, message -> {
			log.info(">>>>groupchat member add received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			String reply = groupChatServiceImpl.insertBatchAppGroupUser(serial_number, reqData);
			// 同步群信息
			ZJSONObject mess = Json.decode(reply, ZJSONObject.class);
			if (mess.containsKey("code") && mess.getInteger("code") == 200) {
				if (reqData.containsKey("user")) {
					JSONArray users = reqData.getJSONArray("user");
					users.forEach(item->{
						log.info("添加群聊成员，同步信息开始.");
						JSONObject user = (JSONObject) item;
						Integer uid = user.getInteger("uid");
						vertx.eventBus().send("device.data.push", groupChatServiceImpl.postToWatchApp(uid, null));
					});
				}
			}
			message.reply(reply);
		});
		// 退出群聊
		vertx.eventBus().consumer(ServerConstants.SERVER_GROUPCHAT_GROUP_QUIT, message -> {
			log.info(">>>>groupchat group quit received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			String reply = groupChatServiceImpl.updateQuitAppGroup(vertx,serial_number, reqData);
			message.reply(reply);
		});
		// 屏蔽群聊
		vertx.eventBus().consumer(ServerConstants.SERVER_GROUPCHAT_GROUP_SCREEN, message -> {
			log.info(">>>>groupchat group screen received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			message.reply(groupChatServiceImpl.updateScreenAppGroup(serial_number, reqData));
		});
		// 群聊名称编辑
		vertx.eventBus().consumer(ServerConstants.SERVER_GROUPCHAT_GROUP_MODIFY, message -> {
			log.info(">>>>groupchat group modify received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			String reply = groupChatServiceImpl.updateModifyAppGroupName(serial_number, reqData);
			// 同步群信息
			ZJSONObject mess = Json.decode(reply, ZJSONObject.class);
			if (mess.containsKey("code") && mess.getInteger("code") == 200) {
				Optional<List<AppGroupUser>> groups = Optional.ofNullable(groupChatServiceImpl.selectAppGroupUser(reqData.getInteger("gid")));
				if (groups.isPresent()) {
					groups.get().forEach(item -> {
						log.info("修改群聊名称，同步信息开始.");
						vertx.eventBus().send("device.data.push",groupChatServiceImpl.postToWatchApp(item.getUid(), null));
					});
				}
			}	
			message.reply(reply);
		});
		// 删除群聊成员
		vertx.eventBus().consumer(ServerConstants.SERVER_GROUPCHAT_MEMBER_DEL, message -> {
			log.info(">>>>groupchat member del received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			String reply = groupChatServiceImpl.updateDelAppGroupUser(serial_number, reqData);
			// 同步群信息
			ZJSONObject mess = Json.decode(reply, ZJSONObject.class);
			if (mess.containsKey("code") && mess.getInteger("code") == 200) {
				if (reqData.containsKey("user")) {
					JSONArray users = reqData.getJSONArray("user");
					users.forEach(item -> {
						log.info("删除群聊成员，同步信息开始.");
						JSONObject user = (JSONObject) item;
						vertx.eventBus().send("device.data.push", groupChatServiceImpl.postToWatchApp(user.getInteger("uid"), null));
					});
				}
			}
			message.reply(reply);
		});
		// 修改群聊昵称
		vertx.eventBus().consumer(ServerConstants.SERVER_GROUPCHAT_MEMBER_MODIFY, message -> {
			log.info(">>>>groupchat member modify received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			String reply = groupChatServiceImpl.updateModifyAppGroupUserAlias(serial_number, reqData);
			message.reply(reply);
		});
		// 群聊成员列表
		vertx.eventBus().consumer(ServerConstants.SERVER_GROUPCHAT_MEMBER_SEARCH, message -> {
			log.info(">>>>groupchat member search received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			message.reply(groupChatServiceImpl.selectSearchAppGroupUser(serial_number, reqData));
		});
		// 设置手表接收群消息
		vertx.eventBus().consumer(ServerConstants.SERVER_GROUPCHAT_MEMBER_SETWATCH, message -> {
			log.info(">>>>groupchat member setwatch received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serial_number = getSerialNumber(reqData);
			String reply = groupChatServiceImpl.updateSetWatchReceiveAppGroup(serial_number, reqData);
			// 同步群信息
			ZJSONObject mess = Json.decode(reply, ZJSONObject.class);
			if (mess.containsKey("code") && mess.getInteger("code") == 200) {
				log.info("设置手表接收群消息，同步信息开始.");
				Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;
				String did = reqData.containsKey("did") ? reqData.getString("did") : null;
				vertx.eventBus().send("device.data.push", groupChatServiceImpl.postToWatchApp(uid, did));
			}
			message.reply(reply);
		});
		//根据UID推送群信息
		vertx.eventBus().consumer(ServerConstants.SERVER_GROUPCHAT_SEND_BYUID, message -> {
			log.info(">>>>groupchat send byuid received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;
			vertx.eventBus().send("device.data.push", groupChatServiceImpl.postToWatchAppByUID(uid));
		});

	}
}
