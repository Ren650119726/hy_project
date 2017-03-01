package com.mockuai.tradecenter.mop.api.domain;

import java.util.Date;
import java.util.List;


public class MopOrderDTO {
    private String orderUid;
    private String orderSn;
    private boolean isInvoice;
    private String userMemo;
    
	/**
	 * 订单类型
	 */
	private Integer type;
    
    /**
     * 订单附带信息，由业务接入方自己控制和使用其中的数据，平台只负责透传
     */
    private String attachInfo;
    private Integer orderStatus;
    /**
     * 订单总价
     */
    private Long totalPrice;
    /**
     * 订单总金额
     */
    private Long totalAmount;
    private Long deliveryFee;
    /**
     * 总优惠金额
     */
    private Long discountAmount;
    private Integer paymentId;
    private Integer deliveryId;
    private String orderTime;
    private String payTime;
    private String cancelOrderTime;
    private Integer payTimeout;
    private String consignTime;
    private String receiptTime;
    /**
     * 订单来源类型，1代表来自购物车，2代表来自立即购买
     */
    private Integer sourceType;
    private List<MopOrderItemDTO> orderItemList;
    private MopSellerDTO seller;
    private MopInvoiceDTO invoice;
    private MopConsigneeDTO consignee;
    private MopOrderPaymentDTO orderPayment;
    private List<MopDeliveryInfoDTO> deliveryInfoList;
    private List<MopOrderDiscountInfoDTO> orderDiscountList;
    private List<MopUsedCouponDTO> couponList;
    private List<MopUsedWealthDTO> wealthAccountList;
    
    private String storeUid;
    private Integer deliveryType;
    private String pickupTime;
    //税费
    private Long taxFee;
    //跨境扩展信息
    private MopHigoExtraInfoDTO higoExtraInfo;
    
    private MopOrderStoreDTO orderStore;
    
    private Integer refundMark;
    
    private String sellerMemo;
    /**
     * 分销商订单商品列表
     */
    private List<MopDistributorOrderItemDTO> distributorOrderItemList;


    /**
     * 订单取消时间
     */
    private String cancelTime;
    

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelOrderTime() {
		return cancelOrderTime;
	}

	public void setCancelOrderTime(String cancelOrderTime) {
		this.cancelOrderTime = cancelOrderTime;
	}

	public Integer getPayTimeout() {
		return payTimeout;
	}

	public void setPayTimeout(Integer payTimeout) {
		this.payTimeout = payTimeout;
	}

	public String getOrderUid() {
        return this.orderUid;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }

    public String getOrderSn() {
        return this.orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public boolean isInvoice() {
        return this.isInvoice;
    }

    public void setInvoice(boolean isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getUserMemo() {
        return this.userMemo;
    }

    public void setUserMemo(String userMemo) {
        this.userMemo = userMemo;
    }

    public String getAttachInfo() {
        return attachInfo;
    }

    public void setAttachInfo(String attachInfo) {
        this.attachInfo = attachInfo;
    }

    public Integer getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getDeliveryFee() {
        return this.deliveryFee;
    }

    public void setDeliveryFee(Long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getOrderTime() {
        return this.orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayTime() {
        return this.payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getConsignTime() {
        return this.consignTime;
    }

    public void setConsignTime(String consignTime) {
        this.consignTime = consignTime;
    }

    public String getReceiptTime() {
        return this.receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public List<MopOrderItemDTO> getOrderItemList() {
        return this.orderItemList;
    }

    public void setOrderItemList(List<MopOrderItemDTO> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public MopSellerDTO getSeller() {
        return this.seller;
    }

    public void setSeller(MopSellerDTO seller) {
        this.seller = seller;
    }

    public MopInvoiceDTO getInvoice() {
        return this.invoice;
    }

    public void setInvoice(MopInvoiceDTO invoice) {
        this.invoice = invoice;
    }

    public MopConsigneeDTO getConsignee() {
        return this.consignee;
    }

    public void setConsignee(MopConsigneeDTO consignee) {
        this.consignee = consignee;
    }

    public MopOrderPaymentDTO getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(MopOrderPaymentDTO orderPayment) {
        this.orderPayment = orderPayment;
    }

    public List<MopDeliveryInfoDTO> getDeliveryInfoList() {
        return deliveryInfoList;
    }

    public void setDeliveryInfoList(List<MopDeliveryInfoDTO> deliveryInfoList) {
        this.deliveryInfoList = deliveryInfoList;
    }

    public List<MopOrderDiscountInfoDTO> getOrderDiscountList() {
        return orderDiscountList;
    }

    public void setOrderDiscountList(List<MopOrderDiscountInfoDTO> orderDiscountList) {
        this.orderDiscountList = orderDiscountList;
    }

    public List<MopUsedCouponDTO> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<MopUsedCouponDTO> couponList) {
        this.couponList = couponList;
    }

    public List<MopUsedWealthDTO> getWealthAccountList() {
        return wealthAccountList;
    }

    public void setWealthAccountList(List<MopUsedWealthDTO> wealthAccountList) {
        this.wealthAccountList = wealthAccountList;
    }

	public String getStoreUid() {
		return storeUid;
	}

	public void setStoreUid(String storeUid) {
		this.storeUid = storeUid;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	public MopOrderStoreDTO getOrderStore() {
		return orderStore;
	}

	public void setOrderStore(MopOrderStoreDTO orderStore) {
		this.orderStore = orderStore;
	}

    public Long getTaxFee() {
        return taxFee;
    }

    public void setTaxFee(Long taxFee) {
        this.taxFee = taxFee;
    }

    public MopHigoExtraInfoDTO getHigoExtraInfo() {
        return higoExtraInfo;
    }

    public void setHigoExtraInfo(MopHigoExtraInfoDTO higoExtraInfo) {
        this.higoExtraInfo = higoExtraInfo;
    }

	public Integer getRefundMark() {
		return refundMark;
	}

	public void setRefundMark(Integer refundMark) {
		this.refundMark = refundMark;
	}

	public String getSellerMemo() {
		return sellerMemo;
	}

	public void setSellerMemo(String sellerMemo) {
		this.sellerMemo = sellerMemo;
	}

    public List<MopDistributorOrderItemDTO> getDistributorOrderItemList() {
        return distributorOrderItemList;
    }

    public void setDistributorOrderItemList(List<MopDistributorOrderItemDTO> distributorOrderItemList) {
        this.distributorOrderItemList = distributorOrderItemList;
    }
}
