package com.mockuai.tradecenter.core.service.action.payment;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alipay.sign.RSA;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.SumPayCallBackDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumRefundStatus;
import com.mockuai.tradecenter.core.dao.OrderItemDAO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.DozerBeanService;

public class SumPayRefundCallback implements Action{
	private static final Logger log = LoggerFactory.getLogger(SumPayRefundCallback.class);
	private static final Logger notifyLogger = LoggerFactory.getLogger("notifyLogger");

	
	@Autowired
	private OrderItemDAO orderItemDAO;
	
	@Resource
	private DozerBeanService dozerBeanService;
	
	 @Autowired
	 private RefundOrderItemManager refundOrderItemManager;

	public TradeResponse<String> execute(RequestContext context)
			throws TradeException {
		Request request = context.getRequest();

		if(request.getParam("sumPayCallBackDTO") == null){
			log.error("sumPayCallBackDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"sumPayCallBackDTO is null");
		}
		
		
		// 字段验证
		final SumPayCallBackDTO sumPayCallBackDTO = (SumPayCallBackDTO)request.getParam("sumPayCallBackDTO");
		
		if(sumPayCallBackDTO.getCode()==null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING," sumpay refund callback code is null");
		}
		if(sumPayCallBackDTO.getTradeno()==null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING," sumpay refund callback order_no is null");
		}
		if(sumPayCallBackDTO.getPaystatus()==null){
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING," sumpay refund callback status is null");
		}
//		if(sumPayCallBackDTO.getRefundNo()==null){
//			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING," sumpay refund callback rerundNo is null");
//		}
		
		
		OrderItemQTO query = new OrderItemQTO();
		query.setOrderItemId(Long.parseLong(sumPayCallBackDTO.getTradeno()));
		OrderItemDO orderItemDO = orderItemDAO.getOrderItem(query);
		
		if(orderItemDO.getRefundStatus().intValue()==Integer.parseInt(EnumRefundStatus.REFUND_FINISHED.getCode())){
			 //以银联约定的格式返回数据
	        TradeResponse<String> response = ResponseUtils.getSuccessResponse("success");
	        return response;
		}
		
		Boolean result = false;
		if(null==orderItemDO){
			//TODO ...
		}else{
			
			//签名验证
	        Map<String,String> paramMap = sumPayCallBackDTO.getOriginParamMap();
	        if(paramMap != null){
	            Map<String,String> signMap = new TreeMap<String, String>();
	            for(Map.Entry<String,String> entry: paramMap.entrySet()){
	                if("notify_url".equals(entry.getKey())==false && "sign".equals(entry.getKey())==false){
	                	 signMap.put(entry.getKey(), entry.getValue());
	                }
	            }
	
	            StringBuilder paramSb = new StringBuilder();
	            for (Map.Entry<String, String> entry : signMap.entrySet()) {
	                paramSb.append(entry.getKey());
	                paramSb.append("=");
	                paramSb.append(entry.getValue());
	                paramSb.append("&");
	            }
	            paramSb.deleteCharAt(paramSb.length() - 1);
	
	            String sumpayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTHryKyTmvERzzIH3ACXuU7hyRoFfHgXlMOqyfAiBxvCSd5kuYqvhi5LlHW/khQcuULWcUYQuX1CkV+/DDCvLVyva1UDByPsZrw1NibnXOEe2xq/Te+i9UbiAzUN/uS5dWvwLOMjqIiv6lOSzcXUzCvivSBaHHii3Nc9FSEDXF4wIDAQAB";
				boolean checkSignResult = RSA.verify(paramSb.toString(), sumPayCallBackDTO.getSign(), sumpayPublicKey, "utf-8");
				log.error("sumpay sign check>>>>>>>>>>>>>>>>>result="+checkSignResult);
	        }
			
			
			
			
			if("000000".equals(sumPayCallBackDTO.getCode())&&"200".equals(sumPayCallBackDTO.getPaystatus())){//支付成功
				RefundOrderItemDTO refundOrderItemDTO = new RefundOrderItemDTO();
				refundOrderItemDTO = dozerBeanService.cover(orderItemDO, RefundOrderItemDTO.class);
				refundOrderItemDTO.setOrderItemId(orderItemDO.getId());
				result = 	refundOrderItemManager.notifyRefundSuccess(refundOrderItemDTO,orderItemDO);
			}
		}
		
		
		
		
		 //以银联约定的格式返回数据
        TradeResponse<String> response = ResponseUtils.getSuccessResponse(result.toString());
        return response;
		
	}

	@Override
	public String getName() {
		return ActionEnum.SUMPAY_REFUND_CALLBACK.getActionName();
	}



}
