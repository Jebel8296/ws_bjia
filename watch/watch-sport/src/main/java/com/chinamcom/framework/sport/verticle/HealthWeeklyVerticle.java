package com.chinamcom.framework.sport.verticle;
import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.DateUtil;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.sport.ApplicationSport;
import com.chinamcom.framework.sport.model.SportHealthWeekly;
import com.chinamcom.framework.sport.model.SportSummary;
import com.chinamcom.framework.sport.service.SportHealthWeeklyService;
import com.chinamcom.framework.sport.service.SportSummaryService;

@Component
public class HealthWeeklyVerticle extends BaseVerticle{

	private static Logger logger = Logger.getLogger(HealthWeeklyVerticle.class);
	
	@Autowired
	private SportSummaryService sportSummaryService;
	@Autowired
	private SportHealthWeeklyService healthWeeklyService;
	
	@Override
	public void start() throws Exception {
		
		//健康周报
		vertx.eventBus().consumer("sport.sportinfo.healthweekly", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			try {
			logger.info("查询健康周报请求：" + message.body());
			Properties config = ApplicationSport.config;
			RedisOptions redisConfig = new RedisOptions();  
			redisConfig.setHost(config.getProperty("redis.host"));  
			redisConfig.setPort(Integer.parseInt(config.getProperty("redis.port")));
			redisConfig.setSelect(Integer.parseInt(config.getProperty("redis.db","3")));
			redisConfig.setTcpKeepAlive(true);
			RedisClient client = RedisClient.create(Vertx.vertx(), redisConfig);
			
			Integer uid = request.getInteger("uid");
			String imei = request.getString("imei");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
			String weekStar = sdf.format(DateUtil.getLastSunday(-1));//上周日0点
			String weekEnd = sdf.format(DateUtil.getLastSaturday(-1));//上周六0点
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("weekStar", weekStar);
			map.put("weekEnd", weekEnd);  
			map.put("imei", imei);
			map.put("uid", uid);
			String redisKey =imei+"_healthweekly"+"_"+uid+"_"+weekStar;
			client.get(redisKey, getRes ->{
			Object ret = null;
			if(getRes.succeeded()){
				ret = getRes.result();
				if(ret==null){
					try{
					SportSummary summary = sportSummaryService.weeklyTotalStep(map);//本周运动总步数、平均步数、总热量
					String lastWeekStar = sdf.format(DateUtil.getLastSunday(-2));//上周日0点
					String lastWeekEnd = sdf.format(DateUtil.getLastSaturday(-2));//上周六0点
					map.put("lastWeekStar", lastWeekStar);
					map.put("lastWeekEnd", lastWeekEnd);  
					SportSummary lastWeekSummary = sportSummaryService.lastWeekTotalStep(map);//上周运动总步数、平均步数、总热量
					if(summary!=null && lastWeekSummary!=null){
 						if(summary.getWeeklySumStep()<=0 && lastWeekSummary.getLastWeekSumStep()<=0){
							SportSummary sport = new SportSummary();
							sport.setWeekSatr(DateUtil.getLastSunday(-1));
							sport.setWeekEnd(DateUtil.getLastSaturday(-1));
							message.reply(respWriter.toSuccess(getSerialNumber(request),sport));
							return ;
						}
					}
					if(summary!=null ){
						Long[] weekArray = new Long[7];
						for(int i=0;i<weekArray.length;i++){
							weekArray[i] = DateUtil.getNextNDayMorning(DateUtil.getLastSaturday(-1), 6-i).getTime();
						}
						SportSummary delaySport = new SportSummary();
						if(lastWeekSummary!=null){
							delaySport.setLastWeekSumStep(lastWeekSummary.getLastWeekSumStep());
							delaySport.setLastWeekAvgStep(lastWeekSummary.getLastWeekAvgStep());
							delaySport.setLastWeekSumCalorie(lastWeekSummary.getLastWeekSumCalorie());
						}
						delaySport.setWeekSatr(DateUtil.getLastSunday(-1));
						delaySport.setWeekEnd(DateUtil.getLastSaturday(-1));
						List<SportSummary> stepList = sportSummaryService.healthWeeklyList(map);//健康周报运动步数
						if(stepList !=null && stepList.size()<7){
							SportSummary shortWeek = new SportSummary();
							shortWeek.setWeekSatr(DateUtil.getLastSunday(-1));
							shortWeek.setWeekEnd(DateUtil.getLastSaturday(-1));
							message.reply(respWriter.toSuccess(getSerialNumber(request),shortWeek));
							return ;
						}
						//查询最大运动步数记录
						List<SportSummary> maxStep = sportSummaryService.maxStepList(map);//健康周报运动步数
						//查询最小运动步数记录
						List<SportSummary> minStep = sportSummaryService.minStepList(map);
						delaySport.setSysTime(System.currentTimeMillis());
						if(stepList!=null && stepList.size()>0){
							for(int i=0;i<stepList.size();i++){
								if(maxStep.size()==1){
									if(stepList.get(i).getCreateTime().equals(maxStep.get(0).getCreateTime())){
										stepList.get(i).setExtremum(1);
									}
								}
								if(minStep.size()==1){
									if(stepList.get(i).getCreateTime().equals(minStep.get(0).getCreateTime())){
										stepList.get(i).setExtremum(0);
									}
								}
								long createTime = stepList.get(i).getCreateTime().getTime();
								int index = indexDeal(createTime,weekArray,6);
								stepList.get(i).setIndex(index);
							}
							delaySport.setStepList(stepList);
						}
						delaySport.setWeeklySumStep(summary.getWeeklySumStep());
						delaySport.setWeeklyAvgStep(summary.getWeeklyAvgStep());
						delaySport.setWeeklySumCalorie(summary.getWeeklySumCalorie());
						/*String lastWeekStar = sdf.format(DateUtil.getLastSunday(-2));//上周日0点
						String lastWeekEnd = sdf.format(DateUtil.getLastSaturday(-2));//上周六0点
						map.put("lastWeekStar", lastWeekStar);
						map.put("lastWeekEnd", lastWeekEnd);  
						SportSummary lastWeekSummary = sportSummaryService.lastWeekTotalStep(map);//上周运动总步数、平均步数、总热量
						if(lastWeekSummary!=null){
							delaySport.setLastWeekSumStep(lastWeekSummary.getLastWeekSumStep());
							delaySport.setLastWeekAvgStep(lastWeekSummary.getLastWeekAvgStep());
							delaySport.setLastWeekSumCalorie(lastWeekSummary.getLastWeekSumCalorie());
						}*/
						Integer goalDays = sportSummaryService.goalDaysCount(map);//达标天数查询
						delaySport.setGoalDays(goalDays);
						String lastWorkDayStar = sdf.format(DateUtil.getLastMonday(-1));//上周一0点
						String lastWorkDayEnd = sdf.format(DateUtil.getLastFriday(-1));//上周五24点
						map.put("lastWorkDayStar", lastWorkDayStar);
						map.put("lastWorkDayEnd", lastWorkDayEnd);  
						Integer workDayStep = sportSummaryService.workDayStepCount(map);//工作日运动步数
//						DecimalFormat df = new DecimalFormat("0.00");
//						String workDayPercentage = df.format((float)workDayStep/summary.getWeeklySumStep()*100);
						String workDayPercentage = Math.round((float)workDayStep/summary.getWeeklySumStep()*100)+"";
						delaySport.setWorkDayPercentage(workDayPercentage);
						if(summary.getWeeklySumStep()!=0){
							delaySport.setWeekendDayPercentage((100-Integer.valueOf(workDayPercentage))+"");
						}else{
							delaySport.setWeekendDayPercentage(0+"");
						}						
						List<SportHealthWeekly> rankList = healthWeeklyService.queryRank(map);
						List<SportHealthWeekly> newRankList = new ArrayList<SportHealthWeekly>();
						if(rankList.size()<=1){//无好友
							delaySport.setRank(1);
							delaySport.setHeadImg(rankList.get(0).getHeadImg());
							delaySport.setNickName(rankList.get(0).getNickName());
						}else{
							for(int i=0;i<rankList.size();i++){
								if(rankList.get(i).getHeadImg()!=null && StringUtil.isNotEmpty(rankList.get(i).getHeadImg()) && !rankList.get(i).getHeadImg().startsWith("http://")){
									rankList.get(i).setHeadImg(config.getProperty("headImageUrl")+rankList.get(i).getHeadImg());
									delaySport.setSex(rankList.get(0).getSex());
								}
								rankList.get(i).setShow(1);//设置默认好友排名全部显示
								if(uid.equals(rankList.get(i).getUid())){
									delaySport.setRank(i+1);
									delaySport.setBeatNum(rankList.size()-1-i);
									SportHealthWeekly healthWeekly0 = new SportHealthWeekly();
									healthWeekly0.setShow(0);
									SportHealthWeekly healthWeekly1 = new SportHealthWeekly();
									healthWeekly1.setShow(0);
									SportHealthWeekly healthWeekly3 =  new SportHealthWeekly();
									healthWeekly3.setShow(0);
									SportHealthWeekly healthWeekly4 =  new SportHealthWeekly();
									healthWeekly4.setShow(0);
									if(i == 0){//本人排第一名
										newRankList.add(0,healthWeekly0);
										newRankList.add(1,healthWeekly1);
										newRankList.add(2,rankList.get(i));
										if(rankList.size()>=3){
											for(int n =1;n<3;n++){
												newRankList.add(2+n,rankList.get(i+n));
											}
										}else if(rankList.size()>=2 && rankList.size()<3){
											for(int n =1;n<2;n++){
												newRankList.add(3,rankList.get(i+n));
											}
											newRankList.add(4,healthWeekly4);
										}else{
											newRankList.add(3,healthWeekly3);
											newRankList.add(4,healthWeekly4);
										}
										delaySport.setHeadImg(rankList.get(i).getHeadImg());
										delaySport.setNickName(rankList.get(i).getNickName());
									}
									if(i == 1){//本人排第二名
										newRankList.add(0,healthWeekly0);
										newRankList.add(1,rankList.get(i-1));
										newRankList.add(2,rankList.get(i));
										if(rankList.size()>=4){
											for(int n =1;n<3;n++){
												newRankList.add(2+n,rankList.get(i+n));
											}
										}else if(rankList.size()>=3 && rankList.size()<4 ){
											for(int n =1;n<2;n++){
												newRankList.add(3,rankList.get(i+n));
											}
											newRankList.add(4,healthWeekly4);
										}else{
											newRankList.add(3,healthWeekly3);
											newRankList.add(4,healthWeekly4);
										}
										delaySport.setHeadImg(rankList.get(i).getHeadImg());
										delaySport.setNickName(rankList.get(i).getNickName());
									}
									if(i>=2){//本人排名第三名或第三名以后
										newRankList.add(0,rankList.get(i-2));
										newRankList.add(1,rankList.get(i-1));
										newRankList.add(2,rankList.get(i));
										if(rankList.size()>=5){
											for(int n =1;n<3;n++){
												newRankList.add(2+n,rankList.get(i+n));
											}
										}else if(rankList.size()>=4 && rankList.size()<5){
											for(int n =1;n<2;n++){
												newRankList.add(3,rankList.get(i+n-1));
											}
											newRankList.add(4,healthWeekly4);
										}else{
											newRankList.add(3,healthWeekly3);
											newRankList.add(4,healthWeekly4);
										}
										delaySport.setHeadImg(rankList.get(i).getHeadImg());
										delaySport.setNickName(rankList.get(i).getNickName());
									}
								}
							}
							delaySport.setRankList(newRankList);//排名头像昵称
							Integer sexRank = healthWeeklyService.querySexRank(map);
							delaySport.setSexRank(sexRank);
						}
						client.setex(redisKey,60*60*24,JSONObject.toJSONString(delaySport), setRes->{
							if(!setRes.succeeded()){  
						         setRes.cause().printStackTrace();  
						         } 
							});
						message.reply(respWriter.toSuccess(getSerialNumber(request), delaySport));
					}else{
						SportSummary sport = new SportSummary();
						sport.setWeekSatr(DateUtil.getLastSunday(-1));
						sport.setWeekEnd(DateUtil.getLastSaturday(-1));
						message.reply(respWriter.toSuccess(getSerialNumber(request),sport));
					}
					}catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage(), e);
						message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
					}
				}else{
					JSONObject result = JSONObject.parseObject(ret.toString());
					ZJSONObject json =new ZJSONObject();
					json.put("sportsummary", result);
					message.reply(respWriter.toSuccess(getSerialNumber(request), json));
				}
			}
			});
			} catch (Exception e) {
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


