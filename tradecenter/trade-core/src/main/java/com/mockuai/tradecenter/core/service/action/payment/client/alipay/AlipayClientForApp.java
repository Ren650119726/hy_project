package com.mockuai.tradecenter.core.service.action.payment.client.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.listener.OrderListenerAbstract;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.SignUtils;
import com.mockuai.tradecenter.core.util.TradeUtil;
@Service
public class AlipayClientForApp extends  ClientExecutorAbstract {

	private static final Logger log = LoggerFactory.getLogger(AlipayClientForApp.class);
	  

	private Map<String, String> getMockAlipayParams(String bizName,OrderDO orderDO, Map<String, BizPropertyDTO> bizPropertyMap)
			throws Exception {
		BizPropertyDTO returnUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_RETURN_URL);
		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_NOTIFY_URL);

		if (null == returnUrlAppProperty || null == notifyUrlAppProperty) {
			throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
		}
		 Map<String, String> paramMap = new TreeMap<String, String>();
         paramMap.put("service", "mobile.securitypay.pay");
         paramMap.put("partner", TradeUtil.getAlipayPartnerId(bizPropertyMap));
         paramMap.put("notify_url", notifyUrlAppProperty.getValue());
         // 设置参数
         String orderUid = ""+orderDO.getSellerId()+"_"+orderDO.getUserId()+"_"+orderDO.getId();
       
         
         paramMap.put("return_url", TradeUtil.getReturnUrl(returnUrlAppProperty.getValue(),orderDO));
         paramMap.put("_input_charset", "utf-8");
         paramMap.put("subject", bizName+"商品");//TODO 待重构，组成规则为（应用名称＋‘商品’）
         paramMap.put("body", bizName+"商品");
         //TODO 支付金额的格式需要充分测试
         paramMap.put("total_fee", String.format("%.2f", ((double)(orderDO.getTotalAmount())/100)));
         paramMap.put("out_trade_no", orderDO.getOrderSn());
         paramMap.put("payment_type", "1");
         paramMap.put("seller_id", TradeUtil.getAlipaySellerId(bizPropertyMap));
         paramMap.put("it_b_pay", "30m");

         StringBuilder paramSb = new StringBuilder();
         for (Entry<String, String> entry : paramMap.entrySet()) {
             paramSb.append((String) entry.getKey());
             paramSb.append("=");
             paramSb.append("\"");
             paramSb.append((String) entry.getValue());
             paramSb.append("\"");
             paramSb.append("&");
         }

         paramSb.deleteCharAt(paramSb.length() - 1);
         System.out.println(new StringBuilder().append("paramSb:").append(paramSb.toString()).toString());
         String signStr = SignUtils.sign(paramSb.toString(), TradeUtil.getMchPrivateKey(bizPropertyMap));
         try {
			signStr = URLEncoder.encode(signStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error("alipayClientForApp getMockAlipayParams UnsupportedEncodingException",e);
			throw new TradeException(ResponseCode.SYS_E_DEFAULT_ERROR,e.getMessage());
		}
         String paramStr = new StringBuilder().append(paramSb.toString()).append("&sign_type=\"RSA\"&sign=\"").append(signStr).append("\"").toString();
         Map params = new HashMap();
         params.put("param", paramStr);

		return params;
	}

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
		Map<String, String> paramMap = new HashMap<String, String>();
		try{
			paramMap = getMockAlipayParams(bizName,orderDO, bizPropertyMap);
		}catch(Exception e){
			log.error("alipayClientForApp getMockAlipayParams error",e);
			throw new TradeException("alipay for app getPaymentUrl error");
		}
		
		 paymentUrlDTO.setRequestMethod(1);
         paymentUrlDTO.setParams(paramMap);
         paymentUrlDTO.setPayType(1);
     	 paymentUrlDTO.setPayAmount(orderDO.getTotalAmount());

		return paymentUrlDTO;
	}

}
