package com.mockuai.tradecenter.core.service.action.payment.client.weixin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.itemcenter.client.SupplierClient;
import com.mockuai.itemcenter.common.domain.dto.SupplierDTO;
import com.mockuai.itemcenter.common.domain.qto.SupplierQTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.domain.PaymentDeclareDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderPaymentManager;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.manager.RefundOrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;


@Service
public class WxpayClientForCustomDeclare  extends ClientExecutorAbstract {
	private static final Logger log = LoggerFactory.getLogger(WxpayClientForCustomDeclare.class);

	
	@Autowired
	private AppManager appManager;
	
	@Resource
	private OrderItemManager orderItemMng;
	
	@Autowired
	SupplierClient supplierClient;
	
	@Resource
	private OrderPaymentManager orderPaymentManager;

	private void doDeclare(Map<String, BizPropertyDTO> bizPropertyMap,OrderDO orderDO,String appKey,PaymentDeclareDTO paymentDeclareDTO ) throws Exception{
			String appid = TradeUtil.getWxPayAppId(orderDO.getPaymentId(),bizPropertyMap); // 微信公众号apid
			String partnerId = TradeUtil.getWxPayPartnerId(orderDO.getPaymentId(),bizPropertyMap);
			String mch_id = partnerId; // 微信商户id
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			String partnerKey = TradeUtil.getWxPayPartnerKey(orderDO.getPaymentId(),bizPropertyMap);

			packageParams.put("appid", appid);
			packageParams.put("mch_id", mch_id);
			packageParams.put("out_trade_no", orderDO.getOrderSn());
			
			OrderPaymentDO orderPayment = orderPaymentManager.getOrderPayment(orderDO.getId(), orderDO.getUserId());

			String outTradeNo = orderPayment.getOutTradeNo();
			
			
			packageParams.put("transaction_id", outTradeNo);
		
			
			packageParams.put("customs", paymentDeclareDTO.getCustomsPlace());
			
			packageParams.put("mch_customs_no", paymentDeclareDTO.getMchCustomsCode());
			
			packageParams.put("sign", TradeUtil.getWxParamSign(packageParams, partnerKey));


			String reqUrl = "https://api.mch.weixin.qq.com/cgi-bin/mch/customs/customdeclareorder";
			String xmlData = XmlUtil.map2XmlStr(packageParams);
			log.info("wxpay custome declare request:"+xmlData);
			String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
			log.info("wxpay custome declare response:" + response);
			Map<String, String> respMap = XmlUtil.xmlStr2Map(response);
			
			String return_code = respMap.get("return_code");
			String result_code = respMap.get("result_code");
			if(!(return_code.equals("SUCCESS")&&result_code.equals("SUCCESS"))){
				String return_msg = respMap.get("return_msg");
				String err_code_des = respMap.get("err_code_des");
				StringBuffer refundErrorReason = new StringBuffer();
//				if(StringUtils.isNotBlank(return_msg)){
//					refundErrorReason.append(return_msg);
//				}
				if(StringUtils.isNotBlank(err_code_des)){
					refundErrorReason.append(err_code_des);
				}
				throw new TradeException("申报失败:"+refundErrorReason.toString());
			}

	}

	@Override
	public void paymentDeclare(OrderDO orderDO, String appKey,PaymentDeclareDTO paymentDeclareDTO) throws TradeException {
		
		String bizCode = orderDO.getBizCode();
		

		BizInfoDTO bizInfo = null;
		bizInfo = appManager.getBizInfo(bizCode);
		if (null == bizInfo) {
			log.error("bizInfo is null"+bizCode);
			throw new TradeException(bizCode+" bizInfo is null");
		}
		if (null == bizInfo.getBizPropertyMap()) {
			log.error("bizPropertyMap is null,  bizCode:{}", bizCode);
			throw new TradeException(bizCode+" bizPropertyMap is null");
		}
		
		try {

			doDeclare(bizInfo.getBizPropertyMap(),orderDO,appKey,paymentDeclareDTO);

		} catch (Exception e) {
			log.error("wechat declare error", e);
			throw new TradeException(" refund declare "+e.getMessage());
		}


	}

}
