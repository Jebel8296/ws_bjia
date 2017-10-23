package com.chinamcom.framework.user.verticle;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.chinamcom.framework.ApplicationMybatis;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.AppUser;
import com.chinamcom.framework.user.dao.AppUserInfo;
import com.chinamcom.framework.user.dao.TargetBaseInfo;
import com.chinamcom.framework.user.service.AppUserDeviceService;
import com.chinamcom.framework.user.service.DeviceInfoService;
import com.chinamcom.framework.user.service.LoginService;
import com.chinamcom.framework.user.service.PersonalInfoService;
import com.chinamcom.framework.user.service.TargetBaseInfoService;

@Component
public class LoginVerticle extends BaseVerticle{
	private static Logger logger = Logger.getLogger(LoginVerticle.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private DeviceInfoService deviceInfoService;
	
	@Autowired
	private PersonalInfoService personalInfoService;
	
	@Autowired
	private AppUserDeviceService appUserDeviceService;
	@Autowired
	private TargetBaseInfoService targetBaseInfoService;
	
	public LoginVerticle() {
	}
	
	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer("user.loginverticle.login", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("登陆请求：" + message.body());
			try{
			Properties config = ApplicationMybatis.config;
			String imei = request.getJSONObject("appuser").getString("imei");
			String did = request.getJSONObject("appuser").getString("did");
			Integer loginModel = request.getJSONObject("appuser").getInteger("loginModel");
			String weiboId = request.getJSONObject("appuser").getString("weiboId");
			String weixinId = request.getJSONObject("appuser").getString("weixinId");
			String qqId = request.getJSONObject("appuser").getString("qqId");
			Integer env = request.getJSONObject("appuser").getInteger("env");
			String result = "";
			String cn = getCn(request);
			Map<String , Object> map = new HashMap<String , Object>();
			Map<String , Object> appuserDeviceMap = new HashMap<String , Object>();
			if(loginModel==1){
				if(imei!=null){
					boolean loginCheck =  imeiValid(imei);//校验imei是否合法
					if(loginCheck){
						map.put("imei", imei);
						AppUser appUser = loginService.queryByMap(map);//根据imei查询用户信息
						if(appUser!=null){//存在、查询用户信息\返回
							AppUserInfo appUserInfo = personalInfoService.getAppUserInfoByUid(appUser.getUid());
							if(appUserInfo==null){
								AppUserInfo userInfo = new AppUserInfo();
								/*userInfo.setUid(appUser.getUid());
								userInfo.setHeadimage(appUser.getHead());
								userInfo.setNickname(appUser.getName());
								userInfo.setTarget(6000);//默认设置目标6000
								userInfo.setFirstLogin(0);
								Integer primaryKey = personalInfoService.insertAppUserInfo(userInfo);
								userInfo.setId(primaryKey);*/
								userInfo = appUserInfoInsert(userInfo,appUser.getUid(),appUser.getHead(),appUser.getName(),0);
								appUser.setAppuserinfo(userInfo);
							}else{
								if(appUserInfo.getHeadimage()!=null && !appUserInfo.getHeadimage().contains("http://")){
									appUserInfo.setHeadimage(config.getProperty("headImageUrl")+appUserInfo.getHeadimage());
								}
								appUser.setAppuserinfo(appUserInfo);
							}
							processToken(appUser,did);
							appuserDeviceMap.put("did", did);
							appuserDeviceMap.put("uid", appUser.getUid());
							appuserDeviceMap.put("env", env);
							if(!appUserDeviceService.appUserDeviceOnline(appuserDeviceMap)){
								addUserDevice(appUser.getUid(),did,cn,1,request.getJSONObject("appuser").getString("apple_id"),env);
							}
							pushOffline(did,appUser.getUid());
							addTargetBase(appUser);
							result= respWriter.toSuccess(getSerialNumber(request), appUser); 
						}else{
							//不存在，第一次登陆，插入,
							String appUserAdd = request.getString("appuser");
							AppUser bean = JSON.parseObject(appUserAdd, AppUser.class);
							Date day = new Date();
							bean.setCreatetime(day);
							Integer primaryId =  loginService.addUser(bean);
							bean.setUid(primaryId);	
							AppUserInfo userInfo = new AppUserInfo();
							/*userInfo.setUid(primaryId);
							userInfo.setHeadimage(bean.getHead());
							userInfo.setNickname(bean.getName());
							userInfo.setTarget(6000);//默认设置目标6000
							userInfo.setFirstLogin(1);
							Integer primaryKey = personalInfoService.insertAppUserInfo(userInfo);
							userInfo.setId(primaryKey);	*/
							userInfo = appUserInfoInsert(userInfo,primaryId,bean.getHead(),bean.getName(),1);
							bean.setAppuserinfo(userInfo);
							processToken(bean,did);
							if(userInfo.getId()!=null){
/*								ZJSONObject parama =new ZJSONObject();
								parama.put("imei", imei);
								parama.put("uid", primaryId);
								logger.info("send data.server.remove----:"+parama.toString());
								vertx.eventBus().send("data.server.remove", parama.toString(), res -> {});*/
								serverRemove(imei,primaryId);
								appuserDeviceMap.put("did", did);
								appuserDeviceMap.put("uid", primaryId);
								appuserDeviceMap.put("env",env);
								if(!appUserDeviceService.appUserDeviceOnline(appuserDeviceMap)){
									/*appuserDeviceMap.put("device_type", cn);
									appuserDeviceMap.put("status",1);
									appuserDeviceMap.put("apple_id", request.getJSONObject("appuser").getString("apple_id"));
									appUserDeviceService.addUserDevice(appuserDeviceMap);*/
									addUserDevice(primaryId,did,cn,1,request.getJSONObject("appuser").getString("apple_id"),env);
								}
								/*ZJSONObject parama_status =new ZJSONObject();
								parama_status.put("did", did);
								parama_status.put("uid", primaryId);
								vertx.eventBus().send("notify.push.offline", parama_status.toString(), res -> {});*/
								pushOffline(did,primaryId);
								addTargetBase(bean);
								result= respWriter.toSuccess(getSerialNumber(request),bean); 
							}else{
								result= respWriter.toError(getSerialNumber(request),RespCode.CODE_1004);
							}
						}
					}else{
						result= respWriter.toError(getSerialNumber(request),RespCode.CODE_1003);
					}
				}else{
					result= respWriter.toError(getSerialNumber(request),RespCode.CODE_500);
				}
			}else if(loginModel==2){//第三方登陆、在数据库注册一个三方登陆账号
				map.put("weiboId", weiboId);
				map.put("weixinId", weixinId);
				map.put("qqId", qqId);
				AppUser appUser = loginService.queryByMap(map);//查询用户信息
				if(appUser!=null){//已存在用户信息
					AppUserInfo appUserInfo = personalInfoService.getAppUserInfoByUid(appUser.getUid());
					if(appUserInfo==null){
						AppUserInfo userInfo = new AppUserInfo();
						userInfo = appUserInfoInsert(userInfo,appUser.getUid(),appUser.getHead(),appUser.getName(),0);
						appUser.setAppuserinfo(userInfo);
					}else{
						if(appUserInfo.getHeadimage()!=null && !appUserInfo.getHeadimage().contains("http://")){
							appUserInfo.setHeadimage(config.getProperty("headImageUrl")+appUserInfo.getHeadimage());
						}
						appUser.setAppuserinfo(appUserInfo);
					}
					processToken(appUser,did);
					appuserDeviceMap.put("did", did);
					appuserDeviceMap.put("uid", appUser.getUid());
					appuserDeviceMap.put("env", env);
					if(!appUserDeviceService.appUserDeviceOnline(appuserDeviceMap)){
						addUserDevice(appUser.getUid(),did,cn,1,request.getJSONObject("appuser").getString("apple_id"),env);
					}
					pushOffline(did,appUser.getUid());
					addTargetBase(appUser);
					result= respWriter.toSuccess(getSerialNumber(request), appUser); 
				}else{
					//插入
					String appUserAdd = request.getString("appuser");
					AppUser bean = JSON.parseObject(appUserAdd, AppUser.class);
					Date day = new Date();
					bean.setCreatetime(day);
					Integer primaryId =  loginService.addUser(bean);
					bean.setUid(primaryId);	
					AppUserInfo userInfo = new AppUserInfo();
					/*userInfo.setUid(primaryId);
					userInfo.setHeadimage(bean.getHead());
					userInfo.setNickname(bean.getName());
					userInfo.setTarget(6000);//默认设置目标6000
					userInfo.setFirstLogin(1);
					Integer primaryKey = personalInfoService.insertAppUserInfo(userInfo);
					userInfo.setId(primaryKey);*/
					userInfo = appUserInfoInsert(userInfo,primaryId,bean.getHead(),bean.getName(),1);
					bean.setAppuserinfo(userInfo);
					processToken(bean,did);
					if(userInfo.getId()!=null){
						/*ZJSONObject parama =new ZJSONObject();
						parama.put("imei", imei);
						parama.put("uid", primaryId);
						logger.info("send data.server.remove----:" + parama.toString());
						vertx.eventBus().send("data.server.remove", parama.toString(), res -> {});*/
						serverRemove(imei,primaryId);
						appuserDeviceMap.put("did", did);
						appuserDeviceMap.put("uid", primaryId);
						appuserDeviceMap.put("env", env);
						if(!appUserDeviceService.appUserDeviceOnline(appuserDeviceMap)){
							/*appuserDeviceMap.put("device_type", cn);
							appuserDeviceMap.put("status",1);
							appuserDeviceMap.put("apple_id", request.getJSONObject("appuser").getString("apple_id"));
							appUserDeviceService.addUserDevice(appuserDeviceMap);*/
							addUserDevice(primaryId,did,cn,1,request.getJSONObject("appuser").getString("apple_id"),env);
						}
						/*ZJSONObject parama_status =new ZJSONObject();
						parama_status.put("did", did);
						parama_status.put("uid", primaryId);
						vertx.eventBus().send("notify.push.offline", parama_status.toString(), res -> {});*/
						pushOffline(did,primaryId);
						addTargetBase(bean);
						result= respWriter.toSuccess(getSerialNumber(request),bean); 
					}else{
						result= respWriter.toError(getSerialNumber(request),RespCode.CODE_1004);
					}
				}
			}
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
		});
		
		vertx.eventBus().consumer("user.loginverticle.binding", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("三方登陆绑定imei请求：" + message.body());
			String result="";
			String imei = request.getJSONObject("appuser").getString("imei");
			Integer uid = request.getJSONObject("appuser").getInteger("uid");
			Map<String , Object> map = new HashMap<String , Object>();
			boolean loginCheck =  imeiValid(imei);//校验imei是否合法
			if(loginCheck){
				map.put("imei", imei);
				AppUser appUser = loginService.queryByMap(map);//根据imei查询用户信息
				if(appUser!=null){
					if(appUser.getUid()!=uid){
						result= respWriter.toError(getSerialNumber(request),RespCode.CODE_1001);
					}else{
						result= respWriter.toError(getSerialNumber(request),RespCode.CODE_1002);
					}
				}else{
					Map<String , Object> para_map = new HashMap<String , Object>();
					para_map.put("uid", uid);
					para_map.put("imei", imei);
//					para_map.put("loginModel", 2);
					boolean flag = loginService.binding(para_map);
					if(flag){
						ZJSONObject parama =new ZJSONObject();
						parama.put("imei", imei);
						parama.put("uid", uid);
						logger.info("binding send data.server.remove----:"+parama.toString());
						vertx.eventBus().send("data.server.remove", parama.toString(), res -> {});
						result= respWriter.toSuccess(getSerialNumber(request), para_map);
					}else{
						result= respWriter.toError(getSerialNumber(request), RespCode.CODE_1005);
					}
				}
			}else{
				result= respWriter.toError(getSerialNumber(request), RespCode.CODE_1003);
			}
			message.reply(result);
		});
		
		
		vertx.eventBus().consumer("user.loginverticle.unbundling", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("三方登陆解绑imei请求：" + message.body());
			String result="";
			Integer uid = request.getJSONObject("appuser").getInteger("uid");
			Map<String , Object> para_map = new HashMap<String , Object>();
			para_map.put("uid", uid);
			AppUser user = loginService.getUserById(uid);
			String imei = user.getImei();
			boolean flag = loginService.unbundling(para_map);
			if(flag){
				ZJSONObject parama =new ZJSONObject();
				parama.put("imei", imei);
				parama.put("uid", uid);
				logger.info("unbundling send data.server.remove----:"+parama.toString());
				vertx.eventBus().send("data.server.remove", parama.toString(), res -> {});
				result= respWriter.toSuccess(getSerialNumber(request),flag);
			}else{
				result= respWriter.toError(getSerialNumber(request), RespCode.CODE_1006);
			}
			message.reply(result);
		});
		
		//客户端登出
		vertx.eventBus().consumer("user.loginverticle.logout", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("客户端登出请求：" + message.body());
			Integer uid = request.getInteger("uid");
			String did = request.getString("did");
			ZJSONObject parama =new ZJSONObject();
			parama.put("uid", uid);
			parama.put("did", did);
			logger.info("logout parama----:"+parama.toString());
			vertx.eventBus().send("data.channel.remove", parama.toString(), res -> {});
			message.reply(respWriter.toSuccess(getSerialNumber(request)));
		});
	}

	public AppUserInfo appUserInfoInsert(AppUserInfo userInfo, Integer uid,String headImage,String nickname,Integer firstLogin){
		userInfo.setUid(uid);
		userInfo.setHeadimage(headImage);
		userInfo.setNickname(nickname);
		userInfo.setTarget(6000);//默认设置目标6000
		userInfo.setFirstLogin(firstLogin);
		Integer primaryKey = personalInfoService.insertAppUserInfo(userInfo);
		userInfo.setId(primaryKey);
		return userInfo;
	}
	
	public void serverRemove(String imei,Integer uid){
		ZJSONObject parama =new ZJSONObject();
		parama.put("imei", imei);
		parama.put("uid", uid);
		logger.info("send data.server.remove----:" + parama.toString());
		vertx.eventBus().send("data.server.remove", parama.toString(), res -> {});
	}
	
	public void addUserDevice(Integer uid,String did,String cn,Integer status,String apple_id,Integer env){
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("uid", uid);
		map.put("did", did);
		map.put("device_type", cn);
		map.put("status",status);
		map.put("apple_id", apple_id);
		map.put("env", env);
		appUserDeviceService.addUserDevice(map);
	}
	
	public void pushOffline(String did,Integer uid){
		ZJSONObject parama_status =new ZJSONObject();
		parama_status.put("did", did);
		parama_status.put("uid", uid);
		vertx.eventBus().send("notify.push.offline", parama_status.toString(), res -> {});
	}
	
	public void addTargetBase(AppUser appUser){
		if(appUser.getAppuserinfo().getFirstLogin()!=0){
			List<TargetBaseInfo> list = targetBaseInfoService.getTargetBaseList();
			String[] targetArray = new String[list.size()];
			for(int i=0;i<list.size();i++){
				targetArray[i] = list.get(i).getStepNo();
			}
			appUser.setTargetbaseinfoarray(targetArray);
		}
	}
	
	private void processToken(AppUser appUser,String did) {
		String secretKey = ApplicationMybatis.config.getProperty("jwt.secretKey", "056538af5de907cf43cdb535f9eb016437b69a14");
    	long now = (new Date()).getTime();
    	long tokenValidityInSeconds = 1000 * Long.parseLong(ApplicationMybatis.config.getProperty("jwt.tokenValidityInSeconds", "86400"));
		Date validity = new Date(now + tokenValidityInSeconds);
		String token = Jwts.builder().setSubject(appUser.getUid().toString()).claim("did", did)
				.signWith(SignatureAlgorithm.HS512, secretKey).setExpiration(validity ).compact();
		appUser.setToken(token);
	}

	public boolean imeiValid(String imei){
		//根据imei 到device_info 表中查询该设备是否有、判断合法
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("imei", imei);
		boolean flag = deviceInfoService.imeiValid(map);
		return flag;
	}
}
