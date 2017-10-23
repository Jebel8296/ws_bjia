package com.chinamcom.framework.sport.verticle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.DateUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.sport.model.SportSummary;
import com.chinamcom.framework.sport.service.SportInfoTotalService;
import com.chinamcom.framework.sport.service.SportSummaryService;

@Component
public class SportSummaryVerticle extends BaseVerticle{
	private static Logger logger = Logger.getLogger(SportSummaryVerticle.class);
	
	@Autowired
	private SportSummaryService sportSummaryService;
	@Autowired
	private SportInfoTotalService sportInfoTotalService;
	
	public void start() throws Exception{
		/**
		 *  用来测试当天数据统计正确与否的方法（不做接口调用）
		 * 
		 * **/
		vertx.eventBus().consumer("sport.sportsummary.sportsummarylist", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("查询当日运动数据请求：" + message.body());
			Date date = new Date();
//			System.out.println("日期："+date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
			String dateStar = sdf.format(date)+" 00:00:00";
			String dateEnd = sdf.format(date)+" 23:59:59";
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("dateStar", dateStar);
			map.put("dateEnd", dateEnd);
//			List<SportSummary> delayList = sportSummaryService.delaySportInfo(map);
			List<SportSummary> delayList = sportInfoTotalService.delaySportInfoTotal(map);
			if(delayList !=null && delayList.size()>0){
				sportSummaryService.batchInsert(delayList);
			}
			String result = respWriter.toSuccess(getSerialNumber(request), delayList).toString();
			message.reply(result);
			});
		/**
		 *  后门，如果当天的的定时任务统计失败，
		 *  可以调用通过传入String日期统计当天的运动（不做接口调用）
		 * 
		 * **/
		vertx.eventBus().consumer("sport.sportsummary.sportsummaryDay", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("查询当日运动数据请求：" + message.body());
			String day = request.getString("day");
//			System.out.println("日期："+day);
			String dateStar = day+" 00:00:00";
			String dateEnd = day+" 23:59:59";
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("dateStar", dateStar);
			map.put("dateEnd", dateEnd);
//			List<SportSummary> delayList = sportSummaryService.delaySportInfo(map);
			List<SportSummary> delayList = sportInfoTotalService.delaySportInfoTotal(map);
			if(delayList !=null && delayList.size()>0){
				sportSummaryService.batchInsert(delayList);
			}
			String result = respWriter.toSuccess(getSerialNumber(request), delayList).toString();
			message.reply(result);
			});
		
		//TODO
		vertx.eventBus().consumer("sport.sportsummary.queryhistory", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("查询运动历史请求：" + message.body());
			try{
				Date date = new Date();
//				System.out.println("日期："+date);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStar = sdf.format(DateUtil.getTimesmorning());
				String dateEnd = sdf.format(DateUtil.getTimesnight());
				String imei = request.getString("imei");
				Integer sportType = request.getInteger("sportType");
				Integer type = request.getInteger("type");//标识日、周、月
				Map<String , Object> map = new HashMap<String , Object>();
				map.put("dateStar", dateStar);
				map.put("dateEnd", dateEnd);
				map.put("imei", imei);
				map.put("sportType", sportType);
				if(type==1){//日
					List<SportSummary> stepList = sportSummaryService.delayStepList(map);
					Integer sumStep = sportSummaryService.countStep(map);//总步数
					Integer target = null;
					if(sportType==1){
						target = sportSummaryService.queryTarget(map);//当日目标
					}
					List<SportSummary> useTimeList = sportSummaryService.delayUseTimeList(map);
					Integer useTimeSum = sportSummaryService.countUseTime(map);//总步数
					SportSummary delaySport = new SportSummary();
					if(stepList!=null && stepList.size()>0){
						delaySport.setStepList(stepList);
						for(int i=0;i<stepList.size();i++){
							stepList.get(i).setIndex(Integer.parseInt(stepList.get(i).getxValue()));
						}
					}
					delaySport.setStepSum(sumStep);
					if(target!=null){
						delaySport.setTarget(target);
					}
					if(useTimeList!=null && useTimeList.size()>0){
						delaySport.setUseTimeList(useTimeList);
						for(int i=0;i<useTimeList.size();i++){
							useTimeList.get(i).setIndex(Integer.parseInt(useTimeList.get(i).getxValue()));
						}
					}
					delaySport.setUseTimeSum(useTimeSum);
					List<SportSummary> distanceList = sportSummaryService.delayDistanceList(map);
					Integer distanceSum = sportSummaryService.countDistance(map);//总步数
					if(distanceList!=null && distanceList.size()>0){
						delaySport.setDistanceList(distanceList);
						for(int i=0;i<distanceList.size();i++){
							distanceList.get(i).setIndex(Integer.parseInt(distanceList.get(i).getxValue()));
						}
					}
					delaySport.setDistanceSum(distanceSum);
					//卡路里
					List<SportSummary> calorieList = sportSummaryService.delayCalorieList(map);
					Integer calorieSum = sportSummaryService.countCalorie(map);//总步数
					if(calorieList!=null && calorieList.size()>0){
						delaySport.setCalorieList(calorieList);
						for(int i=0;i<calorieList.size();i++){
							calorieList.get(i).setIndex(Integer.parseInt(calorieList.get(i).getxValue()));
						}
					}
					delaySport.setCalorieSum(calorieSum);
					
					List<SportSummary> heartRateList = sportSummaryService.delayHeartRateList(map);
					if(heartRateList!=null && heartRateList.size()>0){
						delaySport.setHeartRateList(heartRateList);
						for(int i=0;i<heartRateList.size();i++){
							heartRateList.get(i).setIndex(Integer.parseInt(heartRateList.get(i).getxValue()));
						}
					}
					delaySport.setSysTime(System.currentTimeMillis());
					Integer lastHeartRate = sportSummaryService.lastHeartRate(map);
					if(lastHeartRate!=null){
						delaySport.setHeartRateSum(lastHeartRate);
					}
					message.reply(respWriter.toSuccess(getSerialNumber(request), delaySport));
				}else if(type==2){//周
					String weekStar = sdf.format(DateUtil.getNextNDayMorning(date, 6));
					String weekEnd = sdf.format(DateUtil.getTimesnight());
					map.put("weekStar", weekStar);
					map.put("weekEnd", weekEnd);
					Long[] weekArray = new Long[7];
					for(int i=0;i<weekArray.length;i++){
						weekArray[i] = DateUtil.getNextNDayMorning(date, 6-i).getTime();
					}
					SportSummary delaySport = new SportSummary();
					delaySport.setAllXValue(weekArray);
					//List<SportSummary> stepList = sportSummaryService.weekStepList(map);
					List<SportSummary> stepList = sportSummaryService.weekHistoryStepList(map);//历史运动步数
					SportSummary dalySportInfo = sportSummaryService.dalyHistorySportInfo(map);//当日运动数据统计
					if(dalySportInfo!=null){
						stepList.add(stepList.size(), dalySportInfo);
					}
					Integer sumStep = sportSummaryService.countWeekStep(map);//总步数
					delaySport.setSysTime(System.currentTimeMillis());
					if(stepList!=null && stepList.size()>0){
						for(int i=0;i<stepList.size();i++){
							long createTime = stepList.get(i).getCreateTime().getTime();
							int index = indexDeal(createTime,weekArray,6);
							stepList.get(i).setIndex(index);
						}
						delaySport.setStepList(stepList);
					}
					delaySport.setStepSum(sumStep);
//					List<SportSummary> useTimeList = sportSummaryService.weekUseTimeList(map);
					List<SportSummary> useTimeList = sportSummaryService.weekHistoryUseTimeList(map);
					SportSummary dalyUseTimeInfo = sportSummaryService.dalyHistoryUseTimeInfo(map);
					if(dalyUseTimeInfo!=null){
						useTimeList.add(useTimeList.size(), dalyUseTimeInfo);
					}
					Integer useTimeSum = sportSummaryService.countWeekUseTime(map);//总耗时
					if(useTimeList!=null && useTimeList.size()>0){
						for(int i=0;i<useTimeList.size();i++){
							long createTime = useTimeList.get(i).getCreateTime().getTime();
							int index = indexDeal(createTime,weekArray,6);
							useTimeList.get(i).setIndex(index);
						}
						delaySport.setUseTimeList(useTimeList);
					}
					delaySport.setUseTimeSum(useTimeSum);
					List<SportSummary> distanceList = sportSummaryService.weekHistoryDistanceList(map);
					SportSummary dalydistanceInfo = sportSummaryService.dalyHistorydistanceInfo(map);
					if(dalydistanceInfo!=null){
						distanceList.add(distanceList.size(), dalydistanceInfo);
					}
					Integer distanceSum = sportSummaryService.countWeekDistance(map);//总距离
					if(distanceList!=null && distanceList.size()>0){
						for(int i=0;i<distanceList.size();i++){
							long createTime = distanceList.get(i).getCreateTime().getTime();
							int index = indexDeal(createTime,weekArray,6);
							distanceList.get(i).setIndex(index);
						}
						delaySport.setDistanceList(distanceList);
					}
					delaySport.setDistanceSum(distanceSum);
					//卡路里
					List<SportSummary> calorieList = sportSummaryService.weekHistoryCalorieList(map);
					SportSummary dalyCalorieInfo = sportSummaryService.dalyHistoryCalorieInfo(map);
					if(dalyCalorieInfo!=null){
						calorieList.add(calorieList.size(), dalyCalorieInfo);
					}
					Integer calorieSum = sportSummaryService.countWeekCalorie(map);//总步数
					if(calorieList!=null && calorieList.size()>0){
						for(int i=0;i<calorieList.size();i++){
							long createTime = calorieList.get(i).getCreateTime().getTime();
							int index = indexDeal(createTime,weekArray,6);
							calorieList.get(i).setIndex(index);
						}
						delaySport.setCalorieList(calorieList);
					}
					delaySport.setCalorieSum(calorieSum);
					List<SportSummary> heartRateList = sportSummaryService.weekHeartRateList(map);
					if(heartRateList!=null && heartRateList.size()>0){
						for(int i=0;i<heartRateList.size();i++){
							long createTime = heartRateList.get(i).getCreateTime().getTime();
							int index = indexDeal(createTime,weekArray,6);
							heartRateList.get(i).setIndex(index);
						}
						delaySport.setHeartRateList(heartRateList);
					}
					Integer lastHeartRate = sportSummaryService.lastHeartRate(map);
					if(lastHeartRate!=null){
						delaySport.setHeartRateSum(lastHeartRate);
					}
					message.reply(respWriter.toSuccess(getSerialNumber(request), delaySport));
				}else if(type==3){//月
					String monthStar = sdf.format(DateUtil.getNextNDayMorning(date, 34));
					String monthEnd = sdf.format(DateUtil.getTimesnight());
					map.put("weekStar", monthStar);
					map.put("weekEnd", monthEnd);
					Long[] monthArray = new Long[35];
					for(int i=0;i<monthArray.length;i++){
						monthArray[i] = DateUtil.getNextNDayMorning(date, 34-i).getTime();
					}
					Long[] monthXValue = new Long[5];
					for(int j=0;j<monthXValue.length;j++){
						monthXValue[j] = DateUtil.getNextNDayMorning(date, 35-7*(j+1)).getTime();
					}
					SportSummary sportSummary = new SportSummary();
					sportSummary.setAllXValue(monthXValue);
					List<SportSummary> stepList = sportSummaryService.weekHistoryStepList(map);
					SportSummary dalySportInfo = sportSummaryService.dalyHistorySportInfo(map);
					if(dalySportInfo!=null){
						stepList.add(stepList.size(), dalySportInfo);
					}
					Integer sumStep = sportSummaryService.countWeekStep(map);//总步数
					sportSummary.setSysTime(System.currentTimeMillis());
					if(stepList!=null && stepList.size()>0){
						for(int i=0;i<stepList.size();i++){
							long createTime = stepList.get(i).getCreateTime().getTime();
							int index = indexDeal(createTime,monthArray,34);
							stepList.get(i).setIndex(index);
						}
						sportSummary.setStepList(stepList);
					}
					sportSummary.setStepSum(sumStep);
					List<SportSummary> useTimeList = sportSummaryService.weekHistoryUseTimeList(map);
					SportSummary dalyUseTimeInfo = sportSummaryService.dalyHistoryUseTimeInfo(map);
					if(dalyUseTimeInfo!=null){
						useTimeList.add(useTimeList.size(), dalyUseTimeInfo);
					}
					Integer useTimeSum = sportSummaryService.countWeekUseTime(map);//总耗时
					if(useTimeList!=null && useTimeList.size()>0){
						for(int i=0;i<useTimeList.size();i++){
							long createTime = useTimeList.get(i).getCreateTime().getTime();
							int index = indexDeal(createTime,monthArray,34);
							useTimeList.get(i).setIndex(index);
						}
						sportSummary.setUseTimeList(useTimeList);
					}
					sportSummary.setUseTimeSum(useTimeSum);
					List<SportSummary> distanceList = sportSummaryService.weekHistoryDistanceList(map);
					SportSummary dalydistanceInfo = sportSummaryService.dalyHistorydistanceInfo(map);
					if(dalydistanceInfo!=null){
						distanceList.add(distanceList.size(), dalydistanceInfo);
					}
					Integer distanceSum = sportSummaryService.countWeekDistance(map);//总距离
					if(distanceList!=null && distanceList.size()>0){
						for(int i=0;i<distanceList.size();i++){
							long createTime = distanceList.get(i).getCreateTime().getTime();
							int index = indexDeal(createTime,monthArray,34);
							distanceList.get(i).setIndex(index);
						}
						sportSummary.setDistanceList(distanceList);
					}
					sportSummary.setDistanceSum(distanceSum);
					//卡路里
					List<SportSummary> calorieList = sportSummaryService.weekHistoryCalorieList(map);
					SportSummary dalyCalorieInfo = sportSummaryService.dalyHistoryCalorieInfo(map);
					if(dalyCalorieInfo!=null){
						calorieList.add(calorieList.size(), dalyCalorieInfo);
					}
					Integer calorieSum = sportSummaryService.countWeekCalorie(map);//总步数
					if(calorieList!=null && calorieList.size()>0){
						for(int i=0;i<calorieList.size();i++){
							long createTime = calorieList.get(i).getCreateTime().getTime();
							int index = indexDeal(createTime,monthArray,34);
							calorieList.get(i).setIndex(index);
						}
						sportSummary.setCalorieList(calorieList);
					}
					sportSummary.setCalorieSum(calorieSum);
					List<SportSummary> heartRateList = sportSummaryService.weekHeartRateList(map);
					if(heartRateList!=null && heartRateList.size()>0){
						for(int i=0;i<heartRateList.size();i++){
							long createTime = heartRateList.get(i).getCreateTime().getTime();
							int index = indexDeal(createTime,monthArray,34);
							heartRateList.get(i).setIndex(index);
						}
						sportSummary.setHeartRateList(heartRateList);
					}
					Integer lastHeartRate = sportSummaryService.lastHeartRate(map);
					if(lastHeartRate!=null){
						sportSummary.setHeartRateSum(lastHeartRate);
					}
					/*
					List<SportSummary> heartRateList = sportSummaryService.monthHeartRateList(map);
					if(heartRateList!=null && heartRateList.size()>0){
						sportSummary.setHeartRateList(heartRateList);
					}*/
					message.reply(respWriter.toSuccess(getSerialNumber(request),sportSummary));
				}else{
					message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
		});
	}
	
	public Integer indexDeal(long createTime,Long[] weekArray,Integer datenum){
		Integer index = null;
			for(int j=0;j<weekArray.length;j++){
				if(createTime>weekArray[weekArray.length-1]){
					index = datenum;
					break;
				}
				if(createTime>=weekArray[j]){
					continue;
				}else if(createTime<weekArray[j]){
					index = j-1==0?0:j-1;
					break;
				}
			}
		return index;
	}
}