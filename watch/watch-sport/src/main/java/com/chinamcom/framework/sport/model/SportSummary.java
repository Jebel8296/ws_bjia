package com.chinamcom.framework.sport.model;

import java.util.Date;
import java.util.List;

public class SportSummary {
	private Integer id;
	private String imei;
	private Integer sportType;
	private Integer timeDimension;
	private String valDiemension;
	private Integer step;//步数
	private Integer distance; //距离
	private Integer calorie; //卡路里
	private Date countTime;
	private Integer heartRate;
	private Integer hour;//一天24小时（0-23）
	private Integer week;//一周的第几天（0=星期一,1=星期二,……6= 星期天）
	private Integer month;//月份
	private String xValue;//横坐标
	private Integer yValue;//纵坐标
	private Integer stepSum;//总步数
	private List<SportSummary> stepList; //步数
	private Integer target;//目标
	private List<SportSummary> useTimeList;//消耗时间
	private Integer useTimeSum;//总消耗时间
	private Long sysTime;
	private List<SportSummary> distanceList; //距离
	private Integer distanceSum; //总距离
	private List<SportSummary> calorieList;//卡路里
	private Integer calorieSum; //总卡路里
	private Integer heartRateMax;//心率最大值
	private Integer heartRateMin;//心率最小值
	private List<SportSummary> heartRateList;//卡路里
	private Date createTime;
	private Long[] allXValue;
	private Integer index;
	private Integer heartRateSum;
	
	private Integer weeklySumStep;//本周总步数
	private Integer weeklyAvgStep;//本周平均步数
	private Integer lastWeekSumStep;//上周总步数
	private Integer lastWeekAvgStep;//上周平均步数
	private Integer goalDays;//达标天数
	private String workDayPercentage;//工作日运动步数%
	private String weekendDayPercentage;//周末日运动步数%
	private Integer weeklySumCalorie;//本周消耗总热量
	private Integer lastWeekSumCalorie;//上周消耗总热量
	private Integer rank;//排名
	private Integer sexRank;//性别排名
	private Integer sex;//性别
	private String headImg;//头像
	private String nickName;//昵称
	private Integer beatNum;//击败了多少名好友
	private List<SportHealthWeekly> rankList;
	private Date weekSatr;//周开始时间
	private Date weekEnd;//周结束时间
	private Integer extremum;//极值1 最大、0最小
	
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getWorkDayPercentage() {
		return workDayPercentage;
	}
	public void setWorkDayPercentage(String workDayPercentage) {
		this.workDayPercentage = workDayPercentage;
	}
	public String getWeekendDayPercentage() {
		return weekendDayPercentage;
	}
	public void setWeekendDayPercentage(String weekendDayPercentage) {
		this.weekendDayPercentage = weekendDayPercentage;
	}
	public Integer getGoalDays() {
		return goalDays;
	}
	public void setGoalDays(Integer goalDays) {
		this.goalDays = goalDays;
	}
	public Integer getWeeklySumCalorie() {
		return weeklySumCalorie;
	}
	public void setWeeklySumCalorie(Integer weeklySumCalorie) {
		this.weeklySumCalorie = weeklySumCalorie;
	}
	public Integer getLastWeekSumCalorie() {
		return lastWeekSumCalorie;
	}
	public void setLastWeekSumCalorie(Integer lastWeekSumCalorie) {
		this.lastWeekSumCalorie = lastWeekSumCalorie;
	}
	public Integer getWeeklySumStep() {
		return weeklySumStep;
	}
	public void setWeeklySumStep(Integer weeklySumStep) {
		this.weeklySumStep = weeklySumStep;
	}
	public Integer getWeeklyAvgStep() {
		return weeklyAvgStep;
	}
	public void setWeeklyAvgStep(Integer weeklyAvgStep) {
		this.weeklyAvgStep = weeklyAvgStep;
	}
	public Integer getLastWeekSumStep() {
		return lastWeekSumStep;
	}
	public void setLastWeekSumStep(Integer lastWeekSumStep) {
		this.lastWeekSumStep = lastWeekSumStep;
	}
	public Integer getLastWeekAvgStep() {
		return lastWeekAvgStep;
	}
	public void setLastWeekAvgStep(Integer lastWeekAvgStep) {
		this.lastWeekAvgStep = lastWeekAvgStep;
	}
	public Integer getyValue() {
		return yValue;
	}
	public void setyValue(Integer yValue) {
		this.yValue = yValue;
	}
	public Integer getHeartRateSum() {
		return heartRateSum;
	}
	public void setHeartRateSum(Integer heartRateSum) {
		this.heartRateSum = heartRateSum;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Long[] getAllXValue() {
		return allXValue;
	}
	public void setAllXValue(Long[] allXValue) {
		this.allXValue = allXValue;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<SportSummary> getHeartRateList() {
		return heartRateList;
	}
	public void setHeartRateList(List<SportSummary> heartRateList) {
		this.heartRateList = heartRateList;
	}
	public Integer getHeartRateMax() {
		return heartRateMax;
	}
	public void setHeartRateMax(Integer heartRateMax) {
		this.heartRateMax = heartRateMax;
	}
	public Integer getHeartRateMin() {
		return heartRateMin;
	}
	public void setHeartRateMin(Integer heartRateMin) {
		this.heartRateMin = heartRateMin;
	}
	public String getxValue() {
		return xValue;
	}
	public void setxValue(String xValue) {
		this.xValue = xValue;
	}
	public List<SportSummary> getCalorieList() {
		return calorieList;
	}
	public void setCalorieList(List<SportSummary> calorieList) {
		this.calorieList = calorieList;
	}
	public Integer getCalorieSum() {
		return calorieSum;
	}
	public void setCalorieSum(Integer calorieSum) {
		this.calorieSum = calorieSum;
	}
	public List<SportSummary> getDistanceList() {
		return distanceList;
	}
	public void setDistanceList(List<SportSummary> distanceList) {
		this.distanceList = distanceList;
	}
	public Integer getDistanceSum() {
		return distanceSum;
	}
	public void setDistanceSum(Integer distanceSum) {
		this.distanceSum = distanceSum;
	}
	public Long getSysTime() {
		return sysTime;
	}
	public void setSysTime(Long sysTime) {
		this.sysTime = sysTime;
	}
	public Integer getUseTimeSum() {
		return useTimeSum;
	}
	public void setUseTimeSum(Integer useTimeSum) {
		this.useTimeSum = useTimeSum;
	}
	public List<SportSummary> getUseTimeList() {
		return useTimeList;
	}
	public void setUseTimeList(List<SportSummary> useTimeList) {
		this.useTimeList = useTimeList;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public List<SportSummary> getStepList() {
		return stepList;
	}
	public void setStepList(List<SportSummary> stepList) {
		this.stepList = stepList;
	}
	public Integer getStepSum() {
		return stepSum;
	}
	public void setStepSum(Integer stepSum) {
		this.stepSum = stepSum;
	}
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public Integer getSportType() {
		return sportType;
	}
	public void setSportType(Integer sportType) {
		this.sportType = sportType;
	}
	public Integer getTimeDimension() {
		return timeDimension;
	}
	public void setTimeDimension(Integer timeDimension) {
		this.timeDimension = timeDimension;
	}
	public String getValDiemension() {
		return valDiemension;
	}
	public void setValDiemension(String valDiemension) {
		this.valDiemension = valDiemension;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public Integer getCalorie() {
		return calorie;
	}
	public void setCalorie(Integer calorie) {
		this.calorie = calorie;
	}
	public Date getCountTime() {
		return countTime;
	}
	public void setCountTime(Date countTime) {
		this.countTime = countTime;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getSexRank() {
		return sexRank;
	}
	public void setSexRank(Integer sexRank) {
		this.sexRank = sexRank;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getBeatNum() {
		return beatNum;
	}
	public void setBeatNum(Integer beatNum) {
		this.beatNum = beatNum;
	}
	public List<SportHealthWeekly> getRankList() {
		return rankList;
	}
	public void setRankList(List<SportHealthWeekly> rankList) {
		this.rankList = rankList;
	}
	public Date getWeekSatr() {
		return weekSatr;
	}
	public void setWeekSatr(Date weekSatr) {
		this.weekSatr = weekSatr;
	}
	public Date getWeekEnd() {
		return weekEnd;
	}
	public void setWeekEnd(Date weekEnd) {
		this.weekEnd = weekEnd;
	}
	public Integer getExtremum() {
		return extremum;
	}
	public void setExtremum(Integer extremum) {
		this.extremum = extremum;
	}
	
}
