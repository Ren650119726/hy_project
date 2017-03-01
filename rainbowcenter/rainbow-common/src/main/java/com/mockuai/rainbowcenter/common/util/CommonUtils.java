package com.mockuai.rainbowcenter.common.util;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommonUtils {
  public static Logger log = Logger.getLogger(CommonUtils.class);

  public static <T> T fillBean(Class<T> T, Object object, String... fieldNames){
    Field[] fieldC = T.getDeclaredFields();
    Field[] fieldO = object.getClass().getDeclaredFields();
    Object obj = null;
    try {
      obj = T.newInstance();
      for (int k = 0; k < fieldC.length; k++)
      {
        label:for (int j = 0; j < fieldO.length; j++)
          if ((fieldC[k].getName().equals(fieldO[j].getName())) && (fieldC[k].getType().getSimpleName().equals(fieldO[j].getType().getSimpleName()))) {
        	  if(!isEmpty(fieldNames)){
        		  for (int i = 0; i < fieldNames.length; i++) {
                      if (fieldC[k].getName().equals(fieldNames[i])) {
                        break label;
                      }
        		  }
            }
            String firstLetter = fieldC[k].getName().substring(0, 1).toUpperCase();
            String setMethodName = "set" + firstLetter + fieldC[k].getName().substring(1);
            String getMethodName = "get" + firstLetter + fieldC[k].getName().substring(1);

            Method getMethod = object.getClass().getMethod(getMethodName, new Class[0]);
            Method setMethod = T.getMethod(setMethodName, new Class[] { getMethod.getReturnType() });
            setMethod.invoke(obj, new Object[] { getMethod.invoke(object, new Object[0]) });
          }
      }
    }
    catch (IllegalAccessException e1) {
      e1.printStackTrace();
      log.info("errorMessage:IllegalAccessException");
    } catch (InvocationTargetException e2) {
      e2.printStackTrace();
      log.info("errorMessage:InvocationTargetException");
    } catch (NoSuchMethodException e3) {
      e3.printStackTrace();
      log.info("errorMessage:NoSuchMethodException");
    } catch (InstantiationException e4) {
      e4.printStackTrace();
      log.info("errorMessage:NoSuchMethodException");
    }

    return (T) obj;
  }


  public static <T> T fillBeanWithArray(Class<T> T, String[] strArray)
    throws Exception
  {
    Field[] field = T.getDeclaredFields();
    if (field.length != strArray.length) {
      throw new Exception("所传字符串数组长度和对应的java对象字段数量不符...");
    }
    Object obj = null;
    try {
      obj = T.newInstance();
      for (int k = 0; k < strArray.length; k++) {
        String firstLetter = field[k].getName().substring(0, 1).toUpperCase();
        String setMethodName = "set" + firstLetter + field[k].getName().substring(1);
        Method setMethod = T.getMethod(setMethodName, new Class[] { String.class });
        if (strArray[k] != null)
          setMethod.invoke(obj, new Object[] { strArray[k] });
      }
    }
    catch (IllegalAccessException e1) {
      e1.printStackTrace();
      log.info("errorMessage:IllegalAccessException");
    } catch (InvocationTargetException e2) {
      e2.printStackTrace();
      log.info("errorMessage:InvocationTargetException");
    } catch (NoSuchMethodException e3) {
      e3.printStackTrace();
      log.info("errorMessage:NoSuchMethodException");
    } catch (InstantiationException e4) {
      e4.printStackTrace();
      log.info("errorMessage:InstantiationException");
    }
    return (T) obj;
  }
  
  /**
   * 描述：验证对象是否为空
   * @param o
   * @return boolean
   */
  public static boolean isEmpty(Object o){
  	if (o == null || o.toString().trim().length() == 0 || o.toString().trim().equals("null"))
  		return true;
  	else if (o instanceof Object[] && ((Object[])o).length == 0)
  		return true;
  	else if (o instanceof List && ((List<?>)o).size() == 0)
  		return true;
  	return false;
  }
 
  /**
   * 描述：date日期格式变为String
   * @param date
   * @return
   */
  public static String formateDateToStr(Date date){
		if(date == null ) return null;
	    SimpleDateFormat sdfLongTimePlus = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowDate = null;
		try{
			nowDate = sdfLongTimePlus.format(date);
		}catch (Exception e){
			e.printStackTrace();
		}
		return nowDate;
  }
  /**
   * 描述：长整型的金额'分'变为double型的'元'
   * @param num
   * @return
   */
  public static Double moneryFenToYuan(Long num){
	  return (double)num/100;
  }
  /**
   * 描述：获取当前时间
   * @return
   * @throws java.text.ParseException
   */
  public static String getCurrentTime() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
  }
  public static void main(String[] args) {
	System.out.println(Md5("haihuan123"));
}
  /**
   * 描述：MD5加密
   * @param plainText
   * @return
   */
  public static String Md5(String plainText) {
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			System.out.println("result: " + buf.toString());// 32位的加密
			System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

  /**
   * 将字符串转为时间戳
   *
   * @param user_time
   * @return
   */
  public static String dateToTimestamp(String user_time) {
    String re_time = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date d;
    try {
      d = sdf.parse(user_time);
      long l = d.getTime();
      String str = String.valueOf(l);
      re_time = str.substring(0, 10);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return re_time;
  }

  /**
   * //时间戳转化为Sting或Date
   * @param timestamp
   * @return
   */
  public  static Date  timestampTodate (String timestamp){

    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Long time = new Long(timestamp);
    String d = format.format(time);
    Date date = null;
    try {
        date = format.parse(d);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return  date;
  }
}