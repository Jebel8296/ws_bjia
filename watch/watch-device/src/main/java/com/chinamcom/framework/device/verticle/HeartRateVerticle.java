package com.chinamcom.framework.device.verticle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.constants.Constants;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.device.service.LocationInfoService;
import com.chinamcom.framework.device.util.RestUtil;


@Service
public class HeartRateVerticle extends BaseVerticle {
	
	@Autowired
	private LocationInfoService locationInfoService;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
    public void start() throws Exception {
		 vertx.eventBus().consumer("data.heartrate.post", message -> {
			log.info("data.heartrate.post: " + message.body());
			String serial_number = null;
            String cmd = null;
            try {
            	ZJSONObject params = Json.decode(message.body().toString(),ZJSONObject.class);
                serial_number = getSerialNumber(params);
                cmd = getCmd(params);
                String imei = params.getString("imei");
                JSONArray data = params.getJSONArray("data");
                for(Object da : data){
                	JSONObject obj = (JSONObject)da;
                	String sql = "" +
 	                        "INSERT INTO heart_rate_info (" +
 	                        "  type," +
 	                        "  imei," +
 	                        "  heart_rate," +
 	                        "  time" +
 	                        ") " +
 	                        "VALUES" +
 	                        "  (?, ?, ?, ?)";
 	                jdbcTemplate.update(sql, obj.getInteger("type"), imei, obj.getInteger("hr"), new Date(obj.getLong("time")*1000));
                }
            	message.reply(respWriter.toCmdSuccess(serial_number,cmd));
            } catch (Exception e) {
            	e.printStackTrace();
            	log.error(e.getMessage(),e);
            	message.reply(respWriter.toCmdError(serial_number,cmd));
            }
		 });
		 
		 vertx.eventBus().consumer("data.weather.get", message -> {
			 log.info("data.weather.get: " + message.body());
            try {
                String imei = message.body().toString();
                String adcode = locationInfoService.getadCodeByImei(imei);
                if(adcode == null){
                	message.reply("");
                	return;
                }
                Map<String, String> restParams = new HashMap<String,String>();
                restParams.put("key", Constants.AMAP_WEBAPI_KEY);
                restParams.put("city", adcode);
                String result = RestUtil.getData(Constants.WEATHER_URL, restParams);
                JSONObject jsonResult = JSONObject.parseObject(result);
                String status = jsonResult.getString("status");
                Integer weather = null;
                Integer temperature = null;
                if("1".equals(status)){
                	JSONObject live = jsonResult.getJSONArray("lives").getJSONObject(0);
                	String weatherDesc = live.getString("weather");
                	if(weatherDesc.contains("晴")){
                		weather = 0;
                	}else if(weatherDesc.contains("阴") || weatherDesc.contains("多云")){
                		weather = 1;
                	}else if(weatherDesc.contains("雨")){
                		weather = 2;
                	}else if(weatherDesc.contains("雪")){
                		weather = 3;
                	}
                	else{
                		weather = 4;
                	}
                	temperature = live.getInteger("temperature");
                }
                JSONObject resultJson = new JSONObject();
                resultJson.put("weather", weather);
                resultJson.put("temp", temperature);
                message.reply(resultJson.toString());
			 } catch (Exception e) {
            	e.printStackTrace();
            	log.error(e.getMessage(),e);
            	message.reply(respWriter.toError(""));
            }
		 });
		 
		 vertx.eventBus().consumer("data.weather.getbycity", message -> {
				log.info("data.weather.getbycity: " + message.body());
	            try {
	                String adcode = message.body().toString();
	                if(adcode == null){
	                	message.reply("");
	                	return;
	                }
	                Map<String, String> restParams = new HashMap<String,String>();
	                restParams.put("key", Constants.AMAP_WEBAPI_KEY);
	                restParams.put("city", adcode);
	                String result = RestUtil.getData(Constants.WEATHER_URL, restParams);
	                JSONObject jsonResult = JSONObject.parseObject(result);
	                String status = jsonResult.getString("status");
	                Integer weather = null;
	                Integer temperature = null;
	                if("1".equals(status)){
	                	JSONObject live = jsonResult.getJSONArray("lives").getJSONObject(0);
	                	String weatherDesc = live.getString("weather");
	                	if(weatherDesc.contains("晴")){
	                		weather = 0;
	                	}else if(weatherDesc.contains("阴") || weatherDesc.contains("多云")){
	                		weather = 1;
	                	}else if(weatherDesc.contains("雨")){
	                		weather = 2;
	                	}else if(weatherDesc.contains("雪")){
	                		weather = 3;
	                	}
	                	else{
	                		weather = 4;
	                	}
	                	temperature = live.getInteger("temperature");
	                }
	                JSONObject resultJson = new JSONObject();
	                resultJson.put("weather", weather);
	                resultJson.put("temp", temperature);
	                message.reply(resultJson.toString());
				 } catch (Exception e) {
	            	e.printStackTrace();
	            	log.error(e.getMessage(),e);
	            	message.reply(respWriter.toError(""));
	            }
			 });
	}
}
