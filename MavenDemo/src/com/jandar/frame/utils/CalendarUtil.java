package com.jandar.frame.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CalendarUtil {
	/**
	 * @param year
	 * @param week
	 *            注意 week 从 1 开始
	 * @return
	 */
	static public Date getMondayOfWeekWithMonDayIsFirstDay(int year, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	/**
	 * @param year
	 * @param week
	 *            注意 week 从 1 开始
	 * @return
	 */
	static public Date getSundayOfWeekWithMonDayIsFirstDay(int year, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}

	static public boolean isWeekend(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		return (day == Calendar.SATURDAY || day == Calendar.SUNDAY);
	}

	static public boolean isTimeCross(Date start1, Date end1, Date start2,
			Date end2) {
		if (start1 == null || start2 == null || end1 == null || end2 == null) {
			return true;
		}
		if (start1.after(start2) && start1.before(end2)) {
			return true;
		}
		if (end1.after(start2) && end1.before(end2)) {
			return true;
		}
		if (start1.before(start2) && end1.after(end2)) {
			return true;
		}
		return false;
	}

	/**
	 * @param year
	 * @param week
	 *            注意 week 从 1 开始
	 * @return
	 */
	static public Date getMondayOfWeekWithMonDayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	/**
	 * @param year
	 * @param week
	 *            注意 week 从 1 开始
	 * @return
	 */
	static public Date getSundayOfWeekWithMonDayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}

	static public int getWeekOfYearWithMondayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	};

	static public int getYearWithMondayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar.get(Calendar.YEAR);
	};

	static public int getMonthWithMondayIsFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar.get(Calendar.MONTH) + 1;
	};

	/**
	 * @param year
	 * @param month
	 *            注意 month 从 1 开始
	 * @return 第一天 00:00:00.000
	 */
	static public Date getFirstDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	/**
	 * @param year
	 * @param month
	 *            注意 month 从 1 开始
	 * @return 最后一天 23:59:59.999
	 */
	static public Date getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	};

	/**
	 * 获取一天的开始
	 * 
	 * @param date
	 * @return 00:00:00.000
	 */
	static public Date getBeginOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	/**
	 * 获取一天的开始
	 * 
	 * @param date
	 * @return 00:00:00.000
	 */
	static public Date getMiddleOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 11);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	/**
	 * 获取虚拟的第一天
	 */
	static public Date getVirtualStartOfFirstDay() {
		Calendar calendar = new GregorianCalendar(1978, 11, 30);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	};

	static public int getDayOfYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	static public int getYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取今天的结束
	 */
	static public Date getEndOfToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	};

	/**
	 * 获取一天的结束
	 * 
	 * @param date
	 * @return 23:59:59.999
	 */
	static public Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	};

	/**
	 * @param date
	 * @return 周一是 1
	 */
	static public int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (i == 0) {
			return 7;
		} else {
			return i;
		}
	};

	/**
	 * @param date
	 */
	static public int getDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	};

	/**
	 * @param year
	 * @param month
	 *            注意 month 从 1 开始
	 * @return
	 */
	static public int getDayCountOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	};

	static public int getDayCountOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	};

	static public List<Date> getNormalDaysWithMonDayIsFirstDay(int year,
			int week) {
		List<Date> days = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		for (int d = 2; d < 7; d++) {
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.DAY_OF_WEEK, d);
			days.add(calendar.getTime());
		}
		return days;
	}

	static public List<Date> getWeekendDaysWithMonDayIsFirstDay(int year,
			int week) {
		List<Date> days = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, 7);
		days.add(calendar.getTime());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		days.add(calendar.getTime());
		return days;
	}

	static public Date getTime(int hour, int min, int sec) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, sec);
		return calendar.getTime();
	}

	static public Date getDateTime(Date date, Date time) {
		Calendar calendarD = Calendar.getInstance();
		calendarD.setTime(date);
		Calendar calendarT = Calendar.getInstance();
		calendarT.setTime(time);
		calendarD
		.set(Calendar.HOUR_OF_DAY, calendarT.get(Calendar.HOUR_OF_DAY));
		calendarD.set(Calendar.MINUTE, calendarT.get(Calendar.MINUTE));
		calendarD.set(Calendar.SECOND, calendarT.get(Calendar.SECOND));
		return calendarD.getTime();
	}

	static public String getGrantStringDate(Date date) {
		SimpleDateFormat timeDf = new SimpleDateFormat("yyyyMMdd", new Locale(
				"en"));
		String retString = timeDf.format(date);
		return retString;
	}

	static public String getBroadCastTimeStringDate(Date date) {
		SimpleDateFormat timeDf = new SimpleDateFormat("yyyyMMdd HH:mm");
		String retString = timeDf.format(date);
		return retString;
	}

	static public List<DateExt> getWeekDaysWithMonDayIsFirstDay(Date date) {
		List<DateExt> days = new ArrayList<DateExt>(7);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int i = calendar.get(Calendar.DAY_OF_WEEK);
		int d = calendar.get(Calendar.DAY_OF_YEAR);
		if (i == 1) {// SunDay
			for (int ii = 6; ii > -1; ii--) {
				calendar.set(Calendar.DAY_OF_YEAR, d - ii);
				DateExt de = new DateExt();
				de.setDate(calendar.getTime());
				days.add(de);
			}
		} else {
			int monday = d - (i - 2);
			for (int ii = 0; ii < 7; ii++) {
				calendar.set(Calendar.DAY_OF_YEAR, monday + ii);
				DateExt de = new DateExt();
				de.setDate(calendar.getTime());
				days.add(de);
			}
		}
		return days;
	}



	static public List<DateExt> getFullMonthDaysWithMonDayIsFirstDay(int year,
			int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		// 要制定日期
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 本月天数
		int monthCount = calendar.getActualMaximum(Calendar.DATE);
		// 本月1号是周几
		int thisMonthFirstDay = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 下月1号是周几
		int nextMonthFirstDay = calendar.get(Calendar.DAY_OF_WEEK);
		int addF = 0;
		int addL = 0;
		if (thisMonthFirstDay == Calendar.SUNDAY) { // 第一天是SunDay
			addF = 6;
		} else {
			addF = thisMonthFirstDay - 2;
		}
		if (nextMonthFirstDay == Calendar.MONDAY) { // 第一天是SunDay
			addL = 0;
		} else if (nextMonthFirstDay == Calendar.SUNDAY) { // 第一天是SunDay
			addL = 1;
		} else {
			addL = 9 - nextMonthFirstDay;
		}
		int alldays = addF + addL + monthCount;
		List<DateExt> days = new ArrayList<DateExt>(alldays);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int d = calendar.get(Calendar.DAY_OF_YEAR);
		int allInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int ii = 0; ii < alldays; ii++) {
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month - 1);
			calendar.set(Calendar.DAY_OF_YEAR, d - addF + ii);
			DateExt de = new DateExt();
			de.setDate(calendar.getTime());
			if ((-addF + ii < 0) || (-addF + ii > allInMonth - 1)) {
				de.setThisMonth(false);
			}
			days.add(de);
		}
		return days;
	}

	static public int getDaysBetweenDates(Date date1, Date date2) {
		long d = date2.getTime() - date1.getTime();
		return Long.valueOf(d / (24 * 60 * 60 * 1000)).intValue();
	}

	static public Date getDayAfterDate(Date date, int days) {
		Calendar calendarD = Calendar.getInstance();
		calendarD.setTime(date);
		calendarD.set(Calendar.DATE, calendarD.get(Calendar.DATE) + days);
		return calendarD.getTime();

	}

	static public Boolean isDayBetweenDates(Date date, Date sd, Date md) {
		return date.before(md) && date.after(sd);
	}

	static public Date getMonDayOfFirstWeekInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 0);
		return calendar.getTime();
	};

	static public Date getSunDayOfLastWeekInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	};

	static public  long daysBetween(Date date1,Date date2)     
	{     
		Calendar cal = Calendar.getInstance();     
		cal.setTime(date1);     
		long time1 = cal.getTimeInMillis();                  
		cal.setTime(date2);     
		long time2 = cal.getTimeInMillis();          
		long between_days=(time2-time1)/(1000*3600*24);     

		return Integer.parseInt(String.valueOf(between_days));            
	}   ;

	

	//获取上上月第一天日期
	static public Date getDayOfLastLastMonth() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.setTime(format.parse(format.format(calendar.getTime())));
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			return format.parse(format.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	};

	//获取上上月月份
	static public int getYearOfLastLastMonth() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.setTime(format.parse(format.format(calendar.getTime())));
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			return Integer.parseInt(format.format(calendar.getTime()).substring(0, 4));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	};

	//获取上上月月份
	static public int getMonthOfLastLastMonth() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.setTime(format.parse(format.format(calendar.getTime())));
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			return Integer.parseInt(format.format(calendar.getTime()).substring(5, 7));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	};

	//获取上月年份
	static public int getYearOfLastMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return Integer.parseInt(format.format(calendar.getTime()).substring(0, 4));
	};

	//获取上月第一天
	static public Date getFirstDayOfLastMonth() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			return format.parse(format.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	};

	//获取上月最后一天
	static public Date getLastDayOfLastMonth() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar calendar = Calendar.getInstance();   
			calendar.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
			return format.parse(format.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	};

	//获取本月第一天
	static public Date getFirstDayOfThisMonth() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();    
			calendar.add(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
			return format.parse(format.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	};

	//获取本月最后一天
	static public Date getLastDayOfThisMonth() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			Calendar calendar = Calendar.getInstance();   
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); 
			return format.parse(format.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	};
	
	/**
	 * 时间加减
	 * 如果是年份加减cal.add(1, i);
	 * 如果是月份加减cal.add(2, i);
		如果是星期加减cal.add(3, i);
		如果是每日加减cal.add(5, i);
		如果是小时加减cal.add(10, i);
		如果是分钟加减cal.add(12, i);
		如果是秒的加减cal.add(13, i);
	 * @param ti
	 * @param i
	 * @return
	 */
	static public Date addOrMinusTime(Date date, String type, int i) {
		try {
			GregorianCalendar cal = new GregorianCalendar();  
			cal.setTime(date);  
			switch(type) {  
			    case "year": 
			    	cal.add(1, i);   
			    	break;  
			    case "month": 
			    	cal.add(2, i);
			    	break;  
			    case "week": 
			    	cal.add(3, i);
			    	break;  
			    case "day": 
			    	cal.add(5, i);
			    	break;  
			    case "hour": 
			    	cal.add(10, i);
			    	break;  
			    case "minute": 
			    	cal.add(12, i);
			    	break;  
			    case "second": 
			    	cal.add(13, i);
			    	break;  
			    default: break;  
			}  
			return cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
	};
	
	public static void main(String[] args) {
		// Calendar calendar=Calendar.getInstance();
		// calendar.setFirstDayOfWeek(Calendar.MONDAY);
		// calendar.set(Calendar.YEAR, 2009);
		// calendar.set(Calendar.WEEK_OF_YEAR, 49);
		// calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		// SimpleDateFormat df=new SimpleDateFormat("EEE d/MMM");
		// System.out.println(df.format(calendar.getTime()));
		// week= Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
		// year= Calendar.getInstance().get(Calendar.YEAR);
		// Calendar calendar = new GregorianCalendar(2010, 0, 13);
		// System.out.println(calendar.getTime());
		// System.out.print(CalendarUtil.getWeekOfYearWithMonDayIsFirstDay(calendar.getTime()));
		// System.out.print(CalendarUtil.getFirstDayOfMonth(2009, 11));

		// System.out.println(CalendarUtil
		// .getSundayOfWeekWithMonDayIsFirstDay(calendar.getTime()));
//		SimpleDateFormat timeDf = new SimpleDateFormat("yyyy-MM-dd K:mm a",
//				new Locale("en"));
//		Date date = null;
		try {
//			date = timeDf.parse("2010-02-10 12:30 AM");
			Date d = new Date();
			System.out.println(d);
			Date dt = addOrMinusTime(d, null, 100);
			System.out.println(dt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(getMonthWithMondayIsFirstDay(date));

		// calendar.setFirstDayOfWeek(Calendar.MONDAY);
		// // n.setFirstDayOfWeek(Calendar.MONDAY);
		// SimpleDateFormat df = new SimpleDateFormat("E d/MMM yyyy");
		// System.out.println("ddd" + df.format(calendar.getTime()));
		// System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
		// int year = calendar.get(Calendar.YEAR);
		// int week = calendar.get(Calendar.WEEK_OF_YEAR);
		// int day = calendar.get(Calendar.DAY_OF_YEAR);
		// int DAY_OF_WEEK = calendar.get(Calendar.DAY_OF_WEEK);
		// // int DAY_OF_WEEK_IN_MONTH =n.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		// if (day > 7 && week == 1) {
		// year++;
		// }
		// System.out.println(year);
		// System.out.println(day);
		// System.out.println(DAY_OF_WEEK);
		// // System.out.println(DAY_OF_WEEK_IN_MONTH);
		// // Calendar calendar=Calendar.getInstance();
		//
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.WEEK_OF_YEAR, week);
		// // SimpleDateFormat df=new SimpleDateFormat("EEE d/MMM yyyy");
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 2);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 3);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 4);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 5);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 6);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 7);
		// System.out.println(df.format(calendar.getTime()));
		// calendar.set(Calendar.YEAR, year);
		// calendar.set(Calendar.DAY_OF_WEEK, 1);
		// System.out.println(df.format(calendar.getTime()));
		// // for (int i=Calendar.MONDAY;i<8;i++){
		// calendar.set(Calendar.DAY_OF_WEEK,i);
		//
		// //System.out.println(df.format(calendar.getTime()));
		// }
	}
	
}
