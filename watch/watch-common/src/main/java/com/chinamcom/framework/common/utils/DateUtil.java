package com.chinamcom.framework.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * SimpleDateFormat 格式化时间本身不是线程安全的
 */
public class DateUtil {

	static final Logger logger = Logger.getLogger(DateUtil.class);

	/**
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateTime(String dateStr) throws ParseException {
		return getDateTime(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date getDateTimeUnsigned(String dateStr)
			throws ParseException {
		return getDateTime(dateStr, "yyyyMMddHHmmss");
	}

	/**
	 * 字符串时间按格式转成 Date时间对象
	 * 
	 * @param dateStr
	 *            字符串时间
	 * @param pattern
	 *            字符串时间格式
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateTime(String dateStr, String pattern)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.parse(dateStr);
	}

	/**
	 * 时间对象按照格式转成相应的字符串表示形式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	/**
	 * yyyyMMddHHmmss
	 */
	public static String getDateUnsigned(Date date) {
		return getDateString(date, "yyyyMMddHHmmss");
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String getAppDateString(Date date) {
		return getDateString(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * yyyy-MM-dd
	 */
	public static String getAppDate(Date date) {
		return getDateString(date, "yyyy-MM-dd");
	}

	/**
	 * yyyy/MM/dd
	 */
	public static String getDateXie(Date date) {
		return getDateString(date, "yyyy/MM/dd");
	}

	public static String getMonthString(Date date) {
		return getDateString(date, "yyyyMM");
	}

	public static String getCNDateXie(Date date) {
		return getDateString(date, "yyyy年MM月dd日");
	}

	/**
	 * yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		return getDateString(date, "yyyyMMdd");
	}

	public static String getMonthString(String dateStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
		Date date = format2.parse(dateStr);
		return format.format(date);
	}

	public static Date getMonthDate(String monthDateStr) throws ParseException {
		return getDateTime(monthDateStr, "yyyyMM");
	}

	public static String getYearMonth(String monthDateStr)
			throws ParseException {
		Date date = getDateTime(monthDateStr);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return format.format(date);
	}

	/**
	 * yyyy年MM月
	 */
	public static String getMonthStringCN(Date date) {
		return getDateString(date, "yyyy年MM月");
	}

	/**
	 * yyyyMM
	 */
	public static String getNowMonth() {
		return getMonthString(new Date());
	}

	/**
	 * yyyy年MM月
	 */
	public static String getNowMonthCN() {
		return getMonthStringCN(new Date());
	}

	/**
	 * yyyy年MM月dd日
	 */
	public static String getNowMonthCN(String date) {
		Date date2 = String2UtilDate(date);
		SimpleDateFormat yxmxFormat = new SimpleDateFormat("yyyy年MM月dd日");
		return yxmxFormat.format(date2);
	}

	/**
	 * yyyy/MM/dd
	 */
	public static String getNowMonthXie(String date) {
		Date date2 = String2UtilDate(date);
		SimpleDateFormat yxmxFormat = new SimpleDateFormat("yyyy/MM/dd");
		return yxmxFormat.format(date2);
	}

	/**
	 * @param monthStr
	 *            如:201404
	 * @return 2014年4月
	 * @throws ParseException
	 */
	public static String getMonthCN(String monthStr) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy年M月");
		Date date = formatter.parse(monthStr);
		String monthCN = formatter2.format(date);
		return monthCN;
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param String
	 *            yearMonth like:201402
	 * @return 最后一天
	 */
	public static int getLastDay(String yearMonth) {
		int year = Integer.parseInt(yearMonth.substring(0, 4));
		int month = Integer.parseInt(yearMonth.substring(4, 6));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);// Java月份才0开始算
		return cal.getActualMaximum(Calendar.DATE);
	}

	public static String firstDayOfMonth(String monthDateStr,
			String formatPattern) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter2 = new SimpleDateFormat(formatPattern);
		Date date = getMonthDate(monthDateStr);
		// 得到月第一天
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		String dateStr = formatter2.format(calendar.getTime());

		return dateStr;
	}

	public static String lastDayOfMonth(String monthDateStr,
			String formatPattern) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter2 = new SimpleDateFormat(formatPattern);

		Date date = getMonthDate(monthDateStr);
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String dateStr = formatter2.format(calendar.getTime());

		return dateStr;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 * @return boolean
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 得到当前月份的第一天
	 * 
	 * @return yyyyMMdd
	 * @author Cai
	 */
	public static String getDateBegin() {
		Calendar cal = Calendar.getInstance();
		String year = cal.get(Calendar.YEAR) + "";
		String month = (cal.get(Calendar.MONTH) + 1) + "";
		if (month.length() == 1) {
			month = "0" + month;
		}

		return year + month + "01";
	}

	/**
	 * 得到当前月份的最后一天
	 * 
	 * @return
	 * @author Cai
	 */
	public static String getDateEnd() {
		Calendar cal = Calendar.getInstance();

		String year = cal.get(Calendar.YEAR) + "";
		String month = (cal.get(Calendar.MONTH) + 1) + "";
		if (month.length() == 1)
			month = "0" + month;
		String day = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + "";
		return year + month + day;
	}

	/**
	 * 字符串转日期
	 */
	public static Date String2UtilDate(String str) {
		Date parse = null;
		try {
			SimpleDateFormat sd4app = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			parse = sd4app.parse(str);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return parse;
	}

	/**
	 * 得到下月生效时间，即下月的月初，返回2014-04-01 00:00:00
	 */
	public static String getNextEffTime() {
		SimpleDateFormat sd4app = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, +1);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date time = c.getTime();
		return sd4app.format(time);
	}

	/* 下月月底 */
	public static Date getNextMonthEndTime() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, +1);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 得到下月月初时间
	 */
	public static Date getNextMonthStart() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, +1);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date time = c.getTime();
		return time;
	}

	/**
	 * @param yyyyMM
	 * @return yyyyMMdd 返回对应月份的最后一天的日期；
	 */
	public static String getNextMonth(String yyyyMM) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		try {
			Date date = format.parse(yyyyMM);
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			// 发生异常，则返回当前日期
		}
		return format.format(cal.getTime());
	}

	/**
	 * @param yyyyMM
	 * @return yyyyMMdd 返回对应月份的最后一天的日期；
	 */
	public static String getMonthEndDate(String yyyyMM) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		try {
			Date date = format.parse(yyyyMM);
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			// 发生异常，则返回当前日期
		}
		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
		return format2.format(new Date(cal.getTimeInMillis() - 100));
	}

	/**
	 * 
	 * @param netTime
	 *            入网时间
	 * @return 查询月到入网时间中的时间
	 * @throws ParseException
	 */
	public static List<String> getAvaliableMonthStrs(String netTime)
			throws ParseException {
		List<String> list = new ArrayList<String>();

		Date now = new Date();
		now = DateUtils.truncate(now, Calendar.MONTH);

		Date netTimeDate = getDateTime(netTime);
		netTimeDate = DateUtils.truncate(netTimeDate, Calendar.MONTH);

		// 从当前月向前查询,最多查询前6个月
		for (int i = -5; i < 1; i++) { // 修改前是 i = -6
			Date date = DateUtils.addMonths(now, i);
			if (!date.before(netTimeDate)) {
				list.add(getMonthString(date));
			}
		}

		return list;
	}

	public static List<String> getSixMonthStrs() {
		List<String> list = new ArrayList<String>();

		Date now = new Date();
		now = DateUtils.truncate(now, Calendar.MONTH);

		// 从当前月向前查询,最多查询前6个月
		for (int i = 0; i >= -5; i--) {
			Date date = DateUtils.addMonths(now, i);
			list.add(getMonthString(date));
		}
		return list;
	}

	public static Map<String, String> getAvaliableMonths(String netTime)
			throws ParseException {
		Map<String, String> map = new HashMap<String, String>();
		List<String> list = getAvaliableMonthStrs(netTime);

		for (String monthStr : list) {
			map.put(monthStr, getMonthCN(monthStr));
		}
		return map;

	}

	public static Map<String, String> getMyBillAvaliableMonths(String netTime)
			throws ParseException {

		Map<String, String> map = new LinkedHashMap<String, String>();
		List<String> list = getAvaliableMonthStrs(netTime);

		String billShowMonthStr = null;
		Collections.reverse(list);
		for (String monthStr : list) {
			billShowMonthStr = firstDayOfMonth(monthStr, "yyyy/M/d") + "-"
					+ lastDayOfMonth(monthStr, "yyyy/M/d");
			map.put(monthStr, billShowMonthStr);
		}
		return map;

	}

	public static Map<String, String> getUserPointsHisAvaliableMonths(
			String netTime) throws ParseException {

		Map<String, String> map = new LinkedHashMap<String, String>();
		List<String> list = getAvaliableMonthStrs(netTime);

		String billShowMonthStr = null;
		Collections.reverse(list);
		for (String monthStr : list) {
			billShowMonthStr = DateUtil.getMonthCN(monthStr);
			map.put(monthStr, billShowMonthStr);
		}
		return map;

	}

	public static Map<String, String> getUserUsedAvaliableMonths(String netTime)
			throws ParseException {

		Map<String, String> map = new LinkedHashMap<String, String>();
		List<String> list = getAvaliableMonthStrs(netTime);

		Collections.reverse(list);
		String billShowMonthStr = null;
		for (String monthStr : list) {
			billShowMonthStr = getMonthCN(monthStr);
			map.put(monthStr, billShowMonthStr);
		}
		return map;

	}

	/**
	 * 
	 * @param time
	 *            格式如"20140415"
	 * @throws ParseException
	 */
	public static int compareTimeStr(String time, String time2)
			throws ParseException {

		Integer dateToInt = Integer.parseInt(time);
		Integer dateToInt2 = Integer.parseInt(time2);

		return dateToInt.compareTo(dateToInt2);
	}

	public static int compareMounthStr(String startDate, String endDate)
			throws ParseException {

		int result = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c2.setTime(sdf.parse(startDate));
		c1.setTime(sdf.parse(endDate));

		result = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);

		// System.out.println(result);

		return result == 0 ? 1 : Math.abs(result);

	}

	public static int compareDaysStr(String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(startDate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(endDate));
			long time2 = cal.getTimeInMillis();
			long between_days = (time1 - time2) / (1000 * 3600 * 24);
			return Math.abs(Integer.parseInt(String.valueOf(between_days)));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return -1;

		// System.out.println(Integer.parseInt(String.valueOf(between_days)));
	}

	public static int getNowYear() {
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		return currYear;
	}

	public static String getLastDayCalendar(String pattern) {
		if (null == pattern || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		String lastDay = format.format(ca.getTime());
		return lastDay;
	}

	public static String getLastDayCalendar22(String pattern) {
		if (null == pattern || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		ca.set(Calendar.HOUR_OF_DAY, 22);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		String lastDay = format.format(ca.getTime());
		return lastDay;
	}

	public static String getpreLastDayCalendar(String pattern) {
		if (null == pattern || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
		cale.set(Calendar.HOUR_OF_DAY, 23);
		cale.set(Calendar.MINUTE, 59);
		cale.set(Calendar.SECOND, 59);
		String lastDay = format.format(cale.getTime());
		return lastDay;
	}

	public static String getFirstDayCalendar(String pattern) {
		if (null == pattern || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		String first = format.format(c.getTime());
		return first;
	}

	public static String getLastFirstDayCalendar(String pattern) {
		if (null == pattern || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		String first = format.format(c.getTime());
		return first;
	}

	public static String getNextFirstDayCalendar(String pattern) {
		if (null == pattern || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		String first = format.format(c.getTime());
		return first;
	}

	public static Date getCurDayBegin() {
		return new Date();
	}

	public static Date getCurDayEnd() {
		return new Date();
	}

	// 获得当天0点时间
	public static Date getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获得当天24点时间
	public static Date getTimesnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获得本周一0点时间
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY),
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	// 获得本周日24点时间
	public static Date getTimesWeeknight() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		return cal.getTime();
	}

	// 获得本月第一天0点时间
	public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY),
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	// 获得本月最后一天24点时间
	public static Date getTimesMonthnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY),
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return cal.getTime();
	}

	/**
	 * 获得传入参数日期之前的n天的0点
	 * 
	 * **/
	public static Date getNextNDayMorning(Date smdate, int n)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nextDay = sdf.format(smdate);
		Date date = sdf.parse(nextDay);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.add(Calendar.DAY_OF_MONTH, -n);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 获得传入参数日期之前的n天的24点
	 * 
	 * **/
	public static Date getNextNDayNight(Date smdate, int n)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nextDay = sdf.format(smdate);
		Date date = sdf.parse(nextDay);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.add(Calendar.DAY_OF_MONTH, -n);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 获得传入参数前n周的周日0点 n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
	 * **/
	public static Date getLastSunday(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, n * 7);
		// 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得传入参数前n周的周六24点 n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
	 * **/
	public static Date getLastSaturday(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, n * 7);
		// 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得传入参数前n周的周一0点 n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
	 * **/
	public static Date getLastMonday(int n) {
		Calendar cal = Calendar.getInstance();
		// n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
		cal.add(Calendar.DATE, n * 7);
		// 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得传入参数前n周的周五24点 n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
	 * **/
	public static Date getLastFriday(int n) {
		Calendar cal = Calendar.getInstance();
		// n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
		cal.add(Calendar.DATE, n * 7);
		// 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
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
	
	public static void main(String[] args) throws Exception {
		System.out.println(new Date().getTime());
		System.out.println(new Date().getTime() / 1000 * 1000);
		System.out.println(DateUtil.getAppDateString(new Date(1474362000000l)));
	}
}
