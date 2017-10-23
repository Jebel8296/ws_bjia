package com.chinamcom.framework.user.verticle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.ClassModel;
import com.chinamcom.framework.user.push.ClassModelPushUtil;
import com.chinamcom.framework.user.push.RepeatDateUtil;
import com.chinamcom.framework.user.service.ClassModelService;
@Component
public class ClassModelVerticle extends BaseVerticle{
	private static Logger logger = Logger.getLogger(ClassModelVerticle.class);
		@Autowired
		private ClassModelService classModelService;
		
		public ClassModelVerticle() {
		}

		@Override
		public void start() throws Exception {
			vertx.eventBus().consumer("user.classmodel.classmodellist", message -> {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				logger.info("查询课堂模式列表请求：" + message.body());
				try{
//				Integer id = request.getJSONObject("classmodel").getInteger("id");//闹钟id
				String imei = request.getJSONObject("classmodel").getString("imei");//设备id
				Integer uid = request.getJSONObject("classmodel").getInteger("uid");//
				Map<String , Object> map = new HashMap<String , Object>();
//				map.put("id", id);
				map.put("imei", imei);
				map.put("uid", uid);
				List<ClassModel> list = classModelService.getClassModelList(map);
				List<RepeatDateUtil> repeatlist = new ArrayList<RepeatDateUtil>();
				ZJSONObject params =new ZJSONObject();
				if(list!=null && list.size()>0){
						String repeat = list.get(0).getRepeat();
						char[] charArray = repeat.toCharArray();
						String[] weekDay = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
						for(int j=0;j<charArray.length;j++){
							RepeatDateUtil rdu = new RepeatDateUtil();
							rdu.setId(j);
							rdu.setName(weekDay[j]);
							rdu.setStatus(String.valueOf(charArray[j]));
							repeatlist.add(rdu);
						}
//						list.get(0).setRepeatList(repeatlist);
						params.put("power", list.get(0).getPower());
						params.put("repeatList", repeatlist);
						params.put("classmodel", list);
				}
				String result= respWriter.toSuccess(getSerialNumber(request), params);
				logger.info("返回结果："+result);
				message.reply(result);
				}catch(Exception e){
					e.printStackTrace();
					logger.error(e.getMessage(), e);
					message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
				}
				});
			
				vertx.eventBus().consumer("user.classmodel.push", message -> {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				logger.info("推送课堂模式列表请求：" + message.body());
				Integer uid = request.getInteger("uid");
				String imei = request.getString("imei");
				Map<String , Object> map = new HashMap<String , Object>();
				map.put("imei", imei);
				map.put("uid", uid);
				map.put("power", 1);//总开关
				map.put("status", 1);//小开关
				List<ClassModel> list = classModelService.getClassModelList(map);
				List<ClassModelPushUtil> pushList = new ArrayList<ClassModelPushUtil>();
				if(list !=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						ClassModel classModel = list.get(i);
						ClassModelPushUtil pushUtil = new ClassModelPushUtil();
						pushUtil.setId(classModel.getId());
						pushUtil.setRepeat(classModel.getRepeat());
						pushUtil.setTime(classModel.getTime().replace(":", "").replace(" ", ""));
						pushList.add(pushUtil);
					}
				}
				String cmd="L10";
				if(uid!=null && imei!=null){
					ZJSONObject params =new ZJSONObject();
					ZJSONObject data =new ZJSONObject();
					data.put("data", pushList);
					params.put("body", data);
					params.put("cmd", cmd);
					params.put("uid", uid);
					params.put("did", imei);
					logger.info("----send---"+params.toString());
					vertx.eventBus().send("device.data.push", params.toString(), res -> {});
				}else{
					message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_1014));
				}
				});
			
			vertx.eventBus().consumer("user.classmodel.updateclassmodel", message -> {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				logger.info("修改保存课堂模式列表请求：" + message.body());
				try{
				Integer power= request.getInteger("power");
				JSONArray repeatList = request.getJSONArray("repeatList");
				List<RepeatDateUtil> utilList = null;
				if(repeatList!=null){
					utilList = JSON.parseArray(repeatList.toString(),RepeatDateUtil.class);
					Collections.sort(utilList, new Comparator<Object>() { 
						public int compare(Object a, Object b) {
							int one = ((RepeatDateUtil) a).getId();
							int two = ((RepeatDateUtil) b).getId();
							return one - two;
						}
					});
				}
				String repeatDate = "";
				if(utilList!=null && utilList.size()>0){
				for(int i = 0 ;i< utilList.size();i++){
					repeatDate +=utilList.get(i).getStatus();
				}}
				String result="";
				boolean flag = false;
				JSONArray jsonArray = request.getJSONArray("classmodel");
				List<ClassModel> list = JSON.parseArray(jsonArray.toString(),ClassModel.class);
				if(list!=null && list.size()>0){
					for(int j =0 ;j<list.size();j++){
//						Integer id = list.get(j).getId();
						Map<String , Object> map = new HashMap<String , Object>();
						map.put("uid", list.get(j).getUid());
						map.put("imei", list.get(j).getImei());
						map.put("displayOrder", list.get(j).getDisplayOrder());
						ClassModel cm = classModelService.queryClassMode(map);
						list.get(j).setPower(power);
						list.get(j).setRepeat(repeatDate);
						ClassModel bean = list.get(j);
						bean.setRepeat(repeatDate);
						if(cm!=null){
							bean.setId(cm.getId());
							flag = classModelService.updateclassmodel(bean);
							if(flag){
								result= respWriter.toSuccess(getSerialNumber(request), flag);
							}else{
								result= respWriter.toError(getSerialNumber(request), RespCode.CODE_1009);
								return ;
							}
						}else{
							flag = classModelService.insertClassModel(bean);
							if(flag){
								result= respWriter.toSuccess(getSerialNumber(request), flag);
							}else{
								result= respWriter.toError(getSerialNumber(request), RespCode.CODE_1015);
								return ;
							}
						}
					}
				}
				ZJSONObject params =new ZJSONObject();
				params.put("uid", list.get(0).getUid());
				params.put("imei", list.get(0).getImei());
				vertx.eventBus().send("user.classmodel.push", params.toString());
				logger.info("返回结果："+result);
				message.reply(result);
				}catch(Exception e){
					e.printStackTrace();
					logger.error(e.getMessage(), e);
					message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
				}
				});
			
			//關閉或開啟課堂模式
			vertx.eventBus().consumer("user.classmodel.classmodelopenorclose", message -> {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				logger.info("關閉或開啟課堂模式请求：" + message.body());
				try{
				Object imei = request.get("imei");//设备id
				Object id = request.get("id");//设备id
				Object uid = request.get("uid");//设备id
				Object status = request.get("status");//狀態
				Map<String , Object> map = new HashMap<String , Object>();
				map.put("id", id);
				map.put("uid", uid);
				map.put("imei", imei);
				map.put("status", status);
				boolean flag = classModelService.classmodelopenorclose(map);
				if(flag){
					ZJSONObject params =new ZJSONObject();
					params.put("uid",uid);
					params.put("imei", imei);
					vertx.eventBus().send("user.classmodel.push", params.toString());
				}
				String result= respWriter.toSuccess(getSerialNumber(request), flag);
				logger.info("返回结果："+result);
				message.reply(result);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
				});
			
			//关闭或者开启课堂模式总开关
			vertx.eventBus().consumer("user.classmodel.updatepower", message -> {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				logger.info("关闭或者开启课堂模式总开关：" + message.body());
				try{
				Object imei = request.get("imei");//设备id
				Object uid = request.get("uid");//设备id
				Object power = request.get("power");//狀態
				Map<String , Object> map = new HashMap<String , Object>();
				map.put("imei", imei);
				map.put("uid", uid);
				map.put("power", power);
				boolean flag = classModelService.updatepower(map);
				if(flag){
					ZJSONObject params =new ZJSONObject();
					params.put("uid",uid);
					params.put("imei", imei);
					vertx.eventBus().send("user.classmodel.push", params.toString());
				}
				String result= respWriter.toSuccess(getSerialNumber(request), flag);
				logger.info("返回结果："+result);
				message.reply(result);
				}catch(Exception e){
					e.printStackTrace();
					logger.error(e.getMessage(), e);
					message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
				}
				});
			
			/**
			 * 废弃接口
			 * 
			 * **/
			vertx.eventBus().consumer("user.classmodel.saveclassmodel", message -> {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				logger.info("保存课堂模式列表请求：" + message.body());
				JSONArray jsonArray = request.getJSONArray("classmodel");
				List<ClassModel> list = JSON.parseArray(jsonArray.toString(),ClassModel.class);
				boolean flag = classModelService.batchInsert(list);
				String result="";
				if(flag){
					result= respWriter.toSuccess(getSerialNumber(request), flag);
				}else{
					result= respWriter.toError(getSerialNumber(request), "保存失败");
				}
				logger.info("返回结果："+result);
				message.reply(result);
				});
		}
}
