package com.chinamcom.framework.sociality.service.impl;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.constants.Constants;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.sociality.Runner;
import com.chinamcom.framework.sociality.dao.GroupChatMapper;
import com.chinamcom.framework.sociality.model.AppGroup;
import com.chinamcom.framework.sociality.model.AppGroupUser;
import com.chinamcom.framework.sociality.service.IGroupChatService;

@Service
public class GroupChatServiceImpl extends BaseService implements IGroupChatService {

	@Autowired
	private GroupChatMapper groupChatMapper;
	
	@Override
	public String insertCreateAppGroup(String headuri,String serialNumber, ZJSONObject reqData) throws Exception {
		String message = null;
		List<AppGroup> groups = new ArrayList<AppGroup>();
		List<AppGroupUser> list = new ArrayList<AppGroupUser>();
		try {
			Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;
			AppGroup group = new AppGroup();
			group.setCreateUid(uid);
			if (uid != 0) {
				groups = groupChatMapper.selectAppGroups(group);
				log.info(">>>>" + Json.encode(groups) + "<<<<");
				if (!groups.isEmpty() && groups.size() >= 5) {
					message = respWriter.toError(serialNumber, "您已超过5个群组!");
				} else {
					group.setName(reqData.getString("name"));
					group.setHead(headuri != null ? headuri : "/v0/v2/50000000.icon");
					group.setStatus(Constants.GROUPCHAT_GROUP_STATUS_ACTIVE);
					group.setDescription(reqData.containsKey("desc") ? reqData.getString("desc") : "群聊");
					groupChatMapper.insertAppGroup(group);// 创建群组
					log.info(">>>>groupid:" + group.getId() + "<<<<");
					AppGroupUser user = new AppGroupUser();
					user.setGid(group.getId());
					user.setUid(uid);
					user.setStatus(Constants.GROUPCHAT_MEMBER_STATUS_ACTIVE);
					list.add(user);
					if (reqData.containsKey("user")) {
						JSONArray array = reqData.getJSONArray("user");
						array.forEach(item -> {
							JSONObject u = (JSONObject) item;
							AppGroupUser agu = new AppGroupUser();
							agu.setGid(group.getId());
							agu.setUid(u.getInteger("uid"));
							agu.setStatus(Constants.GROUPCHAT_MEMBER_STATUS_ACTIVE);
							agu.setAlias(u.getString("name"));
							list.add(agu);
						});
					}
					groupChatMapper.insertBatchAppGroupUser(list);// 成员加入群组
					log.info(">>>>add member to groupid =" + group.getId() + ",users=" + Json.encode(list) + "<<<<");
					message = respWriter.toSuccess(serialNumber, new JsonObject(Json.encode(group)).getMap());
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return message != null ? message : respWriter.toError(serialNumber);
	}

	@Override
	public String insertBatchAppGroupUser(String serialNumber, ZJSONObject reqData) {
		String message = null;
		JSONArray users = null;
		List<AppGroupUser> list = new ArrayList<AppGroupUser>();
		try {
			Integer gid = reqData.containsKey("gid") ? reqData.getInteger("gid") : 0;// 群组ID
			if (gid != 0) {
				if (reqData.containsKey("user")) {
					users = reqData.getJSONArray("user");
					log.info(">>>>add before : groupid =" + gid + ",users=" + users + "<<<<");
					users.forEach(item -> {
						JSONObject u = (JSONObject) item;
						AppGroupUser user = new AppGroupUser();
						user.setGid(gid);
						user.setUid(u.getInteger("uid"));
						user.setStatus(Constants.GROUPCHAT_MEMBER_STATUS_ACTIVE);
						user.setAlias(u.containsKey("alias") ? u.getString("alias") : u.getString("name"));
						list.add(user);
					});
					groupChatMapper.insertBatchAppGroupUser(list);
					log.info(">>>>add after : groupid =" + gid + ",users=" + Json.encode(list) + "<<<<");
					message = respWriter.toSuccess(serialNumber);
				} else {
					message = respWriter.toError(serialNumber, "请选择要添加的成员.");
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return message != null ? message : respWriter.toError(serialNumber);
	}

	@Override
	public String updateQuitAppGroup(Vertx vertx,String serialNumber, ZJSONObject reqData) {
		String message = null;
		List<AppGroup> groups = new ArrayList<AppGroup>();
		try {
			Integer gid = reqData.containsKey("gid") ? reqData.getInteger("gid") : 0;// 群组ID
			Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;// 用户ID
			if (gid != 0 && uid != 0) {
				AppGroup group = new AppGroup();
				group.setId(gid);
				group.setCreateUid(uid);
				groups = groupChatMapper.findAppGroupByUid(group);
				if (!groups.isEmpty()) {
					group.setStatus(Constants.GROUPCHAT_GROUP_STATUS_INACTIVE);// 解散状态
					groupChatMapper.deleteAppGroup(group);
					log.info(">>>> status of groupid:" + gid + " is inactive<<<<");
					//同步群信息
					Optional<List<AppGroupUser>> gs = Optional.ofNullable(selectAppGroupUser(gid));
					if (gs.isPresent()) {
						gs.get().forEach(item -> {
							log.info("退出群聊，同步信息开始.");
							vertx.eventBus().send("device.data.push",postToWatchApp(item.getUid(), null));
						});
					}
				} else {
					List<AppGroupUser> list = new ArrayList<AppGroupUser>();
					AppGroupUser user = new AppGroupUser();
					user.setGid(gid);
					user.setUid(uid);
					user.setStatus(Constants.GROUPCHAT_MEMBER_STATUS_QUIT);// 退群状态
					list.add(user);
					groupChatMapper.deleteBatchAppGroupUser(list);
					log.info(">>>> status of userid:" + uid + " is quit for groupid:" + gid + "<<<<");
					// 同步群信息
					log.info("退出群聊，同步信息开始.");
					String did = reqData.containsKey("did") ? reqData.getString("did") : null;
					vertx.eventBus().send("device.data.push", postToWatchApp(uid, did));
				}
				message = respWriter.toSuccess(serialNumber);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return message != null ? message : respWriter.toError(serialNumber);
	}

	@Override
	public String updateScreenAppGroup(String serialNumber, ZJSONObject reqData) {
		String message = null;
		try {
			Integer gid = reqData.containsKey("gid") ? reqData.getInteger("gid") : 0;// 群组ID
			Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;// 用户ID
			if (gid != 0 && uid != 0) {
				List<AppGroupUser> list = new ArrayList<AppGroupUser>();
				AppGroupUser user = new AppGroupUser();
				user.setGid(gid);
				user.setUid(uid);
				user.setScreenstat(reqData.containsKey("stat") ? reqData.getInteger("stat") : 0);
				list.add(user);
				groupChatMapper.deleteBatchScreenstatAppGroupUser(list);
				log.info(">>>> screenstat of userid:" + uid + " is " + reqData.getInteger("stat") + " for groupid:" + gid + "<<<<");
				message = respWriter.toSuccess(serialNumber);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return message != null ? message : respWriter.toError(serialNumber);
	}

	@Override
	public String updateDelAppGroupUser(String serialNumber, ZJSONObject reqData) {
		String message = null;
		try {
			Integer gid = reqData.containsKey("gid") ? reqData.getInteger("gid") : 0;// 群组ID
			JSONArray users = null;
			List<AppGroupUser> list = new ArrayList<AppGroupUser>();
			if (gid != 0) {
				if (reqData.containsKey("user")) {
					users = reqData.getJSONArray("user");
					log.info(">>>>del before groupid =" + gid + ",users=" + users + "<<<<");
					users.forEach(item -> {
						JSONObject u = (JSONObject) item;
						AppGroupUser user = new AppGroupUser();
						user.setGid(gid);
						user.setUid(u.getInteger("uid"));
						user.setStatus(Constants.GROUPCHAT_MEMBER_STATUS_QUIT);
						list.add(user);
					});
					groupChatMapper.deleteBatchAppGroupUser(list);
					log.info(">>>>del after groupid =" + gid + ",users=" + users + "<<<<");
					message = respWriter.toSuccess(serialNumber);
				} else {
					message = respWriter.toError(serialNumber, "请选择要删除的成员.");
				}
			}

		} catch (Exception e) {
			log.error(e);
		}
		return message != null ? message : respWriter.toError(serialNumber);
	}

	@Override
	public String updateModifyAppGroupName(String serialNumber, ZJSONObject reqData) {
		String message = null;
		try {
			Integer gid = reqData.containsKey("gid") ? reqData.getInteger("gid") : 0;// 群组ID
			Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;// 用户ID
			String name = reqData.containsKey("name") ? reqData.getString("name") : null;// 群组名称
			if (gid != 0 && uid != 0) {
				AppGroup group = new AppGroup();
				group.setId(gid);
				group.setCreateUid(uid);
				group.setName(name);
				groupChatMapper.updateAppGroupName(group);
				log.info(">>>> name of gid:" + gid + " is " + name + "<<<<");
				message = respWriter.toSuccess(serialNumber);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return message != null ? message : respWriter.toError(serialNumber);
	}

	public String updateModifyAppGroupUserAlias(String serialNumber, ZJSONObject reqData) {
		String message = null;
		try {
			Integer gid = reqData.containsKey("gid") ? reqData.getInteger("gid") : 0;// 群组ID
			Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;// 用户ID
			String alias = reqData.containsKey("alias") ? reqData.getString("alias") : null;// 成员昵称
			if (uid != 0 && gid != 0) {
				AppGroupUser user = new AppGroupUser();
				user.setGid(gid);
				user.setUid(uid);
				user.setAlias(alias);
				groupChatMapper.updateAppGroupUserAlias(user);
				log.info(">>>> alias of uid:" + uid + " is " + alias + " for groupid:" + gid + "<<<<");
				message = respWriter.toSuccess(serialNumber);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return message != null ? message : respWriter.toError(serialNumber);
	}

	public String selectSearchAppGroupUser(String serialNumber, ZJSONObject reqData) {
		String message = null;
		try {
			Integer gid = reqData.containsKey("gid") ? reqData.getInteger("gid") : 0;// 群组ID
			Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;// 用户ID
			if (gid != 0) {
				List<AppGroupUser> users = new ArrayList<AppGroupUser>();
				AppGroupUser user = new AppGroupUser();
				user.setGid(gid);
				user.setStatus(Constants.GROUPCHAT_MEMBER_STATUS_QUIT);
				users.clear();
				users = groupChatMapper.selectAppGroupUsers(user);
				//TODO
				List<AppGroupUser> us = new ArrayList<AppGroupUser>();
				users.forEach(item -> {
					if(item.getHeadimage() != null && !item.getHeadimage().contains("http:")){
//						String head = "http://" + Runner.config.getProperty("uploadFile.host", "127.0.0.1") + ":"
//								+ Runner.config.getProperty("uploadFile.port", "40020") + "/download/"
//								+ item.getHeadimage();
						String head = Runner.config.getProperty("headImageUrl")+ item.getHeadimage();
						item.setHeadimage(head);
					}
					
					if(StringUtils.isEmpty(item.getAlias())){
						Map<String , Object> map = new HashMap<String , Object>();
						map.put("uid_0", uid);
						map.put("uid_1", item.getUid());
						String alias0 = groupChatMapper.selectFriendAlias(map);
						if(StringUtils.isEmpty(alias0)){
							String nickname = groupChatMapper.selectUserNickname(item.getUid());
							item.setAlias(nickname);	
						}else{
							item.setAlias(alias0);	
						}
					}
					us.add(item);
				});
				log.info(">>>> members of gid:" + gid + " has " + Json.encode(us) + "<<<<");
				message = respWriter.toSuccess(serialNumber, us);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return message != null ? message : respWriter.toError(serialNumber);
	}

	@Override
	public String updateSetWatchReceiveAppGroup(String serialNumber, ZJSONObject reqData) {
		String message = null;
		try {
			Integer gid = reqData.containsKey("gid") ? reqData.getInteger("gid") : 0;// 群组ID
			Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;// 用户ID
			Integer stat = reqData.containsKey("stat") ? reqData.getInteger("stat") : 0;// 状态
			if (gid != 0 && uid != 0) {
				List<AppGroupUser> list = new ArrayList<AppGroupUser>();
				AppGroupUser user = new AppGroupUser();
				user.setUid(uid);
				
				Integer gnum = 0;
				if(stat==1){
					gnum = groupChatMapper.selectAppGroupCountByWatchstat(user);	
				}
				if (gnum > 4) {
					message = respWriter.toError(serialNumber, "此设置已超过5个群限制!");
				} else {
					user.setGid(gid);
					user.setWatchstat(reqData.containsKey("stat") ? reqData.getInteger("stat") : 0);
					list.add(user);
					groupChatMapper.deleteBatchWatchstatAppGroupUser(list);
					log.info(">>>> watchstat of userid:" + uid + " is " + reqData.getInteger("stat") + " for groupid:"+ gid + "<<<<");
					message = respWriter.toSuccess(serialNumber);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return message != null ? message : respWriter.toError(serialNumber);
	}
	
	@Override
	public String postToWatchApp(Integer uid, String did) {
		JsonObject cmd = new JsonObject();
		AppGroup group = new AppGroup();
		group.setCreateUid(uid);
		Optional.ofNullable(groupChatMapper.selectAllAppGroupsByUID(group)).ifPresent(list -> {
			JsonObject body = new JsonObject();
			JsonArray data = new JsonArray();
			list.forEach(item -> {
				data.add(new JsonObject().put("id", item.getId()).put("name", item.getName()));
			});
			body.put("uid", uid);
			if (did != null) {
				body.put("did", did);
			}
			body.put("data", data);
			cmd.put("cmd", "L11");
			cmd.put("uid", uid);
			if (did != null) {
				cmd.put("did", did);
			}
			cmd.put("to", uid.toString());
			cmd.put("body", body);
			cmd.put("ack", 0);

			log.info(">>>>sync group data send:" + cmd + "<<<<");
		});
		return cmd.toString();
	}

	@Override
	public String postToWatchAppByUID(Integer uid) {
		JsonObject cmd = new JsonObject();
		AppGroup group = new AppGroup();
		group.setCreateUid(uid);
		Optional.ofNullable(groupChatMapper.selectAllAppGroupsByUID(group)).ifPresent(list -> {
			JsonObject body = new JsonObject();
			JsonArray data = new JsonArray();
			list.forEach(item -> {
				data.add(new JsonObject().put("id", item.getId()).put("name", item.getName()));
			});
			body.put("uid", uid);
			body.put("data", data);
			cmd.put("cmd", "L11");
			cmd.put("uid", uid);
			cmd.put("to", uid.toString());
			cmd.put("body", body);
			cmd.put("ack", 0);
			log.info(">>>>group send byuid :" + cmd + "<<<<");
		});
		return cmd.toString();
	}

	@Override
	public String selectHeadByUid(Integer uid) {
		String head = "default";
		Optional<String> headimage = Optional.ofNullable(groupChatMapper.selectHeadByUid(uid));
		if (headimage.isPresent()) {
			head = headimage.get();
			if (head.equals("null") || head.equals("")) {
				head = "default";
			}
		}
		return head;
	}

	@Override
	public List<AppGroupUser> selectAppGroupUser(Integer gid) {
		List<AppGroupUser> users = null;
		try {
			AppGroupUser user = new AppGroupUser();
			user.setGid(gid);
			user.setStatus(2);
			users = groupChatMapper.selectAppGroupUsers(user);
		} catch (Exception e) {
			log.error(e);
		}
		return users;
	};
	
}
