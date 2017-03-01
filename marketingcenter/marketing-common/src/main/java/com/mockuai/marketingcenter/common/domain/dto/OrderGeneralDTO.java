package com.mockuai.marketingcenter.common.domain.dto;

/**
 * Created by edgar.zr on 7/25/2016.
 */
public class OrderGeneralDTO {

	/**
	 * 用户数量
	 */
	private Integer userCount;
	/**
	 * 订单数量
	 */
	private Integer orderCount;
	/**
	 * 订单金额
	 */
	private Long total;
	/**
	 * 客单价 = total/ userCount
	 */
	private Long avgTotal;
	/**
	 * 总优惠值, 实付 = total - discount
	 */
	private Long discount;
	/**
	 * 退款金额
	 */
	private Long refund;

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getAvgTotal() {
		return avgTotal;
	}

	public void setAvgTotal(Long avgTotal) {
		this.avgTotal = avgTotal;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getRefund() {
		return refund;
	}

	public void setRefund(Long refund) {
		this.refund = refund;
	}
}