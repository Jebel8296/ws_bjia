package com.chinamcom.framework.sport.task.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.utils.DateUtil;
import com.chinamcom.framework.sport.model.SportHealthWeekly;
import com.chinamcom.framework.sport.model.SportSummary;
import com.chinamcom.framework.sport.service.SportHealthWeeklyService;
import com.chinamcom.framework.sport.service.SportInfoTotalService;
import com.chinamcom.framework.sport.service.SportSummaryService;
import com.chinamcom.framework.sport.task.AppSportSummaryTask;

@Component
public class AppSportSummaryTaskImpl implements AppSportSummaryTask{
	static final Logger log = Logger.getLogger(AppSportSummaryTaskImpl.class);
	
	@Autowired
	private SportSummaryService sportSummaryService;
	@Autowired
	private SportHealthWeeklyService sportHealthWeeklyService;
	
	@Autowired
	private SportInfoTotalService sportInfoTotalService;
	
	//	@Scheduled(fixedDelay = 6 * 1000) //5分钟执行一次，用于测试
	@Scheduled(cron = "0 30 23 * * ?")//每天晚上23.30点执行一次
	public void appSportSummary() throws Exception {
		//按运动模式统计当天上传的运动数据
		Date date = new Date();
//		System.out.println("日期："+date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStar = sdf.format(date)+" 00:00:00";
		String dateEnd = sdf.format(date)+" 23:59:59";
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("dateStar", dateStar);
		map.put("dateEnd", dateEnd);
		//sport_info表sum
//		List<SportSummary> delayList = sportSummaryService.delaySportInfo(map);
		//sport_info_total表Max
		List<SportSummary> delayList = sportInfoTotalService.delaySportInfoTotal(map);
		if(delayList !=null && delayList.size()>0){
			sportSummaryService.batchInsert(delayList);
		}
	}
	
	@Scheduled(cron = "0 50 23 ? * 6")//每周六晚上23.50点执行一次
	public void SportHealthWeekly() throws Exception {
		//统计当前周的运动数据（周日到周六走路和跑步）并上传
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		String weekstarTime = sdf.format(DateUtil.getLastSunday(0));
		String weekendTime = sdf.format(DateUtil.getLastSaturday(0));
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("weekstarTime", weekstarTime);
		map.put("weekendTime", weekendTime);
		List<SportHealthWeekly> list = sportHealthWeeklyService.weeklyHealthSportSummary(map);
		if(list !=null && list.size()>0){
			sportHealthWeeklyService.batchInsert(list);
		}
	}
	
	public static void mian(String[] args){
		
	}

}
