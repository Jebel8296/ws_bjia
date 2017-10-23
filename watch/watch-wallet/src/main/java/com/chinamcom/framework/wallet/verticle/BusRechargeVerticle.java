package com.chinamcom.framework.wallet.verticle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.wallet.model.BusRecharge;
import com.chinamcom.framework.wallet.service.BusRechargeService;

@Component
public class BusRechargeVerticle extends BaseVerticle{
	private static Logger logger = Logger.getLogger(BusRechargeVerticle.class);
	
	@Autowired
	private  BusRechargeService busRechargeService;
	
	@Override
	public void start() throws Exception {
		//公交卡充值记录
		vertx.eventBus().consumer("wallet.busrecharge.record", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("充值记录请求报文:"+message.body());
			String serial_number = getSerialNumber(request);
//			String imei = request.getString("imei");
//			Integer uid = request.getInteger("uid");
			BusRecharge br = (BusRecharge) JSONObject.toJavaObject(request,BusRecharge.class);
			try{
				busRechargeService.insert(br);
				message.reply(respWriter.toSuccess(serial_number));
			}catch(Exception e){
				message.reply(respWriter.toError(serial_number, RespCode.CODE_500));
			}
			});
	}

}
