package com.chinamcom.framework.device.verticle;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.utils.PropertyConfigLoader;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.device.model.UserDevice;
import com.chinamcom.framework.device.service.UserDeviceService;
import com.chinamcom.framework.device.util.RestUtil;

@Service
public class TaskVerticle extends BaseVerticle {
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Autowired
	private UserDeviceService userDeviceService;
	
	@Autowired
    private PropertyConfigLoader config;
	
    @Override
    public void start() throws Exception {
    	
    	vertx.setPeriodic(Integer.parseInt(config.get("push.weather.time", 1000*60*60+"")), t -> {
    		log.info("task data.weatch.push");
			try {
				List<UserDevice> list = userDeviceService.getUserDeviceByStatus("watch");
				for(UserDevice ud : list){
					vertx.eventBus().send("data.weather.get", ud.getDid(), result->{
						if(result.succeeded()){
							if(StringUtil.isNotEmpty(result.result().body().toString())){
								JSONObject reponse = JSONObject.parseObject(result.result().body().toString());
								JSONObject postData = new JSONObject();
								postData.put("cmd", "L14");
								postData.put("to", ud.getDid());
								postData.put("body", reponse);
								RestUtil.postData(ud.getServer(), postData);
							}
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
        });
    }
}
