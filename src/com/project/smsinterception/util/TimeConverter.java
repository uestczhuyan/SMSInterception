package com.project.smsinterception.util;

/***********************************************************************
 * Module:  TimeConverter.java
 * Author:  suwei
 * Purpose: Defines the Interface TimeConverter
 ***********************************************************************/


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TimeConverter {

	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");
	
	private static SimpleDateFormat format1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	private static SimpleDateFormat format2 = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	private static SimpleDateFormat format3 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	
	public static SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");
	
	private static long MIN_MILLISECONDS  = 60*1000;
	private static long HALF_HOUR_MILLISECONDS = 30 * MIN_MILLISECONDS;
	private static long HOUR_MILLISECONDS = 60 * MIN_MILLISECONDS;
	private static long DAY_MILLISECONDS = 24 * HOUR_MILLISECONDS;
	
	/**
	 * 把日期格式转化为时间格式 传入的日期格式必须为 0000-00-00 00:00:00这种格式的
	 * 
	 * @param timeStr
	 */
	public static Date convertToTime(String timeStr) throws ParseException {
		Date time = format.parse(timeStr);
		return time;
	}
	
	public static final Date getFromString(String time){
		try {
			return formatHour.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}
	}
	
	/**
	 * 把日期格式转化为时间格式 传入的日期格式必须为 0000-00-00 00:00这种格式的
	 * 
	 * @param timeStr
	 */
	public static Date convertToTime1(String timeStr) throws ParseException {
		Date time = format1.parse(timeStr);
		return time;
	}
	
	/**
	 * 把日期格式转化为时间格式 传入的日期格式必须为 0000-00-00这种格式的
	 * 
	 * @param time
	 */
	public static Date convertToTime2(String time) throws ParseException {
		return format2.parse(time);
	}
	

	/**
	 * 将日期格式的转化为字符串格式的 转换出的字符串形式为 0000-00-00 00:00:00
	 * 
	 * @param time
	 */
	public static String convertToStr(Date time) {
		return format.format(time);
	}
	
	/**
	 * 将日期格式的转化为字符串格式的 转换出的字符串形式为 0000-00-00
	 * 
	 * @param time
	 */
	public static String convertToStr2(Date time) {
		return format2.format(time);
	}
	
	/**
	 * 将日期格式的转化为字符串格式的 转换出的字符串形式为 0000-00-00 00:00
	 * 
	 * @param time
	 */
	public static String convertToStr3(Date time) {
		return format1.format(time);
	}
	
	/**
	 * 将日期格式的转化为字符串格式的 转换出的字符串形式为 0000年00月00日 00:00
	 * 
	 * @param time
	 */
	public static String convertToStr4(Date time) {
		return format3.format(time);
	}
	
	
	/**
	 * 将日期格式的转化为字符串格式的 转换出的字符串形式为 0000-00-00
	 * 
	 * @param time 为timestamp形式
	 */
	public static String convertToStr(Timestamp time) {
		return convertToStr2(new Date(time.getTime()));
	}
	
	/**
	 * 将时间转换成字符串。<br>
	 * 并且对时间进行计算。如果时间在一定段类就进行具体的显示。<br>
	 * 例如：一分钟以前。2小时以前。
	 * @param timestamp
	 * @return
	 */
	public static String convertToStr2(Timestamp timestamp){
		long time = System.currentTimeMillis() - timestamp.getTime();
		if(time > 2*DAY_MILLISECONDS){
			return convertToStr4(new Date(timestamp.getTime()));
		}
		if(time > DAY_MILLISECONDS){
			return "1天前";
		}else if(time > 3*HOUR_MILLISECONDS){
			return "3小时前";
		}else if(time > 2*HOUR_MILLISECONDS){
			return "2小时前";
		}else if(time > HOUR_MILLISECONDS){
			return "1小时前";
		}else if(time > HALF_HOUR_MILLISECONDS){
			return "30分钟前";
		}else if(time > 10 * MIN_MILLISECONDS){
			return "10分钟前";
		}else if(time > MIN_MILLISECONDS){
			return "1分钟前";
		}else if(time >= 0 && time < MIN_MILLISECONDS){
			return "刚刚";
		}
		return "";
	}
	
	public static String convertToStr2(long date){
		long time = System.currentTimeMillis() - date;
		if(time > 2*DAY_MILLISECONDS){
			return convertToStr4(new Date(date));
		}
		if(time > DAY_MILLISECONDS){
			return "1天前";
		}else if(time > 3*HOUR_MILLISECONDS){
			return "3小时前";
		}else if(time > 2*HOUR_MILLISECONDS){
			return "2小时前";
		}else if(time > HOUR_MILLISECONDS){
			return "1小时前";
		}else if(time > HALF_HOUR_MILLISECONDS){
			return "30分钟前";
		}else if(time > 10 * MIN_MILLISECONDS){
			return "10分钟前";
		}else if(time > MIN_MILLISECONDS){
			return "1分钟前";
		}else if(time >= 0 && time < MIN_MILLISECONDS){
			return "刚刚";
		}
		return "";
	}

}