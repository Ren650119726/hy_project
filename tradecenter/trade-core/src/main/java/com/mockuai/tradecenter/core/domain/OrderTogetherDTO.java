package com.mockuai.tradecenter.core.domain;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.UsedCouponDTO;
import com.mockuai.tradecenter.common.domain.UsedWealthDTO;
import com.mockuai.tradecenter.core.base.result.ItemResponse;
/**
 * 用于拆单的订单聚合DTO
 * @author hzmk
 *
 */
public class OrderTogetherDTO {
	
	private boolean needPay = true;
	
	private OrderDO orderDO; //订单
	
	private List<OrderItemDO> itemList;//商品
	
	private List<OrderItemDTO> orderItemDTOList;
	
	private List<OrderServiceDO> itemServiceList;//商品服务列表
	
	private List<OrderDiscountInfoDO> orderDiscountInfoDOs;//折扣
	
	private OrderConsigneeDO orderConsigneeDO;//收货地址
	
	private OrderInvoiceDO orderInvoiceDO;
	
	private OrderPaymentDO orderPaymentDO;
	
	private OrderViewDO  orderViewDO;
	
	private OrderStoreDO orderStoreDO;
	
	private Long wealthDiscountAmount;//虚拟财富折扣金额
	
	private Long pointAmount;
	
	/**
	 * 订单使用优惠券信息列表
	 */
	private List<UsedCouponDTO> usedCouponDTOs;
	
	/**
	 * 订单使用虚拟财富列表
	 */
	private List<UsedWealthDTO> usedWealthDTOs;
	
	ItemResponse itemResponse;
	
	

	public boolean isNeedPay() {
		return needPay;
	}

	public void setNeedPay(boolean needPay) {
		this.needPay = needPay;
	}

	public List<OrderItemDTO> getOrderItemDTOList() {
		return orderItemDTOList;
	}

	public void setOrderItemDTOList(List<OrderItemDTO> orderItemDTOList) {
		this.orderItemDTOList = orderItemDTOList;
	}

	public OrderViewDO getOrderViewDO() {
		return orderViewDO;
	}

	public void setOrderViewDO(OrderViewDO orderViewDO) {
		this.orderViewDO = orderViewDO;
	}

	public OrderInvoiceDO getOrderInvoiceDO() {
		return orderInvoiceDO;
	}

	public void setOrderInvoiceDO(OrderInvoiceDO orderInvoiceDO) {
		this.orderInvoiceDO = orderInvoiceDO;
	}

	public OrderPaymentDO getOrderPaymentDO() {
		return orderPaymentDO;
	}

	public void setOrderPaymentDO(OrderPaymentDO orderPaymentDO) {
		this.orderPaymentDO = orderPaymentDO;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}

	public List<OrderItemDO> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderItemDO> itemList) {
		this.itemList = itemList;
	}

	public List<OrderDiscountInfoDO> getOrderDiscountInfoDOs() {
		return orderDiscountInfoDOs;
	}

	public void setOrderDiscountInfoDOs(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
		this.orderDiscountInfoDOs = orderDiscountInfoDOs;
	}

	public OrderConsigneeDO getOrderConsigneeDO() {
		return orderConsigneeDO;
	}

	public void setOrderConsigneeDO(OrderConsigneeDO orderConsigneeDO) {
		this.orderConsigneeDO = orderConsigneeDO;
	}

	public Long getWealthDiscountAmount() {
		return wealthDiscountAmount;
	}

	public void setWealthDiscountAmount(Long wealthDiscountAmount) {
		this.wealthDiscountAmount = wealthDiscountAmount;
	}

	public OrderStoreDO getOrderStoreDO() {
		return orderStoreDO;
	}

	public void setOrderStoreDO(OrderStoreDO orderStoreDO) {
		this.orderStoreDO = orderStoreDO;
	}

	public Long getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(Long pointAmount) {
		this.pointAmount = pointAmount;
	}

	public List<OrderServiceDO> getItemServiceList() {
		return itemServiceList;
	}

	public void setItemServiceList(List<OrderServiceDO> itemServiceList) {
		this.itemServiceList = itemServiceList;
	}

	public List<UsedCouponDTO> getUsedCouponDTOs() {
		return usedCouponDTOs;
	}

	public void setUsedCouponDTOs(List<UsedCouponDTO> usedCouponDTOs) {
		this.usedCouponDTOs = usedCouponDTOs;
	}

	public List<UsedWealthDTO> getUsedWealthDTOs() {
		return usedWealthDTOs;
	}

	public void setUsedWealthDTOs(List<UsedWealthDTO> usedWealthDTOs) {
		this.usedWealthDTOs = usedWealthDTOs;
	}

	public ItemResponse getItemResponse() {
		return itemResponse;
	}

	public void setItemResponse(ItemResponse itemResponse) {
		this.itemResponse = itemResponse;
	}
	
	
	
	

}
