package com.lfq.tts.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


/**
 * @author
 */
public class DateUtil {
	/**
	 * 将日期格式化为字符串
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDateToStr(Date date, String pattern) {
		if (date == null || StringUtils.isEmpty(pattern)) {
			return "";
		}
		DateFormat df = new SimpleDateFormat(pattern);
		String dateStr = df.format(date);
		return dateStr;
	}

	/**
	 * 将字符串转换为日期
	 *
	 * @param str
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date formatStrToDate(String str, String pattern) throws ParseException {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.parse(str);
	}

	//将时间戳转换为时间
	public static Date stampToTime(String s) throws Exception{
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		//将时间戳转换为时间
		Date date = new Date(lt);
		return date;
	}


	/**
	 * 获取几分钟后的时间
	 *
	 * @param minute
	 * @param format
	 * @return
	 */
	public static String getAfterDateTime(int minute, String format) {
		LocalTime localTime = LocalTime.now().plusMinutes(minute);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
		String dataTime = localTime.format(dateTimeFormatter);
		return dataTime;
	}



	/**
	 * 获取某个日期的前一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBeforeDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 获取某个日期指定天数前/后的日期
	 * @param date 当前的日期
	 * @param day +往后的天数;
	 *            -往前的天数
	 * @return Date
	 */
	public static Date getAfterDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		date = calendar.getTime();
		return date;
	}


	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	 *
	 * @param nowTime 当前时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @author jqlin
	 */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime()
				|| nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 *
	 * 获得当天最小时间
	 * @param date 格式yyyy-MM-dd
	 * @return
	 */
	public static Date getStartOfDay(String date) throws ParseException {
		Date temp = formatStrToDate(date, "yyyy-MM-dd");
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(temp.getTime()),
				ZoneId.systemDefault());
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 获得当天最小时间
	 * @param date
	 * @return
	 */
	public static Date getStartOfDay(Date date) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
				ZoneId.systemDefault());
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 获得当天最大时间
	 * @param date 格式yyyy-MM-dd
	 * @return
	 */
	public static Date getEndOfDay(String date) throws ParseException {
		Date temp = formatStrToDate(date, "yyyy-MM-dd");
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(temp.getTime()),
				ZoneId.systemDefault());
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
	}
	/**
	 * 获得当天最大时间
	 * @param date
	 * @return
	 */
	public static Date getEndOfDay(Date date) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
				ZoneId.systemDefault());
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 获取月的第一天
	 * @param date Date
	 * @return
	 */
	public static Date getMonthStartDate(Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar.getTime();
	}
	/**
	 * 获取月的第一天
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public static Date getMonthStartDate(String date) throws ParseException {
		Date temp = formatStrToDate(date, "yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(temp);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar.getTime();
	}
	/**
	 * 获取月的最后一天
	 * @param date Date
	 * @return
	 */
	public static Date getMonthEndDate(Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}
	/**
	 * 获取月的最后一天
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public static Date getMonthEndDate(String date) throws ParseException {
		Date temp = formatStrToDate(date, "yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(temp);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}
}
