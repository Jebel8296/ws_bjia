package com.chinamcom.framework.user.verticle;

import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.ApplicationMybatis;
import com.chinamcom.framework.common.constants.ServerConstants;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.utils.RestUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.DeviceInfo;
import com.chinamcom.framework.user.service.DeviceInfoService;

@Component
public class DeviceInfoVerticle extends BaseVerticle {
	
	@Autowired
	private DeviceInfoService deviceInfoService;
	
	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer(ServerConstants.FIRMWARE_UPGRADE, message -> {
			log.info("message received:" + message.body());
			String serial_number = null;
			try{
				Properties config = ApplicationMybatis.config;
				RedisOptions redisConfig = new RedisOptions();  
				redisConfig.setHost(config.getProperty("redis.host"));  
				redisConfig.setPort(Integer.parseInt(config.getProperty("redis.port")));
				redisConfig.setSelect(Integer.parseInt(config.getProperty("redis.db","3")));
				redisConfig.setTcpKeepAlive(true);
			    RedisClient client = RedisClient.create(Vertx.vertx(), redisConfig);
	            ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
	            serial_number = getSerialNumber(reqData);
	            String imei = reqData.getString("imei");
				DeviceInfo deviceInfo = deviceInfoService.getInfoByImei(imei);
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("curVer", deviceInfo.getFwVer());
				JSONObject postData = new JSONObject();
				JSONObject device =  new JSONObject();
				device.put("sn", imei);
				device.put("lang", "zh");
				device.put("ua", "mtk;BRAND/ALPHA;MODEL/BSW1");
				device.put("ch", "mtk");
				postData.put("device", device);
				postData.put("sv", deviceInfo.getFwVer());
				postData.put("br", "ALPHA");
				postData.put("m", "BSW1");
//				Object ret = RestUtil.postData("api.iot.fotapro.com/api/iot/connect", postData);
				String redisKey =imei+"_firm";
				client.get(redisKey, getRes ->{
				Object ret = null;
				if(getRes.succeeded()){
					ret = getRes.result();
					if(ret==null){
						ret = RestUtil.postData("api.iot.fotapro.com/api/iot/connect", postData);
						client.setex(redisKey,60*60*24,ret.toString(), setRes->{
						if(!setRes.succeeded()){  
					         setRes.cause().printStackTrace();  
					         } 
						});
					}
				}else{
					log.error(getRes.cause().getMessage(), getRes.cause());
					ret = RestUtil.postData("api.iot.fotapro.com/api/iot/connect", postData);
					client.setex(redisKey,60*60*24,ret.toString(), setRes->{
					if(!setRes.succeeded()){  
				         setRes.cause().printStackTrace();  
				         } 
					});
				log.error("读取缓存失败，重新调取,并把结果放到缓存中");
				}
				log.info(ret);
				JSONObject firmRusult = JSONObject.parseObject(ret.toString());
				if(firmRusult.containsKey("tv") && firmRusult.getString("tv") != null){
					resultMap.put("latestVer", firmRusult.getString("tv"));
				}else{
					resultMap.put("latestVer", "");
				}
				message.reply(respWriter.toSuccess(getSerialNumber(reqData), resultMap));
				});
			}catch(Exception ex){
				message.reply(respWriter.toError(serial_number, ex.getMessage()));
			}
		});
	}
}
