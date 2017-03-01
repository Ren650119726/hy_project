package com.mockuai.tradecenter.core.service.action.payment.client.alipay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.util.AlipaySubmit;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;

/**
 * 
 * 
 *
 */
@Service
public class AlipayClientForPaymentSingleQuery extends ClientExecutorAbstract  {

	private static final Logger log = LoggerFactory.getLogger(AlipayClientForPaymentSingleQuery.class);

	private Map<String, String> getMockAlipayParams(Map<String, BizPropertyDTO> bizPropertyMap,
			String outTradeNo)
			throws Exception {

		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "single_trade_query");
		sParaTemp.put("partner", TradeUtil.getAlipayPartnerId(bizPropertyMap));
		sParaTemp.put("_input_charset", "utf-8");

		sParaTemp.put("out_trade_no",outTradeNo);
		
		
		return sParaTemp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {
		String bizCode = (String) context.get("bizCode");

		Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		if (null == bizPropertyMap) {
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}
		
		String out_trade_no = (String) context.get("out_trade_no");
		
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();

		try {
			boolean isPaid = false;
			Map<String, String> param = AlipaySubmit.buildRequestPara(getMockAlipayParams(bizPropertyMap,out_trade_no),TradeUtil.getMchPrivateKey(bizPropertyMap));
			
			System.out.println("requestParam:"+param);
			
			String response = HttpUtil.post("https://mapi.alipay.com/gateway.do?_input_charset=utf-8", param);
			Map<String, String> respMap = XmlUtil.xmlStr2Map(response);
			log.info("respMap:"+respMap);
			String is_success = respMap.get("is_success");
			String trade_status = respMap.get("trade_status");
			if(is_success.equals("T")&&(trade_status.equals("TRADE_SUCCESS")||trade_status.equals("TRADE_FINISHED"))){
				isPaid = true;
			}
			paymentUrlDTO.setPaid(isPaid);
			
		} catch (Exception e) {
			log.error("alipay refund error", e);
			throw new TradeException("alipay refund error");
		}
		return paymentUrlDTO;
		
	}

}
