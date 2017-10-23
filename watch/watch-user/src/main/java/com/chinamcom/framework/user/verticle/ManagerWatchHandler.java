package com.chinamcom.framework.user.verticle;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.ApplicationMybatis;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.UploadFileUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.DeviceWearInfo;
import com.chinamcom.framework.user.push.DataPushUtil;
import com.chinamcom.framework.user.push.DeviceWearInfoPushUtil;
import com.chinamcom.framework.user.service.DeviceWearInfoService;

/**
 * 
 * 手表管理
 * 
 * **/
@Component
public class ManagerWatchHandler extends BaseVerticle{
	private static Logger logger = Logger.getLogger(ManagerWatchHandler.class);

	/*@Autowired
	private TransactionTemplate sjc;

	@Autowired
	private DataSource dataSource;

	@Qualifier(value = "jdbcTemplate11")
	private JdbcTemplate jdbcTemplate;*/

	@Autowired
	private DeviceWearInfoService deviceWearInfoService;
	
	public ManagerWatchHandler() {

	}

	@Override
	public void start() throws Exception {
		//设备佩戴者信息查询deviceActiveInfo
		Properties config = ApplicationMybatis.config;
		
		vertx.eventBus().consumer("user.managerwatch.getdevicewearinfo",message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("请求：" + message.body());
			try{
			String deviceImei = request.getString("deviceImei");
			Integer uid = request.getInteger("uid");
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("deviceImei", deviceImei);
			map.put("uid", uid);
			DeviceWearInfo deviceActiveInfo = deviceWearInfoService.getDeviceWearInfoByImei(map);
			if(deviceActiveInfo!=null){
				if(deviceActiveInfo.getDeviceHead()!=null && !deviceActiveInfo.getDeviceHead().equals("") && !deviceActiveInfo.getDeviceHead().startsWith("http://")){
					String headImage = config.getProperty("headImageUrl")+deviceActiveInfo.getDeviceHead();
					deviceActiveInfo.setDeviceHead(headImage);
				}	
			}
			String result = respWriter.toSuccess(getSerialNumber(request), deviceActiveInfo);
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(),e);
				message.reply(respWriter.toError(getSerialNumber(request),RespCode.CODE_500));
			}
		});
		
		//佩戴者信息修改
		vertx.eventBus().consumer("user.managerwatch.savedevicewearinfo",message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("请求：" + message.body());
			try{
			byte[] avatar = request.getJSONObject("devicewearinfo").getBytes("avatar");
			String deviceactiveinfo = request.getString("devicewearinfo");
			DeviceWearInfo bean = JSON.parseObject(deviceactiveinfo, DeviceWearInfo.class);
//			boolean flag = false;
//			String result = null;
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("deviceImei", bean.getDeviceImei());
			map.put("uid", bean.getUid());
			DeviceWearInfo deviceActiveInfo = deviceWearInfoService.getDeviceWearInfoByImei(map);
			Map<String, String> param = new HashMap<>();
			String headUri = request.getJSONObject("devicewearinfo").getString("deviceHead");
			if(headUri !=null && !headUri.endsWith("") && headUri.contains("/download/")){
				String uri = headUri.substring(headUri.indexOf("download/")+9,headUri.length());
				bean.setDeviceHead(uri);
				param.put("uri", uri);
			}
			if(avatar!=null ){
				UploadFileUtil.uploadFile(vertx, config.getProperty("uploadFile.host"),
						new Integer(config.getProperty("uploadFile.port")), param, avatar, resp -> {
							if(resp.statusCode() != 200){
								message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_1008));
							}else{
								resp.bodyHandler(body -> {
									ZJSONObject uploadFileData = Json.decode(body.toString(), ZJSONObject.class);
									String code = uploadFileData.getString("code");
									if("0".equals(code)){
										ZJSONObject uploadFileDataRespData = Json.decode(uploadFileData.getString("respData"), ZJSONObject.class);
//										String headUrl = "http://"+config.getProperty("uploadFile.host")+":"+config.getProperty("uploadFile.port")+"/download/"+uploadFileDataRespData.getString("uri");
										bean.setDeviceHead(uploadFileDataRespData.getString("uri"));
										if(deviceActiveInfo!=null){
											//修改
											boolean flag = deviceWearInfoService.saveDeviceWearInfo(bean);
											if(flag){
												message.reply(respWriter.toSuccess(getSerialNumber(request), flag));
											}else{
												message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_1009));
											}
										}else{
											//新增
											Integer id = deviceWearInfoService.insertDeviceWearInfo(bean);
											bean.setId(id);
											String headUrl = config.getProperty("headImageUrl")+uploadFileDataRespData.getString("uri");
											bean.setDeviceHead(headUrl);
											message.reply(respWriter.toSuccess(getSerialNumber(request), bean));
										}
									}else{
										message.reply(respWriter.toError(getSerialNumber(request), "头像上传失败："+uploadFileData.getString("msg")));
									}
								});
							}
						});
			}else{
				if(deviceActiveInfo!=null){
					//修改
					boolean flag = deviceWearInfoService.saveDeviceWearInfo(bean);
					if(flag){
						message.reply(respWriter.toSuccess(getSerialNumber(request), flag));
					}else{
						message.reply(respWriter.toError(getSerialNumber(request),RespCode.CODE_1009));
					}
				}else{
					//新增
					Integer id = deviceWearInfoService.insertDeviceWearInfo(bean);
					bean.setId(id);
					String headUrl = config.getProperty("headImageUrl")+bean.getDeviceHead();
					bean.setDeviceHead(headUrl);
					message.reply(respWriter.toSuccess(getSerialNumber(request), bean));
				}
			}
			//下发佩戴者信息
			JSONObject postJson = new JSONObject();
			postJson.put("uid", bean.getUid());
			postJson.put("did", bean.getDeviceImei());
			vertx.eventBus().send("user.managerwatch.psuh", postJson.toString(), res -> {});
			}catch(Exception e){
				e.printStackTrace();
				message.reply(respWriter.toError(getSerialNumber(request),RespCode.CODE_500));
			}
		});
		
		vertx.eventBus().consumer("user.managerwatch.psuh",message -> {
			JSONObject request = Json.decode(message.body().toString(), JSONObject.class);
			logger.info("请求：" + message.body());
			String deviceImei = request.getString("did");
			Integer uid = request.getInteger("uid");
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("deviceImei", deviceImei);
			map.put("uid", uid);
			DeviceWearInfo deviceWearInfo = deviceWearInfoService.getDeviceWearInfoByImei(map);
			if(deviceWearInfo!=null){
				DataPushUtil data = new DataPushUtil();
				String cmd="L13";
				DeviceWearInfoPushUtil pushData = new DeviceWearInfoPushUtil();
				Integer stepd = (int)(deviceWearInfo.getDeviceHeight()*0.45);
				pushData.setHigh(deviceWearInfo.getDeviceHeight());
				pushData.setSex(deviceWearInfo.getDeviceSex());
				pushData.setWeight(deviceWearInfo.getDeviceWeight());
				pushData.setStepd(stepd);
				String para = data.pushData(cmd, uid,deviceImei, uid+"", pushData);
				logger.info("+++send+++"+para);
				vertx.eventBus().send("device.data.push", para, res -> {});
			}
		});
	}
}
