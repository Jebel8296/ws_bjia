package com.chinamcom.framework.sociality.verticle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.constants.ServerConstants;
import com.chinamcom.framework.common.exception.ServiceException;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.sociality.model.CallInfo;
import com.chinamcom.framework.sociality.service.CallInfoService;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/03
 */
@Component
public class CallInfoVerticle extends BaseVerticle {
	@Autowired
	private CallInfoService callInfoService;
	
	@Override
    public void start() throws Exception {
        vertx.eventBus().consumer(ServerConstants.SERVER_CALL_INFO_LIST, message -> {
            log.info("message received:" + message.body());
            ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
            String serial_number = getSerialNumber(reqData);
            String imei = reqData.getString("imei");
            String type = reqData.getString("type");
            Integer pageNo = reqData.getInteger("pageNo");
            Integer pageSize = reqData.getInteger("pageSize");
            List<CallInfo> list = callInfoService.getList(imei, type, pageNo, pageSize);
            message.reply(respWriter.toSuccess(serial_number, list));
        });
        
        vertx.eventBus().consumer(ServerConstants.SERVER_CALL_INFO_DEL, message -> {
            log.info("message received:" + message.body());
            ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
            String serial_number = getSerialNumber(reqData);
            String ids = reqData.getString("ids");
            Integer uid = reqData.getInteger("uid");
            String did = reqData.getString("did");
            if(ids==null){
            	message.reply(respWriter.toError(serial_number, "参数不正确"));
            }
            try {
            	List<CallInfo> list = callInfoService.selectByids(ids);
				checkResultDelete(callInfoService.deleteByids(ids));
				if(list != null && list.size() > 0){
					JSONObject postJson = new JSONObject();
					List<JSONObject> timeList = new ArrayList<JSONObject>();
					for(CallInfo call : list){
						ZJSONObject timeJson = new ZJSONObject();
						timeJson.put("time", call.getBeginTime().getTime()/1000);
						timeList.add(timeJson);
					}
					JSONObject data = new JSONObject();
					data.put("data", timeList);
					postJson.put("uid", uid);
					postJson.put("did", did);
					postJson.put("cmd", "L15");
					postJson.put("body", data);
					vertx.eventBus().send("device.data.push",postJson.toString());
				}
				
			} catch (ServiceException e) {
				message.reply(respWriter.toError(serial_number, e.getRespCode()));
			}
            message.reply(respWriter.toSuccess(serial_number));
        });
    }
}
