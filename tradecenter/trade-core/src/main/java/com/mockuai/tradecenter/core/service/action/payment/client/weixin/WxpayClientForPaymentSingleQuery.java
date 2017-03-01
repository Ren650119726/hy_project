package com.mockuai.tradecenter.core.service.action.payment.client.weixin;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.manager.RefundOrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;

/**
 * 微信支付单笔查询
 *
 */
@Service
public class WxpayClientForPaymentSingleQuery  extends ClientExecutorAbstract {
	private static final Logger log = LoggerFactory.getLogger(WxpayClientForPaymentSingleQuery.class);

	@Autowired
	private RefundOrderManager refundOrderManager;

	@Autowired
	private RefundOrderItemManager refundOrderItemManager;

	private boolean  orderQuery(Map<String, BizPropertyDTO> bizPropertyMap, String out_trade_no) {
		boolean isPaid = false;
		try {
			String nonce_str = "4232343765";// 随机字符串
			String appid = TradeUtil.getWxWapPayAppId(bizPropertyMap); // 微信公众号apid
			String partnerId = TradeUtil.getWxWapPayPartnerId(bizPropertyMap);
			String mch_id = partnerId; // 微信商户id
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			String partnerKey = TradeUtil.getWxWapPayPartnerKey(bizPropertyMap);

			Map paramMap = new TreeMap();
			paramMap.put("appid", appid);
			paramMap.put("mch_id", mch_id);
			paramMap.put("nonce_str", nonce_str);
			paramMap.put("out_trade_no", out_trade_no); //商户订单号

			paramMap.put("sign", TradeUtil.getWxParamSign(paramMap, partnerKey));

			System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

			String reqUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
			String xmlData = XmlUtil.map2XmlStr(paramMap);
			log.info("wxpay payment single query request:"+xmlData);
			String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
			log.info("wxpay payment query refund response:" + response);
			Map<String, String> respMap = XmlUtil.xmlStr2Map(response);
			if (null != respMap.get("return_code")) {

				String result_code = respMap.get("result_code");
				String return_code = respMap.get("return_code");
				String trade_state = respMap.get("trade_state");
				
				if (null!=result_code&&result_code.equalsIgnoreCase("SUCCESS")  &&  return_code!=null &&
						return_code.equalsIgnoreCase("SUCCESS") &&
						trade_state != null &&
						trade_state.equalsIgnoreCase("SUCCESS")//代表支付成功
						) {
					isPaid = true;
				}
			}

		} catch (Exception e) {
			log.error("WxpayClientForRefundSingleQuery refundQuery error ",e);
		}
		
		return isPaid;

	}

	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {

		String bizCode = (String) context.get("bizCode");
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();

		Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		if (null == bizPropertyMap) {
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}

		String out_trade_no = (String) context.get("out_trade_no");
		try {

			boolean isPaid = orderQuery(bizPropertyMap, out_trade_no);
			paymentUrlDTO.setPaid(isPaid);
		} catch (Exception e) {
			log.error("wechat refund error", e);
			throw new TradeException(" refund error ");
		}

		return paymentUrlDTO;

	}

}
