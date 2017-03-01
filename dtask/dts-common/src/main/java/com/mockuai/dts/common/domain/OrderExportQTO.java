package com.mockuai.dts.common.domain;

import java.io.Serializable;
import java.util.Date;

import com.mockuai.itemcenter.common.page.PageInfo;
/**
 * 
 * @author liuchao
 *
 */
public class OrderExportQTO extends PageInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7945658600833594821L;

	


	/**
	 *订单ID
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

	private Long shareUserId;

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
    
    
    private String appKey;
    
    private String orderStatusStr;

	private String itemName;
	
	private Long itemSkuId;
    

	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public Long getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Long shareUserId) {
		this.shareUserId = shareUserId;
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

	public Long getSupplierStoreId() {
		return supplierStoreId;
	}

	public void setSupplierStoreId(Long supplierStoreId) {
		this.supplierStoreId = supplierStoreId;
	}

	public Integer getHigoMark() {
		return higoMark;
	}

	public void setHigoMark(Integer higoMark) {
		this.higoMark = higoMark;
	}

	public String getOrderStatusStr() {
		return orderStatusStr;
	}

	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
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

	
	
	


}
