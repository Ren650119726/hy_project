package com.mockuai.tradecenter.core.service.action.order.add;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.RuleChainService;
import com.mockuai.tradecenter.core.service.action.TopTradeAction;
import com.mockuai.tradecenter.core.util.ModelUtil;

public class AddActivityOrder extends TopTradeAction {

	private static final Logger log = LoggerFactory.getLogger(AddActivityOrder.class);

	@Autowired
	OrderManager orderManager;

	private RuleChainService ruleChain;

	private String serviceName;

	public void setRuleChain(RuleChainService ruleChain) {
		this.ruleChain = ruleChain;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_ACTIVITY_ORDER.getActionName();
	}

	public String getServiceName() {
		return "add order service";
	}

	public RuleChainService getNextRuleChain() {
		return ruleChain;
	}

	@Override
	public TradeResponse<OrderDTO> beginExecute(final RequestContext context) {
		Request request = context.getRequest();
		if (request.getParam("orderDTO") == null) {
			log.error("orderDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderDTO is null");
		}

		final OrderDTO orderDTO = (OrderDTO) request.getParam("orderDTO");

		if (orderDTO.getPaymentId() == null) {
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "paymentId is null");
		}
		if (orderDTO.getOrderItems() == null || orderDTO.getOrderItems().size() == 0) {
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "orderItems  is null");
		}
		if (null == orderDTO.getDeliveryId()) {
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "deliveryId  is null");
		}

		if (orderDTO.getDeliveryId() == 1 && (null == orderDTO.getOrderConsigneeDTO()
				|| null == orderDTO.getOrderConsigneeDTO().getConsigneeId())) {
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "consigneeId  is null");
		}

		try {
			checkPayment(orderDTO);
		} catch (TradeException e) {
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}

		List<OrderItemDTO> orderItemList = orderDTO.getOrderItems();

		Long skuId = orderItemList.get(0).getItemSkuId();

		OrderDO preOrderDO;
//		try {
//			preOrderDO = orderManager.getPreOrder(orderDTO.getUserId(), skuId);
////			if (null == preOrderDO) {
////				preOrderDO = orderManager.getActivityOrder(orderDTO.getUserId(), skuId);
////			}
//			if(null==preOrderDO){
//				return ResponseUtils.getFailResponse(ResponseCode.PRE_ORDER_HAS_EXIST, "预单不存在");
//			}
//			
//		} catch (TradeException e) {
//			return ResponseUtils.getFailResponse(ResponseCode.SYS_E_REMOTE_CALL_ERROR, e.getMessage());
//		}

		return ResponseUtils.getSuccessResponse(orderDTO);

	}

	private void checkPayment(OrderDTO orderDTO) throws TradeException {
		int payType = orderDTO.getPaymentId();
		if (TradeConstants.PaymentType.isValidPayType(payType) == false) {
			log.error("payment is not valid: " + orderDTO.getPaymentId());
			throw new TradeException(ResponseCode.BIZ_E_PAYMENT_TYPE_ERROR, "payment is not valid");
		}
	}

}
