package com.mockuai.tradecenter.core.service.action.payment.client.alipay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mockuai.tradecenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.util.AlipaySubmit;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.PaymentUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;
@Service
public class AlipayClientForWap extends ClientExecutorAbstract  {

	@Autowired
	AppManager appManager;
	
	@Autowired
	OrderItemManager orderItemManager;
	
	private static final Logger log = LoggerFactory.getLogger(AlipayClientForWap.class);

	private Map<String, String> getMockAlipayParams(String bizName,OrderDO orderDO, Map<String, BizPropertyDTO> bizPropertyMap,String appKey)
			throws Exception {
		BizPropertyDTO returnUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_RETURN_URL);
		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_NOTIFY_URL);

		if (null == returnUrlAppProperty || null == notifyUrlAppProperty) {
			throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("service", "alipay.wap.create.direct.pay.by.user");
		paramMap.put("partner", TradeUtil.getAlipayPartnerId(bizPropertyMap));
		paramMap.put("seller_id", TradeUtil.getAlipaySellerId(bizPropertyMap));
		paramMap.put("_input_charset", com.alipay.config.AlipayConfig.input_charset);
		paramMap.put("payment_type", "1");
		paramMap.put("notify_url", notifyUrlAppProperty.getValue());
		// 设置参数
		String orderUid = "" + orderDO.getSellerId() + "_" + orderDO.getUserId() + "_" + orderDO.getId();

		
		String alipayReturnUrl = TradeUtil.getReturnUrl(returnUrlAppProperty.getValue(), orderDO);

		
		paramMap.put("return_url", alipayReturnUrl);
//		paramMap.put("return_url", "http://192.168.31.182:8090/trade/order/payment/callback/alipay_notify");
		
		paramMap.put("out_trade_no", orderDO.getOrderSn());
		paramMap.put("subject", bizName+"商品");
		paramMap.put("total_fee", String.format("%.2f", ((double) (orderDO.getTotalAmount()) / 100)));
		paramMap.put("body", bizName+"商品");
		paramMap.put("it_b_pay", "30m");
//		paramMap.put("extra_common_param", "测试");
		return paramMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {
		String bizCode = (String) context.get("bizCode");
		int paymentId = 4;
		OrderDO orderDO = (OrderDO)context.get("orderDO");
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();
		if (orderDO.getPaymentId() != null) {
			paymentId = orderDO.getPaymentId().intValue();
		}

		Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		log.info("[PAYMENT_TRACE] bizPropertyMap:{}, bizCode:{}", JsonUtil.toJson(bizPropertyMap), bizCode);
		if (null == bizPropertyMap) {
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}
		
		String bizName = (String) context.get("bizName");

		String appKey = (String)context.get("appKey");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		try{
			paramMap = getMockAlipayParams(bizName,orderDO, bizPropertyMap,appKey);
			// 建立请求
			String sHtmlText = AlipaySubmit.buildRequest(paramMap, "post", "确认", TradeUtil.getMchPrivateKey(bizPropertyMap));
			paymentUrlDTO.setRequestMethod(2);
			paymentUrlDTO.setPayType(paymentId);
			paramMap = new HashMap<String, String>();
			paramMap.put("request_form", sHtmlText);
			paymentUrlDTO.setParams(paramMap);
		}catch(Exception e){
			log.error("alipayClientForWap getMockAlipayParams error",e);
			throw new TradeException("alipay for wap getPaymentUrl error");
		}
		
		return paymentUrlDTO;
	}

}
