package com.mockuai.tradecenter.core.service.action.payment.client.sumpay;

import java.util.Date;
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
import com.mockuai.tradecenter.core.util.Base64;
import com.mockuai.tradecenter.core.util.DateUtil;
import com.mockuai.tradecenter.core.util.PaymentUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;
@Service
public class SumPayClientForApp  extends ClientExecutorAbstract {

	@Autowired
	AppManager appManager;
	
	@Autowired
	OrderItemManager orderItemManager;
	
	private static final Logger log = LoggerFactory.getLogger(SumPayClientForApp.class);

	private Map<String, String> getSumpayRequestParams(String bizName,OrderDO orderDO, Map<String, BizPropertyDTO> bizPropertyMap,String appKey)
			throws Exception {
		BizPropertyDTO returnUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.SUMPAY_RETURN_URL);
		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.SUMPAY_NTIFY_URL);

		if (null == returnUrlAppProperty || null == notifyUrlAppProperty) {
			throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		
		
		Date curDate = new Date(System.currentTimeMillis());
	    paramMap.put("version", "1.0");
	    paramMap.put("service","sumpay.mobile.trade.order.apply");
	    paramMap.put("format","JSON");
	    paramMap.put("timestamp", DateUtil.getDateTime("yyyyMMddHHmmss", curDate));
	    paramMap.put("terminal_type", "mobile");
		
		paramMap.put("sign_type","RSA");
		
		
		
		paramMap.put("mer_id",TradeUtil.getSumpayMchId(bizPropertyMap) );
		paramMap.put("app_id", TradeUtil.getSumpayMchId(bizPropertyMap));
		paramMap.put("rec_cstno", TradeUtil.getSumpayMchId(bizPropertyMap));
		paramMap.put("cstno", orderDO.getUserId()+"");
		
		 // 设置参数
        String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
//		paramMap.put("order_no", orderDO.getOrderSn());
        paramMap.put("order_no", orderUid);
		paramMap.put("order_time", DateUtil.getDateTime("yyyyMMddHHmmss", orderDO.getGmtCreated()));
		//setCur_type
		paramMap.put("cur_type", "CNY");
		
		paramMap.put("order_amt", MoneyUtil.getMoneyStr(orderDO.getTotalAmount()));
//		paramMap.put("notify_url", notifyUrlAppProperty.getValue());
//		paramMap.put("notify_url", "http://192.168.31.181:8090/trade/order/payment/callback/alipay_notify");
		paramMap.put("notify_url", notifyUrlAppProperty.getValue());

		
		
		paramMap.put("goods_name", bizName+"商品");
		paramMap.put("remark", orderUid);
		
		String mchPrivateKey = TradeUtil.getSumpayPrivateKey(bizPropertyMap);
		String sign = AlipaySubmit.buildRequestMysign(paramMap,mchPrivateKey);
		paramMap.put("sign",sign);
		paramMap.put("mch_private_key",mchPrivateKey);
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
			
			paymentUrlDTO.setRequestMethod(7);
			paymentUrlDTO.setPayType(7);
			paymentUrlDTO.setParams(paramMap);
		}catch(Exception e){
			log.error("alipayClientForWap getMockAlipayParams error",e);
			throw new TradeException("alipay for wap getPaymentUrl error");
		}
		
		return paymentUrlDTO;
	}

}
