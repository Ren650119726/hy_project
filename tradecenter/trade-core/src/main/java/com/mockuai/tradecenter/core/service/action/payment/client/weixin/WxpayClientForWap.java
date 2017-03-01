package com.mockuai.tradecenter.core.service.action.payment.client.weixin;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.BizPropertyDTO;
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
public class WxpayClientForWap  extends ClientExecutorAbstract {
	private static final Logger log = LoggerFactory.getLogger(WxpayClientForWap.class);
	@Resource
	private CacheManager cacheManager;

	private String getWxOpenId(Map<String, BizPropertyDTO> bizPropertyMap, String authCode) throws TradeException {
		// TODO access_token缓存逻辑实现
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "authorization_code");
		params.put("appid", TradeUtil.getWxWapPayAppId(bizPropertyMap));
		params.put("secret", TradeUtil.getWxWapPayAppsecret(bizPropertyMap));
		params.put("code", authCode);
		String response = HttpUtil.get("https://api.weixin.qq.com/sns/oauth2/access_token", params);
		Map<String, String> respMap = JsonUtil.parseJson(response, Map.class);

		log.info("getWxOpenId>>>>>>>>>>>>>>>>response:" + JsonUtil.toJson(respMap));

		return respMap.get("openid");
	}

	private Map<String, String> getWxPrepayIdForH5(String bizName, String appId, String partnerId, String partnerKey,
			String noncestr, String outTradeNo, String attach, String openId, OrderDO orderDO,
			BizPropertyDTO notifyDTO) {
		String reqUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		try {
			long totalFee = orderDO.getTotalAmount();
			Map paramMap = new TreeMap();
			paramMap.put("appid", appId);
			paramMap.put("mch_id", partnerId);
			paramMap.put("body", bizName + "商品");
			paramMap.put("nonce_str", noncestr);
			paramMap.put("out_trade_no", outTradeNo);

			paramMap.put("total_fee", "" + totalFee);
			// TODO 参数配置
			paramMap.put("spbill_create_ip", "183.157.67.92");
			// paramMap.put("notify_url",
			// "http://218.244.147.209:8080/trade/order/payment/callback/wechat_notify");
			paramMap.put("notify_url", notifyDTO.getValue());
			paramMap.put("trade_type", "JSAPI");
			paramMap.put("attach", attach);

			paramMap.put("openid", openId);
			paramMap.put("sign", TradeUtil.getWxParamSign(paramMap, partnerKey));

			System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

			String xmlData = XmlUtil.map2XmlStr(paramMap);

			// FIXME 注意，这里必须要对转出来的xmlData进行ISO8859-1编码，否则会报签名错误
			String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
			log.info("getWxPrepayIdForH5>>>>>>>>>>>>>>>>>>>>>>>>>paramMap:" + JsonUtil.toJson(paramMap));
			log.info("getWxPrepayIdForH5>>>>>>>>>>>>>>>>>>>>>>>>>response:" + response);
			return XmlUtil.xmlStr2Map(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getWxOpenId(String appId, String appSecret, String authCode) {
		// TODO access_token缓存逻辑实现
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "authorization_code");
		params.put("appid", appId);
		params.put("secret", appSecret);
		params.put("code", authCode);
		String response = HttpUtil.get("https://api.weixin.qq.com/sns/oauth2/access_token", params);
		Map<String, String> respMap = JsonUtil.parseJson(response, Map.class);

		log.info("getWxOpenId>>>>>>>>>>>>>>>>response:" + JsonUtil.toJson(respMap));

		return respMap.get("openid");
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

	private Map<String, String> getWxPrepayIdForH5(String appId, String partnerId, String partnerKey, String noncestr,
			String outTradeNo, String attach, String openId, OrderDO orderDO, BizPropertyDTO notifyDTO,
			String bizName) {
		String reqUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		try {
			long totalFee = orderDO.getTotalAmount();
			Map paramMap = new TreeMap();
			paramMap.put("appid", appId);
			paramMap.put("mch_id", partnerId);
			paramMap.put("body", "yangdongxi");
//			paramMap.put("body", bizName + "商品");
			paramMap.put("nonce_str", noncestr);
			paramMap.put("out_trade_no", outTradeNo);

			paramMap.put("total_fee", "" + totalFee);
			// TODO 参数配置
			paramMap.put("spbill_create_ip", "183.157.67.92");
			paramMap.put("notify_url", notifyDTO.getValue());
			paramMap.put("trade_type", "JSAPI");
			paramMap.put("attach", attach);

			paramMap.put("openid", openId);
			paramMap.put("sign", TradeUtil.getWxParamSign(paramMap, partnerKey));

			System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

			String xmlData = XmlUtil.map2XmlStr(paramMap);

			// FIXME 注意，这里必须要对转出来的xmlData进行ISO8859-1编码，否则会报签名错误
			String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
			log.info("getWxPrepayIdForH5>>>>>>>>>>>>>>>>>>>>>>>>>paramMap:" + JsonUtil.toJson(paramMap));
			log.info("getWxPrepayIdForH5>>>>>>>>>>>>>>>>>>>>>>>>>response:" + response);
			return XmlUtil.xmlStr2Map(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getWxPayParamForH5(String bizName, String wxAuthCode, OrderDO orderDO,
			Map<String, BizPropertyDTO> bizPropertyMap) throws Exception {
		BizPropertyDTO notifyDTO = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.WECHAT_NOTIFY_URL);

		if (null == notifyDTO) {
			throw new TradeException("notifyUrlAppProperty is null");
		}
		String timestamp = new StringBuilder().append("").append(System.currentTimeMillis() / 1000L).toString();

		String appId = TradeUtil.getWxWapPayAppId(bizPropertyMap);

		String partnerId = TradeUtil.getWxWapPayPartnerId(bizPropertyMap);

		String partnerKey = TradeUtil.getWxWapPayPartnerKey(bizPropertyMap);

		String appSecret = TradeUtil.getWxWapPayAppsecret(bizPropertyMap);

		String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";

		String outTradeNo = orderDO.getOrderSn();

		String orderUid = "" + orderDO.getSellerId() + "_" + orderDO.getUserId() + "_" + orderDO.getId();

		String attachData = orderUid;

		long startTime = System.currentTimeMillis();

		String openId = getWxOpenId(bizPropertyMap, wxAuthCode);

		long prepayIdStartTime = System.currentTimeMillis();

		Map resMap = getWxPrepayIdForH5(bizName, appId, partnerId, partnerKey, nonceStr, outTradeNo, attachData, openId,
				orderDO, notifyDTO);

		log.info("getWxOpenId_cost:{}, getWxPrepayIdForH5_cost:{}", (prepayIdStartTime - startTime),
				(System.currentTimeMillis() - prepayIdStartTime));
		Map paramMap = new TreeMap();
		paramMap.put("appId", appId);
		if (StringUtils.isBlank((String) resMap.get("prepay_id"))) {
			throw new TradeException("resMap response invalid prepay_id is null");
		}
		if (StringUtils.isBlank((String) resMap.get("nonce_str"))) {
			throw new TradeException("resMap response invalid nonce_str is null ");
		}
		paramMap.put("package", "prepay_id=" + resMap.get("prepay_id"));
		paramMap.put("nonceStr", resMap.get("nonce_str"));
		paramMap.put("timeStamp", timestamp);
		paramMap.put("signType", "MD5");
		paramMap.put("paySign", TradeUtil.getWxParamSign(paramMap, partnerKey));
		long wxTicketStartTime = System.currentTimeMillis();
		String wxJsTicket = getWxJsTicket(appId, appSecret);
		log.info("getWxJsTicket, cost:{}", (System.currentTimeMillis() - wxTicketStartTime));
		paramMap.put("jsapi_ticket", wxJsTicket);

		return paramMap;
	}

	private Map<String, String> getWxPayParamForH5(String appId, String noncestr, String partnerid, String partnerKey,
			String outTradeNo, String attach, String appSecret, String wxAuthCode, OrderDO orderDO,
			BizPropertyDTO notifyDTO, String bizName) throws TradeException {
		String timestamp = new StringBuilder().append("").append(System.currentTimeMillis() / 1000L).toString();

		long startTime = System.currentTimeMillis();
		String openId = getWxOpenId(appId, appSecret, wxAuthCode);
		long prepayIdStartTime = System.currentTimeMillis();
		Map resMap = getWxPrepayIdForH5(appId, partnerid, partnerKey, noncestr, outTradeNo, attach, openId, orderDO,
				notifyDTO, bizName);
		log.info("getWxOpenId_cost:{}, getWxPrepayIdForH5_cost:{}", (prepayIdStartTime - startTime),
				(System.currentTimeMillis() - prepayIdStartTime));
		Map paramMap = new TreeMap();
		paramMap.put("appId", appId);
		if( null ==resMap.get("prepay_id") ){
			throw new TradeException("prepay_id is null");
		}
		paramMap.put("package", "prepay_id=" + resMap.get("prepay_id"));
		paramMap.put("nonceStr", resMap.get("nonce_str"));
		paramMap.put("timeStamp", timestamp);
		paramMap.put("signType", "MD5");
		paramMap.put("paySign", TradeUtil.getWxParamSign(paramMap, partnerKey));
		long wxTicketStartTime = System.currentTimeMillis();
		String wxJsTicket = getWxJsTicket(appId, appSecret);
		log.info("getWxJsTicket, cost:{}", (System.currentTimeMillis() - wxTicketStartTime));
		paramMap.put("jsapi_ticket", wxJsTicket);

		return paramMap;
	}

	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {
		// TODO Auto-generated method stub
		String bizCode = (String) context.get("bizCode");

		String wxAuthCode = (String) context.get("wxAuthCode");
		if (StringUtils.isBlank(wxAuthCode)) {
			throw new TradeException("wxAuthCode is null");
		}
		int paymentId = 5;
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
		try {
			boolean isYangdongxiRequest = TradeUtil.isYangdongxiRequest(bizCode);
			Map paramMap = null;
			if (isYangdongxiRequest) {
				String appId = "wx791233ecd4e71c3f";
				String partnerId = "1220845901";
				String partnerKey = "e0cf8509911342e029ab77fa1a513aeb";
				String appSecret = "bde7a203a8082025ed97b12821a52d20";
				String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";
				String outTradeNo = orderDO.getOrderSn();
				String orderUid = "" + orderDO.getSellerId() + "_" + orderDO.getUserId() + "_" + orderDO.getId();
				String attachData = orderUid;
				BizPropertyDTO notifyUrlAppProperty = bizPropertyMap.get(com.mockuai.tradecenter.core.util.BizPropertyKey.WECHAT_NOTIFY_URL);
				paramMap = getWxPayParamForH5(appId, nonceStr, partnerId, partnerKey, outTradeNo, attachData, appSecret,
						wxAuthCode, orderDO, notifyUrlAppProperty, bizName);
			} else {
				paramMap = getWxPayParamForH5(bizName, wxAuthCode, orderDO, bizPropertyMap);
			}

			paymentUrlDTO.setRequestMethod(2);
			paymentUrlDTO.setPayType(5);
			paymentUrlDTO.setParams(paramMap);
			paymentUrlDTO.setPayAmount(orderDO.getTotalAmount());
			return paymentUrlDTO;
		} catch (Exception e) {
			log.error("WxpayClientForWap getWxPayParam error", e);
			throw new TradeException("wxpay for wap getPaymentUrl error");
		}

	}

}
