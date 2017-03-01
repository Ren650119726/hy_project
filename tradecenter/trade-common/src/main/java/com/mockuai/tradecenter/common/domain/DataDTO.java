package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

public class DataDTO<T> implements Serializable {
	
	/**
	 * 序列化的随机ID
	 */
	private static final long serialVersionUID = 2435620977968771243L;

	private T  data;

	/**
	 * 总成交额
	 */
	private long totalAmount;
	
	/**
	 * 总订单数
	 */
	private long totalOrderCount;
	
	/**
	 * 已支付订单数
	 */
	private long paidOrderCount;
	
	/**
	 * 总用户数
	 */
	private long totalUserCount;
	
	/**
	 * 已支付用户数
	 */
	private long paidUserCount;
	
	/**
	 * 客单价
	 */
	private long priceOfUserAverage;
	
	/**
	 * 下单转化率
	 */
	private double orderConversionRate;
	
	/**
	 * 支付转化率
	 */
	private Double paidConversionRate;
	
	/**
	 * 重复购买率
	 */
	private double repeatPurchaseRate;
	
	private Long itemCount;
	
	private Long paidItemCount;
	
	/**
	 * 总商品金额
	 */
	private long totalPrice;
	
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Double getRepeatPurchaseRate() {
		return repeatPurchaseRate;
	}

	public void setRepeatPurchaseRate(Double repeatPurchaseRate) {
		this.repeatPurchaseRate = repeatPurchaseRate;
	}

	
	
	

	public Double getPaidConversionRate() {
		return paidConversionRate;
	}

	public void setPaidConversionRate(Double paidConversionRate) {
		this.paidConversionRate = paidConversionRate;
	}

	public void setRepeatPurchaseRate(double repeatPurchaseRate) {
		this.repeatPurchaseRate = repeatPurchaseRate;
	}



	public double getOrderConversionRate() {
		return orderConversionRate;
	}

	public void setOrderConversionRate(double conversionRate) {
		this.orderConversionRate = conversionRate;
	}

	public long getPriceOfUserAverage() {
		return priceOfUserAverage;
	}

	public void setPriceOfUserAverage(long priceOfEveryUser) {
		this.priceOfUserAverage = priceOfEveryUser;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getTotalOrderCount() {
		return totalOrderCount;
	}

	public void setTotalOrderCount(long totalOrderAmount) {
		this.totalOrderCount = totalOrderAmount;
	}

	public long getPaidOrderCount() {
		return paidOrderCount;
	}

	public void setPaidOrderCount(long paidOrderAmount) {
		this.paidOrderCount = paidOrderAmount;
	}

	public long getTotalUserCount() {
		return totalUserCount;
	}

	public void setTotalUserCount(long totalUserAmount) {
		this.totalUserCount = totalUserAmount;
	}

	public long getPaidUserCount() {
		return paidUserCount;
	}

	public void setPaidUserCount(long paidUserAmount) {
		this.paidUserCount = paidUserAmount;
	}

	public Long getItemCount() {
		return itemCount;
	}

	public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}

	public Long getPaidItemCount() {
		return paidItemCount;
	}

	public void setPaidItemCount(Long paidItemCount) {
		this.paidItemCount = paidItemCount;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
	
}
