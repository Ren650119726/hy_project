package com.mockuai.tradecenter.core.service.action.refund;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.core.dao.TradeConfigDAO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.TradeConfigDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.manager.RefundOrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.TradeUtil;

/**
 * 申请退款
 *
 */
public class ApplyRefund implements Action {
	private static final Logger log = LoggerFactory.getLogger(ApplyRefund.class);

	private static final int DEFUALT_TIMEOUT_DAYS = 15;

	@Resource
	private OrderManager orderManager;

	@Resource
	private OrderItemManager orderItemManager;

	@Resource
	private RefundOrderManager refundOrderManager;

	@Resource
	private RefundOrderItemManager refundOrderItemManager;
	
	@Resource
	private TradeConfigDAO tradeConfigDAO;

	public TradeResponse<Boolean> execute(RequestContext context) {

		Request request = context.getRequest();
		TradeResponse<Boolean> response = null;

		if (request.getParam("refundOrderDTO") == null) {
			log.error("refundOrderDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "refundOrderDTO is null");
		}

		RefundOrderDTO refundOrderDTO = (RefundOrderDTO) request.getParam("refundOrderDTO");
		String bizCode = (String) context.get("bizCode");
		String openRightsKey = "open_rights_mark";
		TradeConfigDO tradeOpenRightsConfig = tradeConfigDAO.getTradeConfig(bizCode, openRightsKey);
		if(null==tradeOpenRightsConfig||tradeOpenRightsConfig.getAttrValue().equals("0")){
			log.error(bizCode+"未开通维权配置");
			return ResponseUtils.getFailResponse(ResponseCode.NO_REFUND_FUNCTION);
		}
		Integer opemRefundMark = 0;
		if(null!=tradeOpenRightsConfig&&tradeOpenRightsConfig.getAttrValue().equals("1")){
			opemRefundMark = 1;
		}
		
		TradeConfigDO tradeRightsTimeOutConfig = tradeConfigDAO.getTradeConfig(bizCode, "rights_timeout_days");
		Long rightsTimeoutDays = 15l;
		if(null!=tradeRightsTimeOutConfig){
			rightsTimeoutDays = Long.parseLong(tradeRightsTimeOutConfig.getAttrValue());
		}
		
		String errorMsg;
		try {
			errorMsg = this.refundOrderManager.validateFields4Apply(refundOrderDTO);

			if (!StringUtils.isEmpty(errorMsg)) {
				log.error(errorMsg);
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, errorMsg);
			}
		} catch (TradeException e1) {
			log.error("apply refund validator error",e1);
			return ResponseUtils.getFailResponse(e1.getResponseCode());
		}

		long orderId = refundOrderDTO.getOrderId();
		long userId = refundOrderDTO.getUserId();

		OrderDO order = null;
		try {
			order = this.orderManager.getActiveOrder(orderId, userId);
			if (order == null) {
				log.error("order is null");
				return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order not exist");
			}
			for (RefundOrderItemDTO refundOrderItem : refundOrderDTO.getReturnItems()) {
				Integer canRefundMark = TradeUtil.getCanRefundMark(opemRefundMark,refundOrderItem.getRefundStatus(),
						order.getReceiptTime(),rightsTimeoutDays);
				if(canRefundMark!=1){
					log.error(""+refundOrderItem.getOrderItemId()+"不能申请退款");
					return ResponseUtils.getFailResponse(ResponseCode.ORDER_REFUND_TIMEOUT);
				}
			}
			
			
			
		} catch (TradeException e) {
			log.error("db error:", e);
			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_DATABASE_ERROR);
		}

		if (order.getOrderStatus().intValue() < TradeConstants.Order_Status.PAID) {
			log.error("order unpaid");
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_STATUS_ERROR, "order unpaid");
		}

		// 如果是订单已经结单 无法申请退货
		if (order.getOrderStatus() != null
				&& order.getOrderStatus().intValue() == TradeConstants.Order_Status.FINISHED) {
			log.error("order already closed");
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_ORDER_STATUS_ERROR,
					"order closed,cannot apply return");
		}

		long sellerId = order.getSellerId();

		
//		// 如果是订单已经超期14天则无法退货 发货时间判断还是下单时间判断
//		Date now = new Date();
//		// 如果订单超时不能申请退货
//		if (now.getTime() > (order.getReceiptTime().getTime() + DEFUALT_TIMEOUT_DAYS * 24 * 3600 * 1000)) {
//			log.error("order timeout :" + orderId + "," + sellerId);
//			return ResponseUtils.getFailResponse(ResponseCode.ORDER_REFUND_TIMEOUT);
//		}
		
		
		
		
		try {
			Integer refundType = 0;
			if(order.getOrderStatus() ==Integer.parseInt(EnumOrderStatus.SIGN_OFF.getCode())){
				refundType = 1;
			}

			Boolean applyResult = refundOrderItemManager.applyOrderItemRefund(refundOrderDTO,refundType);
			response = ResponseUtils.getSuccessResponse(applyResult);

		} catch (TradeException e) {
			log.error("db operation error : " + e);
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}

		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.APPLY_RETURN.getActionName();
	}
}
