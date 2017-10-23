package com.chinamcom.framework.user.verticle;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.MessageSet;
import com.chinamcom.framework.user.service.MessageSetService;

@Component
public class MessageSetVerticle extends BaseVerticle{
	private static Logger logger = Logger.getLogger(MessageSetVerticle.class);
	@Autowired
	private MessageSetService messageSetService;
	
	public MessageSetVerticle() {
	}

	@Override
	public void start() throws Exception {
		
		vertx.eventBus().consumer("user.messageset.messagesetlist", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("查询消息設置请求：" + message.body());
			try{
			Object did = request.get("did");//设备id
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("did", did);
			MessageSet messageSet = messageSetService.messagesetlist(map);
			String result= respWriter.toSuccess(getSerialNumber(request), messageSet);
			logger.info("返回结果："+result);
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), "参数出错"));
			}
			});
		
		vertx.eventBus().consumer("user.messageset.updatemessageset", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("修改消息設置请求：" + message.body());
			try{
			String messageset = request.getString("messageset");
			MessageSet bean = JSON.parseObject(messageset, MessageSet.class);
			boolean flag = messageSetService.updateMessageSet(bean);
			String result= respWriter.toSuccess(getSerialNumber(request), flag);
			logger.info("返回结果："+result);
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), "参数出错"));
			}
			});
	}
}
