package com.mockuai.tradecenter.core.service.action.payment.client.sumpay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.util.AlipaySubmit;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.util.MoneyUtil;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.DateUtil;
import com.mockuai.tradecenter.core.util.PaymentUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;
@Service
public class SumPayClientForWap  extends ClientExecutorAbstract {

	@Autowired
	AppManager appManager;
	
	@Autowired
	OrderItemManager orderItemManager;
	
	private static final Logger log = LoggerFactory.getLogger(SumPayClientForWap.class);

	private Map<String, String> getSumpayRequestParams(String bizName,OrderDO orderDO, Map<String, BizPropertyDTO> bizPropertyMap,String appKey)
			throws Exception {
		BizPropertyDTO returnUrlProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.SUMPAY_RETURN_URL);
		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.SUMPAY_NTIFY_URL);

		if (null == returnUrlProperty || null == notifyUrlAppProperty) {
			throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("version","1.0.1");
		paramMap.put("partnerid",TradeUtil.getSumpayMchId(bizPropertyMap));
		// terminaltype
		
		paramMap.put("terminaltype","WAP");
		// channel
		paramMap.put("channel","2");
		
		//productcode
		paramMap.put("productcode","INSTANT");
		
		// 设置参数
        String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
		//tradeno
		paramMap.put("tradeno",orderUid);
		
		//tradename
		paramMap.put("tradename",bizName+"商品");
		
		//tradetype
		paramMap.put("tradetype","2");
		
		//tradeamount
		paramMap.put("tradeamount",MoneyUtil.getMoneyStr(orderDO.getTotalAmount()));
		
		//traderemark
		paramMap.put("traderemark",orderUid);
		
		
		

		String returnUrl = TradeUtil.getReturnUrl(returnUrlProperty.getValue(), orderDO);

		
		paramMap.put("returnurl", returnUrl);

		paramMap.put("notifyurl", notifyUrlAppProperty.getValue());
		
		paramMap.put("tradetime", DateUtil.getDateTime("yyyyMMddHHmmss", orderDO.getGmtCreated()));
		
//		paramMap.put("tradetimeout", "30");
		
		paramMap.put("consumerid", orderDO.getUserId()+"");
		
		
		
		
		
		return paramMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {
		String bizCode = (String) context.get("bizCode");
		OrderDO orderDO = (OrderDO)context.get("orderDO");
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();

		Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		if (null == bizPropertyMap) {
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}
		
		String bizName = (String) context.get("bizName");

		String appKey = (String)context.get("appKey");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		try{
			paramMap = getSumpayRequestParams(bizName,orderDO, bizPropertyMap,appKey);
			
			
			// 建立请求
			String sHtmlText = AlipaySubmit.buildSumpayRequest(paramMap, "post", "确认", TradeUtil.getSumpayPrivateKey(bizPropertyMap));
			
			paymentUrlDTO.setRequestMethod(8);
			paymentUrlDTO.setPayType(8);
			paramMap = new HashMap<String, String>();
			paramMap.put("request_form", sHtmlText);
			paymentUrlDTO.setParams(paramMap);
			
			
			paymentUrlDTO.setParams(paramMap);
		}catch(Exception e){
			log.error("alipayClientForWap getMockAlipayParams error",e);
			throw new TradeException("alipay for wap getPaymentUrl error");
		}
		
		return paymentUrlDTO;
	}

}
