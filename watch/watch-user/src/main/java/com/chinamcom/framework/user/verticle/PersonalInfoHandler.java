package com.chinamcom.framework.user.verticle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//import net.sf.json.JSONArray;
//import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chinamcom.framework.ApplicationMybatis;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.common.utils.UploadFileUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.AppUserInfo;
import com.chinamcom.framework.user.dao.BaseTags;
import com.chinamcom.framework.user.dao.PersonalityTag;
import com.chinamcom.framework.user.service.BaseTagsService;
import com.chinamcom.framework.user.service.LoginService;
import com.chinamcom.framework.user.service.PersonalInfoService;
import com.chinamcom.framework.user.service.PersonalityTagService;

/**
 * 
 * 个人资料接口
 * 
 * **/
@Component
public class PersonalInfoHandler extends BaseVerticle {
	private static Logger logger = Logger.getLogger(PersonalInfoHandler.class);

	/*
	 * @Autowired private TransactionTemplate sjc;
	 * 
	 * @Autowired private DataSource dataSource;
	 * 
	 * @Qualifier(value="jdbcTemplate11") private JdbcTemplate jdbcTemplate;
	 */
	@Autowired
	private PersonalInfoService personalInfoService;

	@Autowired
	private PersonalityTagService personalityTagService;
	@Autowired
	private BaseTagsService baseTagsService;
	/*
	 * @Autowired private TargetCountService targetCountService;
	 */

	@Autowired
	private LoginService loginService;

	public PersonalInfoHandler() {

	}

	@Override
	public void start() throws Exception {
		// 获取个人资料
		Properties config = ApplicationMybatis.config;

		vertx.eventBus()
				.consumer(
						"user.personalinfohandler.getPersonalInfo",
						message -> {
							ZJSONObject request = Json.decode(message.body()
									.toString(), ZJSONObject.class);
							String result = "";
							logger.info("查询个人资料请求：" + message.body());
							Integer uid = request.getInteger("uid");
							AppUserInfo appUserInfo = personalInfoService
									.getAppUserInfoByUid(uid);
							List<PersonalityTag> list = personalityTagService
									.findPersonalTag(uid);
							List<Map<String, Object>> tags = new ArrayList<Map<String, Object>>();
							if (list != null && list.size() > 0) {
								for (int i = 0; i < list.size(); i++) {
									Map<String, Object> tagMap = new HashMap<String, Object>();
									tagMap.put("tagId", list.get(i).getTagId());
									tagMap.put("tagName", list.get(i)
											.getTagName());
									tagMap.put("uid", list.get(i).getUid());
									tags.add(tagMap);
								}
							}
							if (appUserInfo != null) {
								if (appUserInfo.getHeadimage() != null
										&& !appUserInfo.getHeadimage().equals(
												"")
										&& !appUserInfo.getHeadimage()
												.startsWith("http://")) {
									String headUrl = config
											.getProperty("headImageUrl")
											+ appUserInfo.getHeadimage();
									appUserInfo.setHeadimage(headUrl);
								}
								appUserInfo.setTags(tags);
								result = respWriter.toSuccess(
										getSerialNumber(request), appUserInfo);
							} else {
								result = respWriter.toError(
										getSerialNumber(request),
										RespCode.CODE_1007);
							}
							message.reply(result);
						});
		// 修改个人资料
		vertx.eventBus()
				.consumer(
						"user.personalinfohandler.updatePersonalInfo",
						message -> {
							ZJSONObject request = Json.decode(message.body()
									.toString(), ZJSONObject.class);
							logger.info("修改个人资料请求：" + message.body());
							try {
								Integer uid = request.getJSONObject(
										"appuserinfo").getInteger("uid");
								if (StringUtil.isEmpty(uid)) {
									message.reply(respWriter.toError(
											getSerialNumber(request),
											RespCode.CODE_1016));
									return;
								}
								String appuserinfo = request
										.getString("appuserinfo");
								byte[] avatar = request.getJSONObject(
										"appuserinfo").getBytes("avatar");
								JSONArray jsonArray = request.getJSONObject(
										"appuserinfo").getJSONArray("tags");
								AppUserInfo appUserInfo = JSON.parseObject(
										appuserinfo, AppUserInfo.class);
								Map<String, String> param = new HashMap<>();
								String headUri = request.getJSONObject(
										"appuserinfo").getString("headimage");
								if (headUri != null && !headUri.equals("")
										&& headUri.contains("/download/")) {
									String uri = headUri.substring(
											headUri.indexOf("download/") + 9,
											headUri.length());
									appUserInfo.setHeadimage(uri);
									param.put("uri", uri);
								}
								if (avatar != null) {
									UploadFileUtil.uploadFile(
											vertx,
											config.getProperty("uploadFile.host"),
											new Integer(
													config.getProperty("uploadFile.port")),
											param,
											avatar,
											resp -> {
												if (resp.statusCode() != 200) {
													respWriter
															.toError(
																	getSerialNumber(request),
																	RespCode.CODE_1008);
												} else {
													resp.bodyHandler(body -> {
														ZJSONObject uploadFileData = Json.decode(
																body.toString(),
																ZJSONObject.class);
														String code = uploadFileData
																.getString("code");
														if ("0".equals(code)) {
															ZJSONObject uploadFileDataRespData = Json
																	.decode(uploadFileData
																			.getString("obj"),
																			ZJSONObject.class);
															// String headUrl =
															// "http://"+config.getProperty("uploadFile.host")+":"+config.getProperty("uploadFile.port")+"/download/"+uploadFileDataRespData.getString("uri");
															appUserInfo
																	.setHeadimage(uploadFileDataRespData
																			.getString("uri"));
															appUserInfo
																	.setFirstLogin(0);
															boolean updateInfoFlag = personalInfoService
																	.updateById(appUserInfo);
															List<PersonalityTag> list = new ArrayList<PersonalityTag>();
															boolean flag = false;
															if (updateInfoFlag) {
																if (jsonArray != null
																		&& jsonArray
																				.size() > 0) {
																	list = JSON
																			.parseArray(
																					jsonArray
																							.toString(),
																					PersonalityTag.class);
																	for (int i = 0; i < list
																			.size(); i++) {
																		list.get(
																				i)
																				.setUid(uid);
																	}
																	// 批量删除原标签，然后批量新增新标签
																	flag = dealTags(
																			uid,
																			list);
																} else {
																	flag = true;
																}
															}
															if (updateInfoFlag
																	&& flag) {
																if (StringUtil
																		.isNotEmpty(appUserInfo
																				.getHeadimage())) {
																	String headUrl = config
																			.getProperty("headImageUrl")
																			+ uploadFileDataRespData
																					.getString("uri");
																	appUserInfo
																			.setHeadimage(headUrl);
																}
																message.reply(respWriter
																		.toSuccess(
																				getSerialNumber(request),
																				appUserInfo));
															} else {
																message.reply(respWriter
																		.toError(
																				getSerialNumber(request),
																				RespCode.CODE_1009));
															}
														} else {
															message.reply(respWriter
																	.toError(
																			getSerialNumber(request),
																			RespCode.CODE_1008,
																			"头像上传失败："
																					+ uploadFileData
																							.getString("msg")));
														}
													});
												}
											});
								} else {
									appUserInfo.setFirstLogin(0);
									boolean updateInfoFlag = personalInfoService
											.updateById(appUserInfo);
									List<PersonalityTag> list = new ArrayList<PersonalityTag>();
									boolean flag = false;
									if (updateInfoFlag) {
										if (jsonArray != null) {
											list = JSON.parseArray(
													jsonArray.toString(),
													PersonalityTag.class);
											for (int i = 0; i < list.size(); i++) {
												list.get(i).setUid(uid);
											}
											// 批量删除原标签，然后批量新增新标签
											flag = dealTags(uid, list);
										} else {
											flag = true;
										}
									}
									if (updateInfoFlag && flag) {
										if (StringUtil.isNotEmpty(appUserInfo
												.getHeadimage())) {
											String headUrl = config
													.getProperty("headImageUrl")
													+ appUserInfo
															.getHeadimage();
											appUserInfo.setHeadimage(headUrl);
										}
										message.reply(respWriter.toSuccess(
												getSerialNumber(request),
												appUserInfo));
									} else {
										message.reply(respWriter.toError(
												getSerialNumber(request),
												RespCode.CODE_1009));
										return;
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.error(e.getMessage(), e);
								message.reply(respWriter.toError(
										getSerialNumber(request),
										RespCode.CODE_500));
							}
						});

		// 设置目标
		vertx.eventBus()
				.consumer(
						"user.personalinfohandler.settarget",
						message -> {
							ZJSONObject request = Json.decode(message.body()
									.toString(), ZJSONObject.class);
							logger.info("设置目标请求：" + message.body());
							try {
								String result = "";
								Integer id = request.getJSONObject(
										"appuserinfo").getInteger("id");
								Integer uid = request.getJSONObject(
										"appuserinfo").getInteger("uid");
								Integer target = request.getJSONObject(
										"appuserinfo").getInteger("target");
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("id", id);
								map.put("uid", uid);
								map.put("target", target);
								/*
								 * Date dateStar = DateUtil.getTimesmorning();
								 * Date dateEnd = DateUtil.getTimesnight();
								 * SimpleDateFormat sdf = new
								 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								 */
								boolean flag = personalInfoService
										.settarget(map);
								if (flag) {
									/*
									 * map.put("dateStar",
									 * sdf.format(dateStar)); map.put("dateEnd",
									 * sdf.format(dateEnd)); AppUser user =
									 * loginService
									 * .getUserById(uid);//根据uid查询用户信息 String
									 * imei =""; if(user!=null){ imei =
									 * user.getImei(); } map.put("imei", imei);
									 * TargetCount targetCount =
									 * targetCountService.queryTargetCount(map);
									 * if(targetCount!=null){ map.put("id",
									 * targetCount.getId());
									 * targetCountService.updateTargetCount
									 * (map); }else{
									 * targetCountService.addTargetCount(map); }
									 */
									ZJSONObject parama = new ZJSONObject();
									parama.put("uid", uid);
									logger.info("send data.push.target----:"
											+ parama.toString());
									vertx.eventBus().send("user.target.push",
											parama.toString(), res -> {
											});
									ZJSONObject resultJson = new ZJSONObject();
									resultJson.put("target", target);
									result = respWriter.toSuccess(
											getSerialNumber(request),
											resultJson);
								} else {
									result = respWriter.toError(
											getSerialNumber(request),
											RespCode.CODE_1010);
								}
								message.reply(result);
							} catch (Exception e) {
								e.printStackTrace();
								logger.error(e.getMessage(), e);
								message.reply(respWriter.toError(
										getSerialNumber(request),
										RespCode.CODE_500));
							}
						});
		// 目标设置推送
		vertx.eventBus()
				.consumer(
						"user.target.push",
						message -> {
							ZJSONObject request = Json.decode(message.body()
									.toString(), ZJSONObject.class);
							logger.info("推送设置目标请求：" + message.body());
							try {
								Integer uid = request.getInteger("uid");
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("uid", uid);
								Integer target = personalInfoService
										.queryTarget(map);
								ZJSONObject params = new ZJSONObject();
								ZJSONObject data = new ZJSONObject();
								data.put("target", target);
								params.put("body", data);
								params.put("cmd", "L16");
								params.put("uid", uid);
								logger.info("----send---"
										+ params.toString());
								vertx.eventBus().send("device.data.push",
										params.toString(), res -> {
										});
							} catch (Exception e) {
								e.printStackTrace();
								logger.error(e.getMessage(), e);
								message.reply(respWriter.toError(
										getSerialNumber(request),
										RespCode.CODE_500));
							}
						});

		// 批量查询好友列表信息
		vertx.eventBus()
				.consumer(
						"user.personalinfohandler.appuserinfolist",
						message -> {
							ZJSONObject request = Json.decode(message.body()
									.toString(), ZJSONObject.class);
							logger.info("批量查询好友列表信息请求：" + message.body());
							try {
								String uids = request.getString("ids");
								String[] arrayUid = uids.split(",");
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("uids", arrayUid);

								List<AppUserInfo> list = personalInfoService
										.appUserInfoList(map);
								String result = respWriter.toSuccess(
										getSerialNumber(request), list);
								logger.info("批量查询好友列表信息:" + result);
								message.reply(result);
							} catch (Exception e) {
								e.printStackTrace();
								logger.error(e.getMessage(), e);
								message.reply(respWriter.toError(
										getSerialNumber(request),
										RespCode.CODE_500));
							}
						});
		
		// 查询好友关系及好友用户信息
		vertx.eventBus().consumer("user.personalinfo.friendinfo",message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("查询好友关系及好友用户信息请求：" + message.body());
			try {
				String uid = request.getString("uid");
				String friendUid = request.getString("friendUid");
						
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("uid", uid);
				map.put("friendUid", friendUid);
				AppUserInfo userInfo = personalInfoService.queryFriendinfo(map);
				if(userInfo!=null){
					if(userInfo.getHeadimage()!=null && !userInfo.getHeadimage().startsWith("http://")){
						String headUrl = config.getProperty("headImageUrl") + userInfo.getHeadimage();
						userInfo.setHeadimage(headUrl);
					}
					logger.info("查询好友关系及好友用户信息请求：" + respWriter.toSuccess(getSerialNumber(request), userInfo));
					message.reply(respWriter.toSuccess(getSerialNumber(request), userInfo));
				}else{
					AppUserInfo friendInfo = personalInfoService.findFriendinfo(map);
					if(friendInfo!=null){
						friendInfo.setStatus(3);//陌生人状态
						if(friendInfo.getHeadimage()!=null && !friendInfo.getHeadimage().startsWith("http://")){
							String headUrl = config.getProperty("headImageUrl") + friendInfo.getHeadimage();
							friendInfo.setHeadimage(headUrl);
						}
					}
					logger.info("查询好友关系及好友用户信息请求：" + respWriter.toSuccess(getSerialNumber(request), friendInfo));
					message.reply(respWriter.toSuccess(getSerialNumber(request), friendInfo));
					}
				} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request),RespCode.CODE_500));
				}
				});
		// 好友详情页、好友是否在手表上显示
		vertx.eventBus().consumer("user.personalinfo.friendshow",message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("修改好友是否在详情页显示的请求：" + message.body());
			try{
				Integer uid = request.getInteger("uid");
				Integer friendUid = request.getInteger("friendUid");
				Integer watchstat = request.getInteger("watchstat");//0否1是
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("uid", uid);
				map.put("friendUid", friendUid);
				map.put("watchstat", watchstat);
				//查询显示好友是否超过5个
				boolean flag = false;
				if(watchstat==1){
					flag = personalInfoService.countFriend(map);
				}
				if(flag || watchstat==0){
					//修改显示状态
					boolean updateFlag = personalInfoService.updateFriendShow(map);
					if(updateFlag){//修改成功
						//TODO 推送给手表显示好友列表
					ZJSONObject params =new ZJSONObject();
					params.put("uid", uid);
					vertx.eventBus().send("user.friendshow.push", params.toString());
					message.reply(respWriter.toSuccess(getSerialNumber(request),"修改成功"));
					}else{
					message.reply(respWriter.toError(getSerialNumber(request),RespCode.CODE_4008));
					}
				}else{
				message.reply(respWriter.toError(getSerialNumber(request),RespCode.CODE_4007));
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request),RespCode.CODE_500));
				}
			});
		
		// 查询基本标签
		vertx.eventBus()
				.consumer(
						"user.personalinfo.basetagslist",
						message -> {
							ZJSONObject request = Json.decode(message.body()
									.toString(), ZJSONObject.class);
							logger.info("查询基本标签请求：" + message.body());
							try {
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("uids", "id");
								List<BaseTags> list = baseTagsService
										.basetagslist(map);
								String result = respWriter.toSuccess(
										getSerialNumber(request), list);
								logger.info("查询基本标签列表信息:" + result);
								message.reply(result);
							} catch (Exception e) {
								e.printStackTrace();
								logger.error(e.getMessage(), e);
								message.reply(respWriter.toError(
										getSerialNumber(request),
										RespCode.CODE_500));
							}
						});

		// 个人资料电话号码查询，用于充值
		vertx.eventBus().consumer(
				"user.appuserinfo.queryappuserinfo",
				message -> {
					ZJSONObject request = Json.decode(
							message.body().toString(), ZJSONObject.class);
					String result = "";
					logger.info("根据uid查询个人资料请求（充值用）：" + message.body());
					Integer uid = request.getInteger("uid");
					AppUserInfo appUserInfo = personalInfoService
							.queryAppUserInfoByUid(uid);
					if (appUserInfo != null) {
						result = respWriter.toSuccess(getSerialNumber(request),
								appUserInfo);
					} else {
						result = respWriter.toError(getSerialNumber(request),
								RespCode.CODE_500);
					}
					message.reply(result);
				});
		//推送
		vertx.eventBus().consumer("user.friendshow.push", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("推送手表端好友显示请求：" + message.body());
			Integer uid = request.getInteger("uid");//新增的闹钟id，因为现在闹钟没有修改功能，所以不考虑上传语音修改的情况
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("watchstat", 1);
			List<AppUserInfo> list =personalInfoService.queryAllFriends(map);
			List<Map<String,Object>> list1=new ArrayList<Map<String,Object>>();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Map<String,Object> dataMap = new HashMap<String, Object>();
					dataMap.put("id", list.get(i).getUid1());
					dataMap.put("name", list.get(i).getName());
					list1.add(dataMap);
				}
			}
			String cmd ="L18";
			ZJSONObject params =new ZJSONObject();
			ZJSONObject data =new ZJSONObject();
			data.put("data", list1);
			data.put("uid", uid);
			params.put("body", data);
			params.put("cmd", cmd);
			params.put("uid", uid);
			logger.info("----手机端好友显示推送----"+params.toString());
			vertx.eventBus().send("device.data.push", params.toString(), res -> {});
			});
	}

	@Transactional
	public boolean dealTags(int uid, List<PersonalityTag> list) {
		boolean flag = false;

		List<PersonalityTag> tagList = personalityTagService
				.findPersonalTag(uid);
		boolean deleteTagsFlag = true;
		boolean insertTagsFlag = true;
		if (tagList.size() > 0) {
			deleteTagsFlag = personalityTagService.batchDeleteByUid(uid);
		}
		if (list.size() > 0) {
			insertTagsFlag = personalityTagService.batchInsert(list);
		}
		if (deleteTagsFlag && insertTagsFlag) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}
}
