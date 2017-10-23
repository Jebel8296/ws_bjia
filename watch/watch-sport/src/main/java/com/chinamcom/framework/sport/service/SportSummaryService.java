package com.chinamcom.framework.sport.service;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.sport.model.SportSummary;

public interface SportSummaryService {
	
	//按天统计运动数据（增量表）
	public List<SportSummary> delaySportInfo(Map<String ,Object> map);
	
	public boolean batchInsert(List<SportSummary> list);
	
	//按运动模式查询当天运动步数
	public List<SportSummary> delayStepList(Map<String ,Object> map);
	
	//按运动模式查询当天运动总步数
	public Integer countStep(Map<String ,Object> map);
	//查询当天目标（多次设置取最后设置的）
	public Integer queryTarget(Map<String ,Object> map);
	
	//按运动模式查询当天运动时间
	public List<SportSummary> delayUseTimeList(Map<String ,Object> map);
	public Integer countUseTime(Map<String ,Object> map);
	
	//按运动模式查询当天运动距离
	public List<SportSummary> delayDistanceList(Map<String ,Object> map);
	public Integer countDistance(Map<String ,Object> map);
	
	//按运动模式查询当天运动消耗卡路里
	public List<SportSummary> delayCalorieList(Map<String ,Object> map);
	public Integer countCalorie(Map<String ,Object> map);
	
	//按运动模式查询当天心率（每小时的最大值与最小值）
	public List<SportSummary> delayHeartRateList(Map<String ,Object> map);
	
	//查最新上传的一次心率值
	public Integer lastHeartRate(Map<String ,Object> map);
	
	//按运动模式查询周步数
//	public List<SportSummary> weekStepList(Map<String ,Object> map);
	public Integer countWeekStep(Map<String ,Object> map);
	
	//按运动模式查询周耗时
//	public List<SportSummary> weekUseTimeList(Map<String ,Object> map);
	public Integer countWeekUseTime(Map<String ,Object> map);
	
	//按运动模式查询周距离
//	public List<SportSummary> weekDistanceList(Map<String ,Object> map);
	public Integer countWeekDistance(Map<String ,Object> map);
	
	//按运动模式查询周消耗热量
//	public List<SportSummary> weekCalorieList(Map<String ,Object> map);
	public Integer countWeekCalorie(Map<String ,Object> map);
	
	//按运动模式查询周心率
	public List<SportSummary> weekHeartRateList(Map<String ,Object> map);
	
	//按运动模式查月运动信息
//	public List<SportSummary> monthHistorySportInfoList(Map<String ,Object> map);
	
//	public Integer countMonthStep(Map<String ,Object> map);
//	public Integer countMonthUseTime(Map<String ,Object> map);
//	public Integer countMonthDistance(Map<String ,Object> map);
//	public Integer countMonthCalorie(Map<String ,Object> map);
	
//	public List<SportSummary> monthHeartRateList(Map<String ,Object> map);
	
	//查询周的运动历史（定时任务）
	public List<SportSummary> weekHistoryStepList(Map<String ,Object> map);
	//定时任务只能统计到前一天的，所以要单独处理今天的
	public SportSummary dalyHistorySportInfo(Map<String ,Object> map);
	
	//查询周的运动历史消耗时间（定时任务）
	public List<SportSummary> weekHistoryUseTimeList(Map<String ,Object> map);
	//定时任务只能统计到前一天的，所以要单独处理今天的
	public SportSummary dalyHistoryUseTimeInfo(Map<String ,Object> map);
	
	//查询周的运动历史（距离）（定时任务）
	public List<SportSummary> weekHistoryDistanceList(Map<String ,Object> map);
	//定时任务只能统计到前一天的，所以要单独处理今天的
	public SportSummary dalyHistorydistanceInfo(Map<String ,Object> map);
	
	//查询周的运动历史（卡路里）（定时任务）
	public List<SportSummary> weekHistoryCalorieList(Map<String ,Object> map);
	public SportSummary dalyHistoryCalorieInfo(Map<String ,Object> map);
	
	//健康周报周运动数据
	public List<SportSummary> healthWeeklyList(Map<String ,Object> map);//
	public List<SportSummary> maxStepList(Map<String ,Object> map);
	public List<SportSummary> minStepList(Map<String ,Object> map);
	public SportSummary weeklyTotalStep(Map<String ,Object> map);//本周总运动步数、日平均步数、总消耗热量
	public SportSummary lastWeekTotalStep(Map<String ,Object> map);//上周总运动步数、日平均步数、总消耗热量
	public Integer goalDaysCount(Map<String ,Object> map);//达标天数
	public Integer workDayStepCount(Map<String ,Object> map);//工作日运动步数
	
	
}
