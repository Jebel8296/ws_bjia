package com.chinamcom.framework.user.verticle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.MessageInfo;
import com.chinamcom.framework.user.service.MessageInfoService;

@Component
public class MessageInfoVerticle extends BaseVerticle{
	
	private static Logger logger = Logger.getLogger(MessageInfoVerticle.class);
	
	@Autowired
	private MessageInfoService messageInfoService;
	@Override
	public void start() throws Exception {
		//查询闹钟请求
		vertx.eventBus().consumer("user.messageinfo.messageinfolist", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("查询消息列表请求：" + message.body());
			try{
			Integer uid= request.getJSONObject("messageinfo").getInteger("uid");
			Object pageNum = request.getJSONObject("messageinfo").get("pageNum");
			Object pageSize = request.getJSONObject("messageinfo").get("pageSize");
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("uid", uid);
			map.put("status", 2);//消息状态 0：未读 1：已读 2：删除
			Integer page=1;
			Integer size=20;
			if(pageNum!=null && pageNum!=""){
			page = (Integer)pageNum;
			}
			if(pageSize!=null && pageSize!=""){
				size = (Integer)pageSize;
			}
			Integer limitstar = (page-1)*size;
			map.put("limitstar", limitstar);
			map.put("pageSize", pageSize);
			List<MessageInfo> list= messageInfoService.messageInfoList(map);
			message.reply(respWriter.toSuccess(getSerialNumber(request), list));
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), "参数出错"));
			}
			});
		
		//查询消息详情
		vertx.eventBus().consumer("user.messageinfo.messageinfodetail", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("查询消息详情请求：" + message.body());
			try{
			Integer id= request.getJSONObject("messageinfo").getInteger("id");
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("id", id);
			MessageInfo messageInfo = messageInfoService.queryMessageInfo(map);
			message.reply(respWriter.toSuccess(getSerialNumber(request), messageInfo));
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), "参数出错"));
			}
		});
		
	    //删除
		vertx.eventBus().consumer("user.messageinfo.deletemessageinfo", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("删除消息请求：" + message.body());
			try{
				Integer id= request.getJSONObject("messageinfo").getInteger("id");
				Map<String , Object> map = new HashMap<String , Object>();
				map.put("id", id);
				map.put("status", 2);
				boolean flag = messageInfoService.deleteMessageInfo(map);
				if(flag){
					message.reply(respWriter.toSuccess(getSerialNumber(request), flag));
				}else{
					message.reply(respWriter.toError(getSerialNumber(request), "删除失败！"));
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), "参数出错"));
			}
		});
		
		//批量删除
		vertx.eventBus().consumer("user.messageinfo.batchdelete", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("删除消息请求：" + message.body());
			try{
			JSONArray jsonArray = request.getJSONObject("messageinfo").getJSONArray("ids");
			Object[] ids = jsonArray.toArray();
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("ids", ids);
			boolean flag = messageInfoService.batchdelete(map);
			if(flag){
				message.reply(respWriter.toSuccess(getSerialNumber(request), flag));
			}else{
				message.reply(respWriter.toError(getSerialNumber(request), "删除失败！"));
			}
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), "参数出错"));
			}
		});
	}
}
