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
 * 退款 无密接口
 *
 */
@Service
public class AlipayClientForRefundNoPwd extends ClientExecutorAbstract  {

	private static final Logger log = LoggerFactory.getLogger(AlipayClientForRefundNoPwd.class);

	private class DetailDataDTO {
		protected String detailData;

		public String getDetailData() {
			return detailData;
		}

		public void setDetailData(String detailData) {
			this.detailData = detailData;
		}

	}

	private String getLine(OrderItemDTO dto) {
		StringBuilder line = new StringBuilder();
		line.append(dto.getOutTradeNo() + "^");
		line.append(String.format("%.2f", ((double) (dto.getRefundAmount()) / 100)) + "^");
		line.append("备注");
		return line.toString();
	}

	public DetailDataDTO getDetailData(List<OrderItemDTO> list) throws TradeException {
		DetailDataDTO detailDataDTO = new DetailDataDTO();

		StringBuilder detailData = new StringBuilder();
		if (null == list || list.size() == 0) {
			throw new TradeException("refund detail is null");
		}
		for (OrderItemDTO dto : list) {
			detailData.append(getLine(dto));
//			detailData.append("#");
		}
		detailDataDTO.setDetailData(detailData.toString());
		if (log.isInfoEnabled()) {
			log.info("AlipayClientForPayment.DetailDataDTO=" + JSONObject.toJSONString(detailDataDTO));
		}
		
		
		
		
		return detailDataDTO;
	}

	

	private Map<String, String> getMockAlipayParams(List<OrderItemDTO> list, Map<String, BizPropertyDTO> bizPropertyMap,
			RefundOrderItemDTO refundOrderItemDTO)
			throws Exception {
		BizPropertyDTO returnUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_RETURN_URL);
		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_NOTIFY_URL);

		if (null == returnUrlAppProperty || null == notifyUrlAppProperty) {
			throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
		}
		String batchId = refundOrderItemDTO.getRefundBatchNo();
		
		System.out.println("batchId:"+batchId);

		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
		sParaTemp.put("partner", TradeUtil.getAlipayPartnerId(bizPropertyMap));
		sParaTemp.put("_input_charset", "utf-8");
		sParaTemp.put("notify_url", notifyUrlAppProperty.getValue());
//		sParaTemp.put("notify_url", "http://74.207.229.184:8081/alipay/notify_url.jsp");
		sParaTemp.put("seller_email", TradeUtil.getAlipaySellerId(bizPropertyMap));
		sParaTemp.put("refund_date", DateUtil.getFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		sParaTemp.put("batch_no", batchId);
		sParaTemp.put("batch_num", list.size() + "");
		DetailDataDTO detailDataDTO = getDetailData(list);
		sParaTemp.put("detail_data", detailDataDTO.getDetailData());

		sParaTemp.put("return_type", "html");
		
		
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
		
		OrderDO orderDO = (OrderDO) context.get("orderDO");
		if (null == orderDO) {
			throw new TradeException("orderDO is null");
		}
		OrderPaymentDO orderPaymentDO = (OrderPaymentDO) context.get("orderPaymentDO");

		Long refundAmount = (Long) context.get("refundAmount");
		
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();

		Map<String, String> paramMap = new HashMap<String, String>();
		
		RefundOrderItemDTO refundOrderItemDTO = (RefundOrderItemDTO) context.get("refundOrderItemDTO");
		
		try {
			List<OrderItemDTO> list = new ArrayList<OrderItemDTO>();
			OrderItemDTO orderItemDTO = new OrderItemDTO();
			orderItemDTO.setOutTradeNo(orderPaymentDO.getOutTradeNo());
			orderItemDTO.setRefundAmount(refundAmount);
			list.add(orderItemDTO);
			paramMap = getMockAlipayParams(list, bizPropertyMap,refundOrderItemDTO);
			
			Map<String, String> param = AlipaySubmit.buildRequestPara(paramMap,TradeUtil.getMchPrivateKey(bizPropertyMap));
			
			System.out.println("requestParam:"+param);
			
			String response = HttpUtil.post("https://mapi.alipay.com/gateway.do?_input_charset=utf-8", param);
			Map<String, String> respMap = XmlUtil.xmlStr2Map(response);
			log.info("respMap:"+respMap);
			String is_success = respMap.get("is_success");
			if(!is_success.equals("T")){
				throw new TradeException("alipay refund error");
			}
			
		} catch (Exception e) {
			log.error("alipay refund error", e);
			throw new TradeException("alipay refund error");
		}
		return paymentUrlDTO;
		
	}

}
