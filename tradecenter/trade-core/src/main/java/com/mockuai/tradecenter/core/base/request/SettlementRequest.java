package com.mockuai.tradecenter.core.base.request;

import java.util.List;

import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.UsedCouponDTO;
import com.mockuai.tradecenter.common.domain.UsedWealthDTO;

public class SettlementRequest{

	Long consigneeId;
	
	List<OrderItemDTO> orderItems;
	
	/**
	 * 优惠券使用标记(二进制方式存储)，0代表未使用优惠券，1代表使用了优惠券
	 */
	private Long couponMark;
	
	/**
	 * 订单使用优惠券信息列表
	 */
	private List<UsedCouponDTO> usedCouponDTOs;
	/**
	 * 订单使用虚拟财富信息列表
	 */
	private List<UsedWealthDTO> usedWealthDTOs;

	public Long getConsigneeId() {
		return consigneeId;
	}

	public void setConsigneeId(Long consigneeId) {
		this.consigneeId = consigneeId;
	}

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}

	public Long getCouponMark() {
		return couponMark;
	}

	public void setCouponMark(Long couponMark) {
		this.couponMark = couponMark;
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
	
	
	
}
