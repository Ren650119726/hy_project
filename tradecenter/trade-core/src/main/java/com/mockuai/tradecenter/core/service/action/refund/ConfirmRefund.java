package com.mockuai.tradecenter.core.service.action.refund;

import java.util.Map;

import javax.annotation.Resource;

import com.mockuai.tradecenter.common.enums.EnumPaymentMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.base.ClientExecutorFactory;
import com.mockuai.tradecenter.core.dao.RefundDAO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderPaymentDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.OrderPaymentManager;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.manager.SellerTransLogManager;
import com.mockuai.tradecenter.core.service.RefundService;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 确认退款 (先冻结、在划钱) 1、收支明细增加退款支出记录状态为未结算、商户余额表冻结金额=冻结金额+退款金额 2、调用支付退款
 * TODO 退款流程相关的接口及实现都写得很烂，逻辑不清晰，数据存储不清晰，有时间要重构掉，add by caishen on 2016-06-16
 */
public class ConfirmRefund implements Action {
	private static final Logger log = LoggerFactory.getLogger(ConfirmRefund.class);

	@Resource
	private OrderManager orderManager;

	@Resource
	private ClientExecutorFactory clientExecutorFactory;

	@Resource
	private RefundOrderItemManager refundOrderItemManager;

	@Resource
	private SellerTransLogManager sellerTransLogManager;

	@Resource
	private OrderPaymentManager orderPaymentManager;

	@Autowired
	private RefundService refundService;
	
	@Autowired
	private RefundDAO refundDAO;
	
	@Autowired
	private OrderItemManager  orderItemManager;

	public TradeResponse<PaymentUrlDTO> execute(RequestContext context) {
		Request request = context.getRequest();
		TradeResponse<PaymentUrlDTO> response = null;
		if (request.getParam("refundOrderItemDTO") == null) {
			log.error("refundOrderItemDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "refundOrderItemDTO is null");
		}

		RefundOrderItemDTO refundOrderItemDTO = (RefundOrderItemDTO) request.getParam("refundOrderItemDTO");

		// 必要的字段
		String validatorResult;
		try {
			validatorResult = refundOrderItemManager.validator4RefundConfirm(refundOrderItemDTO);
			if (StringUtils.isNotBlank(validatorResult)) {
				log.error(validatorResult);
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, validatorResult);
			}
		} catch (TradeException e1) {
			log.error("confirmRefund validator error",e1);
			return ResponseUtils.getFailResponse(e1.getResponseCode());
		}

		OrderDO order = null;
		try {
			order = this.orderManager.getActiveOrder(refundOrderItemDTO.getOrderId(), refundOrderItemDTO.getUserId());
		} catch (TradeException e) {
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}

		if (order == null) {
			log.error("order doesn't exist");
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order not exist");
		}

//		if (order.getOrderStatus() != Integer.parseInt(EnumOrderStatus.REFUND_APPLY.getCode())
//				&& order.getOrderStatus() != Integer.parseInt(EnumOrderStatus.PARTIAL_REFUND.getCode())) {
//			log.error("order status error");
//			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_STATUS_ERROR);
//		}

		try {
			String bizCode = (String) context.get("bizCode");
			Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
			if (null == bizPropertyMap) {
				throw new TradeException(bizCode + " bizPropertyMap is null");
			}
			
//			ClientExecutor clientExecutor = clientExecutorFactory.getExecutor(order.getPaymentId() + "_Refund");
//			if (null == clientExecutor) {
//				if(refundOrderItemDTO.get)
//				return new TradeResponse<PaymentUrlDTO>(ResponseCode.BIZ_NOT_EXIST_PAYMENT);
//			}
//			OrderItemDO orderItemDO = refundDAO.getOrderItem(refundOrderItemDTO.getUserId(),refundOrderItemDTO.getOrderId(), refundOrderItemDTO.getItemSkuId());
			
			OrderItemQTO orderItemQTO = new OrderItemQTO();
			orderItemQTO.setOrderId(refundOrderItemDTO.getOrderId());
			orderItemQTO.setOrderItemId(refundOrderItemDTO.getOrderItemId());
			orderItemQTO.setUserId(refundOrderItemDTO.getUserId());
			OrderItemDO orderItemDO = orderItemManager.getOrderItem(orderItemQTO);
			if( null== orderItemDO ){
				throw new TradeException("orderItem is null");
			}
			if(orderItemDO.getPaymentAmount()>0){
				OrderPaymentDO orderPaymentDO = orderPaymentManager.getOrderPayment(order.getId(), order.getUserId());
				if (null == orderPaymentDO) {
					throw new TradeException("orderPaymentDO is null");
				}
//				if ((order.getPaymentId()!=7&&order.getPaymentId()!=8)&&orderPaymentDO.getOutTradeNo() == null) {
//					throw new TradeException("outTradeno is null");
//				}
				if ((order.getPaymentId()!=Integer.valueOf(EnumPaymentMethod.ACCOUNT_BALANCE_PAY.getCode()).intValue()
						&&order.getPaymentId()!=Integer.valueOf(EnumPaymentMethod.HI_COIN_PAY.getCode()).intValue())
						&&orderPaymentDO.getOutTradeNo() == null) {
					throw new TradeException("outTradeNo is null");
				}
				context.put("orderPaymentDO", orderPaymentDO);
			}
			
			
			// TODO ....
			refundOrderItemDTO.setBizCode(order.getBizCode());
			refundOrderItemDTO.setOrderSn(order.getOrderSn());
			refundOrderItemDTO.setSellerId(order.getSellerId());
			refundOrderItemDTO.setOrderItemId(orderItemDO.getId());
			refundOrderItemDTO.setItemSkuId(orderItemDO.getItemSkuId());
			
			context.put("orderDO", order);
			context.put("bizPropertyMap", bizPropertyMap);
			context.put("orderItemDO", orderItemDO);
			context.put("refundOrderItemDTO",refundOrderItemDTO);
			//FIXME 退款商品流水号，查询微信退款状态的时候也会用到这里的退款流水号（推测这里应该是为了实现商品级别退款而构造的）add by caishen on 2016-06-19
			context.put("refundItemSn", refundOrderItemDTO.getSellerId()+"_"+
					refundOrderItemDTO.getUserId()+"_"+refundOrderItemDTO.getOrderItemId());


			response = (TradeResponse<PaymentUrlDTO>) refundService.confirmOrderItemRefund(context);
			
			
			

		} catch (TradeException e) {
			log.error("confirm refund error", e);
			return new TradeResponse<PaymentUrlDTO>(e.getResponseCode(),e.getMessage());
		} catch (Exception e) {
			log.error("system error :" + e);
			return new TradeResponse<PaymentUrlDTO>(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}

		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.CONFIRM_REFUND.getActionName();
	}

}
