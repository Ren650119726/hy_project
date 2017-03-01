package com.mockuai.tradecenter.core.util;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class WeixinPayUtil {
	
	
	
	
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	/**
	 * 微信支付签名算法sign
	 * @param characterEncoding
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String createSign(SortedMap<Object,Object> parameters,String key){
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
//		String sign = DigestUtils.md5Hex(sb.toString()).toUpperCase();
		String sign = MD5Encode(sb.toString(), "utf-8").toUpperCase();
		return sign;
	}
	
	public static void main(String args[]){
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", "wx791233ecd4e71c3f");
		parameters.put("attach", "91_40090_3999");
		parameters.put("mch_id", "1220845901");
		parameters.put("openid", "oc9Xijt__DS0SyGDGqGnUPxSkDWQ");
		parameters.put("cash_fee", "7600");
		parameters.put("fee_type", "CNY");
		parameters.put("time_end", "20150831172810");
		parameters.put("bank_type", "BOC_DEBIT");
		parameters.put("nonce_str", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS");
		parameters.put("total_fee", "7600");
		parameters.put("trade_type", "JSAPI");
		parameters.put("result_code", "SUCCESS");
		parameters.put("return_code", "SUCCESS");
		parameters.put("is_subscribe", "Y");
		parameters.put("out_trade_no", "2015083100400904657685188272");
		parameters.put("transaction_id", "1007950222201508310755172159");
		
		String sign = createSign(parameters,"e0cf8509911342e029ab77fa1a513aeb");
//		63608DE4C3952EF1E165EFADBD869B01
//		63608DE4C3952EF1E165EFADBD8
		System.out.println("sign="+sign);
		
	}
}
