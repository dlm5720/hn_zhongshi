package com.hnzs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 摘录至：http://blog.csdn.net/lzkkevin/article/details/6698213
 * Class Name:DateTimeUtil.java
 * Purpose:为了便于操作 时间计算和格式化
 */
public class DateTimeUtil
{
	public static String TIME_PATTERN = "HH:mm:ss";// 定义标准时间格式
	public static String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";//常用标准时间格式
	public static String STANDARD_FORMAT1 = "yyyyMMddHHmmss";//不带符号的完整时间戳
	public static String DATE_PATTERN_1 = "yyyy/MM/dd";// 定义标准日期格式1
	public static String DATE_PATTERN_2 = "yyyy-MM-dd";// 定义标准日期格式2
	public static String DATE_PATTERN_3 = "yyyy/MM/dd HH:mm:ss";// 定义标准日期格式3，带有时间
	public static String DATE_PATTERN_4 = "yyyy/MM/dd HH:mm:ss E";// 定义标准日期格式4，带有时间和星期
	public static String DATE_PATTERN_5 = "yyyy年MM月dd日 HH:mm:ss E";// 定义标准日期格式5，带有时间和星期
	public static String DATE_PATTERN_MY = "yy-MM-dd";// 定义标准日期格式 used in parse
	// Excel

	/**
	 * 定义时间类型常量
	 */
	private final static int SECOND = 1;
	private final static int MINUTE = 2;
	private final static int HOUR = 3;
	private final static int DAY = 4;
	private Date now;

	public Date getNow()
	{
		return now;
	}

	public void setNow(Date now)
	{
		this.now = now;
	}

	/**
	 * 构造方法，初始化now时间
	 */
	public DateTimeUtil()
	{
		now = new Date();
	}

	/**
	 * 把一个日期，按照某种格式 格式化输出
	 *
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            格式模型
	 * @return 返回字符串类型
	 */
	public static String formatDate(Date date, String pattern)
	{
		if (date == null)
		{
			return null;
		}
		else
		{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
	}

	/**
	 * 将字符串类型的时间转换为Date类型
	 *
	 * @param str
	 *            时间字符串
	 * @param pattern
	 *            格式
	 * @return 返回Date类型
	 */
	public static Date formatString(String str, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date time = null;

		// 需要捕获ParseException异常，如不要捕获，可以直接抛出异常，或者抛出到上层
		time = sdf.parse(str);
		return time;
	}

	/**
	 * 将一个表示时间段的数转换为毫秒数
	 *
	 * @param num
	 *            时间差数值,支持小数
	 * @param type
	 *            时间类型：1->秒,2->分钟,3->小时,4->天
	 * @return long类型时间差毫秒数，当为-1时表示参数有错
	 */
	public static long formatToTimeMillis(double num, int type)
	{
		if (num <= 0) return 0;
		switch (type)
		{
			case SECOND:
				return (long) (num * 1000);
			case MINUTE:
				return (long) (num * 60 * 1000);
			case HOUR:
				return (long) (num * 60 * 60 * 1000);
			case DAY:
				return (long) (num * 24 * 60 * 60 * 1000);
			default:
				return -1;
		}
	}

	/**
	 * 只输出一个时间中的月份
	 *
	 * @param date
	 * @return 返回int数值类型
	 */
	public static int getMonth(Date date)
	{
		String month = formatDate(date, "MM");// 只输出时间
		return Integer.parseInt(month);
	}

	/**
	 * 只输出一个时间中的年份
	 *
	 * @param date
	 * @return 返回数值类型
	 */
	public static int getYear(Date date)
	{
		String year = formatDate(date, "yyyy");// 只输出年份
		return Integer.parseInt(year);
	}

	/**
	 * 得到一个日期函数的格式化时间
	 *
	 * @param date
	 *            日期对象
	 * @return
	 */
	public static String getTimeByDate(Date date)
	{
		return formatDate(date, TIME_PATTERN);
	}

	/**
	 * 获取当前的时间，new Date()获取当前的日期
	 *
	 * @return
	 */
	public static String getNowTime()
	{
		return formatDate(new Date(), TIME_PATTERN);
	}

	/**
	 * 获取某一指定时间的前一段时间
	 *
	 * @param num
	 *            时间差数值
	 * @param type
	 *            时间差类型：1->秒,2->分钟,3->小时,4->天
	 * @param date
	 *            参考时间
	 * @return 返回格式化时间字符串
	 */
	public static String getPreTimeStr(double num, int type, Date date)
	{
		long nowLong = date.getTime();// 将参考日期转换为毫秒时间
		Date time = new Date(nowLong - formatToTimeMillis(num, type));// 减去时间差毫秒数
		return getTimeByDate(time);
	}

	/**
	 * 获取某一指定时间的前一段时间
	 *
	 * @param num
	 *            时间差数值
	 * @param type
	 *            时间差类型：1->秒,2->分钟,3->小时,4->天
	 * @param date
	 *            参考时间
	 * @return 返回Date对象
	 */
	public static Date getPreTime(double num, int type, Date date)
	{
		long nowLong = date.getTime();// 将参考日期转换为毫秒时间
		Date time = new Date(nowLong - formatToTimeMillis(num, type));// 减去时间差毫秒数
		return time;
	}

	/**
	 * 获取某一指定时间的后一段时间
	 *
	 * @param num
	 *            时间差数值
	 * @param type
	 *            时间差类型：1->秒,2->分钟,3->小时,4->天
	 * @param date
	 *            参考时间
	 * @return 返回格式化时间字符串
	 */
	public static String getNextTimeStr(double num, int type, Date date)
	{
		long nowLong = date.getTime();// 将参考日期转换为毫秒时间
		Date time = new Date(nowLong + formatToTimeMillis(num, type));// 加上时间差毫秒数
		return getTimeByDate(time);
	}

	/**
	 * 获取某一指定时间的后一段时间
	 *
	 * @param num
	 *            时间差数值
	 * @param type
	 *            时间差类型：1->秒,2->分钟,3->小时,4->天
	 * @param date
	 *            参考时间
	 * @return 返回Date对象
	 */
	public static Date getNextTime(double num, int type, Date date)
	{
		long nowLong = date.getTime();// 将参考日期转换为毫秒时间
		Date time = new Date(nowLong + formatToTimeMillis(num, type));// 加上时间差毫秒数
		return time;
	}

	/**
	 * 得到前几个月的现在
	 * 利用GregorianCalendar类的set方法来实现
	 *
	 * @param num
	 * @param date
	 * @return
	 */
	public static Date getPreMonthTime(int num, Date date)
	{
		GregorianCalendar gregorianCal = new GregorianCalendar();
		gregorianCal
				.set(Calendar.MONTH, gregorianCal.get(Calendar.MONTH) - num);
		return gregorianCal.getTime();
	}

	/**
	 * 得到后几个月的现在时间
	 * 利用GregorianCalendar类的set方法来实现
	 *
	 * @param num
	 * @param date
	 * @return
	 */
	public static Date getNextMonthTime(int num, Date date)
	{
		GregorianCalendar gregorianCal = new GregorianCalendar();
		gregorianCal
				.set(Calendar.MONTH, gregorianCal.get(Calendar.MONTH) + num);
		return gregorianCal.getTime();
	}

	/**
	 * Author: Tom Hu
	 * Method Name:getDaysBetweenDates
	 * Purpose:Get the days between two Date objects.
	 */
	public static int getDaysBetweenDates(Date startDate, Date endDate)
	{
		int days = 0;

		days = (int) ((endDate.getTime() - startDate.getTime()) / 86400000);

		return days;
	}

	/**
	 * Author:Tom Hu
	 * Method Name:getDayOfWeek
	 * Purpose:Gat which of this week
	 * Sunday --> 7 Saturday --> 6
	 */
	public static int getDayOfWeek(Date date)
	{
		int day = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if (c.get(Calendar.DAY_OF_WEEK) == 1)
		{
			day = 7;
		}
		else
		{
			day = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return day;
	}

	/**
	 * Author:Tom Hu
	 * Method Name:getWeeksBetweenDates
	 * Purpose:Get the weeks between two date, but when the date
	 */
	public static int getWeeksBetweenDates(Date startDate, Date endDate)
	{
		int weeks = 0;
		int days = getDaysBetweenDates(startDate, endDate);
		weeks = (days + 1) / 7;
		return weeks;
	}

	/**
	 * Method Name:getPreFriDay
	 * Purpose:
	 */
	public static Date getPreFriday(Date date)
	{
		int dayOfWeek = getDayOfWeek(date);
		if (5 == dayOfWeek)
		{
			return date;
		}
		else if (5 > dayOfWeek)
		{
			date = getPreTime(dayOfWeek + 2, 4, date);
			return date;
		}
		else
		{
			date = getPreTime(dayOfWeek - 5, 4, date);
			return date;
		}

	}

	public static Date getNextFriday(Date date)
	{
		int dayOfWeek = new DateTimeUtil().getDayOfWeek(date);
		if (5 == dayOfWeek)
		{
			return date;
		}
		else if (5 < dayOfWeek)
		{
			date = new DateTimeUtil().getNextTime(dayOfWeek, 4, date);
			return date;
		}
		else
		{
			date = new DateTimeUtil().getNextTime(5 - dayOfWeek, 4, date);
			return date;
		}

	}

	/**
	 * 获取当前年的前某一年
	 * @return
	 */
	public static String getFormatBeforeYear(int beforeNum, String format){
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR)-beforeNum;//yy  直接计算年数+2
		cal.set(year,1,1);
		Date date = cal.getTime();
		String time = DateTimeUtil.formatDate(date,format);
//		System.out.println(time);
		return time;
	}
}
