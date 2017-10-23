package com.chinamcom.framework.device.verticle;

import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.PropertyConfigLoader;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.device.model.DeviceWearer;
import com.chinamcom.framework.device.model.LocationInfo;
import com.chinamcom.framework.device.model.UserDevice;
import com.chinamcom.framework.device.service.LocationInfoService;
import com.chinamcom.framework.device.service.UserDeviceService;
import com.chinamcom.framework.device.util.IOSUtil;
import com.chinamcom.framework.device.util.RestUtil;



@Service
public class DeviceVerticle extends BaseVerticle {
	
	@Autowired
    private PropertyConfigLoader config;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserDeviceService userDeviceService;
	
	@Autowired
	private LocationInfoService locationInfoService;
	
	@Override
    public void start() throws Exception {
		
		RedisClient redis = RedisClient.create(vertx, new RedisOptions()
		.setHost(config.get("redis.host", "localhost"))
		.setPort(Integer.parseInt(config.get("redis.port")))
		.setTcpKeepAlive(true)
		.setSelect(Integer.valueOf(config.get("redis.db", "3"))));
		
		vertx.eventBus().consumer("data.di.post", message -> {
			log.info(message.body());
            String serial_number = null;
            String cmd = null;
            try {
            	ZJSONObject params = Json.decode(message.body().toString(),ZJSONObject.class);
                serial_number = getSerialNumber(params);
                cmd = getCmd(params);
                String imei = params.getString("imei");
                String imsi = params.getString("imsi");
                String productId = params.getString("productId");
                String fwVer = params.getString("fwVer");
                String fwBuild = params.getString("fwBuild");
                String mcuBuild = params.getString("mcuBuild");
                String btName = params.getString("btName");
                String btMac = params.getString("btMac");
                int ret = jdbcTemplate.update("update device_info set imsi = ?,productId = ?,fwVer = ?,fwBuild = ? ,mcuBuild = ? ,fwBuild = ? ,btMac = ? where imei = ?",
                		imsi, productId, fwVer, fwBuild,mcuBuild,btName,btMac,imei);
                if (ret <= 0) {
                    jdbcTemplate.update("insert into device_info(imei,imsi,productId,fwVer,fwBuild,mcuBuild,btName,btMac) values (?,?,?,?,?,?,?,?)", 
                    		imei, imsi, productId, fwVer, fwBuild, mcuBuild, btName, btMac);
                }
            	message.reply(respWriter.toCmdSuccess(serial_number,cmd));
            } catch (Exception e) {
            	e.printStackTrace();
            	log.error(e.getMessage(),e);
            	message.reply(respWriter.toCmdError(serial_number,cmd));
            }
		 });
		 
		 vertx.eventBus().consumer("data.deviceInfo.get", message -> {
			log.info(message.body());
			String serial_number = null;
            String cmd = null;
            try {
            	ZJSONObject params = Json.decode(message.body().toString(),ZJSONObject.class);
                serial_number = getSerialNumber(params);
                cmd = getCmd(params);
                Integer uid = params.getInteger("uid");
                String sql = "select sex, height, weight from app_userinfo where uid = ?";
                List<DeviceWearer> dwearer = jdbcTemplate.query(sql, new Object[]{uid}, (rs, i) -> {
                	DeviceWearer dw = new DeviceWearer();
                	dw.setHigh(rs.getInt("height"));
                	dw.setSex(rs.getInt("sex"));
                	dw.setWeight(rs.getInt("weight"));
                	dw.setStepd((int)(rs.getInt("height") * 0.45));
                    return dw;
                });
                Map<String,Object> result = new HashMap<String,Object>();
                if(dwearer != null && dwearer.size() > 0){
                	DeviceWearer rwearer = dwearer.get(0);
                	result.put("high", rwearer.getHigh());
                	result.put("sex", rwearer.getSex());
                	result.put("weight", rwearer.getWeight());
                	result.put("stepd", rwearer.getStepd());
                }
                message.reply(respWriter.toCmdSuccess(serial_number, cmd, dwearer.get(0)));
            } catch (Exception e) {
            	e.printStackTrace();
            	log.error(e.getMessage(),e);
            	message.reply(respWriter.toError(serial_number,cmd));
            }
		 });
		 
		 vertx.eventBus().consumer("notify.push.wear", message->{
			 	String serial_number = null;
	            try {
	            	ZJSONObject params = Json.decode(message.body().toString(),ZJSONObject.class);
	                serial_number = getSerialNumber(params);
	            	Integer uid = params.getInteger("uid");
	            	String imei = params.getString("imei");
	                String sql = "select sex, height, weight from app_userinfo where uid = ?";
	                List<DeviceWearer> dwearer = jdbcTemplate.query(sql, new Object[]{uid}, (rs, i) -> {
	                	DeviceWearer dw = new DeviceWearer();
	                	dw.setHigh(rs.getInt("height"));
	                	dw.setSex(rs.getInt("sex"));
	                	dw.setWeight(rs.getInt("weight"));
	                	dw.setStepd((int)(rs.getInt("height") * 0.45));
	                    return dw;
	                });
	                if(dwearer != null && dwearer.size() > 0){
	                	Map<String,Object> result = new HashMap<String,Object>();
	                	DeviceWearer rwearer = dwearer.get(0);
	                	result.put("high", rwearer.getHigh());
	                	result.put("sex", rwearer.getSex());
	                	result.put("weight", rwearer.getWeight());
	                	result.put("stepd", rwearer.getStepd());
	                	JSONObject postJson = new JSONObject();
		                UserDevice ud = userDeviceService.getUserDeviceByUid(uid, "watch");
		                if(ud == null){
		                	message.reply(respWriter.toError(serial_number, RespCode.CODE_2000));
		                	return;
		                }
		                postJson.put("cmd", "L13");
		                postJson.put("uid", uid);
		                postJson.put("did", imei);
		                postJson.put("body", result);
		                vertx.eventBus().send("device.data.push", postJson.toString());
	                }
	            } catch (Exception e) {
	            	e.printStackTrace();
	            	log.error(e.getMessage(),e);
	            	message.reply(respWriter.toError(e.getMessage()));
	            }
		 });
		 
		 vertx.eventBus().consumer("notify.push.findDevice", message -> {
				log.info("notify.push.findDevice: " + message.body());
				String serial_number = null;
	            try {
	            	ZJSONObject params = Json.decode(message.body().toString(),ZJSONObject.class);
	                serial_number = getSerialNumber(params);
	                Integer uid = params.getInteger("uid");
	                String did = params.getString("did");
	                JSONObject queryParam = new JSONObject();
	                UserDevice ud = userDeviceService.getUserDeviceByUid(uid,"watch");
	                if(ud == null){
	                	message.reply(respWriter.toError(serial_number,RespCode.CODE_2000));
	                	return;
	                }
	                queryParam.put("uid", uid);
	                queryParam.put("did", did);
	                queryParam.put("cmd", "L1");
	                vertx.eventBus().send("device.data.push", queryParam.toString());
	                message.reply(respWriter.toSuccess(serial_number));
	            } catch (Exception e) {
                	e.printStackTrace();
                	log.error(e.getMessage(),e);
                	message.reply(respWriter.toError(serial_number));
	            }
	    });
		 
		 vertx.eventBus().consumer("notify.push.locate", message -> {
				log.info("notify.push.locate: "+ message.body());
				String serial_number = null;
	            try {
	            	ZJSONObject params = Json.decode(message.body().toString(),ZJSONObject.class);
	                serial_number = getSerialNumber(params);
	                String sn = serial_number;
	                Integer uid = params.getInteger("uid");
	                String did = params.getString("did");
	                UserDevice ud = userDeviceService.getUserDeviceByUid(uid, "watch");
	                if(ud == null){
	                	message.reply(respWriter.toError(serial_number,RespCode.CODE_2000));
	                	return;
	                }
	                redis.get(ud.getDid() + "_location", result -> {
	                	if(result.succeeded()){
	                		log.info(ud.getDid() + "_location: " + result.result());
	                		if(StringUtil.isNotEmpty(result.result())){
		                		String res = result.result().toString();
		                		JSONObject json = JSONObject.parseObject(res);
		                		message.reply(respWriter.toSuccess(sn, json));
	                		}else{
	                			LocationInfo locationInfo = locationInfoService.getLocationByImei(ud.getDid());
	                			JSONObject json = JSONObject.parseObject(Json.encode(locationInfo));
	        	                message.reply(respWriter.toSuccess(sn, json));
	                		}
	                		JSONObject queryParam = new JSONObject();
        	                queryParam.put("uid", uid);
        	                queryParam.put("did", did);
        	                queryParam.put("cmd", "L2");
        	                vertx.eventBus().send("device.data.push", queryParam.toString());
	                	}
	                });
	            } catch (Exception e) {
                	e.printStackTrace();
                	log.error(e.getMessage(),e);
                	message.reply(respWriter.toError(serial_number));
	            }
	    }); 
		 
		vertx.eventBus().consumer("notify.push.upgrade", message -> {
				log.info("notify.push.upgrade： " + message.body());
				String serial_number = null;
	            String cmd = null;
	            try {
	            	ZJSONObject params = Json.decode(message.body().toString(),ZJSONObject.class);
	                serial_number = getSerialNumber(params);
	                cmd = getCmd(params);
	                Integer uid = params.getInteger("uid");
	                String did = params.getString("did");
	                JSONObject queryParam = new JSONObject();
	                UserDevice ud = userDeviceService.getUserDeviceByUid(uid, "watch");
	                if(ud == null){
	                	message.reply(respWriter.toError(serial_number, RespCode.CODE_2000));
	                	return;
	                }
	                queryParam.put("uid", uid);
	                queryParam.put("did", did);
	                queryParam.put("cmd", "L7");
	                vertx.eventBus().send("device.data.push", queryParam.toString());
	                message.reply(respWriter.toCmdSuccess(serial_number,cmd));
	            } catch (Exception e) {
             	e.printStackTrace();
             	log.error(e.getMessage(),e);
             	message.reply(respWriter.toCmdError(serial_number,cmd));
	            }
	    });
		 
		vertx.eventBus().consumer("notify.push.offline", message -> {
			log.info("notify.push.offline: " + message.body());
			ZJSONObject params = Json.decode(message.body().toString(),ZJSONObject.class);
            Integer uid = params.getInteger("uid");
            String did = params.getString("did");
			List<UserDevice> list = userDeviceService.getUserDeviceExcludeWatchByUid(uid,did);
			// app other device offline
            jdbcTemplate.update("update app_user_device set status = 0 where did != ? and uid = ? and device_type != 'watch'", did, uid);
			for(UserDevice userDevice : list){
            	JSONObject postData = new JSONObject();
                postData.put("cmd", "L0");
                JSONObject body =  new JSONObject();
                body.put("uid", uid);
                postData.put("body", body);
				if("ios".equals(userDevice.getDevice_type())){
					String token = userDevice.getApple_id();
            		if(userDevice.getEnv() != null){
                		Integer env = Integer.parseInt(userDevice.getEnv());
                		IOSUtil.postData(token, env, postData.toString(), "下线通知", "您的账号在其他设备登录，会话已过期。");
            		}
            	}else {
            		postData.put("to", userDevice.getDid());
            		RestUtil.postData(userDevice.getServer(), postData);
            	}
			}
		});
		
		vertx.eventBus().consumer("user.device.get", message -> {
			log.debug("user.device.get: " + message.body());
			String serial_number = null;
			try{
				ZJSONObject params = Json.decode(message.body().toString(),ZJSONObject.class);
				serial_number = getSerialNumber(params);
	            Integer uid = params.getInteger("uid");
	            String did = params.getString("did");
				int status = userDeviceService.getUserDeviceStatus(uid,did);
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("sn", serial_number);
				resultMap.put("code", 200);
		        resultMap.put("msg", "success");
		        Map<String,Object> result = new HashMap<String,Object>();
				result.put("status", status);
		        resultMap.put("respData", result);
				message.reply(Json.encode(resultMap));
			}catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toError(serial_number,ex.getMessage()));
            }
		});
	}
}
