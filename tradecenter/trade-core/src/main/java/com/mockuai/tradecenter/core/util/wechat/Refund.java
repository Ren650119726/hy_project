package com.mockuai.tradecenter.core.util.wechat;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;

public class Refund {
	
	private void downloadDaily(){
	String out_refund_no = "1006600348201510201262687231";// 退款单号
		
		String nonce_str = "4232343765";// 随机字符串
		
		String appid = "wx1798992a7488963c"; //微信公众号apid
		String mch_id = "1247585201";  //微信商户id
		String partnerkey = "e0cf8509911342e029ab77fa1a513aeb";//商户平台上的那个KEY
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("out_refund_no", "1006600348201510201262687231");
		packageParams.put("nonce_str", nonce_str);
		

			String reqUrl = "https://api.mch.weixin.qq.com/pay/downloadbill";
			try {
				Map paramMap = new TreeMap();
				paramMap.put("appid", appid);
				paramMap.put("mch_id", mch_id);
				paramMap.put("nonce_str", nonce_str);
//				paramMap.put("bill_date", DateUtil.convertDateToString("yyyyMMdd",DateUtil.getRelativeDate(new Date(), -1)));
				paramMap.put("bill_date", "20151020");
				paramMap.put("stamp", new Date().getTime()+"");
				paramMap.put("bill_type", "REFUND");

				paramMap.put("sign", TradeUtil.getWxParamSign(paramMap, partnerkey));

				System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

				String xmlData = XmlUtil.map2XmlStr(paramMap);

				// FIXME 注意，这里必须要对转出来的xmlData进行ISO8859-1编码，否则会报签名错误
				String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
				
				System.out.println("response:"+response);
				
				Map<String, String> respMap = null;
				try{
					respMap = XmlUtil.xmlStr2Map(response);
				}catch(Exception e){
				}
//				System.out.println("return_code:"+respMap.get("return_code"));
				if(null!=respMap && null!= respMap.get("return_code")){
						return ;
				}
					String lines[] = response.split("\n");
					if(lines.length>=4){
						for(int i=1;i<=lines.length-3;i++){
							String line = lines[i];
							System.out.println("line==="+line);
							String fileds[] = line.split("\\,");
							String refundStatus = fileds[21].substring(1,fileds[21].length()-1);
							String mchRefundId = fileds[17].substring(1, fileds[17].length()-1);
							System.out.println("refundStatus:"+refundStatus);
							System.out.println("mchRefundId:"+mchRefundId);
						}
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	private void singleQuery(){

		String out_refund_no = "1006600348201510201262687231";// 退款单号
		
		String nonce_str = "4232343765";// 随机字符串
		
		String appid = "wx1798992a7488963c"; //微信公众号apid
		String mch_id = "1247585201";  //微信商户id
		String partnerkey = "e0cf8509911342e029ab77fa1a513aeb";//商户平台上的那个KEY
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("out_refund_no", "1006600348201510201262687231");
		packageParams.put("nonce_str", nonce_str);
		

			String reqUrl = "https://api.mch.weixin.qq.com/pay/refundquery";
			try {
				Map paramMap = new TreeMap();
				paramMap.put("appid", appid);
				paramMap.put("mch_id", mch_id);
				paramMap.put("nonce_str", nonce_str);
				paramMap.put("out_refund_no", out_refund_no);

				paramMap.put("sign", TradeUtil.getWxParamSign(paramMap, partnerkey));

				System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

				String xmlData = XmlUtil.map2XmlStr(paramMap);

				// FIXME 注意，这里必须要对转出来的xmlData进行ISO8859-1编码，否则会报签名错误
				String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
				
				System.out.println("response:"+response);
				
				Map<String, String> respMap = XmlUtil.xmlStr2Map(response);
				if (null != respMap.get("return_code")) {
					
					String refund_status_0 = respMap.get("refund_status_0");
					System.out.println("refund_status_0:"+refund_status_0);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	
	}
	
	
	public void wechatRefund() {
		//api地址：http://mch.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
		String out_refund_no = "1006600348201510201262687231";// 退款单号
		String out_trade_no = "";// 订单号
		String total_fee = "1";// 总金额
		String transaction_id = "1006600348201510201262687231";
		String refund_fee = "1";// 退款金额
		String nonce_str = "4232343765";// 随机字符串
		String appid = "wx1798992a7488963c"; //微信公众号apid
		String appsecret = "9290c6da2d77844711101fab8dc455a8"; //微信公众号appsecret
		String mch_id = "1247585201";  //微信商户id
		String op_user_id = "1247585201";//就是MCHID
		String partnerkey = "e0cf8509911342e029ab77fa1a513aeb";//商户平台上的那个KEY
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
//		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);
		packageParams.put("transaction_id", transaction_id);

		RequestHandler reqHandler = new RequestHandler(
				null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
				+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
				+ "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>"
//				+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
					+ "<transaction_id>" + transaction_id + "</transaction_id>"
				+ "<out_refund_no>" + out_refund_no + "</out_refund_no>"
				+ "<total_fee>" + total_fee + "</total_fee>"
				+ "<refund_fee>" + refund_fee + "</refund_fee>"
				+ "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		try {
			String s= ClientCustomSSL.doRefund(createOrderURL, xml,mch_id,"");
			System.out.println(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Refund refund=new Refund();
//		refund.wechatRefund();
		
		refund.singleQuery();
		
//		refund.downloadDaily();
	}
}
