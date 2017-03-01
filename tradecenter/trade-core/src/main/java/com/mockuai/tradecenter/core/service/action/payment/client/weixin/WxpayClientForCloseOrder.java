package com.mockuai.tradecenter.core.service.action.payment.client.weixin;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

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
import com.mockuai.tradecenter.core.util.TradeCoreConfig;
import com.mockuai.tradecenter.core.util.TradeUtil;

@Service
public class WxpayClientForCloseOrder  extends ClientExecutorAbstract {
	private static final Logger log = LoggerFactory.getLogger(WxpayClientForCloseOrder.class);
	@Resource
	private CacheManager cacheManager;
	
	@Resource
	private TradeCoreConfig tradeCoreConfig;


	private void doClose(Map<String, BizPropertyDTO> bizPropertyMap, String out_trade_no,int paymentId) throws Exception{
			String nonce_str = "4232343765";// 随机字符串
			String appid = TradeUtil.getWxPayAppId(paymentId,bizPropertyMap); // 微信公众号apid
			String partnerId = TradeUtil.getWxPayPartnerId(paymentId,bizPropertyMap);
			String mch_id = partnerId; // 微信商户id
			String partnerKey = TradeUtil.getWxPayPartnerKey(paymentId,bizPropertyMap);

			Map paramMap = new TreeMap();
			paramMap.put("appid", appid);
			paramMap.put("mch_id", mch_id);
			paramMap.put("nonce_str", nonce_str);
			paramMap.put("out_trade_no", out_trade_no);

			paramMap.put("sign", TradeUtil.getWxParamSign(paramMap, partnerKey));

			System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

			String reqUrl = "https://api.mch.weixin.qq.com/pay/closeorder";
			
			String xmlData = XmlUtil.map2XmlStr(paramMap);

			String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
			
			log.info(" do order close response:"+response);
			
			Map<String, String> respMap = XmlUtil.xmlStr2Map(response);
			if (null != respMap.get("return_code")) {

				String result_code = respMap.get("result_code");
				if (null==result_code||!result_code.equalsIgnoreCase("SUCCESS")) {
					//代表失败了
					throw new TradeException(" unpaid order close error ");
				}
			}


	}
	
	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {

		String bizCode = (String) context.get("bizCode");
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();
		Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		if (null == bizPropertyMap) {
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}
		OrderDO orderDO = (OrderDO) context.get("orderDO");
		if (null == orderDO) {
			throw new TradeException("orderDO is null");
		}
		
		String outTradeNo = orderDO.getOrderSn();
		
		
		try {
			doClose(bizPropertyMap,outTradeNo,orderDO.getPaymentId());
		} catch (Exception e) {
			log.error("wechat unpaid order close error", e);
			throw new TradeException(" unpaid order close error ");
		}

		return paymentUrlDTO;

	}

}
