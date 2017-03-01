package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DataQTO  extends BaseQTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8256390392150692435L;
	/**
	 * 查询TOP10的商品
	 */
	public  static int  QUERY_TOP10_ITEM=0;
	/**
	 * 查询成交额
	 */
	public  static int  GET_TOTAL_AMOUNT=1;
	/**
	 * 查询用户订单总数
	 */
	public  static int  GET_TOTAL_ORDER_COUNT=2;
	/**
	 * 查询已支付订单数
	 */
	public  static int  GET_PAID_ORDER_COUNT=3;
	/**
	 * 查询用户总数
	 */
	public  static int  GET_TOTAL_USER_COUNT=4;
	/**
	 * 查询已支付用户数
	 */
	public  static int  GET_PIAD_USER_COUNT=5;
	/**
	 * 查询客单价
	 */
	public  static int  GET_PRICE_OF_USER_AVERAGE=6;
	/**
	 * 查询下单转化率
	 */
	public  static int  GET_ORDER_CONVERSION_RATE=7;
	/**
	 * 查询支付转化率
	 */
	public  static int  GET_PAID_CONVERSION_RATE=8;
	/**
	 * 查询重复购买率
	 */
	public  static int  GET_REPEAT_PURCHASE_RATE=9;
	
	/**
	 * 按天查询成交额
	 */
	public  static int  GET_TOTAL_AMOUNT_DAILY=10;
	/**
	 * 按天查询订单总数
	 */
	public  static int  GET_TOTAL_ORDER_COUNT_DAILT=11;
	/**
	 * 按天查询已支付订单数
	 */
	public  static int  GET_PAID_ORDER_COUNT_DAILY=12;
	/**
	 * 按天查询用户总数
	 */
	public  static int  GET_TOTAL_USER_COUNT_DAILY=13;
	/**
	 * 按天查询已支付用户数
	 */
	public  static int  GET_PIAD_USER_COUNT_DAILY=14;
	/**
	 * 按天查询客单价
	 */
	public  static int  GET_PRICE_OF_USER_AVERAGE_DAILY=15;
	/**
	 * 按天查询下单转化率
	 */
	public  static int  GET_ORDER_CONVERSION_RATE_DAILY=16;
	/**
	 * 按天查询支付转化率
	 */
	public  static int  GET_PAID_CONVERSION_RATE_DAILY=17;
	/**
	 * 按天查询重复购买率
	 */
	public  static int  GET_REPEAT_PURCHASE_RATE_DAILY=18;
	
	public static int GET_ITEM_COUNT = 20;
	
	/**
	 * 卖家id
	 */
	private Long seller_id;
	
	/**
	 * 开始时间
	 */
	private Date timeStart;
	
	/**
	 * 结束时间
	 */
	private Date timeEnd;
	
	/**
	 * top数值
	 */
	private long top_number=10;
	
	/**
	 * 设备型号
	 */
	private Integer device_type;
	
	/**
	 * 查询数据的类型
	 */
	private int data_type= -1;
	
	private String bizCode;
	
	private String groupType;
	
	
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	
	private Long itemId;
	
	private Long userId;
	
	private Long itemSkuId;
	
	private List<Long> itemIds;
	

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Long getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(Long seller_id) {
		this.seller_id = seller_id;
	}


	public void setDevice_type(int device_type) {
		this.device_type = device_type;
	}


	public void setSeller_id(long seller_id) {
		this.seller_id = seller_id;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public long getTop_number() {
		return top_number;
	}

	public void setTop_number(long top_number) {
		this.top_number = top_number;
	}

	public Integer getDevice_type() {
		return device_type;
	}

	public void setDevice_type(Integer device_type) {
		this.device_type = device_type;
	}

	public int getData_type() {
		return data_type;
	}

	public void setData_type(int data_type) {
		this.data_type = data_type;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public List<Long> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<Long> itemIds) {
		this.itemIds = itemIds;
	}

 

	
}
