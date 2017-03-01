package com.mockuai.tradecenter.core.service.action.payment.client.lianlianpay;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.vo.PaymentInfo;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.HttpRequestSimple;
import com.mockuai.tradecenter.core.util.LLPayUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;

/**
 * 
 * 退款 无密接口
 *
 */
@Service
public class LianlianpayClientForRefund extends ClientExecutorAbstract  {

	private static final Logger log = LoggerFactory.getLogger(LianlianpayClientForRefund.class);
//	private final static String SERVER = "https://yintong.com.cn/queryapi/";
	private final static String SERVER = "https://yintong.com.cn/traderapi/";

	private String getLianlianpayParams(OrderDO orderDO, Map<String, BizPropertyDTO> bizPropertyMap,
			RefundOrderItemDTO refundOrderItemDTO,OrderPaymentDO orderPaymentDO) throws TradeException{

//		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_REFUND_NOTIFY_URL);		
		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_NOTIFY_URL);		

		if (null == notifyUrlAppProperty) {
			throw new TradeException(" notifyUrlAppProperty is null");
		}

		// 构造支付请求对象
        PaymentInfo paymentInfo = new PaymentInfo();

        paymentInfo.setOid_partner(TradeUtil.getLianlianpayOidPartner(bizPropertyMap));
        paymentInfo.setSign_type("RSA");
        paymentInfo.setNo_refund(refundOrderItemDTO.getOrderId()+"_"+refundOrderItemDTO.getUserId()+"_"+refundOrderItemDTO.getOrderItemId());
        paymentInfo.setDt_refund(DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss"));
        paymentInfo.setMoney_refund(String.format("%.2f", ((double)(refundOrderItemDTO.getRefundAmount())/100)));
        paymentInfo.setNo_order(orderDO.getOrderSn());
        paymentInfo.setOid_paybill(orderPaymentDO.getOutTradeNo());
        paymentInfo.setNotify_url(notifyUrlAppProperty.getValue());

        String privateKey = TradeUtil.getLianlianpayPrikey(bizPropertyMap);
        // 加签名
        String signStr = LLPayUtil.addSign(JSON.parseObject(JSON.toJSONString(paymentInfo)), privateKey);
        paymentInfo.setSign(signStr);
        
        String reqJson = JSON.toJSONString(paymentInfo);
        
		return reqJson;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {
		
		String bizCode = (String) context.get("bizCode");

		Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		if (null == bizPropertyMap) {
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}
		
		OrderDO orderDO = (OrderDO) context.get("orderDO");
		log.info(" lianlian refund orderDO : "+orderDO);
		if (null == orderDO) {
			throw new TradeException("orderDO is null");
		}
		
		OrderPaymentDO orderPaymentDO = (OrderPaymentDO) context.get("orderPaymentDO");
		log.info(" lianlian refund orderPaymentDO : "+orderPaymentDO);
		
		RefundOrderItemDTO refundOrderItemDTO = (RefundOrderItemDTO) context.get("refundOrderItemDTO");
		log.info(" lianlian refund refundOrderItemDTO : "+refundOrderItemDTO);
		
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();
		
		try {

			String reqJson = getLianlianpayParams(orderDO, bizPropertyMap,refundOrderItemDTO,orderPaymentDO);
			 log.info(" refund req reqJson : "+reqJson);
			HttpRequestSimple httpclent = new HttpRequestSimple();
	        String resJson = httpclent.postSendHttp(SERVER + "refund.htm",
	                reqJson);
	        log.info(" refund res resJson : "+resJson);
	        // String resJson = HttpSender.send(reqJson);
//	        TradeRefundCallbackBean res = JSON.parseObject(resJson, TradeRefundCallbackBean.class);			
			
		} catch (Exception e) {
			log.error("alipay refund error", e);
			throw new TradeException("alipay refund error");
		}
		return paymentUrlDTO;
		
	}

}
