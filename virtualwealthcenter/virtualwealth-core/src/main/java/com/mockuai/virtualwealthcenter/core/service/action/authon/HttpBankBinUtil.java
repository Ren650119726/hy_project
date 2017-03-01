package com.mockuai.virtualwealthcenter.core.service.action.authon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpBankBinUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpBankBinUtil.class);
	
	
	public static String bankBinClient(String bank_no) {
      
      String result = "";
      BufferedReader in = null;
      try {
   	   StringBuffer url = new StringBuffer();
   	   url.append("https://auth.uubee.com/uubee_authserver/api/bankcardbinquery.htm?");
   	   url.append("card_no="+bank_no+"&");
   	   url.append("auth_key=C2EC10817A9D273CE1BF7C88D41B926BECBE5D6CE30C86B87E8ADC2450FB7A5262");
   	   log.info("银行卡binURL"+url.toString());
   	   System.out.println("银行卡binURL"+url.toString());
          URL realUrl = new URL(url.toString());
          // 打开和URL之间的连接
          URLConnection connection = realUrl.openConnection();
          // 设置通用的请求属性
          connection.setRequestProperty("accept", "*/*");
          connection.setRequestProperty("connection", "Keep-Alive");
          connection.setRequestProperty("user-agent",
                  "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
          // 建立实际的连接
          log.info("开始银行卡bin建立连接");
          connection.connect();
          log.info("开始银行卡bin建立连接成功");
          // 获取所有响应头字段
          Map<String, List<String>> map = connection.getHeaderFields();
          // 遍历所有的响应头字段
          for (String key : map.keySet()) {
              log.info(key + "--->" + map.get(key));
          }
          // 定义 BufferedReader输入流来读取URL的响应
          in = new BufferedReader(new InputStreamReader(
                  connection.getInputStream()));
          log.info("银行卡binBufferedReader"+in);
          String line;
          while ((line = in.readLine()) != null) {
              result += line;
          }
          log.info(result);
      } catch (Exception e) {
          log.info("发送GET请求出现异常！" + e);
          e.printStackTrace();
      }
      // 使用finally块来关闭输入流
      finally {
          try {
              if (in != null) {
                  in.close();
              }
          } catch (Exception e2) {
              e2.printStackTrace();
          }
      }
      return result;
  }
	
//	 public synchronized static String BASE64Encoder(String str) throws
//    Exception {
//return new sun.misc.BASE64Encoder().encode(str.getBytes());
//
//}
	public static void main(String args[]) throws Exception{
//	       try {
//	           send("18758019903","123");
//	        } catch (Exception e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }
		//4392260016303271
		bankBinClient("6223691523132912");
		
	}
}
