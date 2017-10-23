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
public class CallHisService extends BaseVerticle {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer("data.callinfo.post", message -> {
            log.info("data.callinfo.post: " + message.body());
            String serial_number = null;
            String cmd = null;
            try {
            	ZJSONObject params = Json.decode(message.body().toString(), ZJSONObject.class);
				serial_number = getSerialNumber(params);
				cmd = getCmd(params);
				String imei = params.getString("imei");
				JSONArray jsonData = params.getJSONArray("data");
				if(jsonData == null){
	                Integer type = params.getInteger("type");
	                String nick_name = params.getString("name");
	                Integer use_time = params.getInteger("duration");
	                String phone = params.getString("phone");
	                Date call_time = new Date(params.getLong("begin") * 1000);
	                String sql = "" +
	                        "INSERT INTO call_info (" +
	                        "  device_imei," +
	                        "  nick_name," +
	                        "  phone_no," +
	                        "  type," +
	                        "  duration," +
	                        "  begin_time" +
	                        ") " +
	                        "VALUES" +
	                        "  (?, ?, ?, ?, ?, ?)";
	
	                jdbcTemplate.update(sql, imei, nick_name, phone, type, use_time, call_time);
				}else{
					for(int i = 0; i < jsonData.size(); i++){
						JSONObject json = jsonData.getJSONObject(i);
						Integer type = json.getInteger("type");
		                String nick_name = json.getString("name");
		                Integer use_time = json.getInteger("duration");
		                String phone = json.getString("phone");
		                Date call_time = new Date(json.getLong("begin") * 1000);
		                String sql = "" +
		                        "INSERT INTO call_info (" +
		                        "  device_imei," +
		                        "  nick_name," +
		                        "  phone_no," +
		                        "  type," +
		                        "  duration," +
		                        "  begin_time" +
		                        ") " +
		                        "VALUES" +
		                        "  (?, ?, ?, ?, ?, ?)";
		
		                jdbcTemplate.update(sql, imei, nick_name, phone, type, use_time, call_time);
					}
				}
                message.reply(respWriter.toCmdSuccess(serial_number,cmd));
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toCmdError(serial_number,cmd));
            }
        });
    }
}
