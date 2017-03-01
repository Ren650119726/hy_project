package com.mockuai.tradecenter.core.service.action.payment.client.alipay;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.util.AlipaySubmit;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.itemcenter.client.SupplierClient;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.PaymentDeclareDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderPaymentManager;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;

@Service
public class AlipayClientForCustomDeclare extends ClientExecutorAbstract {

	private static final Logger log = LoggerFactory.getLogger(AlipayClientForCustomDeclare.class);

	@Resource
	private OrderPaymentManager orderPaymentManager;

	@Resource
	private OrderItemManager orderItemMng;

	@Autowired
	private AppManager appManager;

	@Autowired
	SupplierClient supplierClient;

	private Map<String, String> getMockAlipayParams(OrderDO orderDO, Map<String, BizPropertyDTO> bizPropertyMap,
			String appKey,PaymentDeclareDTO paymentDeclareDTO)
			throws Exception {

		Map<String, String> paramMap = new TreeMap<String, String>();
		paramMap.put("service", "alipay.acquire.customs");
		paramMap.put("partner", TradeUtil.getAlipayPartnerId(bizPropertyMap));

		paramMap.put("_input_charset", "utf-8");

		OrderPaymentDO orderPayment = orderPaymentManager.getOrderPayment(orderDO.getId(), orderDO.getUserId());

		String outTradeNo = orderPayment.getOutTradeNo();

		// 设置参数
		String orderUid = "" + orderDO.getSellerId() + "_" + orderDO.getUserId() + "_" + orderDO.getId();
		paramMap.put("out_request_no", orderUid);
		// 支付宝流水号
		paramMap.put("trade_no", outTradeNo);
		paramMap.put("merchant_customs_code",paymentDeclareDTO.getMchCustomsCode());
		paramMap.put("merchant_customs_name",paymentDeclareDTO.getMchCustomsName());
		paramMap.put("amount", String.format("%.2f", ((double) (orderDO.getTotalAmount()) / 100)));
		paramMap.put("customs_place", paymentDeclareDTO.getCustomsPlace());

		return paramMap;
	}

	@Override
	public void paymentDeclare(OrderDO orderDO, String appKey,PaymentDeclareDTO paymentDeclareDTO) throws TradeException {


		String bizCode = orderDO.getBizCode();


		BizInfoDTO bizInfo = null;
		bizInfo = appManager.getBizInfo(bizCode);
		if (null == bizInfo) {
			log.error("bizInfo is null" + bizCode);
			throw new TradeException(bizCode + " bizInfo is null");
		}
		if (null == bizInfo.getBizPropertyMap()) {
			log.error("bizPropertyMap is null,  bizCode:{}", bizCode);
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}

		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			paramMap = getMockAlipayParams(orderDO, bizInfo.getBizPropertyMap(),appKey,paymentDeclareDTO);
		} catch (Exception e) {
			log.error("alipayClientForCustomDeclare getMockAlipayParams error", e);
			throw new TradeException("alipayClientForCustomDeclare getMockAlipayParams error" + e.getMessage());
		}
		
		Map<String, String> param = AlipaySubmit.buildRequestPara(paramMap,TradeUtil.getMchPrivateKey(bizInfo.getBizPropertyMap()));

		log.info("alipay custom declare request:" + JsonUtil.toJson(param));
		String response = HttpUtil.post("https://mapi.alipay.com/gateway.do?_input_charset=utf-8", param);
		Map<String, String> respMap = XmlUtil.xmlStr2Map(response);
		log.info("alipay custom declare respMap:" + respMap);
		String is_success = respMap.get("is_success");
		if (!is_success.equals("T")) {
			String error = respMap.get("error");
			throw new TradeException("alipay custom declare error:" + error);
		}

	}

}
