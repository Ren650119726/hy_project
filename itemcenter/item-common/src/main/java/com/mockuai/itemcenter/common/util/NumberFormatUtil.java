package com.mockuai.itemcenter.common.util;

import java.text.DecimalFormat;

public class NumberFormatUtil {

	private static DecimalFormat decimalFormat = new DecimalFormat();
	
	private static String pattern = "";
	
	/**
	 * 保留两位小数并去0
	 * @Description 
	 * @author linyue
	 * @param obj
	 * @return
	 */
	public static String formatTwoDecimalNoZero(Object obj){
		
		pattern = "#.##";
		decimalFormat.applyPattern(pattern);
		
		return decimalFormat.format(obj);
	}
	
}
