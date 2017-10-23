package com.chinamcom.framework.device.verticle;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.verticle.BaseVerticle;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/22
 */
@Service
public class SportService extends BaseVerticle {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer("data.sport.post", message -> {
			log.info("data.sport.post: " + message.body());
			String serial_number = null;
			String cmd = null;
			try {
				ZJSONObject params = Json.decode(message.body().toString(), ZJSONObject.class);
				serial_number = getSerialNumber(params);
				cmd = getCmd(params);
				String imei = params.getString("imei");
				JSONArray jsonData = params.getJSONArray("data");
				if(jsonData != null){
					for(int i = 0; i < jsonData.size(); i++){
						JSONObject json = jsonData.getJSONObject(i);
						Integer sport_type = json.getInteger("type");
						Integer step = json.getInteger("step");
						Integer distance = json.getInteger("distance");
						Integer energy = json.getInteger("energy");
						Integer heart_rate = json.getInteger("hr");
						Integer use_time = json.getInteger("use_time");
						Date upload_time = new Date(json.getLong("upload_time") * 1000);
						if(distance > 0 && step > 0){
							String sql = "" + "INSERT INTO sport_info ("
									+ "  imei," + "  sport_type,"
									+ "  step," + "  distance,"
									+ "  energy," + "  heart_rate,"
									+ "  upload_time,"
									+ "  use_time" + ") " 
									+ "VALUES"
									+ "  (?, ?, ?, ?, ?, ?, ?, ?)";
							jdbcTemplate.update(sql, imei, sport_type, step, distance, energy, heart_rate, upload_time, use_time);
						}
					}
				}
				message.reply(respWriter.toCmdSuccess(serial_number, cmd));
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
				message.reply(respWriter.toCmdError(serial_number, cmd));
			}
        });
        
        vertx.eventBus().consumer("data.sporttotal.post",message->{
        	log.info("data.sporttotal.post: " + message.body());
        	String serial_number = null;
			String cmd = null;
			try {
				ZJSONObject params = Json.decode(message.body().toString(), ZJSONObject.class);
				serial_number = getSerialNumber(params);
				cmd = getCmd(params);
				String imei = params.getString("imei");
				JSONArray jsonData = params.getJSONArray("data");
				if(jsonData != null){
					for(int i = 0; i < jsonData.size(); i++){
						JSONObject json = jsonData.getJSONObject(i);
						Integer sport_type = json.getInteger("type");
						Integer step = json.getInteger("step");
						Integer distance = json.getInteger("distance");
						Integer energy = json.getInteger("energy");
						Integer heart_rate = json.getInteger("hr");
						Integer use_time = json.getInteger("use_time");
						Date upload_time = new Date(json.getLong("upload_time") * 1000);
						if(distance > 0 && step > 0){
							String sql = "" + "INSERT INTO sport_info_total ("
									+ "  imei," + "  sport_type,"
									+ "  step," + "  distance,"
									+ "  energy," + "  heart_rate,"
									+ "  upload_time,"
									+ "  use_time" + ") " 
									+ "VALUES"
									+ "  (?, ?, ?, ?, ?, ?, ?, ?)";
							jdbcTemplate.update(sql, imei, sport_type, step, distance, energy, heart_rate, upload_time, use_time);
						}
					}
				}
				message.reply(respWriter.toCmdSuccess(serial_number, cmd));
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
				message.reply(respWriter.toCmdError(serial_number, cmd));
			}
        	
        });
    }
}
