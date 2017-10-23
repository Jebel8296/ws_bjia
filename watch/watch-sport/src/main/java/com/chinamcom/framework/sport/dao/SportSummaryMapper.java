package com.chinamcom.framework.sport.dao;

import java.util.List;
import java.util.Map;

import com.chinamcom.framework.sport.model.SportSummary;

public interface SportSummaryMapper {
	
	 public List<SportSummary> delaySportInfo(Map<String ,Object> map);
	 
	 public boolean batchInsert(List<SportSummary> list);
	 
//	 public List<SportSummary> delaySportSummaryList(Map<String ,Object> map);
	 
//	 public List<SportSummary> queryweeksportinfo(Map<String ,Object> map);
	 
//	 public List<SportSummary> querymonthsportinfo(Map<String ,Object> map);
	 
	 public List<SportSummary> delayStepList(Map<String ,Object> map);
	 
	 public Integer countStep(Map<String, Object> map);
	 
	 public Integer queryTarget(Map<String, Object> map);
	 
	 public List<SportSummary> delayUseTimeList(Map<String, Object> map);
	 
	 public Integer countUseTime(Map<String ,Object> map);
	 
	 public List<SportSummary> delayDistanceList(Map<String ,Object> map);
		
	 public Integer countDistance(Map<String ,Object> map);
	 
	 public List<SportSummary> delayCalorieList(Map<String ,Object> map);
		
	 public Integer countCalorie(Map<String ,Object> map);
	 
	 public List<SportSummary> delayHeartRateList(Map<String, Object> map);
	 
	 public Integer lastHeartRate(Map<String, Object> map);
	 
//	 public List<SportSummary> weekStepList(Map<String ,Object> map);
	 public Integer countWeekStep(Map<String ,Object> map);
	 
//	 public List<SportSummary> weekUseTimeList(Map<String ,Object> map);
	 public Integer countWeekUseTime(Map<String ,Object> map);
	 
//	 public List<SportSummary> weekDistanceList(Map<String ,Object> map);
	 public Integer countWeekDistance(Map<String ,Object> map);
		
//	 public List<SportSummary> weekCalorieList(Map<String ,Object> map);
	 public Integer countWeekCalorie(Map<String ,Object> map);
		
	 public List<SportSummary> weekHeartRateList(Map<String ,Object> map);
	 
//	 public List<SportSummary> monthHistorySportInfoList(Map<String ,Object> map);
	 
//	 public Integer countMonthStep(Map<String ,Object> map);
//	 public Integer countMonthUseTime(Map<String ,Object> map);
//	 public Integer countMonthDistance(Map<String ,Object> map);
//	 public Integer countMonthCalorie(Map<String ,Object> map);
	 
//	 public List<SportSummary> monthHeartRateList(Map<String ,Object> map);
	 
	 public List<SportSummary> weekHistoryStepList(Map<String ,Object> map);
	 public SportSummary dalyHistorySportInfo(Map<String ,Object> map);
	 
	 public List<SportSummary> weekHistoryUseTimeList(Map<String ,Object> map);
	 public SportSummary dalyHistoryUseTimeInfo(Map<String ,Object> map);
	 
	 public List<SportSummary> weekHistoryDistanceList(Map<String ,Object> map);
	 public SportSummary dalyHistorydistanceInfo(Map<String ,Object> map);
	 
	 public List<SportSummary> weekHistoryCalorieList(Map<String ,Object> map);
	 public SportSummary dalyHistoryCalorieInfo(Map<String ,Object> map);
	 
	 public List<SportSummary> healthWeeklyList(Map<String ,Object> map);
	 public List<SportSummary> maxStepList(Map<String ,Object> map);
	 public List<SportSummary> minStepList(Map<String ,Object> map);
	 public SportSummary weeklyTotalStep(Map<String ,Object> map);
	 public SportSummary lastWeekTotalStep(Map<String ,Object> map);
	 public Integer goalDaysCount(Map<String ,Object> map);
	 public Integer workDayStepCount(Map<String ,Object> map);
}
