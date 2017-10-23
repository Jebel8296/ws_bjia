package com.chinamcom.framework.user.verticle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.AppMessageSend;
import com.chinamcom.framework.user.service.AppMessageSendService;

@Component
public class AppMessageSendVerticle extends BaseVerticle{
	private static Logger logger = Logger.getLogger(AppMessageSendVerticle.class);
	@Autowired
	private AppMessageSendService appMessageSendService;
	
	public AppMessageSendVerticle() {
	}

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer("user.appmessagesend.sysmessagelist", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("查询系統消息列表请求：" + message.body());
			Object deviceId = request.get("deviceId");//设备id
			Object type = request.get("type");//消息類型
			Map<String , Object> map = new HashMap<String , Object>();
//			map.put("id", id);
			map.put("deviceId", deviceId);
			map.put("type", type);
			List<AppMessageSend> list = appMessageSendService.sysMessageList(map);
			String result= respWriter.toSuccess(getSerialNumber(request), list).toString();
			logger.info("返回结果："+result);
			message.reply(result);
			});
		
		vertx.eventBus().consumer("user.appmessagesend.deleteSysMessage", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("刪除系統消息请求：" + message.body());
			Object id = request.get("id");//设备id
			Object deviceId = request.get("deviceId");//设备id
			Object type = request.get("type");//消息類型
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("id", id);
			map.put("deviceId", deviceId);
			map.put("type", type);
			boolean flag = appMessageSendService.deleteSysMessage(map);
			String result= respWriter.toSuccess(getSerialNumber(request), flag).toString();
			logger.info("返回结果："+result);
			message.reply(result);
			});
	}
}
