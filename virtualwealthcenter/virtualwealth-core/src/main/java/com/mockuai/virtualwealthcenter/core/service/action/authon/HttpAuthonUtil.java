package com.mockuai.virtualwealthcenter.core.service.action.authon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpAuthonUtil {
	private static final Logger log = LoggerFactory.getLogger(UserAutonAppAction.class);
	
	 public static String authonClient(String authon_personalid,String authon_realname,String authon_mobile,String authon_no) {
		  
//       PrintWriter out = null;
//       BufferedReader in = null;
//       String result = "";
//       try {
//           //http://61.147.98.117:9015 http://61.147.98.117:9001
//           String url = "http://61.147.98.117:9015/servlet/UserServiceAPI?method=sendSMS&username=jiekouceshi&password="+BASE64Encoder("123")+"&smstype=1&mobile="+mobileNumber+"&content="+content;
//           // 打开和URL之间的连接
//           URL realUrl = new URL(url);
//           String param = "method=sendSMS&username=jiekouceshi&password="+BASE64Encoder("123")+"&smstype=1&mobile="+mobileNumber+"&content="+content;
//           URLConnection conn = realUrl.openConnection();
//           // 设置通用的请求属性
//           conn.setRequestProperty("accept", "*/*");
//           conn.setRequestProperty("connection", "Keep-Alive");
//           conn.setRequestProperty("user-agent",
//                   "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//           // 发送POST请求必须设置如下两行
//           conn.setUseCaches(false);
//           conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
//           conn.setRequestProperty("Connection", "Close");
//           conn.setDoOutput(true);
//           conn.setDoInput(true);
//           // 获取URLConnection对象对应的输出流
//           out = new PrintWriter(conn.getOutputStream());
//           // 发送请求参数
//           out.print(param);
//           // flush输出流的缓冲
//           out.flush();
//           // 定义BufferedReader输入流来读取URL的响应
//           in = new BufferedReader(
//                   new InputStreamReader(conn.getInputStream()));
//           String line;
//           while ((line = in.readLine()) != null) {
//               result += line;
//           }
//       } catch (Exception e) {
//           System.out.println("发送 POST 请求出现异常！"+e);
//           e.printStackTrace();
//       }
//       //使用finally块来关闭输出流、输入流
//       finally{
//           try{
//               if(out!=null){
//                   out.close();
//               }
//               if(in!=null){
//                   in.close();
//               }
//           }
//           catch(IOException ex){
//               ex.printStackTrace();
//           }
//       }
//       return result;

       
       
       String result = "";
       BufferedReader in = null;
       try {
    	   StringBuffer url = new StringBuffer();
    	   url.append("https://auth.uubee.com/uubee_authserver/api/bankandall_check.htm?");
    	   url.append("id_no="+authon_personalid+"&");
    	   url.append("id_name="+URLEncoder.encode(authon_realname,"UTF-8")+"&");
    	   url.append("mobile_no="+authon_mobile+"&");
    	   url.append("card_no="+authon_no+"&");
    	   url.append("auth_key=C2EC10817A9D273CE1BF7C88D41B926BECBE5D6CE30C86B87E8ADC2450FB7A5262");
    	   log.info("实名认证URL"+url.toString());
    	   System.out.println("实名认证URL"+url.toString());
           URL realUrl = new URL(url.toString());
           // 打开和URL之间的连接
           URLConnection connection = realUrl.openConnection();
           // 设置通用的请求属性
           connection.setRequestProperty("accept", "*/*");
           connection.setRequestProperty("connection", "Keep-Alive");
           connection.setRequestProperty("user-agent",
                   "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
           // 建立实际的连接
           log.info("开始实名认证建立连接");
           connection.connect();
           log.info("开始实名认证建立连接成功");
           // 获取所有响应头字段
           Map<String, List<String>> map = connection.getHeaderFields();
           // 遍历所有的响应头字段
           for (String key : map.keySet()) {
               log.info(key + "--->" + map.get(key));
           }
           // 定义 BufferedReader输入流来读取URL的响应
           in = new BufferedReader(new InputStreamReader(
                   connection.getInputStream()));
           log.info("实名认证BufferedReader"+in);
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
//     Exception {
// return new sun.misc.BASE64Encoder().encode(str.getBytes());
//
//}
	public static void main(String args[]) throws Exception{
//	       try {
//	           send("18758019903","123");
//	        } catch (Exception e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }
		authonClient("341221198908204870", URLEncoder.encode("张凯强","UTF-8"), "15397056372", "341221198908204870");
		
	}
}
