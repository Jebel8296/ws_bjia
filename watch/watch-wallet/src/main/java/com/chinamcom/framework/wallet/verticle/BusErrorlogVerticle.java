package com.chinamcom.framework.wallet.verticle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.wallet.model.BusErrorlog;
import com.chinamcom.framework.wallet.service.BusErrorlogService;

@Component
public class BusErrorlogVerticle extends BaseVerticle{
private static Logger logger = Logger.getLogger(BusErrorlogVerticle.class);
	
	@Autowired
	private  BusErrorlogService busErrorlogService;
	
	@Override
	public void start() throws Exception {
		//公交卡充值记录
		vertx.eventBus().consumer("wallet.buserrorlog.add", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("公交卡错误记录请求报文:"+message.body());
			String serial_number = getSerialNumber(request);

			BusErrorlog br = (BusErrorlog) JSONObject.toJavaObject(request,BusErrorlog.class);
			try{
				int num = busErrorlogService.insert(br);
				message.reply(respWriter.toSuccess(serial_number));
			}catch(Exception e){
				logger.info("错误信息："+e);
				message.reply(respWriter.toError(serial_number, RespCode.CODE_500));
			}
			});
	}
}
