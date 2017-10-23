package com.chinamcom.framework.sport.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.sport.dao.SportSummaryMapper;
import com.chinamcom.framework.sport.model.SportSummary;
import com.chinamcom.framework.sport.service.SportSummaryService;

@Service
public class SportSummaryServiceImpl implements SportSummaryService{
	@Autowired
	private SportSummaryMapper sportSummaryMapper;
	
	@Override
	public List<SportSummary> delaySportInfo(Map<String, Object> map) {
		
		List<SportSummary> list = sportSummaryMapper.delaySportInfo(map);
		
		return list;
	}

	@Override
	public boolean batchInsert(List<SportSummary> list) {
		boolean flag = sportSummaryMapper.batchInsert(list);
		return flag;
	}

	@Override
	public List<SportSummary> delayStepList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> list = sportSummaryMapper.delayStepList(map);
		return list;
	}

	@Override
	public Integer countStep(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer countStep = sportSummaryMapper.countStep(map);
		return countStep==null?0:countStep;
	}

	@Override
	public Integer queryTarget(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer target = sportSummaryMapper.queryTarget(map);
		return target;
	}

	@Override
	public List<SportSummary> delayUseTimeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> list = sportSummaryMapper.delayUseTimeList(map);
		return list;
	}

	@Override
	public Integer countUseTime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer useTimeSum = sportSummaryMapper.countUseTime(map);
		return useTimeSum==null?0:useTimeSum;
	}

	@Override
	public List<SportSummary> delayDistanceList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> list = sportSummaryMapper.delayDistanceList(map);
		return list;
	}

	@Override
	public Integer countDistance(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer distanceSum = sportSummaryMapper.countDistance(map);
		return distanceSum ==null?0:distanceSum;
	}

	@Override
	public List<SportSummary> delayCalorieList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> list = sportSummaryMapper.delayCalorieList(map);
		return list;
	}

	@Override
	public Integer countCalorie(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer calorieSum = sportSummaryMapper.countCalorie(map);
		return calorieSum==null?0:calorieSum;
	}

	@Override
	public List<SportSummary> delayHeartRateList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> list = sportSummaryMapper.delayHeartRateList(map);
		return list;
	}

//	@Override
//	public List<SportSummary> weekStepList(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		List<SportSummary> list = sportSummaryMapper.weekStepList(map);
//		return list;
//	}

	@Override
	public Integer countWeekStep(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer weekStepSum = sportSummaryMapper.countWeekStep(map);
		return weekStepSum==null?0:weekStepSum;
	}

//	@Override
//	public List<SportSummary> weekUseTimeList(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		List<SportSummary> list = sportSummaryMapper.weekUseTimeList(map);
//		return list;
//	}

	@Override
	public Integer countWeekUseTime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer weekUseTimeSum = sportSummaryMapper.countWeekUseTime(map);
		return weekUseTimeSum==null?0:weekUseTimeSum;
	}

//	@Override
//	public List<SportSummary> weekDistanceList(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		List<SportSummary> list = sportSummaryMapper.weekDistanceList(map);
//		return list;
//	}

	@Override
	public Integer countWeekDistance(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer weekDistanceSum = sportSummaryMapper.countWeekDistance(map);
		return weekDistanceSum==null?0:weekDistanceSum;
	}

//	@Override
//	public List<SportSummary> weekCalorieList(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		List<SportSummary> list = sportSummaryMapper.weekCalorieList(map);
//		return list;
//	}

	@Override
	public Integer countWeekCalorie(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer weekCalorieSum = sportSummaryMapper.countWeekCalorie(map);
		return weekCalorieSum==null?0:weekCalorieSum;
	}

	@Override
	public List<SportSummary> weekHeartRateList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> list = sportSummaryMapper.weekHeartRateList(map);
		return list;
	}

//	@Override
//	public List<SportSummary> monthHistorySportInfoList(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		List<SportSummary> list = sportSummaryMapper.monthHistorySportInfoList(map);
//		return list;
//	}

//	@Override
//	public Integer countMonthStep(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		Integer monthStepSum = sportSummaryMapper.countMonthStep(map);
//		return monthStepSum==null?0:monthStepSum;
//	}

//	@Override
//	public Integer countMonthUseTime(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		Integer monthUseTimeSum = sportSummaryMapper.countMonthUseTime(map);
//		return monthUseTimeSum==null?0:monthUseTimeSum;
//	}

//	@Override
//	public Integer countMonthDistance(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		Integer monthDistanceSum = sportSummaryMapper.countMonthDistance(map);
//		return monthDistanceSum==null?0:monthDistanceSum;
//	}

//	@Override
//	public Integer countMonthCalorie(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		Integer monthCalorieSum = sportSummaryMapper.countMonthCalorie(map);
//		return monthCalorieSum==null?0:monthCalorieSum;
//	}

//	@Override
//	public List<SportSummary> monthHeartRateList(Map<String, Object> map) {
//		// TODO Auto-generated method stub
//		List<SportSummary> list = sportSummaryMapper.monthHeartRateList(map);
//		return list;
//	}

	@Override
	public List<SportSummary> weekHistoryStepList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> list = sportSummaryMapper.weekHistoryStepList(map);
		return list;
	}

	@Override
	public SportSummary dalyHistorySportInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		SportSummary sportInfo = sportSummaryMapper.dalyHistorySportInfo(map);
		Integer sportType = (Integer)map.get("sportType");
		if(sportType==1){
			Integer target = sportSummaryMapper.queryTarget(map);
			if(target!=null){
				if(sportInfo==null){
					sportInfo = new SportSummary();
				}
				sportInfo.setTarget(target);
				Date date = new Date();
				sportInfo.setCreateTime(date);
			}
		}
		return sportInfo;
	}

	@Override
	public List<SportSummary> weekHistoryUseTimeList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> list = sportSummaryMapper.weekHistoryUseTimeList(map);
		return list;
	}

	@Override
	public SportSummary dalyHistoryUseTimeInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		SportSummary sportInfo = sportSummaryMapper.dalyHistoryUseTimeInfo(map);
		return sportInfo;
	}

	@Override
	public List<SportSummary> weekHistoryDistanceList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> list = sportSummaryMapper.weekHistoryDistanceList(map);
		return list;
	}

	@Override
	public SportSummary dalyHistorydistanceInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		SportSummary sportInfo = sportSummaryMapper.dalyHistorydistanceInfo(map);
		return sportInfo;
	}

	@Override
	public List<SportSummary> weekHistoryCalorieList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> list = sportSummaryMapper.weekHistoryCalorieList(map);
		return list;
	}

	@Override
	public SportSummary dalyHistoryCalorieInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		SportSummary sportInfo = sportSummaryMapper.dalyHistoryCalorieInfo(map);
		return sportInfo;
	}

	@Override
	public Integer lastHeartRate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer lastHeartRate = sportSummaryMapper.lastHeartRate(map);
		return lastHeartRate;
	}

	@Override
	public List<SportSummary> healthWeeklyList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> list = sportSummaryMapper.healthWeeklyList(map);
		return list;
	}

	@Override
	public SportSummary weeklyTotalStep(Map<String, Object> map) {
		// TODO Auto-generated method stub
		SportSummary summary = sportSummaryMapper.weeklyTotalStep(map);
		return summary;
	}

	@Override
	public SportSummary lastWeekTotalStep(Map<String, Object> map) {
		// TODO Auto-generated method stub
		SportSummary summary = sportSummaryMapper.lastWeekTotalStep(map);
		return summary;
	}

	@Override
	public Integer goalDaysCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer goalDays = sportSummaryMapper.goalDaysCount(map);
		return goalDays;
	}

	@Override
	public Integer workDayStepCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Integer workDayStep = sportSummaryMapper.workDayStepCount(map);
		return workDayStep;
	}

	@Override
	public List<SportSummary> maxStepList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> maxStepList = sportSummaryMapper.maxStepList(map);
		return maxStepList;
	}

	@Override
	public List<SportSummary> minStepList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<SportSummary> minStepList = sportSummaryMapper.minStepList(map);
		return minStepList;
	}
}
