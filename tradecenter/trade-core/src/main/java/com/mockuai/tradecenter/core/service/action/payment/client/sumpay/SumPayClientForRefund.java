package com.mockuai.tradecenter.core.service.action.payment.client.sumpay;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.util.AlipaySubmit;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.util.MoneyUtil;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.DateUtil;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;
@Service
public class SumPayClientForRefund  extends ClientExecutorAbstract {

	@Autowired
	AppManager appManager;
	
	@Autowired
	OrderItemManager orderItemManager;
	
	private static final Logger log = LoggerFactory.getLogger(SumPayClientForRefund.class);

	private Map<String, String> getSumpayRequestParams(Map<String, BizPropertyDTO> bizPropertyMap,
			String refundOrderId,Long amount,String oriderUid,String terminalType)
			throws Exception {
		BizPropertyDTO refundNotifyUrlAppProperty = bizPropertyMap
				.get(BizPropertyKey.SUMPAY_REFUND_NOTIFY_URL);

		if (null == refundNotifyUrlAppProperty) {
			throw new TradeException("refundNotifyUrlAppProperty config not exist");
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("version","1.0");
		
		paramMap.put("service","sumpay.trade.order.refund");
		
		
		paramMap.put("format","JSON");
		
		paramMap.put("app_id", TradeUtil.getSumpayMchId(bizPropertyMap));
		
		paramMap.put("timestamp", DateUtil.getDateTime("yyyyMMddHHmmss", new Date()));
		//TODO ...
		paramMap.put("terminal_type",terminalType);
		
		paramMap.put("terminal_info",terminalType);
		
		paramMap.put("mer_id", TradeUtil.getSumpayMchId(bizPropertyMap));
		
		paramMap.put("refund_no", refundOrderId);
		
		paramMap.put("refund_time", DateUtil.getDateTime("yyyyMMddHHmmss", new Date()));
		paramMap.put("order_no", oriderUid);
		paramMap.put("refund_amt", MoneyUtil.getMoneyStr(amount));
		paramMap.put("notify_url", refundNotifyUrlAppProperty.getValue());
//		paramMap.put("notify_url", "http://192.168.31.181:8090/trade/order/payment/callback/alipay_notify");

		
		String mchPrivateKey = TradeUtil.getSumpayPrivateKey(bizPropertyMap);
		
		String sign = AlipaySubmit.buildRequestMysign(paramMap,mchPrivateKey);
		paramMap.put("sign",sign);
		
		return paramMap;
	}

	@SuppressWarnings("unchecked")
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

		RefundOrderItemDTO refundOrderItemDTO = (RefundOrderItemDTO) context.get("refundOrderItemDTO");
		
		Long refundAmount = (Long) context.get("refundAmount");
		
		Map<String, String> param;
		try {
//			终端类型：web：PC方式;wap：手机WAP; mobile：手机客户端应用方式
			String terminalType = "mobile";
			if(orderDO.getPaymentId()==8)
				terminalType = "wap";
			
		    String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
			param = getSumpayRequestParams(bizPropertyMap,refundOrderItemDTO.getOrderItemId()+"",refundAmount,
					orderUid,terminalType);
			String response = HttpUtil.post("http://open.sumpay.cn/api.htm", param);
			
			log.info("request:"+param.toString());
			
			if(StringUtils.isBlank(response)){
				throw new Exception("sumpay refund response is blank");
			}
			log.info("response:"+response);
			JSONObject  jsonObject = JSONObject.parseObject(response);
			log.info("respMap:"+jsonObject);
			String resp_code = jsonObject.getString("resp_code");
			String resp_msg = jsonObject.getString("resp_msg");
			if(!resp_code.equals("000000")){
				throw new Exception(resp_msg);
			}
		} catch (Exception e) {
			log.error("sumpay refund error",e);
			throw new TradeException(e.getMessage());
		}
		return paymentUrlDTO;
		
		
		
	}

}
