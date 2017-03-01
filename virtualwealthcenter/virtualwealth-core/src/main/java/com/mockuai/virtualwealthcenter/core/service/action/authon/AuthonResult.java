package com.mockuai.virtualwealthcenter.core.service.action.authon;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.http.HttpUtils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.client.methods.HttpPost;

import com.aliyun.oss.common.utils.HttpUtil;
import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;

public class AuthonResult {

	private String result_auth;
	
	private String ret_code;
	
	private String ret_msg;

	public String getResult_auth() {
		return result_auth;
	}

	public void setResult_auth(String result_auth) {
		this.result_auth = result_auth;
	}

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}
	
	
	
	
	public static void showTimer() {
        TimerTask task = new TimerTask() {  
            @Override  
            public void run() {  
            	System.out.println(123);
            }  
        };  
  
        Calendar calendar = Calendar.getInstance();  
       
        /*** 定制每日00：24：00执行方法 ***/  
        Date date = calendar.getTime();
        
        int year = calendar.get(Calendar.YEAR);  
        int month = calendar.get(Calendar.MONTH);  
        int day = calendar.get(Calendar.DAY_OF_MONTH);  
        
        calendar.set(year, month, day, 24, 00, 00);  
  
        Timer timer = new Timer();  
        long period = 1000*60; 
        timer.schedule(task, date,period);  
    }  
	
	
	/**
	 * 分转元
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String changeF2Y(long a){    
        return BigDecimal.valueOf(a).divide(new BigDecimal(100)).toString();    
    }    
        
      
//    public static String changeY2F(Long amount){    
//        return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();    
//    }  
    
    /**   
     * 将元为单位的转换为分 （乘100）  
     *   
     * @param amount  
     * @return  
     */  
    public static Long changeY2F(String amount){    
        String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额    
        int index = currency.indexOf(".");    
        int length = currency.length();    
        Long amLong = 0l;    
        if(index == -1){    
            amLong = Long.valueOf(currency+"00");    
        }else if(length - index >= 3){    
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));    
        }else if(length - index == 2){    
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);    
        }else{     
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");    
        }    
        return Long.valueOf(amLong.toString());    
    }    
    
    
    
//  //判断一天最多三次
//    if(cacheAuthon.get(authon_mobile) == null){
//    	cacheAuthon.put(authon_mobile, 1);
//    }else if(Integer.valueOf((String) cacheAuthon.get(authon_mobile)) == 3){
//    	return new VirtualWealthResponse(ResponseCode.ERROR_EXCEED_AUTHON);
//    }else{
//    	cacheAuthon.put(authon_mobile, (Integer.valueOf((String) cacheAuthon.get(authon_mobile))+1));
//    }
//	
    
    
    static class  A{
    	String a = null;
    	public A(){
    		
    	}
    	
    	public void abc() throws Exception{
    		try {
				this.def();
			} catch (Exception e) {
				System.out.println("2");
			}
    	}
    	
    	
    	public void def() throws Exception{
    		try {
    			if(a.equals("123")){
					System.out.println("正常");
				}
				System.out.println(4);
			} catch (Exception e) {
				throw new Exception();
			}
    }
	public static void main(String args[]) throws Exception{
	
		
		Long ab = (long) 111;
		String bbb = String.valueOf(ab);
		//System.out.println(bbb);
		int num = 0;
		do {
			
			System.out.println("123");

		} while (num == 0);
		Object[] a = {"33",new String(),new Long(ab)};
//  		Map<Object,Object> cachemobile = new HashMap<Object, Object>();
//  		cachemobile.put("123", "123");
//  		cachemobile.put("456", "456");
//  		cachemobile.put("789", "789");
//  		if(cachemobile != null){
//  			 for (Object v : cachemobile.keySet()) {
//				   if(v != null){
//					 System.out.println(v);
//				   }
//				 }
//  		}
//  		 
//		Calendar calendar = Calendar.getInstance();
//		
//		 int month = calendar.get(Calendar.MONTH);
//		calendar.set(Calendar.MONTH, month-10);
//		
//        /*** 定制每日00：24：00执行方法 ***/  
//        Date date = calendar.getTime();
//        
//        System.out.println(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"));
//    
		
		
//        String wad = "1111111111111";
//        
//        Long wdamount = changeY2F(wad);
//        
//        System.out.println("1111111111111111111".length());
        
//        Test b = new Test();
        
//        System.out.println(b.getA());
        
//  		 Map<String, Object> map = new HashMap<String, Object>();
//  		 Map<String, Object> map1 = new HashMap<String, Object>();
//  		map1.put("123", 123);
//  		 map.put("abc",  map1);
//  		 
//  		 if(map.get("abc") != null){
//  			Map<String,Object> aa= (Map<String, Object>) map.get("abc");
//  			aa.put("456", 456);
//  			map.put("abc", aa);
//  			 System.out.println(map);
//  		 }
  	
	
//		String bresult = HttpBankBinUtil.bankBinClient("01230affw826");
//        BankBinResutl bankBinResutl =  JsonUtil.parseJson(bresult, BankBinResutl.class);
//        if(bankBinResutl.getRet_code().equals("0000")){
//        	//实名认证不支持信用卡验证
//        	if(bankBinResutl.getCard_type().equals("3")){
//        		System.out.println("123");
//        	}
//        }
		//String a = HttpBankBinUtil.bankBinClient("3123123124123123123123");
//		System.out.println("9223372036854775807".length());
//		
//		System.out.println("工商银行".indexOf("工商中国工商银行"));
		//showTimer();
//		String withdrawals_amount = "5.1";
//		Double a = 5.23;
//		if(a > Double.valueOf(changeF2Y(100))){
//			System.out.println(123);
//		}
//		Map<String, Object> b = new HashMap<String, Object>();
//		b.put("18758019903", "123123123");
//		
//		Long c = Long.valueOf(b.get("18758019903").toString());
//		System.out.println(c);
		
		
//		String b = null;
//		Long a = Long.valueOf(b); 
//		System.out.println(a);
		//System.out.println(changeF2Y(100));
	
		
		
		 //long Wd_config_mininum=0;
		 
//		String a  = "100.01";
//		 System.out.println(a.indexOf(".")<0?a+".00":a);
//		 
//		Map<String,Object> cache = new HashMap<String, Object>();
//		 
//		 
//		 String b = null;
//		 cache.put("123", "321");
//		if(cache.get("123")!=null && cache.get("123").toString().equals("321")){
//			System.out.println(b);
//		}
//		 
//		 double myMoney = 9.9999999E10;
//
//		
//		    
//		    DecimalFormat format=new DecimalFormat("###0.00");
//		    String str = "";
//		    str = format.format(myMoney);
//		    double dReturn = Double.parseDouble(str);
//		    System.out.println(dReturn);
//	        
//	        //提现金额
//	        Long wdamount = Long.valueOf(String.valueOf(money));
		//long a = 5.1;
		//System.out.println(changeY2F(wdamount));
//		float ff = (float) 499999.41323123;
//		System.out.println((Math.round(ff*100)));
		//非科学计数法运算
//		String a = "200000000.01";
//		double myMoney = Double.valueOf(a)*100;
//
//	    BigDecimal money= new BigDecimal(Double.toString(myMoney));
//	    Long b = Long.valueOf(String.valueOf(money).split("\\.")[0]);
//	    System.out.println(money); //后面则有很多的小数
	}
}
}