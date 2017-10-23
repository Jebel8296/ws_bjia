package com.chinamcom.framework.user.verticle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.ClockInfo;
import com.chinamcom.framework.user.push.DataPushUtil;
import com.chinamcom.framework.user.push.RepeatDateUtil;
import com.chinamcom.framework.user.service.ClockInfoService;
import com.chinamcom.framework.user.service.LoginService;

@Component
public class ClockInfoVerticle extends BaseVerticle{
	
	private static Logger logger = Logger.getLogger(ClockInfoVerticle.class);
/*	@Autowired
	private TransactionTemplate sjc;
	
	@Autowired
	private DataSource dataSource;
	
	@Qualifier(value="jdbcTemplate11")
	private JdbcTemplate jdbcTemplate;
	*/
	@Autowired
	private ClockInfoService clockInfoService;
	
	@Autowired
	private LoginService loginService;
	
	public ClockInfoVerticle() {
	}

	@Override
	public void start() throws Exception {
		//查询闹钟请求
		vertx.eventBus().consumer("user.clockinfoverticle.clockinfolist", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("查询闹钟列表请求：" + message.body());
			try{
			Integer id = request.getJSONObject("clockinfo").getInteger("id");//闹钟id
			Integer uid = request.getJSONObject("clockinfo").getInteger("uid");//用户uid
			String did = request.getJSONObject("clockinfo").getString("did");//设备id
			Integer modelType = request.getJSONObject("clockinfo").getInteger("modelType");//设备id
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("id", id);
			map.put("did", did);
			map.put("uid", uid);
			map.put("modelType", modelType);
			List<ClockInfo> list = clockInfoService.getClockInfoList(map);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					List<RepeatDateUtil> repeatlist = new ArrayList<RepeatDateUtil>();
					String repeat = list.get(i).getRepeat();
					char[] charArray = repeat.toCharArray();
					String[] weekDay = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
					for(int j=0;j<charArray.length;j++){
						RepeatDateUtil rdu = new RepeatDateUtil();
						rdu.setId(j);
						rdu.setName(weekDay[j]);
						rdu.setStatus(String.valueOf(charArray[j]));
						repeatlist.add(rdu);
					}
//					String repeatReplay = JSON.toJSONString(repeatlist);
//					System.out.println("...."+repeatReplay);
					list.get(i).setRepeatList(repeatlist);
				}
			}
			String result= respWriter.toSuccess(getSerialNumber(request), list);
			logger.info("返回结果："+result);
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
			});
		
		//打开关闭闹钟请求
		vertx.eventBus().consumer("user.clockinfoverticle.openAndCloseClock", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("打开关闭闹钟请求：" + message.body());
			try{
			Integer id = request.getJSONObject("clockinfo").getInteger("id");//闹钟id
			String did = request.getJSONObject("clockinfo").getString("did");//设备id
			Integer uid = request.getJSONObject("clockinfo").getInteger("uid");//用户uid
			Integer modelType = request.getJSONObject("clockinfo").getInteger("modelType");//设备id
			Integer status = request.getJSONObject("clockinfo").getInteger("status");//提醒状态（0关,1开）
//			Integer uid = request.getInteger("uid");
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("id", id);
			map.put("did", did);
			map.put("uid", uid);
			map.put("status", status);
			map.put("modelType", modelType);
			String result="";
			boolean flag = clockInfoService.openAndCloseClock(map);
			if(flag){
				ZJSONObject params =new ZJSONObject();
				params.put("uid", uid);
				params.put("did", did);
				params.put("modelType", modelType);
				vertx.eventBus().send("user.clockinfo.push", params.toString());
				result= respWriter.toSuccess(getSerialNumber(request));
			}else{
				result= respWriter.toError(getSerialNumber(request), RespCode.CODE_1009);
			}
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
			});
		
		//闹钟新增、久坐新增and修改
		vertx.eventBus().consumer("user.clockinfoverticle.saveClock", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("新增闹钟请求：" + message.body());
			try{
			String result="";
			String clockinfo = request.getString("clockinfo");
			String time = request.getJSONObject("clockinfo").getString("time");
			Integer modelType = request.getJSONObject("clockinfo").getInteger("modelType");
			Integer uid = request.getInteger("uid");
			String did = request.getJSONObject("clockinfo").getString("did");
			String voiceBody = request.getJSONObject("clockinfo").getString("voiceBody");
			JSONArray jsonArray = request.getJSONObject("clockinfo").getJSONArray("repeatList");
			List<RepeatDateUtil> list = null;
			Integer voiceId = null; //闹钟新增的时候的id
			if(jsonArray!=null){
				list = JSON.parseArray(jsonArray.toString(),RepeatDateUtil.class);
				Collections.sort(list, new Comparator<Object>() { 
					public int compare(Object a, Object b) {
						int one = ((RepeatDateUtil) a).getId();
						int two = ((RepeatDateUtil) b).getId();
						return one - two;
					}
				});
			}
			String repeatDate = "";
			if(list!=null && list.size() >0){
				for(int i =0 ;i< list.size();i++){
					repeatDate +=list.get(i).getStatus();
				}
			}
			ClockInfo bean = JSON.parseObject(clockinfo, ClockInfo.class);
			bean.setRepeat(repeatDate);
			bean.setRepeatList(list);
			bean.setUid(uid);
			if(modelType==1){//闹钟只有新增没有修改
				Map<String , Object> mapCount = new HashMap<String , Object>();
				mapCount.put("modelType", modelType);
				mapCount.put("uid", uid);
				mapCount.put("did", did);
				Integer count = clockInfoService.countClock(mapCount);
				if(count>=5){
					result= respWriter.toError(getSerialNumber(request), RespCode.CODE_1012);
				}else{
					Integer picType = getPicType(time);
					bean.setPicType(picType);
					Integer id =  clockInfoService.saveClock(bean);
					bean.setId(id);
					voiceId = id;
					bean.setRemainVoiceId("");
					result= respWriter.toSuccess(getSerialNumber(request), bean);
				}
			}else if(modelType==2){//久坐提醒的新增和修改
				//查询该用户下久坐是否存在，存在修改，不存在新增
				Map<String , Object> map = new HashMap<String , Object>();
				map.put("modelType", modelType);
				map.put("uid", uid);
				map.put("did", did);
				ClockInfo sit = clockInfoService.querySitInfo(map);
				if(sit!=null){//修改
					bean.setId(sit.getId());
					boolean flag =  clockInfoService.updateClock(bean);
					result= respWriter.toSuccess(getSerialNumber(request), flag);
				}else{//新增
					Integer id =  clockInfoService.saveClock(bean);
					bean.setId(id);
					bean.setRemainVoiceId("");
					result= respWriter.toSuccess(getSerialNumber(request), bean);
				}
			}
			JSONObject params =new JSONObject();
			params.put("uid", uid);
			params.put("voiceId", voiceId);
			params.put("did", bean.getDid());
			params.put("modelType", bean.getModelType());
			//目前闹钟不存在修改，只是新增所以语音上传推送的状态只有两种0：无、1：变更、 2：不变
			if(voiceBody!=null){
				params.put("voice", 1);
			}else{
				params.put("voice", 0);
			}
			log.info("-----consumer----"+params);
			vertx.eventBus().send("user.clockinfo.push", params.toString());
			logger.info("返回结果："+result);
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
			});
		//推送
		vertx.eventBus().consumer("user.clockinfo.push", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("推送闹钟请求：" + message.body());
			String did = request.getString("did");
			Integer voiceId = request.getInteger("voiceId");//新增的闹钟id，因为现在闹钟没有修改功能，所以不考虑上传语音修改的情况
			Integer modelType = request.getInteger("modelType");
			Integer uid = request.getInteger("uid");
			Integer voice = request.getInteger("voice");
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("uid", uid);
			map.put("did", did);
			map.put("modelType", modelType);
			String cmd ="";
			if(modelType==1){
				cmd ="L3";
			}else if(modelType==2){
				cmd ="L8";
			}
			DataPushUtil data = new DataPushUtil();
			List<ClockInfo> list = clockInfoService.getPushClockInfoList(map);
			String para = "";
			if(modelType==2){//久坐提醒
				ZJSONObject param = new ZJSONObject();
				for(int i =0 ;i<list.size();i++){
					param.put("time", Integer.parseInt(list.get(i).getTime()));
					param.put("status", list.get(i).getStatus());
				}
				para = data.pushData(cmd, uid, did, uid+"", param);
			}else if(modelType==1){//闹钟
				for(int i =0 ;i<list.size();i++){
					list.get(i).setTime(list.get(i).getTime().replace(":", "").replace(" ", ""));
					if(list.get(i).getId() == voiceId){
						list.get(i).setVoice(voice);
					}else{
						list.get(i).setVoice(2);//语音上传内容没变
					}
				}
				ZJSONObject pushPara = new ZJSONObject();
				ZJSONObject paramData = new ZJSONObject();
				pushPara.put("ack", 0);
				pushPara.put("to", uid);
				pushPara.put("cmd", cmd); 
				pushPara.put("uid", uid); 
				pushPara.put("did", did);
				paramData.put("data", list);
				pushPara.put("body", paramData);
//				para = data.pushData(cmd, uid, did, uid+"", list);
				para = pushPara.toString();
			}
			log.info("----send----"+para.toString());
			vertx.eventBus().send("device.data.push", para.toString(), res -> {
			});
			
		});
		//删除
		vertx.eventBus().consumer("user.clockinfoverticle.deleteClock", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("删除闹钟请求：" + message.body());
			try{
			Integer id = request.getJSONObject("clockinfo").getInteger("id");//闹钟id
			String did = request.getJSONObject("clockinfo").getString("did");//设备id
			Integer uid = request.getJSONObject("clockinfo").getInteger("uid");
			Integer modelType = request.getJSONObject("clockinfo").getInteger("modelType");//设备id
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("id", id);
			map.put("did", did);
			map.put("uid", uid);
			map.put("modelType", modelType);
			String result="";
			boolean falg =  clockInfoService.deleteClock(map);
//			Integer uid = request.getInteger("uid");
			if(falg){
				ZJSONObject params =new ZJSONObject();
				params.put("uid", uid);
				params.put("did", did);
				params.put("modelType", modelType);
				vertx.eventBus().send("user.clockinfo.push", params.toString());
				result= respWriter.toSuccess(getSerialNumber(request));
			}else{
				result= respWriter.toError(getSerialNumber(request), RespCode.CODE_1013);
			}
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
			});
		
		//闹钟修改
		vertx.eventBus().consumer("user.clockinfo.updateclock", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("修改闹钟请求：" + message.body());
			try{
			String result="";
			String clockinfo = request.getString("clockinfo");
			String time = request.getJSONObject("clockinfo").getString("time");
//			Integer modelType = request.getJSONObject("clockinfo").getInteger("modelType");
			Integer uid = request.getInteger("uid");
//			String did = request.getJSONObject("clockinfo").getString("did");
			String voiceBody = request.getJSONObject("clockinfo").getString("voiceBody");
			JSONArray jsonArray = request.getJSONObject("clockinfo").getJSONArray("repeatList");
			List<RepeatDateUtil> list = null;
			if(jsonArray!=null){
			list = JSON.parseArray(jsonArray.toString(),RepeatDateUtil.class);
			Collections.sort(list, new Comparator<Object>() { 
			public int compare(Object a, Object b) {
				   int one = ((RepeatDateUtil) a).getId();
				   int two = ((RepeatDateUtil) b).getId();
				   return one - two;
				   }
				});
			}
			String repeatDate = "";
			if(list!=null && list.size() >0){
				for(int i =0 ;i< list.size();i++){
					repeatDate +=list.get(i).getStatus();
				}
			}
			ClockInfo bean = JSON.parseObject(clockinfo, ClockInfo.class);
			bean.setRepeat(repeatDate);
			bean.setRepeatList(list);
			bean.setUid(uid);
			Integer picType = getPicType(time);
			bean.setPicType(picType);
			/*Map<String , Object> map = new HashMap<String , Object>();
			map.put("id", bean.getId());
			map.put("did", bean.getDid());
			map.put("uid", uid);
			map.put("modelType", bean.getModelType());
			ClockInfo clockInfoOld = clockInfoService.queryClockInfo(map);*/
			boolean flag =  clockInfoService.updateClock(bean);
			bean.setRemainVoiceId("");
			if(flag){
				JSONObject params =new JSONObject();
				params.put("uid", uid);
				params.put("voiceId", bean.getId());
				params.put("did", bean.getDid());
				params.put("modelType", bean.getModelType());
				//闹钟修改的时候，如果修改了语音，则voiceBody必不为空，0：无、1：变更、 2：不变
				if(voiceBody!=null){
					params.put("voice", 1);
				}else{
					params.put("voice", 0);
				}
				logger.info("-----consumer----"+params);
				vertx.eventBus().send("user.clockinfo.push", params.toString());
				result= respWriter.toSuccess(getSerialNumber(request));
			}else{
				result= respWriter.toError(getSerialNumber(request),RespCode.CODE_1009);
			}
			logger.info("返回结果："+result);
			message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
			});
	}
	
	@SuppressWarnings("deprecation")
	public Integer getPicType(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd");
		Date day = new Date();
		String date ="";
		if(StringUtil.isEmpty(time)){
		   date = sdf2.format(day);
		}else{
			date =sf.format(day)+" "+time;
		}
		Integer picType = null;
		try{
			Date s = sdf.parse(date);
//			System.out.println("11---"+s.getHours());
			Date morningStr = sdf.parse(sf.format(day)+" "+"4:00");
			Date morningEnd =sdf.parse(sf.format(day)+" "+" 10:00");
			Date middayStr =sdf.parse(sf.format(day)+" "+" 10:00");
			Date middayEnd =sdf.parse(sf.format(day)+" "+" 17:00");
			Date nightStr =sdf.parse(sf.format(day)+" "+" 17:00");
			Date nightEnd =sdf.parse(sf.format(day)+" "+" 23:59");
			if(morningStr.getHours()<=s.getHours() && s.getHours()<morningEnd.getHours()){
				 picType= 0;
			}else
			if(middayStr.getHours()<=s.getHours() && s.getHours()<middayEnd.getHours()){
				 picType= 1;
			}else
			if(nightStr.getHours()<=s.getHours() && s.getHours()<nightEnd.getHours()){
				picType= 2;
			}else{
				picType= 2;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return picType;
	}
}
