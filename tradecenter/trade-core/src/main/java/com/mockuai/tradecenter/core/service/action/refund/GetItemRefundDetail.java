package com.mockuai.tradecenter.core.service.action.refund;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.OrderServiceDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundItemLogDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumRefundReason;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.OrderConsigneeManager;
import com.mockuai.tradecenter.core.manager.OrderDiscountInfoManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.OrderServiceManager;
import com.mockuai.tradecenter.core.manager.RefundOrderItemManager;
import com.mockuai.tradecenter.core.manager.UserManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.ModelUtil;
import com.mockuai.tradecenter.core.util.PaymentUtil;

/**
 * 获取商品退款明细
 *
 */
public class GetItemRefundDetail implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetItemRefundDetail.class);

	@Resource
	private RefundOrderItemManager refundOrderItemManager;
	
	@Resource 
	private OrderManager orderManager;
	
	@Resource
    private DozerBeanService  dozerBeanService;
	
	@Resource
	private OrderItemManager orderItemManager;
	
	@Resource
	private OrderConsigneeManager orderConsigneeManager;
	
	@Resource
	private OrderServiceManager orderServiceManager;
	
	@Resource
	private OrderDiscountInfoManager orderDiscountInfoManager;
	
	@Resource
	private UserManager userManager;
	
	@Override
	public TradeResponse<?> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<RefundOrderDTO> response = null;
		RefundOrderDTO refundOrderDTO = new RefundOrderDTO();
		if (request.getParam("refundOrderItemDTO") == null) {
			log.error("refundOrderItemDTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "refundOrderItemDTO is null");
		}
		String appKey = (String) context.get("appKey");
		RefundOrderItemDTO refundOrderItemDTO = (RefundOrderItemDTO) request.getParam("refundOrderItemDTO");
		OrderDO orderDO = null;
		orderDO = this.orderManager.getOrder(refundOrderItemDTO.getOrderId(), refundOrderItemDTO.getUserId());

		if(orderDO == null){
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"order doesn't exist");
		}
		
		OrderItemQTO orderItemQuery = new OrderItemQTO();
		orderItemQuery.setOrderId(refundOrderItemDTO.getOrderId());
//		orderItemQuery.setItemSkuId(refundOrderItemDTO.getItemSkuId());	
		orderItemQuery.setOrderItemId(refundOrderItemDTO.getOrderItemId());
		OrderItemDO orderItemDO = orderItemManager.getOrderItem(orderItemQuery);
		if(null == orderItemDO ){
			return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST,"order item doesn't exist");
		}
		
		List<RefundOrderItemDTO> list = new ArrayList<RefundOrderItemDTO>();
		RefundOrderItemDTO responseRefundOrderItemDTO = dozerBeanService.cover(orderItemDO, RefundOrderItemDTO.class);
		list.add(responseRefundOrderItemDTO);
		
		
		if(null!=orderItemDO.getItemType()&&orderItemDO.getItemType().intValue()==PaymentUtil.ItemType.suit_item){
			OrderItemQTO querySuitSubItem = new OrderItemQTO();
			querySuitSubItem.setOrderId(refundOrderItemDTO.getOrderId());
			querySuitSubItem.setUserId(orderDO.getUserId());
			querySuitSubItem.setOrderItemId(refundOrderItemDTO.getOrderItemId());
			List<OrderItemDO> subOrderItemList = orderItemManager.queryOrderItem(querySuitSubItem);
			List<RefundOrderItemDTO> refundSuitSubOrderItemDTOList = dozerBeanService.coverList(subOrderItemList, RefundOrderItemDTO.class);
			list.addAll(refundSuitSubOrderItemDTOList);
		}
		
		for(RefundOrderItemDTO record:list){
			
			
			List<OrderServiceDO> orderServiceDOList = orderServiceManager.queryOrderService(record.getOrderId(), record.getId());
			if(null!=orderServiceDOList){
				List<OrderServiceDTO> orderServiceDTOList = dozerBeanService.coverList(orderServiceDOList, OrderServiceDTO.class);
				responseRefundOrderItemDTO.setOrderServiceList(orderServiceDTOList);
			}
		}
		
		
		
		
		refundOrderDTO = dozerBeanService.cover(orderDO, RefundOrderDTO.class);
		
		OrderConsigneeDO orderConsigneeDO = null;
		//查询订单收货地址信息
		orderConsigneeDO = orderConsigneeManager.getOrderConsignee(orderDO.getId(), orderDO.getUserId());

		if(orderConsigneeDO == null){
			//TODO error handle
		}else{
			OrderConsigneeDTO orderConsigneeDTO = ModelUtil.convert2OrderConsigneeDTO(orderConsigneeDO);
//			orderConsigneeDTO = fillOrderConsigneeRegion(orderConsigneeDTO,appKey);
			refundOrderDTO.setOrderConsigneeDTO(orderConsigneeDTO);
		}
		
		responseRefundOrderItemDTO.setRefundReason(EnumRefundReason.toMap().get(responseRefundOrderItemDTO.getRefundReasonId()));
		
		if(null==responseRefundOrderItemDTO.getDiscountAmount()){
			responseRefundOrderItemDTO.setDiscountAmount(0L);
		}
//		if(null==responseRefundOrderItemDTO.getPointAmount()){
//			responseRefundOrderItemDTO.setPointAmount(0L);
//		}
		if(null!=orderItemDO.getPointAmount()){
			responseRefundOrderItemDTO.setPoint(orderItemDO.getPointAmount());
		}else{
			responseRefundOrderItemDTO.setPoint(0L);
		}
		
		List<RefundItemLogDTO> refundItemLogDTOList = refundOrderItemManager.queryRefundItemLogList(refundOrderItemDTO.getOrderId(), 
				orderItemDO.getItemSkuId());
		
		/*获取操作人名称*/
		for(RefundItemLogDTO refundItemLogDTO :refundItemLogDTOList){			
			refundItemLogDTO.setUserName(userManager.getUserName(refundItemLogDTO.getSellerId(), appKey));
		}
		
		
		responseRefundOrderItemDTO.setRefundItemLogList(refundItemLogDTOList);
		
		List<OrderDiscountInfoDO> orderDiscountInfoDOs =
				orderDiscountInfoManager.queryOrderDiscountInfo(refundOrderItemDTO.getOrderId(), refundOrderItemDTO.getUserId());

		refundOrderDTO.setOrderDiscountInfoDTOs(ModelUtil.convert2OrderDiscountInfoDTOList(orderDiscountInfoDOs));
		
		refundOrderDTO.setReturnItems(list);
		
		refundOrderDTO.setPaymentId(orderDO.getPaymentId());
		
		response = ResponseUtils.getSuccessResponse(refundOrderDTO);
		
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_ITEM_REFUND_DETAIL.getActionName();
	}

}
