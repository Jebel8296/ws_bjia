package com.chinamcom.framework.sport.verticle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constants.ServerConstants;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.DateUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.sport.model.LocationInfo;
import com.chinamcom.framework.sport.model.SportInfo;
//import com.chinamcom.framework.sport.model.SportInfoTotal;
import com.chinamcom.framework.sport.model.SportSummary;
import com.chinamcom.framework.sport.service.HeartRateInfoService;
import com.chinamcom.framework.sport.service.LocationInfoService;
import com.chinamcom.framework.sport.service.SportInfoService;
import com.chinamcom.framework.sport.service.SportInfoTotalService;
import com.chinamcom.framework.sport.service.SportSummaryService;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/08
 */
@Component
public class SportInfoVerticle extends BaseVerticle{

	@Autowired
	private SportInfoService sportService;
	@Autowired
	private LocationInfoService locationInfoService;
	@Autowired
	private HeartRateInfoService heartRateInfoService;
	@Autowired
	private SportSummaryService sportSummaryService;
	@Autowired
	private SportInfoTotalService sportInfoTotalService;
	
	@Override
	public void start() throws Exception{
		vertx.eventBus().consumer(
				ServerConstants.SERVER_SPORT_INFO_ADD,
				message -> {
					log.info("message received:" + message.body());
					ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
					String serial_number = getSerialNumber(reqData);
					String imei = reqData.getString("imei");
					Integer sportType = reqData.getInteger("sport_type");
					Integer step = reqData.getInteger("step");
					Integer distance = reqData.getInteger("distance");
					Integer energy = reqData.getInteger("energy");
					Integer heartRate = reqData.getInteger("heart_rate");
					Integer useTime = reqData.getInteger("use_time");
					SportInfo sportInfo = new SportInfo();
					sportInfo.setImei(imei);
					sportInfo.setSportType(sportType);
					sportInfo.setStep(step);
					sportInfo.setDistance(distance);
					sportInfo.setEnergy(energy);
					sportInfo.setHeartRate(heartRate);
					sportInfo.setUseTime(useTime);
					sportService.insert(sportInfo);
					message.reply(respWriter.toSuccess(serial_number));
				});
		 
		vertx.eventBus().consumer(
				ServerConstants.SERVER_SPORT_INFO_QUERY,
				message -> {
					log.info("首页当日运动数据:" + message.body()); 
					ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
					String serial_number = getSerialNumber(reqData);
					try{
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
					String dateStar = sdf.format(date)+" 00:00:00";
					String dateEnd = sdf.format(date)+" 23:59:59";
					Map<String , Object> map = new HashMap<String , Object>();
					map.put("dateStar", dateStar);
					map.put("dateEnd", dateEnd);  
					String imei = reqData.getString("imei");
					Integer sportType = reqData.getInteger("sportType");
					Integer uid = reqData.getInteger("uid");
					map.put("uid", uid);
					map.put("imei", imei);
					map.put("sportType", sportType);
					//校验uid与imei是否存在绑定关系
					boolean checkImie = sportInfoTotalService.checkImie(map);
					if(checkImie){
					//按小时、imei、运动模式分类此运动数据
					List<SportInfo> list = sportService.queryTodaySportInfo(map);
					SportInfo sportInfoCount = sportService.countTotal(map);
					Integer targetStep = sportService.queryTargetStep(map);
					SportInfo sportInfo = new SportInfo();
					if(list!=null &&list.size()>0){
						sportInfo.setList(list);
					}
					if(sportInfoCount!=null){
						sportInfo.setTotalStep(sportInfoCount.getTotalStep());
						sportInfo.setTotalEnergy(sportInfoCount.getTotalEnergy());
						sportInfo.setTotalDistance(sportInfoCount.getTotalDistance());
						sportInfo.setTotalUseTime(sportInfoCount.getTotalUseTime());
					}else{
						sportInfo.setTotalStep(0);
						sportInfo.setTotalEnergy(0);
						sportInfo.setTotalDistance(0);
						sportInfo.setTotalUseTime(0);
					}
					sportInfo.setTargetStep(targetStep);
					sportInfo.setSysTime(System.currentTimeMillis());
					//查当天心率
//					List<HeartRateInfo> heartRateList = heartRateInfoService.queryTodayHeartRate(map);
					map.remove("sportType");
					List<SportSummary> heartRateList = sportSummaryService.delayHeartRateList(map);
					if(heartRateList!=null && heartRateList.size()>0){
						/*if(heartRateList.size()<35){
							List<HeartRateInfo> newHeartRateList = new ArrayList<HeartRateInfo>();
							int length = 35-heartRateList.size();
							for(int i =0 ;i<length;i++){
								HeartRateInfo heartRate = new HeartRateInfo();
								heartRate.setHeartRate(0+"");
								heartRate.setImei(imei);
								newHeartRateList.add(heartRate);
							}
							heartRateList.addAll(0,newHeartRateList);
						}*/
						for(int i=0;i<heartRateList.size();i++){
							heartRateList.get(i).setIndex(Integer.parseInt(heartRateList.get(i).getxValue()));
						}
						sportInfo.setHeartRateList(heartRateList);
					}
					//排名
					int rank = sportService.querySportRank(map);
					sportInfo.setRank(rank);
					Integer lastHeartRate = sportSummaryService.lastHeartRate(map);
					if(lastHeartRate!=null){
						sportInfo.setHeartRateSum(lastHeartRate);
					}
					ZJSONObject params =new ZJSONObject();
					params.put("uid", uid);
					params.put("did", imei);
					params.put("cmd", "L17");
					vertx.eventBus().send("device.data.push", params.toString());
					log.info("L17推送数据:"+params.toString());
					String result = respWriter.toSuccess(serial_number,sportInfo);
					message.reply(result);
					}else{
					message.reply(respWriter.toError(serial_number,RespCode.CODE_2001));
					}
					}catch(Exception e){
						e.printStackTrace();
						log.error(e.getMessage(), e);
						message.reply(respWriter.toError(serial_number, RespCode.CODE_500));
					}
				});
		
		//首页当日运动数据（全量）
		/*vertx.eventBus().consumer("sport.sportinfototal.query", message -> {
					log.info("首页当日运动数据（全量）:" + message.body()); 
					ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
					String serial_number = getSerialNumber(reqData);
					Map<String , Object> map = new HashMap<String , Object>();
					String imei = reqData.getString("imei");
					Integer sportType = reqData.getInteger("sportType");
					Integer uid = reqData.getInteger("uid");
					map.put("uid", uid);
					map.put("imei", imei);
					map.put("sportType", sportType);
					//校验uid与imei是否存在绑定关系
					boolean checkImie = sportInfoTotalService.checkImie(map);
					if(checkImie){
					//按小时、imei、运动模式分类此运动数据
					List<SportInfoTotal> list = sportInfoTotalService.queryTodaySportTotal(map);
					SportInfoTotal sportInfoCount = sportInfoTotalService.countTotal(map);
					Integer targetStep = sportService.queryTargetStep(map);
					SportInfoTotal sportInfo = new SportInfoTotal();
					if(list!=null &&list.size()>0){
						sportInfo.setSprotList(list);
					}
					if(sportInfoCount!=null){
						sportInfo.setTotalStep(sportInfoCount.getTotalStep());
						sportInfo.setTotalEnergy(sportInfoCount.getTotalEnergy());
						sportInfo.setTotalDistance(sportInfoCount.getTotalDistance());
						sportInfo.setTotalUseTime(sportInfoCount.getTotalUseTime());
					}else{
						sportInfo.setTotalStep(0);
						sportInfo.setTotalEnergy(0);
						sportInfo.setTotalDistance(0);
						sportInfo.setTotalUseTime(0);
					}
					sportInfo.setTargetStep(targetStep);
					sportInfo.setSysTime(System.currentTimeMillis());
					//查当天心率
					map.remove("sportType");
					List<SportSummary> heartRateList = sportSummaryService.delayHeartRateList(map);
					if(heartRateList!=null && heartRateList.size()>0){
						for(int i=0;i<heartRateList.size();i++){
							heartRateList.get(i).setIndex(Integer.parseInt(heartRateList.get(i).getxValue()));
						}
						sportInfo.setHeartRateList(heartRateList);
					}
					//排名
					int rank = sportInfoTotalService.querySportRank(map);
					sportInfo.setRank(rank);
					sportInfo.setHeartRateSum(sportSummaryService.lastHeartRate(map));
					ZJSONObject params =new ZJSONObject();
					params.put("uid", uid);
					params.put("did", imei);
					params.put("cmd", "L17");
					vertx.eventBus().send("device.data.push", params.toString());
					log.info("L17推送数据:"+params.toString());
					String result = respWriter.toSuccess(serial_number,sportInfo);
					message.reply(result);
					}else{
						message.reply(respWriter.toError(serial_number,RespCode.CODE_2001));
					}
				});*/
		
		vertx.eventBus().consumer("sport.sportinfo.sportinfomodel",message -> {
					log.info("运动模式列表请求:" + message.body()); 
					ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
					String serial_number = getSerialNumber(reqData);
					try{
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String dateStar = sdf.format(date)+" 00:00:00";
//					System.out.println("dateStar"+dateStar);
					String dateEnd = sdf.format(date)+" 23:59:59";
//					String dateStar = "2016-08-26 00:00:00";
//					String dateEnd = "2016-08-26 23:59:59";
					Map<String , Object> map = new HashMap<String , Object>();
					map.put("dateStar", dateStar);
					map.put("dateEnd", dateEnd);  
					String imei = reqData.getString("imei");
					Integer sportType = reqData.getInteger("sportType");
					Integer uid = reqData.getInteger("uid");
					map.put("uid", uid);
					map.put("imei", imei);
					map.put("sportType", sportType);
					List<SportInfo> list = sportService.sportInfoModel(map);
					List<SportInfo> newList = new ArrayList<SportInfo>();
					List<Integer> typeList = new ArrayList<Integer>();
					List<Integer> arrayList =Arrays.asList(1,2,3,4,5);
					if(list!=null && list.size()>0 && list.size()<5){
						for(int i =0;i<list.size();i++){
							SportInfo si = list.get(i);
							typeList.add(si.getSportType());
							newList.add(list.get(i));
						}
						for(int m =0;m<arrayList.size();m++){
							if(!typeList.contains(arrayList.get(m))){
								SportInfo so = setSportInfo(arrayList.get(m));
								newList.add(so);
							}
						}
					}else if(list==null || list.size()<=0){
						for(int i =0;i<5;i++){
							newList.add(setSportInfo(i+1));
						}
					}else{
						newList = list;
					}
					/*for (SportInfo b : newList) {
			            System.out.println(b.getSportType() + "|");
			        }*/
//					System.out.println("00000000000000");
					newList = listSort(newList);
			        /*for (SportInfo b : newList) {
			            System.out.println(b.getSportType() + "|");
			        }*/
					message.reply(respWriter.toSuccess(serial_number,newList));
					}catch(Exception e){
						e.printStackTrace();
						log.error(e.getMessage(), e);
						message.reply(respWriter.toError(serial_number, RespCode.CODE_500));
					}
				});
		
		//轨迹
		vertx.eventBus().consumer("sport.sportinfo.sportinfopath",message -> {
			log.info("运动轨迹请求:" + message.body()); 
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			try{
			String serial_number = getSerialNumber(reqData);
			String imei = reqData.getJSONObject("locationinfo").getString("imei");
			Integer sportType = reqData.getJSONObject("locationinfo").getInteger("sportType");
			Integer uid = reqData.getJSONObject("locationinfo").getInteger("uid");
			long locationDate = reqData.getJSONObject("locationinfo").getLongValue("locationDate");//时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateStar = DateUtil.getTimesmorning();
			Date dateEnd = DateUtil.getTimesnight();
			Date now =new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String nowDayStr = format.format(now);
			Date nowDay = format.parse(nowDayStr);
			long[] dates = new long[7];
			for(int i=0;i<3;i++){
				dates[i] = getNextnDay(now,3-i);
				dates[3] = nowDay.getTime();
				dates[6-i] = getAfternDay(now,3-i);
			}
//			System.out.println("注册时间在7天以内："+dates);
			Map<String , Object> map = new HashMap<String , Object>();
			if(locationDate!=0){
				Date locationDay = sdf.parse(sdf.format(locationDate));
				String date = sdf.format(locationDay);
				map.put("dateStar", getNextnDayMorning(date));
				map.put("dateEnd", getNextnDayNight(date));  
			}else{//locationDate为空的时候默认取当天
				map.put("dateStar", sdf.format(dateStar));
				map.put("dateEnd", sdf.format(dateEnd));  
			}
			map.put("uid", uid);
			map.put("imei", imei);
			if(sportType!=0){
				map.put("sportType", sportType);
			}
			List<LocationInfo> list = locationInfoService.sportInfoPath(map);
			//查询轨迹中断的点
			List<LocationInfo> breakPoints = locationInfoService.locationBreakPoints(map);
//			List<LocationInfo> newList =new ArrayList<LocationInfo>();
			List<List<LocationInfo>> resultList = new ArrayList<List<LocationInfo>>();
			List<Integer> brokenp = new ArrayList<Integer>();
			if(list.size()>0){
				if(breakPoints.size()>0){
					int point = breakPoints.size();
					for(int i =0;i<point;i++){
							for(int j=0;j<list.size();j++){
								if(list.get(j).getId().intValue() == breakPoints.get(i).getId().intValue()){
									brokenp.add(i,j);
									System.out.println("^^^^^^^^^^^"+j+"*****"+list.get(j).getId());
								}
							}
							}
					if(point>=2){
						resultList.add(list.subList(0, brokenp.get(0)+1));
						for(int m=0;m<brokenp.size();m++){
							if(m+1==point){
							resultList.add(list.subList(brokenp.get(m)+1,list.size()));
							}else{
							resultList.add(list.subList(brokenp.get(m)+1, brokenp.get(m+1)+1));
							}
						}
					}else{
						resultList.add(list.subList(0, brokenp.get(0)+1));
						resultList.add(list.subList(brokenp.get(0)+1,list.size()));
					}
				}else{
					resultList.add(list);
				}
			}
			ZJSONObject params =new ZJSONObject();
			if(list!=null && list.size()>0){
				params.put("locationinfo", resultList);
			}
			params.put("dates", dates);
			if(sportType!=0){
				SportInfo sportInfo = sportService.queryTotalInfo(map);
				if(sportInfo!=null){
					if(sportInfo.getTotalDistance()!=null){
						params.put("totalDistance", sportInfo.getTotalDistance());
					}else{
						params.put("totalDistance", 0);
					}
					if(sportInfo.getTotalEnergy()!=null){
						params.put("totalEnergy", sportInfo.getTotalEnergy());
					}else{
						params.put("totalEnergy", 0);
					}
					if(sportInfo.getTotalUseTime()!=null){
						params.put("totalUseTime", sportInfo.getTotalUseTime());
					}else{
						params.put("totalUseTime", 0);
					}
				}else{
					params.put("totalDistance", 0);
					params.put("totalEnergy", 0);
					params.put("totalUseTime", 0);
				}
			}
			message.reply(respWriter.toSuccess(serial_number,params));
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(reqData), RespCode.CODE_500));
			}
		});
	}
	//返回传入时间的0点0分0秒  long类型
	public static String getNextnDayMorning(String dateTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(dateTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);  
		calendar.set(Calendar.SECOND, 0);  
		calendar.set(Calendar.MINUTE, 0);  
		calendar.set(Calendar.MILLISECOND, 0);
//		calendar.add(Calendar.DAY_OF_MONTH, -n);
		date = calendar.getTime();
//		System.out.println("零点："+format.format(date));
		return format.format(date);
	}
	//返回传入时间的24点0分0秒  long类型
		public static String getNextnDayNight(String dateTime) throws ParseException{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(dateTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 24);  
			calendar.set(Calendar.SECOND, 0);  
			calendar.set(Calendar.MINUTE, 0);  
			calendar.set(Calendar.MILLISECOND, 0);
//			calendar.add(Calendar.DAY_OF_MONTH, -n);
			date = calendar.getTime();
//			System.out.println("天的24点："+date);
			return format.format(date);
		}
		
		/**
		 * 计算两个日期之间相差的天数 后-前
		 * 
		 * **/
		 public static int daysBetween(Date smdate,Date bdate) throws ParseException{    
		        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		        smdate=sdf.parse(sdf.format(smdate));  
		        bdate=sdf.parse(sdf.format(bdate));  
		        Calendar cal = Calendar.getInstance();    
		        cal.setTime(smdate);    
		        long time1 = cal.getTimeInMillis();                 
		        cal.setTime(bdate);    
		        long time2 = cal.getTimeInMillis();         
		        long between_days=(time2-time1)/(1000*3600*24);  
		            
		       return Integer.parseInt(String.valueOf(between_days));           
		    }   
		 /**
		  * 获得传入参数日期之前的n天
		  * 
		  * **/
		 public static long getNextnDay(Date smdate,int n) throws ParseException{    
			 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 	String nextDay = sdf.format(smdate);
				Date date = sdf.parse(nextDay);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, -n);
				date = calendar.getTime();
//				System.out.println(sdf.format(date));
		       return date.getTime();  
		    } 
		 /**
		  * 获得传入参数日期之后的n天
		  * 
		  * **/
		 public static long getAfternDay(Date smdate,int n) throws ParseException{    
			 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 	String afterDay = sdf.format(smdate);
				Date date = sdf.parse(afterDay);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, +n);
				date = calendar.getTime();
//				System.out.println(sdf.format(date));
		       return date.getTime();  
		    }
		
	//list排序
	public List<SportInfo> listSort(List<SportInfo> newList){
		 Collections.sort(newList, new Comparator<Object>() { 
	            public int compare(Object a, Object b) {
	                int one = ((SportInfo) a).getSportType();
	                int two = ((SportInfo) b).getSportType();
	                return one - two;
	            }
	        });
		return newList;
	}
	
	public SportInfo setSportInfo(int sportType){
		SportInfo sif = new SportInfo();
		sif.setSportType(sportType);
		sif.setTotalDistance(0);
		sif.setTotalEnergy(0);
		sif.setTotalStep(0);
		sif.setTotalUseTime(0);
		return sif;
	}
	
}
