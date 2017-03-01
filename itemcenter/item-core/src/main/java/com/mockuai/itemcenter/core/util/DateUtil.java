package com.mockuai.itemcenter.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 将日期以"yyyy-MM-dd HH:mm:ss"的形式的字符串输出
	 * 
	 * @param date
	 * @return
	 */
	public static String toSecondString(Date date) {
		return sdf.format(date);
	}

	/**
	 * 将日期以"yyyy-MM-dd"的形式的字符串输出
	 * 
	 * @param date
	 * @return
	 */
	public static String toDayString(Date date) {
		return sdf2.format(date);
	}

	/**
	 * 将以"yyyy-MM-dd HH:mm:ss"形式的字符串转成Date
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date toSecondDate(String date) throws ParseException {
		return sdf.parse(date);
	}

	/**
	 * 将以"yyyy-MM-dd"形式的字符串转成Date
	 * 
	 * @param date
	 * @return
	 */
	public static Date toDayDate(String date) throws ParseException {
		return sdf2.parse(date);
	}
}
