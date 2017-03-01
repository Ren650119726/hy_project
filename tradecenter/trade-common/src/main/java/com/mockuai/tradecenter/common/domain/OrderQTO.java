package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderQTO extends BaseQTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2327606512438246521L;

	/**
	 * 订单ID
	 */
	private Long id;

	/**
	 * 卖家ID
	 */
	private Long sellerId;

	/**
	 * 买家ID
	 */
	private Long userId;

	/**
	 * 订单类型
	 */
	private Integer type;

	/**
	 * 订单流水号
	 */
	private String orderSn;

	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 订单状态串
	 */
	private String orderStatusStr;
	/**
	 * 配送方式ID
	 */
	private Integer deliveryId;

	/**
	 * 支付方式ID
	 */
	private Integer paymentId;
	/**
	 * 是否需要发票，0代表不需要，1代表需要
	 */
	private Integer invoiceMark;
	/**
	 * 优惠标记，0代表没有任何优惠信息，1代表有优惠
	 */
	private Integer discountMark;

	/**
	 * 订单开始时间
	 */
	private Date orderTimeStart;

	/**
	 * 订单结束时间
	 */
	private Date orderTimeEnd;

	/**
	 * 支付时间起始点
	 */
	private Date payTimeStart;

	/**
	 * 支付时间结束点
	 */
	private Date payTimeEnd;
	
	/**
	 * 供应商id
	 */
	private Long supplierId;

	/*仓库id*/
	private Long supplierStoreId;
	
	/**
	 * 是否跨境
	 */
	private Integer higoMark;

	/**
	 * 收货人手机
	 */
	private String consigneeMobile;

	/**
	 * 收货人姓名
	 */
	private String consignee;
	
    
    


	/**
	 * 0 代表 不是加星。 1 代表是加星
	 */
	private Integer asteriskMark;

	/**
	 * 超时分钟数
	 */
	private Integer timeoutMinuteNumber;

	private Long itemId;

	/**
	 * 原始订单
	 */
	private Long originalOrder;
	
	private List<Long> sellerIds;
	
	private List<Long> userIds;
	
	private String userName;
	
	private String bizCode;
	
	private int timeoutDeliveryDay;
	
	/**
	 * 排序类型
	 */
	private String sortType;
	
	private Integer refundStatus;
	
	private Long storeId;
	
	private Integer deleteMark;//是否删除标注
	
	private Long itemSkuId;
	
	private Integer settlementMark;
	
	private List<Long> orderIds;
	
	private Integer allRefundingMark;//
	
	private Integer refundMark;
	
	List<OrderItemDTO> itemList;
	
	private Integer parentMark;

	private Long shareUserId;

	private String itemName;
	
	

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Long shareUserId) {
		this.shareUserId = shareUserId;
	}

	public Integer getParentMark() {
		return parentMark;
	}

	public void setParentMark(Integer parentMark) {
		this.parentMark = parentMark;
	}

	public Long getSupplierStoreId() {
		return supplierStoreId;
	}

	public void setSupplierStoreId(Long supplierStoreId) {
		this.supplierStoreId = supplierStoreId;
	}

	public String getOrderStatusStr() {
		return orderStatusStr;
	}

	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}

	public List<OrderItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderItemDTO> itemList) {
		this.itemList = itemList;
	}

	public int getTimeoutDeliveryDay() {
		return timeoutDeliveryDay;
	}

	public void setTimeoutDeliveryDay(int timeoutDeliveryDay) {
		this.timeoutDeliveryDay = timeoutDeliveryDay;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public List<Long> getSellerIds() {
		return sellerIds;
	}

	public void setSellerIds(List<Long> sellerIds) {
		this.sellerIds = sellerIds;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getAsteriskMark() {
		return asteriskMark;
	}

	public void setAsteriskMark(Integer asteriskMark) {
		this.asteriskMark = asteriskMark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getInvoiceMark() {
		return invoiceMark;
	}

	public void setInvoiceMark(Integer invoiceMark) {
		this.invoiceMark = invoiceMark;
	}

	public Integer getDiscountMark() {
		return discountMark;
	}

	public void setDiscountMark(Integer discountMark) {
		this.discountMark = discountMark;
	}

	public Date getOrderTimeStart() {
		return orderTimeStart;
	}

	public void setOrderTimeStart(Date orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}

	public Date getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Date orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public Integer getTimeoutMinuteNumber() {
		return timeoutMinuteNumber;
	}

	public void setTimeoutMinuteNumber(Integer timeoutMinuteNumber) {
		this.timeoutMinuteNumber = timeoutMinuteNumber;
	}

	public Long getOriginalOrder() {
		return originalOrder;
	}

	public void setOriginalOrder(Long originalOrder) {
		this.originalOrder = originalOrder;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Integer getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public Integer getSettlementMark() {
		return settlementMark;
	}

	public void setSettlementMark(Integer settlementMark) {
		this.settlementMark = settlementMark;
	}

	public List<Long> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<Long> orderIds) {
		this.orderIds = orderIds;
	}

	public Integer getAllRefundingMark() {
		return allRefundingMark;
	}

	public void setAllRefundingMark(Integer allRefundingMark) {
		this.allRefundingMark = allRefundingMark;
	}

	public Integer getRefundMark() {
		return refundMark;
	}

	public void setRefundMark(Integer refundMark) {
		this.refundMark = refundMark;
	}

	public Date getPayTimeStart() {
		return payTimeStart;
	}

	public void setPayTimeStart(Date payTimeStart) {
		this.payTimeStart = payTimeStart;
	}

	public Date getPayTimeEnd() {
		return payTimeEnd;
	}

	public void setPayTimeEnd(Date payTimeEnd) {
		this.payTimeEnd = payTimeEnd;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getHigoMark() {
		return higoMark;
	}

	public void setHigoMark(Integer higoMark) {
		this.higoMark = higoMark;
	}

	
	
}
