package com.chinamcom.framework.user.verticle;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.FeedBackInfo;
import com.chinamcom.framework.user.service.FeedBackInfoService;

@Component
public class FeedBackInfoVerticle extends BaseVerticle{
	private static Logger logger = Logger.getLogger(FeedBackInfoVerticle.class);
	
		@Autowired
		private FeedBackInfoService feedBackInfoService;
		
		public FeedBackInfoVerticle(){
			
		}

		@Override
		public void start() throws Exception {
			vertx.eventBus().consumer("user.feedbackinfo.feedbackinfolist", message -> {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				logger.info("查询意见反馈列表请求：" + message.body());
				try{
				Object userId = request.getJSONObject("feedbackinfo").get("userId");
				Object pageNum = request.getJSONObject("feedbackinfo").get("pageNum");
				Object pageSize = request.getJSONObject("feedbackinfo").get("pageSize");
				Map<String , Object> map = new HashMap<String , Object>();
				map.put("userId", userId);
				Integer page=1;
				Integer size =5;
				if(pageNum!=null && pageNum!=""){
				page = (Integer)pageNum;
				}
				if(pageSize!=null && pageSize!=""){
					size = (Integer)pageSize;
				}
				Integer limitstar = (page-1)*size;
				map.put("limitstar", limitstar);
				map.put("pageSize", pageSize);
				List<FeedBackInfo> list = feedBackInfoService.feedBackInfoList(map);
				String result= respWriter.toSuccess(getSerialNumber(request), list);
				logger.info("返回结果："+result);
				message.reply(result);
				}catch(Exception e){
					e.printStackTrace();
					logger.error(e.getMessage(), e);
					message.reply(respWriter.toError(getSerialNumber(request), "参数出错"));
				}
				});
			
			vertx.eventBus().consumer("user.feedbackinfo.addfeedbackinfo", message -> {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				logger.info("新增意见反馈请求：" + message.body());
				try{
				String feedBackInfo = request.getString("feedbackinfo");
				FeedBackInfo bean = JSON.parseObject(feedBackInfo, FeedBackInfo.class);
				Date day = new Date();
				bean.setFeedbackTime(day);
				Integer primaryId =  feedBackInfoService.addFeedBackInfo(bean);
				bean.setId(primaryId);
				String result= "";
				if(primaryId!=null){
					result= respWriter.toSuccess(getSerialNumber(request), "反馈成功");
				}else{
					result= respWriter.toError(getSerialNumber(request), "反馈失败");
				}
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
