package com.jandar.frame.utils;

import java.util.Calendar;
import java.util.Date;

public class DateExt {
	private Boolean thisMonth = true;
	private Date date;
	private Integer year;
	private Integer month;
	private Integer week;
	private Integer day;
	

	public Integer getYear() {
		return year;
	}

	public Integer getMonth() {
		return month;
	}

	public Integer getWeek() {
		return week;
	}

	public Integer getDay() {
		return day;
	}

	public Boolean getThisMonth() {
		return thisMonth;
	}

	public void setThisMonth(Boolean thisMonth) {
		this.thisMonth = thisMonth;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
		Calendar calendarD = Calendar.getInstance();
		calendarD.setTime(date);
		year=calendarD.get(Calendar.YEAR);
		month=calendarD.get(Calendar.MONTH)+1;
		day=calendarD.get(Calendar.DAY_OF_MONTH);
		calendarD.setFirstDayOfWeek(Calendar.MONDAY);
		week=calendarD.get(Calendar.WEEK_OF_YEAR);
	}
}
