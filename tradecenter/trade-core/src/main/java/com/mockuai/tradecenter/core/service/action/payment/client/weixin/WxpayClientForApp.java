package com.mockuai.tradecenter.core.service.action.payment.client.weixin;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.CacheManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;

@Service
public class WxpayClientForApp  extends ClientExecutorAbstract {
	private static final Logger log = LoggerFactory.getLogger(WxpayClientForApp.class);
	@Resource
	private CacheManager cacheManager;
	
	public static void main(String args[]){
		WxpayClientForApp wxpayClient = new WxpayClientForApp();
		String reqUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		try {
			
			String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
			
			
			Map paramMap = new TreeMap();
			paramMap.put("appid", "wx8371d08a66595996");
			paramMap.put("mch_id", "1279647101");
			paramMap.put("body", "商品");
			paramMap.put("nonce_str", nonceStr);
			paramMap.put("out_trade_no", "20151211210001");

			paramMap.put("total_fee", "" + 1);
			paramMap.put("spbill_create_ip", "183.157.67.92");// TODO ip确认
			// paramMap.put("notify_url",
			// "http://api.mockuai.com/trade/order/payment/callback/wechat_notify");
			paramMap.put("notify_url", "http://74.207.229.184://notify");
			paramMap.put("trade_type", "APP");
//			paramMap.put("attach", attach);
			paramMap.put("sign", TradeUtil.getWxParamSign(paramMap, "zaqxswcdevfr12345678998765432100"));

			System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

			String xmlData = XmlUtil.map2XmlStr(paramMap);

			// FIXME 注意，这里必须要对转出来的xmlData进行ISO8859-1编码，否则会报签名错误
			String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
			System.out.println(new StringBuilder().append("response=").append(response).toString());
		} catch (Exception e) {
			log.error("WxpayClientForApp getWxPrepayId error",e);
		}
		
		
	}

	private Map<String, String> getWxPrepayId(String bizName,String appId, String partnerId, String partnerKey, String noncestr,
			String outTradeNo, String attach, OrderDO orderDO, BizPropertyDTO notifyDTO) {
		String reqUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		try {
			Map paramMap = new TreeMap();
			paramMap.put("appid", appId);
			paramMap.put("mch_id", partnerId);
			paramMap.put("body", bizName+"商品");
			paramMap.put("nonce_str", noncestr);
			paramMap.put("out_trade_no", outTradeNo);

			paramMap.put("total_fee", "" + orderDO.getTotalAmount());
			paramMap.put("spbill_create_ip", "183.157.67.92");// TODO ip确认
			// paramMap.put("notify_url",
			// "http://api.mockuai.com/trade/order/payment/callback/wechat_notify");
			paramMap.put("notify_url", notifyDTO.getValue());
			paramMap.put("trade_type", "APP");
			paramMap.put("attach", attach);
			paramMap.put("sign", TradeUtil.getWxParamSign(paramMap, partnerKey));

			System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

			String xmlData = XmlUtil.map2XmlStr(paramMap);

			// FIXME 注意，这里必须要对转出来的xmlData进行ISO8859-1编码，否则会报签名错误
			String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
			System.out.println(new StringBuilder().append("response=").append(response).toString());
			return XmlUtil.xmlStr2Map(response);
		} catch (Exception e) {
			log.error("WxpayClientForApp getWxPrepayId error",e);
			return null;
		}
		
	}

	private String getWxAccessToken(String appId, String secret) {
		// TODO access_token缓存逻辑实现
		// 先查询缓存，如果缓存命中，则直接返回
		String accessToken = (String) cacheManager.get("accessToken_" + appId);
		if (accessToken != null) {
			log.info("getWxAccessToken catch the cache, accessToken:{}", accessToken);
			return accessToken;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "client_credential");
		params.put("appid", appId);
		params.put("secret", secret);
		String response = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token", params);
		Map<String, String> respMap = JsonUtil.parseJson(response, Map.class);
		accessToken = respMap.get("access_token");
		if (StringUtils.isNotBlank(accessToken)) {
			// 缓存下accessToken
			cacheManager.set("accessToken_" + appId, 7200, accessToken);
		}
		return accessToken;
	}


	private String getWxJsTicket(String appId, String secret) {
		// 先查询缓存，如果缓存命中，则直接返回
		String wxJsTicket = (String) cacheManager.get("wxJsTicket_" + appId);
		if (wxJsTicket != null) {
			log.info("getWxJsTicket catch the cache, wxJsTicket:{}", wxJsTicket);
			return wxJsTicket;
		}
		long startTime = System.currentTimeMillis();
		String accessToken = getWxAccessToken(appId, secret);// 先拿个accessToken
		log.info("getWxAccessToken, cost:{}", (System.currentTimeMillis() - startTime));
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		params.put("type", "jsapi");
		String response = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket", params);
		Map<String, String> respMap = JsonUtil.parseJson(response, Map.class);
		wxJsTicket = respMap.get("ticket");
		if (StringUtils.isNotBlank(accessToken)) {
			// 缓存下accessToken
			cacheManager.set("wxJsTicket_" + appId, 7200, wxJsTicket);
		}
		return wxJsTicket;
	}

	private Map<String, String> getWxPayParam( String bizName,OrderDO orderDO, Map<String, BizPropertyDTO> bizPropertyMap) throws Exception {
		String timestamp = new StringBuilder().append("").append(System.currentTimeMillis() / 1000L).toString();
		BizPropertyDTO notifyDTO = bizPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.WECHAT_NOTIFY_URL);
        
		log.info(" notifyDTO : " + JSONObject.toJSONString(notifyDTO));
		
		if(null==notifyDTO){
    		throw new TradeException("notifyUrlAppProperty is null");
    	}
		
		String appId = TradeUtil.getWxAppPayAppId(bizPropertyMap);

		String partnerId = TradeUtil.getWxAppPayPartnerId(bizPropertyMap);

		String partnerKey = TradeUtil.getWxAppPayPartnerKey(bizPropertyMap);

		String appSecret = TradeUtil.getWxAppPayAppsecret(bizPropertyMap);

		String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";

		String outTradeNo = orderDO.getOrderSn();

		String orderUid = "" + orderDO.getSellerId() + "_" + orderDO.getUserId() + "_" + orderDO.getId();

		String attachData = orderUid;
		
		
		Map resMap = getWxPrepayId(bizName,appId, partnerId, partnerKey, nonceStr, outTradeNo, attachData, orderDO, notifyDTO);
		if (resMap.containsKey("prepay_id") == false) {// 获取prepayId失败
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}

		Map paramMap = new TreeMap();
		paramMap.put("appid", appId);
		paramMap.put("prepayid", resMap.get("prepay_id"));
		paramMap.put("partnerid", partnerId);
		paramMap.put("package", "Sign=WXPay");
		paramMap.put("noncestr", resMap.get("nonce_str"));
		paramMap.put("timestamp", timestamp);

		paramMap.put("sign", TradeUtil.getWxParamSign(paramMap, partnerKey));

		return paramMap;
	}

	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {
		// TODO Auto-generated method stub
		String bizCode = (String) context.get("bizCode");

		
		Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		if (null == bizPropertyMap) {
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}

		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();
		OrderDO orderDO = (OrderDO) context.get("orderDO");
		if (null == orderDO) {
			throw new TradeException("orderDO is null");
		}
		String bizName = (String) context.get("bizName");
		try{
			Map paramMap = getWxPayParam(bizName,orderDO,
					bizPropertyMap);
	        paymentUrlDTO.setRequestMethod(2);
	        paymentUrlDTO.setPayType(2);
	        paymentUrlDTO.setParams(paramMap);
	        paymentUrlDTO.setPayAmount(orderDO.getTotalAmount());
	        return paymentUrlDTO;
		}catch(Exception e){
			log.error("WxpayClientForApp getWxPayParam error", e);
			throw new TradeException("wxpay for app getPaymentUrl error");
		}
		

		
	}

}
