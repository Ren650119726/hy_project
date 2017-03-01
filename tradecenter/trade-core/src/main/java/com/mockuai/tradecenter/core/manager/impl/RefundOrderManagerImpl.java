package com.mockuai.tradecenter.core.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.domain.OrderServiceDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderDTO;
import com.mockuai.tradecenter.common.domain.refund.RefundOrderItemDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderStatus;
import com.mockuai.tradecenter.core.dao.OrderItemDAO;
import com.mockuai.tradecenter.core.dao.RefundDAO;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;
import com.mockuai.tradecenter.core.domain.RefundItemLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.DeliveryManager;
import com.mockuai.tradecenter.core.manager.OrderConsigneeManager;
import com.mockuai.tradecenter.core.manager.OrderDiscountInfoManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.manager.OrderPaymentManager;
import com.mockuai.tradecenter.core.manager.OrderServiceManager;
import com.mockuai.tradecenter.core.manager.RefundOrderManager;
import com.mockuai.tradecenter.core.manager.UserManager;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.ModelUtil;
import com.mockuai.tradecenter.core.util.TradeUtil;

public class RefundOrderManagerImpl extends BaseService implements RefundOrderManager {
	private static final Logger log = LoggerFactory.getLogger(RefundOrderManagerImpl.class);

	@Resource
	OrderItemManager orderItemManager;

	@Resource
	OrderItemDAO orderItemDAO;

	@Resource
	private OrderPaymentManager orderPaymentManager;

	@Resource
	private RefundDAO refundDAO;

	@Resource
	private OrderManager orderManager;

	@Resource
	private OrderConsigneeManager orderConsigneeManager;
	@Resource
	private UserManager userManager;
	@Resource
	private DeliveryManager deliveryManager;
	
	@Resource
	private OrderServiceManager orderServiceManager;
	
	@Resource
	private DozerBeanService  dozerBeanService;

	@Resource
	private OrderDiscountInfoManager orderDiscountInfoManager;
	
	@Override
	public String validateFields4Apply(RefundOrderDTO returnOrderDTO) throws TradeException {
		// TODO Auto-generated method stub
		if (returnOrderDTO.getUserId() == null) {
			return "userId is null";
		} else if (returnOrderDTO.getReturnItems() == null || returnOrderDTO.getReturnItems().size() == 0) {
			return "returnItems is null";
		} else if (returnOrderDTO.getOrderId() == null) {
			return "orderId is null";
		}
		OrderItemQTO query = new OrderItemQTO();
		query.setOrderId(returnOrderDTO.getOrderId());
		query.setUserId(returnOrderDTO.getUserId());
		List<OrderItemDO> list = orderItemDAO.queryOrderItemNoRefund(query);

		Map<Long, OrderItemDO> orderItemMap = new HashMap<Long, OrderItemDO>();
		for (OrderItemDO orderItem : list) {
			Long orderItemId = orderItem.getId();
			orderItemMap.put(orderItemId, orderItem);
		}
		

		for (RefundOrderItemDTO refundOrderItem : returnOrderDTO.getReturnItems()) {
			if (orderItemMap.containsKey(refundOrderItem.getOrderItemId()) == false) {
				log.error("orderItemId is not contains");
				return "orderItemId is invalid";
			}
			
			OrderItemDO orderItem = orderItemMap.get(refundOrderItem.getOrderItemId());

			
			if(refundOrderItem.getRefundAmount()>TradeUtil.getRefundableTotalAmount(orderItem)){
				return "退款金额不能大于付款金额";
			}
			
			
			refundOrderItem.setRefundStatus(orderItem.getRefundStatus());
			refundOrderItem.setBizCode(orderItem.getBizCode());
			refundOrderItem.setSellerId(orderItem.getSellerId());
			refundOrderItem.setItemId(orderItem.getItemId());
			refundOrderItem.setItemSkuId(orderItem.getItemSkuId());
		}
		if (returnOrderDTO.getReturnItems().size() < list.size()) {
			returnOrderDTO.setOrderRefundStatus(Integer.parseInt(EnumOrderStatus.PARTIAL_REFUND.getCode()));
		} else {
			returnOrderDTO.setOrderRefundStatus(Integer.parseInt(EnumOrderStatus.REFUND_APPLY.getCode()));
		}

		return null;
	}
	
	
//	@Override
//	public String validateFields4Revise(List<RefundOrderItemDTO> refundOrderItemDTOs) throws TradeException {
//		if (returnOrderDTO.getUserId() == null) {
//			return "userId is null";
//		} else if (returnOrderDTO.getReturnItems() == null || returnOrderDTO.getReturnItems().size() == 0) {
//			return "returnItems is null";
//		} else if (returnOrderDTO.getOrderId() == null) {
//			return "orderId is null";
//		}
//		OrderItemQTO query = new OrderItemQTO();
//		query.setOrderId(returnOrderDTO.getOrderId());
//		query.setUserId(returnOrderDTO.getUserId());
//		List<OrderItemDO> list = orderItemDAO.queryOrderItemNoRefund(query);
//
//		Map<Long, OrderItemDO> skuIdMap = new HashMap<Long, OrderItemDO>();
//		for (OrderItemDO orderItem : list) {
//			Long skuId = orderItem.getItemSkuId();
//			skuIdMap.put(skuId, orderItem);
//		}
//		
//
//		for (RefundOrderItemDTO refundOrderItem : returnOrderDTO.getReturnItems()) {
//			if (skuIdMap.containsKey(refundOrderItem.getItemSkuId()) == false) {
//				log.error("sku id is not contains");
//				return "skuId is invalid";
//			}
//			
//			OrderItemDO orderItem = skuIdMap.get(refundOrderItem.getItemSkuId());
//
//			
//			if(refundOrderItem.getRefundAmount()>TradeUtil.getRefundableTotalAmount(orderItem)){
//				return "退款金额不能大于付款金额";
//			}
//			
//			
//			refundOrderItem.setRefundStatus(orderItem.getRefundStatus());
//			refundOrderItem.setBizCode(orderItem.getBizCode());
//			refundOrderItem.setSellerId(orderItem.getSellerId());
//			refundOrderItem.setItemId(orderItem.getItemId());
//		}
//		if (returnOrderDTO.getReturnItems().size() < list.size()) {
//			returnOrderDTO.setOrderRefundStatus(Integer.parseInt(EnumOrderStatus.PARTIAL_REFUND.getCode()));
//		} else {
//			returnOrderDTO.setOrderRefundStatus(Integer.parseInt(EnumOrderStatus.REFUND_APPLY.getCode()));
//		}
//
//		return null;
//	}
	

	@Override
	public OrderItemDO getOrderItemByAlipayDetailData(String detailData,String batchNo) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log, "getOrderItemByAlipayDetailData", detailData, "");

		String lines[] = detailData.split("\\|");
		String line = lines[0];
		String lineData[] = line.split("\\^");
		String outTradeNo = lineData[0];
		String amount = lineData[1];
		String status = lineData[2];
		if (status.equalsIgnoreCase("SUCCESS")) {
			Long paymentAmount = (long) ((Double.valueOf(amount)) * 100L);
			return refundDAO.getOrderItemByRefundBatchNo(batchNo);
		}
		return null;
	}
	
	@Override
	public OrderItemDO getOrderItemByAlipayDetailDataNew(String detailData,String batchNo) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log, "getOrderItemByAlipayDetailData", detailData, "");

		String lines[] = detailData.split("\\|");
		String line = lines[0];
		String lineData[] = line.split("\\^");
		String outTradeNo = lineData[0];
		String amount = lineData[1];
		String status = lineData[2];
		Long paymentAmount = (long) ((Double.valueOf(amount)) * 100L);
		return refundDAO.getOrderItemByRefundBatchNo(batchNo);

	}

	@Override
	public OrderItemDO getOrderItemByRefundId(String refundItemSn) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log, "getOrderItemByRefundId", refundItemSn, "");

		String refundOrderId[] = refundItemSn.split("\\_");
		Long userId = Long.parseLong(refundOrderId[1]);
		Long orderItemId = Long.parseLong(refundOrderId[2]);
		OrderItemQTO query = new OrderItemQTO();
		query.setUserId(userId);
		query.setOrderItemId(orderItemId);

		return orderItemManager.getOrderItem(query);
	}
	
	@Override
	public OrderItemDO getOrderItemByRefundBatchNo(String batchNO) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log, "getOrderItemByRefundBatchNo", batchNO, "");
		return refundDAO.getOrderItemByRefundBatchNo(batchNO);
	}

	@Override
	public RefundOrderItemDTO convert2RefundOrderItemDTO(OrderItemDO orderItem) throws TradeException {
		printIntoService(log, "convert2RefundOrderItemDTO", orderItem, "");
		OrderDO orderDO = orderManager.getOrder(orderItem.getOrderId(), orderItem.getUserId());
		RefundOrderItemDTO refundItemDTO = new RefundOrderItemDTO();
		// 新增 TODO
		BeanUtils.copyProperties(orderItem, refundItemDTO);
		
		refundItemDTO.setOrderSn(orderDO.getOrderSn());
		refundItemDTO.setItemSkuId(orderItem.getItemSkuId());
		refundItemDTO.setOrderId(orderItem.getOrderId());
		refundItemDTO.setBizCode(orderItem.getBizCode());
		refundItemDTO.setSellerId(orderItem.getSellerId());
		refundItemDTO.setRefundAmount(orderItem.getPaymentAmount());
		refundItemDTO.setOrderItemId(orderItem.getId());
		return refundItemDTO;
	}

	private void fillSellerInfo(List<OrderItemDTO> orderItemDTOs, String appKey) throws TradeException {
		Map<Long, List<OrderItemDTO>> orderItemDTOMap = new HashMap<Long, List<OrderItemDTO>>();
		for (OrderItemDTO orderItemDTO : orderItemDTOs) {
			if (orderItemDTOMap.containsKey(orderItemDTO.getSellerId())) {
				orderItemDTOMap.get(orderItemDTO.getSellerId()).add(orderItemDTO);
			} else {
				List<OrderItemDTO> orderItemDTOList = new ArrayList<OrderItemDTO>();
				orderItemDTOList.add(orderItemDTO);
				orderItemDTOMap.put(orderItemDTO.getSellerId(), orderItemDTOList);
			}
		}

		for (Map.Entry<Long, List<OrderItemDTO>> entry : orderItemDTOMap.entrySet()) {
			Long sellerId = entry.getKey();
			String sellerName = userManager.getUserName(sellerId, appKey);
			List<OrderItemDTO> orderItemDTOList = entry.getValue();
			for (OrderItemDTO orderItemDTO : orderItemDTOList) {
				orderItemDTO.setSellerName(sellerName);
			}
		}
	}

	@Override
	public List<OrderDTO> queryRefundStatusOrderList(OrderQTO query, String appKey) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log, "queryRefundStatusOrderList", query, "");
		List<OrderDO> orderDOList = null;
		orderDOList = refundDAO.queryRefundStatusOrderList(query);
		
		List<OrderDTO> returnList = new ArrayList<OrderDTO>();
		for (OrderDO orderDO : orderDOList) {

			OrderDTO orderDTO = ModelUtil.convert2OrderDTO(orderDO);
			OrderItemQTO orderItemQTO = new OrderItemQTO();
			
			orderItemQTO.setOrderId(orderDO.getId());
			if(null!=query.getRefundStatus()){
				orderItemQTO.setRefundStatus(query.getRefundStatus().toString());
			}
			
			List<OrderItemDO> orderItemList = (List<OrderItemDO>) orderItemDAO
					.queryProcessingReturnOrderItemDOList(orderItemQTO);
			
//			OrderItemQTO orderItemQTO = new OrderItemQTO();
//			orderItemQTO.setOrderId(orderDO.getId());
//			orderItemQTO.setUserId(orderDO.getUserId());
//			List<OrderItemDO> orderItemList  = this.orderItemManager.queryOrderItem(orderItemQTO);
			if (orderItemList != null && orderItemList.size() > 0) {
				// TODO error handle
				orderDTO.setOrderItems(ModelUtil.convert2OrderItemDTOListForRefundDetail(orderItemList));
				// 填充订单商品卖家信息
				fillSellerInfo(orderDTO.getOrderItems(), appKey);
			}
			
			//填充
			if(null!=orderDTO.getOrderItems()&&orderDTO.getOrderItems().size()>0){
				for(OrderItemDTO orderItemDTO:orderDTO.getOrderItems()){
					List<OrderServiceDO> orderServiceDOList = orderServiceManager.queryOrderService(orderItemDTO.getOrderId(), orderItemDTO.getId());
					if(null!=orderServiceDOList){
						List<OrderServiceDTO> orderServiceDTOList = dozerBeanService.coverList(orderServiceDOList, OrderServiceDTO.class);
						orderItemDTO.setOrderServiceList(orderServiceDTOList);
					}
				}
			}

			OrderConsigneeDO orderConsigneeDO = orderConsigneeManager.getOrderConsignee(orderDO.getId(),
					orderDO.getUserId());

			if (orderConsigneeDO == null) {
				// TODO error handle
			} else {
				OrderConsigneeDTO orderConsigneeDTO = ModelUtil.convert2OrderConsigneeDTO(orderConsigneeDO);
				orderConsigneeDTO = fillOrderConsigneeRegion(orderConsigneeDTO, appKey);
				orderDTO.setOrderConsigneeDTO(orderConsigneeDTO);
			}

			/*获取订单的优惠信息*/
			List<OrderDiscountInfoDO> orderDiscountInfoDOs =
					orderDiscountInfoManager.queryOrderDiscountInfo(orderDO.getId(), orderDO.getUserId());
			
			/*订单优惠信息填充到订单对象*/
			orderDTO.setOrderDiscountInfoDTOs(ModelUtil.convert2OrderDiscountInfoDTOList(orderDiscountInfoDOs));
			
			returnList.add(orderDTO);
		}
		return returnList;
	}

	@Override
	public Long getRefundStatusOrderCount(OrderQTO query) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log, "getRefundStatusOrderCount", query, "");
		return refundDAO.getRefundStatusOrderCount(query);
	}
	
	
	@Override
	public Long addRefundItemLog(RefundOrderItemDTO refundOrderItemDTO,
			boolean sellerOperator) throws TradeException {
		RefundItemLogDO refundOrderItemDO = dozerBeanService.cover(refundOrderItemDTO, RefundItemLogDO.class);
		if(sellerOperator){
			refundOrderItemDO.setOperatorFrom(1);
		}else{
			refundOrderItemDO.setOperatorFrom(0);
		}
		return refundDAO.addRefundItemLog(refundOrderItemDO);
	}

	private OrderConsigneeDTO fillOrderConsigneeRegion(OrderConsigneeDTO orderConsigneeDTO, String appkey) {

		try {
			// 查询区域信息，并填充收货地址中的区域名称
			Set<String> regionCodes = new HashSet<String>();
			regionCodes.add(orderConsigneeDTO.getCountryCode());
			regionCodes.add(orderConsigneeDTO.getProvinceCode());
			regionCodes.add(orderConsigneeDTO.getCityCode());
			regionCodes.add(orderConsigneeDTO.getAreaCode());
			regionCodes.add(orderConsigneeDTO.getTownCode());
			List<RegionDTO> regionDTOs = deliveryManager.queryRegion(new ArrayList<String>(regionCodes), appkey);
			Map<String, RegionDTO> regionDTOMap = new HashMap<String, RegionDTO>();
			for (RegionDTO regionDTO : regionDTOs) {
				regionDTOMap.put(regionDTO.getCode(), regionDTO);
			}

			if (regionDTOMap.containsKey(orderConsigneeDTO.getCountryCode())) {
				orderConsigneeDTO.setCountry(regionDTOMap.get(orderConsigneeDTO.getCountryCode()).getName());
			}

			if (regionDTOMap.containsKey(orderConsigneeDTO.getProvinceCode())) {
				orderConsigneeDTO.setProvince(regionDTOMap.get(orderConsigneeDTO.getProvinceCode()).getName());
			}

			if (regionDTOMap.containsKey(orderConsigneeDTO.getCityCode())) {
				orderConsigneeDTO.setCity(regionDTOMap.get(orderConsigneeDTO.getCityCode()).getName());
			}

			if (regionDTOMap.containsKey(orderConsigneeDTO.getAreaCode())) {
				orderConsigneeDTO.setArea(regionDTOMap.get(orderConsigneeDTO.getAreaCode()).getName());
			}

			if (regionDTOMap.containsKey(orderConsigneeDTO.getTownCode())) {
				orderConsigneeDTO.setTown(regionDTOMap.get(orderConsigneeDTO.getTownCode()).getName());
			}
		} catch (TradeException e) {
			// TODO error handle
		}

		return orderConsigneeDTO;
	}

	

}
