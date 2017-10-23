package com.chinamcom.framework.user.verticle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.SosSetting;
import com.chinamcom.framework.user.push.SosPhonePushUtil;
import com.chinamcom.framework.user.service.SosSettingService;

@Component
public class SosSettingVerticle extends BaseVerticle{
	
	private static Logger logger = Logger.getLogger(SosSettingVerticle.class);
/*	@Autowired
	private TransactionTemplate sjc;
	
	@Autowired
	private DataSource dataSource;
	
	@Qualifier(value="jdbcTemplate11")
	private JdbcTemplate jdbcTemplate;*/
	
	@Autowired
	private SosSettingService sosSettingService;
	
	public SosSettingVerticle() {
	}

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer("user.sossetting.sossettinglist", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("查询sos请求：" + message.body());
			try{
			Integer id = request.getInteger("id");
			Integer userId = request.getInteger("userId");
			String deviceImei = request.getString("deviceImei");
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("id", id);
			map.put("userId", userId);
			map.put("deviceImei", deviceImei);
			SosSetting sosInfo = sosSettingService.getSosSettingList(map);
			String result= respWriter.toSuccess(getSerialNumber(request), sosInfo);
			logger.info("返回结果："+result);
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
			});
		
		vertx.eventBus().consumer("user.sossetting.updateSosSetting", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("修改sos请求：" + message.body());
			try{
			String sosSetting = request.getString("sossetting");
			SosSetting sosInfo = JSON.parseObject(sosSetting, SosSetting.class);
			Integer id = sosInfo.getId();
			Integer userId = sosInfo.getUserId();
			String deviceImei = sosInfo.getDeviceImei();
			String result="";
			boolean flag = false;
			if(id!=null){
				flag= sosSettingService.updateSosSetting(sosInfo);
				result=respWriter.toSuccess(getSerialNumber(request), flag);
			}else{
				Map<String , Object> map = new HashMap<String , Object>();
				map.put("id", id);
				map.put("userId", userId);
				map.put("deviceImei", deviceImei);
				SosSetting sos = sosSettingService.getSosSettingList(map);
				if(sos != null){
					result=respWriter.toError(getSerialNumber(request), RespCode.CODE_1011);
					message.reply(result);
					return;
				}else{
					id = sosSettingService.addSosSetting(sosInfo);
					sosInfo.setId(id);
					result=respWriter.toSuccess(getSerialNumber(request), sosInfo);
				}
			}
			//下发sos列表
			JSONObject postJson = new JSONObject();
			postJson.put("uid", userId);
			postJson.put("did", deviceImei);
			vertx.eventBus().send("user.sossetting.push", postJson.toString(), res -> {});
			logger.info("返回结果："+result);
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
			});
		
		vertx.eventBus().consumer("user.sossetting.push", message -> {
			JSONObject request = Json.decode(message.body().toString(), JSONObject.class);
			logger.info("推送sos请求：" + message.body());
			//查询sos推送电话号码
			Integer userId = request.getInteger("uid");
			String deviceImei = request.getString("did");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("deviceImei", deviceImei);
			SosSetting sosPhone = sosSettingService.queryPhone(map);
			List<SosPhonePushUtil> list = new ArrayList<SosPhonePushUtil>();
			String sosMsg = "";
			if(sosPhone!=null){
			if(StringUtil.isNotEmpty(sosPhone.getPhoneNo1())){
				SosPhonePushUtil phone1 = new SosPhonePushUtil();
				phone1.setPhone(sosPhone.getPhoneNo1());
				list.add(phone1);
			}
			if(StringUtil.isNotEmpty(sosPhone.getPhoneNo2())){
				SosPhonePushUtil phone2 = new SosPhonePushUtil();
				phone2.setPhone(sosPhone.getPhoneNo2());
				list.add(phone2);
			}
			if(StringUtil.isNotEmpty(sosPhone.getPhoneNo3())){
				SosPhonePushUtil phone3 = new SosPhonePushUtil();
				phone3.setPhone(sosPhone.getPhoneNo3());
				list.add(phone3);
			}
			sosMsg = sosPhone.getSmsContent();
			}
			String cmd="L9";
			ZJSONObject params =new ZJSONObject();
			params.put("cmd", cmd);
			params.put("uid", userId);
			params.put("did", deviceImei);
			ZJSONObject data =new ZJSONObject();
			data.put("data", list);
			data.put("sosMsg", sosMsg);
			params.put("body", data);
			logger.info("----send---"+params.toString());
			vertx.eventBus().send("device.data.push", params.toString(), res -> {});
			});
		
		/**
		 * 废除接口
		 * 
		 * **/
		vertx.eventBus().consumer("user.sossetting.addSosSetting", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("新增sos请求：" + message.body());
			String sosSetting = request.getString("sossetting");
			SosSetting sosInfo = JSON.parseObject(sosSetting, SosSetting.class);
			Integer id = sosSettingService.addSosSetting(sosInfo);
			sosInfo.setId(id);
			String result= respWriter.toSuccess(getSerialNumber(request), sosInfo);
			logger.info("返回结果："+result);
			message.reply(result);
			});
		
		vertx.eventBus().consumer("user.sossetting.deleteSosSetting", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("删除sos请求：" + message.body());
			try{
			Integer id = request.getInteger("id");
			Integer userId = request.getInteger("userId");
			String deviceImei = request.getString("deviceImei");
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("id", id);
			map.put("userId", userId);
			map.put("deviceImei", deviceImei);
			boolean flag = sosSettingService.deleteSosSetting(map);
			if(flag){
				//下发sos列表
				JSONObject postJson = new JSONObject();
				postJson.put("uid", userId);
				postJson.put("did", deviceImei);
				vertx.eventBus().send("user.sossetting.push", postJson.toString(), res -> {});
			}
			String result= respWriter.toSuccess(getSerialNumber(request), flag);
			logger.info("返回结果："+result);
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
			});
	}

}
