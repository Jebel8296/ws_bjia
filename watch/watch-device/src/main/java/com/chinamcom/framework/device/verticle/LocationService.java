package com.chinamcom.framework.device.verticle;

import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.constants.Constants;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.rest.RestTemplateFactory;
import com.chinamcom.framework.common.utils.PropertyConfigLoader;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.device.model.MessageInfo;
import com.chinamcom.framework.device.model.UserDevice;
import com.chinamcom.framework.device.service.MessageInfoService;
import com.chinamcom.framework.device.service.UserDeviceService;
import com.chinamcom.framework.device.service.UserService;
import com.chinamcom.framework.device.util.IOSUtil;
import com.chinamcom.framework.device.util.RestUtil;

/**
 * Created by Administrator on 2016/8/10.
 */
@Service
public class LocationService extends BaseVerticle {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private PropertyConfigLoader config;
    
    @Autowired
    private UserDeviceService userDeviceService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private MessageInfoService messageInfoService;
    
    @Autowired
    private ThreadPoolTaskExecutor locationTaskExecutor;

    @Override
    public void start() throws Exception {
        String sql = "" +
                "INSERT INTO location_info (" +
                "  imei," +
                "  sport_type," +
                "  location_type," +
                "  location_time," +
                "  accuracy," +
                "  altitude," +
                "  lon," +
                "  lat," +
                "  lon_fix," +
                "  lat_fix," +
                "  address," +
                "  adcode," +
                "  msg_id,"
                + "act" +
                ") " +
                "VALUES" +
                "  (?, ?, ?, ?, ?,"
                + " ?, ?, ?, ?, ?,"
                + " ?, ?, ?, ?)";
        
        String sql_lbs = "" +
                "INSERT INTO location_info (" +
                "  imei," +
                "  sport_type," +
                "  location_type," +
                "  location_time," +
                "  accuracy," +
                "  altitude," +
                "  lon," +
                "  lat," +
                "  lon_fix," +
                "  lat_fix," +
                "  lbs_data," +
                "  address," +
                "  adcode," +
                "  msg_id,"
                + "act" +
                ") " +
                "VALUES" +
                "  (?, ?, ?, ?, ?,"
                + " ?, ?, ?, ?, ?,"
                + " ?, ?, ?, ?, ?)";
        RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        
    	RedisClient redis = RedisClient.create(vertx, new RedisOptions()
		.setHost(config.get("redis.host", "localhost"))
		.setPort(Integer.parseInt(config.get("redis.port")))
		.setTcpKeepAlive(true)
		.setSelect(Integer.valueOf(config.get("redis.db", "3"))));
        
    	vertx.eventBus().consumer("data.location.post",message->{
    		vertx.eventBus().send("data.location.process", message.body().toString());
    		ZJSONObject data = Json.decode(message.body().toString(),ZJSONObject.class);
    		Map<String,Object> result = new HashMap<>();
            result.put("sport_type", data.getInteger("sport_type"));
    		message.reply(respWriter.toCmdSuccess(getSerialNumber(data), getCmd(data), result));
    	});
    	
        // 更新用户信息：余额 推送用户信息更新指令进行客户端数据刷新同步
        vertx.eventBus().consumer("data.location.process", message -> {
            log.info("data.location.process: " + message.body());
            try {
            	ZJSONObject data = Json.decode(message.body().toString(),ZJSONObject.class);
                String imei = data.getString("imei");
                Integer sport_type = data.getInteger("sport_type");
                JSONArray location_data = data.getJSONArray("data");
                for (Object object : location_data) {
                	locationTaskExecutor.execute(new LocationConsumer(imei, sport_type, sql, sql_lbs, (JSONObject)object, redis, restTemplate));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
            }
        });
    }
    
    class LocationConsumer implements Runnable{
    	private String imei;
    	private Integer sport_type;
    	private String sql;
    	private String sql_lbs;
    	private JSONObject row;
    	private RedisClient redis;
    	private RestTemplate restTemplate;
    	public LocationConsumer(String imei,Integer sport_type,String sql, String sql_lbs, JSONObject row, RedisClient redis, RestTemplate restTemplate){
    		this.imei = imei;
    		this.sport_type = sport_type;
    		this.row = row;
    		this.redis = redis;
    		this.restTemplate = restTemplate;
    		this.sql = sql;
    		this.sql_lbs = sql_lbs;
    	}

		@Override
		public void run() {
            Integer location_type = row.getInteger("type");
            String time = row.getString("time");
            Integer msgId = row.getInteger("msgId");
            Integer act = row.getInteger("act");
            String plon = null;
            String plat = null;
            String address = null;
            String adcode = null;
            if(location_type == 1){
            	 JSONObject lbsObject = row.getJSONObject("lbs");
            	 Double lon = lbsObject.getDouble("lon");
            	 Double lat = lbsObject.getDouble("lat");
            	 if(lon == 0 && lat == 0){
            		 return;
            	 }
            	 Double accuracy = lbsObject.getDouble("accuracy");
            	 Double altitude = lbsObject.getDouble("altitude");
            	 Map<String, String> params = new HashMap<>();
                 params.put("locations", lon+","+lat);
                 params.put("coordsys", "gps");
                 params.put("output", "json");
                 params.put("key", Constants.AMAP_WEBAPI_KEY);
                 String queryString = "";
                 for (Map.Entry<String, String> entry : params.entrySet()) {
                     queryString = queryString + entry.getKey() + "=" + entry.getValue() + "&";
                 }
                 String ret = restTemplate.getForObject(Constants.COORDINATE_CONVERT_URL  + "?" + queryString, String.class);
                 JSONObject location_amap = JSONObject.parseObject(ret);
                 if("1".equals(location_amap.getString("status"))){
                	 String location = location_amap.getString("locations");
                	 String[] locationArr = location.split(",");
                	 String lon_fix = locationArr[0];
                	 String lat_fix = locationArr[1];
                	 plon = lon_fix;
                     plat = lat_fix;
                	 Map<String, String> regeoParams = new HashMap<>();
                	 regeoParams.put("location", location);
                	 regeoParams.put("output", "json");
                	 regeoParams.put("key", Constants.AMAP_WEBAPI_KEY);
                     String regeoQueryString = "";
                     for (Map.Entry<String, String> entry : regeoParams.entrySet()) {
                    	 regeoQueryString = regeoQueryString + entry.getKey() + "=" + entry.getValue() + "&";
                     }
                     JSONObject regeo_amap = JSONObject.parseObject(restTemplate.getForObject(Constants.REGEO_URL  + "?" + regeoQueryString, String.class));
                     if("1".equals(regeo_amap.getString("status"))){
                    	 JSONObject regeocode = regeo_amap.getJSONObject("regeocode");
                    	 address = regeocode.getString("formatted_address");
                    	 adcode  = regeocode.getJSONObject("addressComponent").getString("adcode");
                     }
                	 jdbcTemplate.update(sql, imei, sport_type, location_type, time, accuracy, altitude, lon, lat, lon_fix, lat_fix, address, adcode, msgId, act);
                 }else{
                	 jdbcTemplate.update(sql, imei, sport_type, location_type, time, accuracy, altitude, lon, lat, null, null, address, adcode, msgId, act);
                 }
            }else if(location_type == 2){
            	JSONArray cells = row.getJSONObject("lbs").getJSONArray("lbsData");
            	String bts = "";
                String nearbts = "";
                for (Object cell : cells) {
                    JSONObject _cell = (JSONObject) cell;
                    Integer mcc = _cell.getInteger("mc");
                    Integer mnc = _cell.getInteger("mn");
                    Integer lac = _cell.getInteger("l");
                    Integer cellid = _cell.getInteger("c");
                    Integer signal = _cell.getInteger("s");
                    String _tmpbts = mcc + "," + mnc + "," + lac + "," + cellid + "," + signal;
                    if (bts.isEmpty()) {
                        bts = _tmpbts;
                    } else {
                        nearbts = nearbts + _tmpbts + "|";
                    }
                }
                Map<String, String> params = new HashMap<>();
                params.put("accesstype", "0");
                params.put("imei", imei);
                params.put("smac", "");
                params.put("cdma", "0");
                params.put("bts", bts);
                params.put("nearbts", nearbts);
                params.put("output", "json");
                params.put("network", "GSM/EDGE");
                params.put("key", Constants.AMAP_DEVICEAPI_KEY);

                String queryString = "";
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    queryString = queryString + entry.getKey() + "=" + entry.getValue() + "&";
                }
                String ret = restTemplate.getForObject(Constants.POSITION_URL + "?" + queryString, String.class);
                JSONObject location_amap = JSONObject.parseObject(ret);
                if (location_amap.getString("status").equals("1")) {
                    String loc = location_amap.getJSONObject("result").getString("location");
                    String lon = loc.split(",")[0];
                    String lat = loc.split(",")[1];
                    address = location_amap.getJSONObject("result").getString("desc");
                    adcode = location_amap.getJSONObject("result").getString("adcode");
                    jdbcTemplate.update(sql_lbs, imei, sport_type, location_type, time, null, null, lon, lat, lon, lat, cells.toJSONString(), address, adcode, msgId, act);
                    plon = lon;
                    plat = lat;
                }
            }
            JSONObject body =  new JSONObject();
            body.put("lon", plon);
            body.put("lat", plat);
            body.put("address", address);
            redis.setex(imei + "_location", 60 * 30, body.toJSONString(), result->{
            	if(result.succeeded()){
            		
            	}
            });
            // Push location to app.
            if(msgId != null){
            	String did = messageInfoService.getDidByMessageId(msgId);
                UserDevice userDevice = userDeviceService.getUserDeviceByDid(did);
                if(userDevice != null){
                	String device_type = userDevice.getDevice_type();
                	JSONObject postData = new JSONObject();
                    postData.put("cmd", "L2");
                    postData.put("body", body);
                	if("ios".equals(device_type)){
                		String token = userDevice.getApple_id();
                		if(userDevice.getEnv() != null){
                    		Integer env = Integer.parseInt(userDevice.getEnv());
                    		IOSUtil.postData(token, env, postData.toString(), "定位通知", "手表当前位置:" + address);
                		}
                	}else if("android".equals(device_type)){
                		postData.put("to", userDevice.getDid());
                		RestUtil.postData(userDevice.getServer(), postData);
                	}
                }else{
                	log.error("msgId: " + msgId + " did: " + did + " is offline.");
                }
            }
            // Pust temp to watch.
            if(sport_type == 7){
            	UserDevice userDevice = userDeviceService.getUserDeviceByDid(imei);
            	if(userDevice != null){
                	vertx.eventBus().send("data.weather.getbycity", adcode, result->{
                		if(result.succeeded()){
                			JSONObject postData = new JSONObject();
    	                    postData.put("cmd", "L14");
    	                    postData.put("to", userDevice.getDid());
    	                    JSONObject weather =  JSONObject.parseObject(result.result().body().toString());
    	                    postData.put("body", weather);
    	                    RestUtil.postData(userDevice.getServer(), postData);
                		}
                	});
            	}else{
                	log.info("did: " + imei + " 设备不在线");
                }
            }
            // Save SosInfo to db.
            if(sport_type == 6){
            	Integer uid = userService.getUserIdByImei(imei);
            	String content = userService.getSosMessageByUid(uid);
            	String remark = plon + "," + plat +  "," + address;
            	messageInfoService.addMessage(new MessageInfo(uid, 1, "SOS报警", content, 0, remark));
            }
		}
    }
}
