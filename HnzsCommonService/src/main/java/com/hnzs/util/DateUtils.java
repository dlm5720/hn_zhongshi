package com.hnzs.util;

import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

	private static final DateFormat mmddFormat = new SimpleDateFormat("MM-dd");
	private static final DateFormat hhmmFormat = new SimpleDateFormat("HH:mm");

	
	/**
	 * 获取2个日期之间的每天
	 * 
	 * @param p_start
	 *            Start Date
	 * @param p_end
	 *            End Date
	 * @return Dates List
	 */
	public List<Date> getDates(Calendar p_start, Calendar p_end) {
		List<Date> result = new ArrayList<Date>();
		Calendar temp = p_start.getInstance();
		temp.add(Calendar.DAY_OF_YEAR, 1);
		while (temp.before(p_end)) {
			result.add(temp.getTime());
			temp.add(Calendar.DAY_OF_YEAR, 1);
		}
		return result;
	}

	/**
	 * 获取String日期的 上一天 String 日期
	 * 
	 * @throws ParseException
	 */
	public static String getPreviousDay(String strdate) throws ParseException {
		String str = "";
		if (!StringUtil.isNull(strdate)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();// 初始化calendar
			calendar.setTime(formatter.parse(strdate));// 转化成calendar日历形式
			calendar.add(Calendar.DATE, -1);// 实现加减运算，例如：加一天calendar.add(Calendar.DATE,1);减一天calendar.add(Calendar.DATE,-1);
			str = formatter.format(calendar.getTime());// 获取加减运算后的日期并转化成String
		}
		return str;
	}

	/**
	 * 获取String日期的 前的几天
	 * 
	 * @throws ParseException
	 */
	public static String getPreviousNDay(String strdate,int days) throws ParseException {
		String str = "";
		if (!StringUtil.isNull(strdate)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();// 初始化calendar
			calendar.setTime(formatter.parse(strdate));// 转化成calendar日历形式
			calendar.add(Calendar.DATE, days);// 实现加减运算，例如：加一天calendar.add(Calendar.DATE,1);减一天calendar.add(Calendar.DATE,-1);
			str = formatter.format(calendar.getTime());// 获取加减运算后的日期并转化成String
		}
		return str;
	}

	/**
	 * 获取String日期的 前n天 String 日期
	 * 
	 * @throws ParseException
	 */
	public static String getPNDay(String strdate,int days) throws ParseException {
		String str = "";
		if (!StringUtil.isNull(strdate)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();// 初始化calendar
			calendar.setTime(formatter.parse(strdate));// 转化成calendar日历形式
			calendar.add(Calendar.DATE, days);// 实现加减运算，例如：加一天calendar.add(Calendar.DATE,1);减一天calendar.add(Calendar.DATE,-1);
			str = formatter.format(calendar.getTime());// 获取加减运算后的日期并转化成String
		}
		return str;
	}
	/**
	 * 获取String时间的 后n秒的String 日期
	 * 
	 * @throws ParseException
	 */
	public static String getPNSecond(String strdatetime,int seconds) throws ParseException {
		String str = "";
		if (!StringUtil.isNull(strdatetime)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();// 初始化calendar
			calendar.setTime(formatter.parse(strdatetime));// 转化成calendar日历形式
			calendar.add(Calendar.SECOND, seconds);// 实现加减运算，例如：加一秒calendar.add(Calendar.SECOND,1);减一秒calendar.add(Calendar.SECOND,-1);
			str = formatter.format(calendar.getTime());// 获取加减运算后的日期并转化成String
		}
		return str;
	}
	
	/**
	 * 这样写才能避免对象的任意创建，达到简便又能节省内存空间
	 * 
	 * @author XuGuo
	 * @since 2009-07-23
	 * @param date
	 * @return
	 */
	public static String formatMD(Date date) {
		return date == null ? "" : mmddFormat.format(date);
	}

	public static String formatHM(Date date) {
		return date == null ? "" : hhmmFormat.format(date);
	}

	public static String formatYMDHMS(String datestr) {

		return datestr == null ? "" : formatDateTime(StrToDate2(datestr, null),
				"yyyyMMddHHmmss");
	}

	public static String formatYMD(String datestr) {
		return datestr == null ? "" : formatDateTime(StrToDate(datestr, null),
				"yyyy-MM-dd");
	}

	public static String formatDateTime(Date date, String format) {
		if (date == null)
			return "";
		if (format == null)
			return date.toString();
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static String formatY0M0D(Date date) {
		return date == null ? "" : formatDateTime(date, "yyyyMMdd");
	}

	public static String formatMMHHSS(Date date) {
		return date == null ? "" : formatDateTime(date, "HHmmss");
	}

	public static String formatYMD(Date date) {
		return date == null ? "" : formatDateTime(date, "yyyy-MM-dd");
	}

	public static String formatYMDHM(Date date) {
		return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm");
	}

	public static String formatDateTimeByDate(Date date) {
		return date == null ? "" : formatDateTime(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formatDateTimeByDate_f2(Date date) {
		return date == null ? "" : formatDateTime(date, "yyyyMMddHHmmss");
	}

	public static boolean showNew(Date time) {
		if (time == null)
			return false;
		return DateUtils.addDays(time, 3).compareTo(new Date()) >= 0;
	}

	public static Date addDays(Date srcDate, int addDays) {
		return getNextDayCurrDay(srcDate, addDays);
	}

	public static Date addMinutes(Date srcDate, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(srcDate);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	public static Date getNextDayCurrDay(Date currDate, int i) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(currDate);
		gc.add(GregorianCalendar.DATE, i);
		return gc.getTime();
	}

	public static int getCurrDay() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 日期比较大小 isBig=true:date1<date2
	 */
	public static boolean isBigDateForDate(Date date1, Date date2) {
		boolean isBig = false;
		isBig = date1.before(date2);
		return isBig;
	}

	/**
	 * 日期比较大小 isBig=true:date1<date2
	 */
	public static boolean isBigDateForStr(String date1, String date2) {
		boolean isBig = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = sdf.parse(date1);
			Date d2 = sdf.parse(date2);
			isBig = d1.before(d2);
		} catch (ParseException e) {
			//e.printStackTrace();
			return false;
		}

		return isBig;
	}

	/**
	 * 字符串转化为日期
	 * 
	 * @param str
	 *            被转化的字符串
	 * @param format
	 *            转化格式
	 * @return 返回日期
	 * @throws ParseException
	 * @author sys53
	 * @serialData 2007-11-03
	 */
	public static Date StrToDate(String str, String format) {
		try {
			if (StringUtil.isNull(format))
				format = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(str);
		} catch (ParseException pe) {
			return null;
		}
	}

	/**
	 * 字符串转化为日期2
	 * 
	 * @param str
	 *            被转化的字符串
	 * @param format
	 *            转化格式
	 * @return 返回日期
	 * @throws ParseException
	 * @author sys53
	 * @serialData 2007-11-03
	 */
	public static Date StrToDate2(String str, String format) {
		try {
			if (StringUtil.isNull(format))
				format = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(str);
		} catch (ParseException pe) {
			return null;
		}
	}

	/**
	 * 字符串转化为日期,默认格式为:yyyy-MM-dd
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date StrToDate(String str) {
		return StrToDate(str, "yyyy-MM-dd");
	}

	/**
	 * 判断某天是否在某个星期时间内 比如"2009-05-10" 是否在"1,2,4,5"星期内
	 * 
	 * @param strDate
	 * @param week
	 * @return
	 */
	public static boolean isExistInWeek(String strDate, String week) {
		Date date = StrToDate(strDate);
		int days = dayOfWeek(date);
		if (week.indexOf(String.valueOf(days)) >= 0) {
			return true;
		}
		return false;
	}

	// 判断日期为星期几,1为星期一,6为星期六,7为星期天，依此类推
	public static int dayOfWeek(Date date) {
		// 首先定义一个calendar，必须使用getInstance()进行实例化
		Calendar aCalendar = Calendar.getInstance();
		// 里面野可以直接插入date类型
		aCalendar.setTime(date);
		// 计算此日期是一周中的哪一天
		int x = aCalendar.get(Calendar.DAY_OF_WEEK);
		if (x == 1)
			x = 7;
		else
			x = x - 1;
		return x;
	}

	public static void main(String[] args) {
		try {
			System.out.println(getZhuSuYfs("2017-08-04","2017-11-04"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// System.out.println(isExistInWeek("2009-06-11","1,2,5,7"));
		// System.out.println(getParsedDate("06MAY09"));
		// System.out.println(getDiffDays(formatYMD(getNextDayCurrDay(new
		// Date(),10)),formatYMD(new Date())));
		//System.out.println(formatDateTime(getLastDate(new Date()),"yyyy-MM"));
	}

	/**
	 * 转换字符串日期类型为 "yyyy-MM-dd" 类型
	 * 
	 * @param strDate
	 *            06MAY09
	 * @return
	 * 
	 */
	public static String getParsedDate(String strDate) {
		String[] monIntArray = { "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12" };
		String[] monStrArray = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
				"JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
		String year = "20" + strDate.substring(5, 7);
		String month = strDate.substring(2, 5);
		String day = strDate.substring(0, 2);
		for (int i = 0; i < monStrArray.length; i++) {
			if (monStrArray[i].equalsIgnoreCase(month)) {
				month = monIntArray[i];
				break;
			}
		}
		return year + "-" + month + "-" + day;
	}

	public static long getNumOfDays(String date1, String date2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d1;
		try {
			// if is true ,date1>date2
			if (isBigDateForStr(date1, date2)) {
				d1 = df.parse(date1);
				Date d2 = df.parse(date2);
				long diff = Math.abs(d2.getTime() - d1.getTime());
				return (long) (-diff / (1000 * 60 * 60 * 24));
			} else {
				d1 = df.parse(date1);
				Date d2 = df.parse(date2);
				long diff = Math.abs(d2.getTime() - d1.getTime());
				return (long) (diff / (1000 * 60 * 60 * 24));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static long getDiffDays(String date1, String date2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d1;
		long diff = 0;
		try {
			d1 = df.parse(date1);
			Date d2 = df.parse(date2);
			long diff_1 = d2.getTime() - d1.getTime();
			if (diff_1 >= 0) {
				diff = Math.abs(diff_1);
				return (long) (diff / (1000 * 60 * 60 * 24));
			} else {
				return (long) (diff_1 / (1000 * 60 * 60 * 24));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static long getNumOfDays(Date date1, Date date2) {
		long diff = Math.abs(date2.getTime() - date1.getTime());
		return (long) (diff / (1000 * 60 * 60 * 24));
	}

	/**
	 * 断判两个日期之间时差是否在5分钟以上
	 * 
	 * @param d1
	 *            　日期1
	 * @param d2
	 *            日期2
	 * @return 返回true两个日期之间相差5分钟以上，false相差十分钟以内.
	 */
	public static boolean compare(Date d1, Date d2) {
		if ((d2.getTime() - d1.getTime()) > 600000l) {
			return true;
		}
		return false;
	}

	/**
	 * 获取某天是星期几
	 * 
	 * @param d
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getTheDay(Date d) {
		return "日一二三四五六".charAt(d.getDay()) + "";
	}

	/**
	 * 获取得某年的第几周的起始日期和结束日期
	 * 
	 * @param year
	 *            年份
	 * @param week
	 *            第几周
	 * @return String 数组， [0] 起始日期 [1] 结束日期
	 */
	public static String[] weekDate(int year, int week) {
		if (week < 1 || week > 52)
			return null;
		String s[] = new String[2];
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		s[0] = formatYMD(c.getTime());
		c.add(Calendar.DATE, 6);
		s[1] = formatYMD(c.getTime());
		return s;

	}

	/**
	 * 获取得某年的第几月的起始日期和结束日期
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            第几月
	 * @return String 数组， [0] 起始日期 [1] 结束日期
	 */
	public static String[] monthDate(int year, int month) {
		if (month < 1 || month > 12)
			return null;
		String s[] = new String[2];
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		s[0] = formatYMD(c.getTime());
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DATE, -1);
		s[1] = formatYMD(c.getTime());
		return s;

	}
	/**
	 * 获取得某年的第几季度的起始日期和结束日期
	 * 
	 * @param year
	 *            年份
	 * @param season
	 *            第几季
	 * @return String 数组， [0] 起始日期 [1] 结束日期
	 */
	public static String[] seasonDate(int year, int season) {
		if (season < 1 || season > 4)
			return null;
		String y = String.valueOf(year);
		String[] s = new String[2];
		switch (season) {
		case 1:
			s[0] = y + "-01-01";
			s[1] = y + "-03-31";
			break;
		case 2:
			s[0] = y + "-04-01";
			s[1] = y + "-06-30";
			break;
		case 3:
			s[0] = y + "-07-01";
			s[1] = y + "-09-30";
			break;
		case 4:
			s[0] = y + "-10-01";
			s[1] = y + "-12-31";
			break;
		}
		return s;

	}

	/**
	 * 获取某年某月有多少天 如:20090225 返回28
	 * 
	 * @param strDate
	 *            某天
	 * @return
	 */
	@SuppressWarnings( { "static-access", "deprecation" })
	public static int getDaysOfMonth(String strDate) {
		int day = 0;
		Calendar cal = Calendar.getInstance();
		// 格式化日期
		SimpleDateFormat dformat = new SimpleDateFormat("yyyymmdd");
		try {
			Date date = dformat.parse(strDate);
			cal.setTime(date);
			// 在当前月份上加一,由于JAVA种JAN为0,所以这里加2
			cal.add(cal.MONTH, 2);
			// 设置日期为1号
			cal.set(cal.DATE, 1);
			// 向前退一天
			cal.add(cal.DAY_OF_MONTH, -1);
			date = cal.getTime();
			// 得到当前日,即是本月的天数
			day = date.getDate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return day;
	}

	@SuppressWarnings("deprecation")
	public static int getDateOfMonth(Date date) {
		return date.getDate();
	}

	/**
	 * 获取当前日期的下个月的若干天后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getDateInNextMonthOfNextDays(int days) {
		return formatYMD(getNextDayCurrDay(StrToDate(getNextMonthFirst()), days));
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	// 计算当月最后一天,返回字符串
	public static String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上月第一天
	public static String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取当月第一天
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取当天时间
	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}

	// 获得当前日期与本周日相差的天数
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得下周星期日的日期
	public static String getNextSunday() {

		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获取给定月份的第一天

	// 获取给定月份的最后一天
	public static String getMonthEnd(String strdate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		// String str = "2012-05-02";
		Date date = df.parse(strdate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return df2.format(calendar.getTime());
	}

	// 获得上月最后一天的日期
	public static String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}
	// 获得给定时间的上一个月
	public static String getPreviousMonth(String strdate,int yfs) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = sdf.parse(strdate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, (-1*yfs));
		return sdf.format(calendar.getTime());
	}
	// 获得给定时间的下一个月
	public static String getAfterMonth(String strdate,int yfs) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = sdf.parse(strdate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, (1*yfs));
		return sdf.format(calendar.getTime());
	}
	// 获得下个月第一天的日期
	public static String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月最后一天的日期
	public static String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年最后一天的日期
	public static String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年第一天的日期
	public static String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	// 获得本年有多少天
	public static int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	// 获得本年第一天的日期
	public static String getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// 获得本年最后一天的日期 *
	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	// 获得上年第一天的日期 *
	public static String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	// 获得本季度
	public static String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days
				+ ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	private static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 是否是同一天的时间判断，主要是正对有些时间带时分秒，有些时间不带时分秒
	 * 
	 * @author XuGuo
	 * @since 2009-04-13
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isTheSameDay(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
				&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
				&& (c1.get(Calendar.DAY_OF_MONTH) == c2
						.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 是否是同一天的时间判断，主要是正对有些时间带时分秒，有些时间不带时分秒
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isTheSameMonth(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
				&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
	}

	/**
	 * 两个时间之间的秒数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getSeconds(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / 1000;
		return day;
	}
	
	/**
	 * 两个时间之间的月数天数小时数分钟数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String getMonthDayMs(String date1, String date2) {
		String ls_return="";
		if (date1 == null || date1.equals(""))
			return "";
		if (date2 == null || date2.equals(""))
			return "";
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long miaoshu = (date.getTime() - mydate.getTime()) / 1000;
		if(miaoshu <=0 ){
			ls_return="0";
		}else {
			long yuefen= miaoshu / (30 * 24 * 60 * 60  );
			if(yuefen>0){
				ls_return=ls_return+yuefen+"个月";
				long tianshu=  (miaoshu % (30 * 24 * 60 * 60  ))/ ( 24 * 60 * 60 );
				if(tianshu>0){
					ls_return=ls_return+tianshu+"天";
					long xiaoshi=((miaoshu % (30 * 24 * 60 * 60  )) % ( 24 * 60 * 60 ) / ( 60 * 60 ) );
					if(xiaoshi>0){
						ls_return=ls_return+xiaoshi+"小时";
					}else{
						long fenzhong= ( ((miaoshu % (30 * 24 * 60 * 60  )) % ( 24 * 60 * 60 ) % ( 60 * 60 )) / (60 ) );
						if(fenzhong>0){
							ls_return=ls_return+fenzhong+"分钟";
						}
					}
				}else{
					long xiaoshi=miaoshu / ( 60 * 60 );
					if(xiaoshi>0){
						ls_return=ls_return+xiaoshi+"小时";
					}else{
						long fenzhong= ( ((miaoshu % (30 * 24 * 60 * 60  )) % ( 24 * 60 * 60 ) % ( 60 * 60 )) / (60 ) );
						if(fenzhong>0){
							ls_return=ls_return+fenzhong+"分钟";
						}
					}
				}
			}else{
				long tianshu=  (miaoshu % (30 * 24 * 60 * 60  ))/ ( 24 * 60 * 60 );
				if(tianshu>0){
					ls_return=ls_return+tianshu+"天";
					long xiaoshi=((miaoshu % (30 * 24 * 60 * 60  )) % ( 24 * 60 * 60 ) / ( 60 * 60 ) );
					if(xiaoshi>0){
						ls_return=ls_return+xiaoshi+"小时";
					}else{
						long fenzhong= ( ((miaoshu % (30 * 24 * 60 * 60  )) % ( 24 * 60 * 60 ) % ( 60 * 60 )) / (60 ) );
						if(fenzhong>0){
							ls_return=ls_return+fenzhong+"分钟";
						}
					}
				}else{
					long xiaoshi=miaoshu / ( 60 * 60 );
					if(xiaoshi>0){
						ls_return=ls_return+xiaoshi+"小时";
					}else{
						long fenzhong= ( ((miaoshu % (30 * 24 * 60 * 60  )) % ( 24 * 60 * 60 ) % ( 60 * 60 )) / (60 ) );
						if(fenzhong>0){
							ls_return=ls_return+fenzhong+"分钟";
						}
					}
				}
			}
		}

		
		
		return ls_return;
	}
	
	//判断两个时间字符串的差
	//获取住宿的月份数
	public static Double getZhuSuYfs(String stime,String etime) throws ParseException{
		double lv_return=0;
		double ld_first_month=0,ld_last_month=0;
		//如果结束时间月份大于开始时间月份
		if(etime.substring(0,7).compareTo(stime.substring(0,7))>0){
			//处理开始月份的计算
			//5号之前算1,5号到20号算0.5,20号以后算0
			String first_month_days=stime.substring(8,10);
			if(!StringUtil.isNull(first_month_days)){
				int day=Integer.parseInt(first_month_days);
				if(day<5){
					ld_first_month=1;
				}else if(day>=5 && day<=20){
					ld_first_month=0.5;
				}else if(day>20){
					ld_first_month=0;
				}
			}
			//处理结束月份的计算
			//5号之前算0,5号到20号算0.5,20号以后算1
			String last_month_days=etime.substring(8,10);
			if(!StringUtil.isNull(last_month_days)){
				int day=Integer.parseInt(last_month_days);
				if(day<5){
					ld_last_month=0;
				}else if(day>=5 && day<=20){
					ld_last_month=0.5;
				}else if(day>20){
					ld_last_month=1;
				}
			}		
			
			//替换开始时间
			//获取下月第一天
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
			Date date = df.parse(stime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 1);
			//重置开始时间
			stime= df.format(calendar.getTime())+"-01";

			
			//重置结束时间  月第一天的上一天
			etime=etime.substring(0, 7)+"-01";
			
			
			//计算中间月份的数量
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar c1=Calendar.getInstance();
			Calendar c2=Calendar.getInstance();
			c1.setTime(sdf.parse(stime));
			c2.setTime(sdf.parse(etime));
			lv_return = ((c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR))*12 )+ (c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH));
			
			if(lv_return<0) lv_return=0;
			
		}else if(etime.substring(0,7).equals(stime.substring(0,7))){
			//处理开始月份的计算
			//5号之前算1,5号到20号算0.5,20号以后算0
			String first_month_days=stime.substring(8,10);
			if(!StringUtil.isNull(first_month_days)){
				int day=Integer.parseInt(first_month_days);
				if(day<5){
					ld_first_month=1;
				}else if(day>=5 && day<=20){
					ld_first_month=0.5;
				}else if(day>20){
					ld_first_month=0;
				}
			}

		}

		//返回结果
		return lv_return + ld_first_month + ld_last_month;
	}
	
    public static double round(double v,int scale){         
        if(scale<0)         
  
        {         
            throw new IllegalArgumentException("The scale must be a positive integer or zero");         
            }         
        BigDecimal b = new BigDecimal(Double.toString(v));         
        BigDecimal one = new BigDecimal("1");         
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();         
    }  
    

	/**
	 * 获取服务器当前时间所在月的最大天数
	 * 
	 */
	public static int getCurrentTimeOfServerDays() throws Exception{
		Calendar cc2 = Calendar.getInstance();
		//获取当月DAY最大值
		int maxMonthDay = cc2.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxMonthDay;
	}
	
	
	/**
	 * 根据年月获取对应月份的最大天数
	 *
	 */
	public static int getTheMonthofMaxDaysByYearMonth(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
	
	/**
	 * 判断两个日期之间相差几个月
	 *
	 */
	public static int getTwoDateBetweenMonth(String str1, String str2) throws ParseException{
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:ss");
		org.joda.time.DateTime startDate = format.parseDateTime(str1);
		org.joda.time.DateTime endDate = format.parseDateTime(str2);
		int months = Months.monthsBetween(startDate, endDate).getMonths();
		return months;
	}
	
	/**
	 * 判断字符串日期的当月的最大天数
	 *
	 */
	public static int getDayOfMonth(String str1) throws ParseException{
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:ss");
		org.joda.time.DateTime startDate = format.parseDateTime(str1);
		int days = startDate.dayOfMonth().getMaximumValue();
		return days;
	}
	
	
	/**
	 * 判断两个日期之间相差几天
	 *
	 */
	public static int getTwoDateBetweenDays(String str1, String str2) throws ParseException{
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		org.joda.time.DateTime startDate = format.parseDateTime(str1);
		org.joda.time.DateTime endDate = format.parseDateTime(str2);
		int days = Days.daysBetween(startDate, endDate).getDays();
		return days;
	}
	
	/**
	 * 判断两个日期之间相差几年
	 *
	 */
	public static int getTwoDateBetweenYears(String str1, String str2) throws ParseException{
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
		org.joda.time.DateTime startDate = format.parseDateTime(str1);
		org.joda.time.DateTime endDate = format.parseDateTime(str2);
		int years = Years.yearsBetween(startDate, endDate).getYears();
		return years;
	}
	
	/**
	 * 获取指定日期后几个月的日期
	 *
	 */
	public static String getDatePlusMonths(String str1, int months) throws ParseException{
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:ss");
		org.joda.time.DateTime startDate = format.parseDateTime(str1);
		String newTimeStr = startDate.plusMonths(months).toString("yyyy-MM-dd HH:ss");
		return newTimeStr;
	}
	
	/**
	 * 获取指定日期前几个月的日期
	 *
	 */
	public static String getDateMinusMonths(String str1, int months) throws ParseException{
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
		org.joda.time.DateTime startDate = format.parseDateTime(str1);
		String newTimeStr = startDate.minusMonths(months).toString("yyyy-MM-dd");
		return newTimeStr;
	}
	
	/**
	 * 获取指定日期前几年的日期
	 *
	 */
	public static String getDateMinusYears(String str1, int years) throws ParseException{
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
		org.joda.time.DateTime startDate = format.parseDateTime(str1);
		String newTimeStr = startDate.minusYears(years).toString("yyyy-MM-dd");
		return newTimeStr;
	}
	
	/**
	 * 两个日期之间的秒数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getSeconds2(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date date = null;
		Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / 1000;
		return day;
	}
}