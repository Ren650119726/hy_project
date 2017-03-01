package com.mockuai.suppliercenter.core.util;

import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCardCheckUtil {

    final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();

    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "宁夏");
        zoneNum.put(65, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "外国");
    }

    final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
            5, 8, 4, 2};

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws java.text.ParseException
     */
    public static String IDCardValidate(String IDStr) throws SupplierException {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";

        // =================身份证号不能为空=================
        if (StringUtils.isBlank(IDStr)) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "idCard is null");
        }

        //将身份证号转成小写
        IDStr = IDStr.toLowerCase();

        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "IDCard length should be 15 or 18";
            throw new SupplierException(ResponseCode.B_ID_CARD_ERROR, errorInfo);
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "IDCard length is 15,must be num ";

            throw new SupplierException(ResponseCode.B_ID_CARD_ERROR, errorInfo);
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "IDCard birthday is error";
            throw new SupplierException(ResponseCode.B_ID_CARD_ERROR, errorInfo);
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "IDCard birthday is invalid";
                throw new SupplierException(ResponseCode.B_ID_CARD_ERROR, errorInfo);
            }
        } catch (Exception e) {
            throw new SupplierException(e);
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "IDCard month is invalid";
            throw new SupplierException(ResponseCode.B_ID_CARD_ERROR, errorInfo);
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "IDCard date is invalid";
            throw new SupplierException(ResponseCode.B_ID_CARD_ERROR, errorInfo);
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        if (zoneNum.get(Integer.valueOf(Ai.substring(0, 2))) == null) {
            errorInfo = "IDCard areaCode is error";
            throw new SupplierException(ResponseCode.B_ID_CARD_ERROR, errorInfo);
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "IDCard is invalid";
                throw new SupplierException(ResponseCode.B_ID_CARD_ERROR, errorInfo);
            }
        } else {
            return "";
        }
        // =====================(end)=====================
        return "";
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证日期字符串是否是YYYY-MM-DD格式
     *
     * @param str
     * @return
     */
    public static boolean isDataFormat(String str) {
        boolean flag = false;
        // String
        // regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }

//	/**
//	 * 身份证验证
//	 *@param s  号码内容
//	 *@return 是否有效 null和"" 都是false
//	 */
//	public static boolean isIDCard(String certNo){
//		if(certNo == null || (certNo.length() != 15 && certNo.length() != 18))
//			return false;
//		final char[] cs = certNo.toUpperCase().toCharArray();
//		//校验位数
//		int power = 0;
//		for(int i=0; i<cs.length; i++){
//			if(i==cs.length-1 && cs[i] == 'X')
//				break;//最后一位可以 是X或x
//			if(cs[i]<'0' || cs[i]>'9')
//				return false;
//			if(i < cs.length -1){
//				power += (cs[i] - '0') * POWER_LIST[i];
//			}
//		}
//
//		//校验区位码
//		if(!zoneNum.containsKey(Integer.valueOf(certNo.substring(0,2)))){
//			return false;
//		}
//
//		//校验年份
//		String year = certNo.length() == 15 ? getIdcardCalendar() + certNo.substring(6,8) :certNo.substring(6, 10);
//
//		final int iyear = Integer.parseInt(year);
//		if(iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR))
//			return false;//1900年的PASS，超过今年的PASS
//
//		//校验月份
//		String month = certNo.length() == 15 ? certNo.substring(8, 10) : certNo.substring(10,12);
//		final int imonth = Integer.parseInt(month);
//		if(imonth <1 || imonth >12){
//			return false;
//		}
//
//		//校验天数
//		String day = certNo.length() ==15 ? certNo.substring(10, 12) : certNo.substring(12, 14);
//		final int iday = Integer.parseInt(day);
//		if(iday < 1 || iday > 31)
//			return false;
//
//		//校验"校验码"
//		if(certNo.length() == 15)
//			return true;
//		return cs[cs.length -1 ] == PARITYBIT[power % 11];
//	}
//
//	private static int getIdcardCalendar() {
//		GregorianCalendar curDay = new GregorianCalendar();
//		int curYear = curDay.get(Calendar.YEAR);
//		int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));
//		return  year2bit;
//	}

    public static void main(String[] args) throws Exception {
        System.out.println("" + IdCardCheckUtil.IDCardValidate("653024198901130023"));
    }

}
