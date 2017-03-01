package com.mockuai.tradecenter.core.service.action.payment.client.weixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO.OrderSku;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.util.XmlUtil;
import com.mockuai.tradecenter.common.vo.RetBean;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.AppManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.manager.RefundOrderManager;
import com.mockuai.tradecenter.core.manager.SupplierManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.HttpUtil;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;

/**
 * 退款单笔查询
 *
 */
@Service
public class WxpayClientForRefundSingleQuery  extends ClientExecutorAbstract {
	private static final Logger log = LoggerFactory.getLogger(WxpayClientForRefundSingleQuery.class);

	@Autowired
	private RefundOrderManager refundOrderManager;

	@Resource
	private OrderManager orderManager;

	@Resource
	private AppManager appManager;
	
	@Resource
	private SupplierManager supplierManager;

	@Autowired
	private RefundOrderItemManager refundOrderItemManager;

	private void refundQuery(Map<String, BizPropertyDTO> bizPropertyMap, String refundItemSn,int paymentId) {
		try {
			String nonce_str = "4232343765";// 随机字符串
			String appid = TradeUtil.getWxPayAppId(paymentId,bizPropertyMap); // 微信公众号apid
			String partnerId = TradeUtil.getWxPayPartnerId(paymentId,bizPropertyMap);
			String mch_id = partnerId; // 微信商户id
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			String partnerKey = TradeUtil.getWxPayPartnerKey(paymentId,bizPropertyMap);

			Map paramMap = new TreeMap();
			paramMap.put("appid", appid);
			paramMap.put("mch_id", mch_id);
			paramMap.put("nonce_str", nonce_str);
			paramMap.put("out_refund_no", refundItemSn);

			paramMap.put("sign", TradeUtil.getWxParamSign(paramMap, partnerKey));

			System.out.println(new StringBuilder().append("prepayIdReq:").append(JsonUtil.toJson(paramMap)).toString());

			String reqUrl = "https://api.mch.weixin.qq.com/pay/refundquery";
			String xmlData = XmlUtil.map2XmlStr(paramMap);
			log.info("wxpay refund single query request:"+xmlData);
			String response = HttpUtil.postXml(reqUrl, new String(xmlData.getBytes("utf-8"), "ISO8859-1"));
			log.info("wxpay single query refund response:" + response);
			Map<String, String> respMap = XmlUtil.xmlStr2Map(response);
			if (null != respMap.get("return_code")) {

				String result_code = respMap.get("result_code");
				if (StringUtils.isNotBlank(result_code)){
					OrderItemDO orderItemDO = refundOrderManager.getOrderItemByRefundId(refundItemSn);
					RefundOrderItemDTO refundOrderItemDTO = refundOrderManager.convert2RefundOrderItemDTO(orderItemDO);
					
					if(result_code.equalsIgnoreCase("SUCCESS")) {
						
						try {

	        				OrderDO orderDOInner = orderManager.getOrder(orderItemDO.getOrderId(), orderItemDO.getUserId());
	        				
	        				//appInfo判断
	        		        AppInfoDTO appInfo = null;
	        		        try {
	        		        	if(null==orderDOInner.getAppType())
	        		        		orderDOInner.setAppType(3);
	        					appInfo = appManager.getAppInfoByBizCode(orderDOInner.getBizCode(),orderDOInner.getAppType());
	        				} catch (TradeException e1) {
	        		            log.error("9999","error to get appInfo, bizCode:{"+orderDOInner.getBizCode()+"}");
	        				}
	        		        final AppInfoDTO finalAppInfo = appInfo;
	        				
							OrderStockDTO orderStockDTO = new OrderStockDTO();
				            orderStockDTO.setOrderSn(orderDOInner.getOrderSn());
				            orderStockDTO.setSellerId(orderDOInner.getSellerId());
				            
				            List<OrderSku> orderSkus = new ArrayList<OrderSku>();			    		
			    			OrderSku orderSku = new OrderSku();						
			    			orderSku.setSkuId(orderItemDO.getItemSkuId());
			    			orderSku.setNumber(orderItemDO.getNumber());
			    			orderSku.setStoreId(orderDOInner.getStoreId());
			    			orderSku.setSupplierId(orderDOInner.getSupplierId());
			    			orderSkus.add(orderSku);
				    		
				    		orderStockDTO.setOrderSkuList(orderSkus);
				            supplierManager.backReduceItemSkuSup(orderStockDTO, finalAppInfo.getAppKey());
				            
						} catch (TradeException e) {
							log.info("  "+e);
						}
						
						
						refundOrderItemManager.notifyRefundSuccess(refundOrderItemDTO,orderItemDO);
						
					}else{
						refundOrderItemManager.notifyRefundFailed(refundOrderItemDTO);
					}
					
					// 记录退款日志
	            	Long refundItemLogId = refundOrderManager.addRefundItemLog(refundOrderItemDTO, true);
	            	if (refundItemLogId == 0) {
	            		log.error(" wxpay refund add log error");
	            	}
				}
			}

		} catch (Exception e) {
			log.error("WxpayClientForRefundSingleQuery refundQuery error ",e);
		}

	}

	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {

		String bizCode = (String) context.get("bizCode");
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();

		Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		if (null == bizPropertyMap) {
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}

		String refundItemSn = (String) context.get("refundItemSn");
		
		OrderDO order = (OrderDO) context.get("order");
		
		try {

			refundQuery(bizPropertyMap, refundItemSn,order.getPaymentId());

		} catch (Exception e) {
			log.error("wechat refund error", e);
			throw new TradeException(" refund error ");
		}

		return paymentUrlDTO;

	}

}
