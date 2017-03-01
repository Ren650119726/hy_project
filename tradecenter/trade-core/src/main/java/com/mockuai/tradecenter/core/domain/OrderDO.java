package com.mockuai.tradecenter.core.domain;

import java.util.Date;

public class OrderDO {
	private Long id;

    /**
     * 应用标志码
     */
    private String bizCode;

    /**
     * 订单流水
     */
    private String orderSn;

    /**
     * 订单类型
     */
    private Integer type;

    /**
     * 买家ID
     */
    private Long userId;

    /**
     * 卖家ID
     */
    private Long sellerId;

    /**
     * 商品总价
     */
    private Long totalPrice;

    /**
     * 运费
     */
    private Long deliveryFee;

    /**
     * 订单总支付金额（商品总价＋总运费＋总税费-优惠总金额-余额抵现-积分抵现）
     */
    private Long totalAmount;

    /**
     * 优惠标记，0代表没有任何优惠信息，1代表有优惠
     */
    private Integer discountMark;

    /**
     * 总优惠额
     */
    private Long discountAmount;


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
     * 删除标记
     */
    private Integer deleteMark;

    /**
     * 买家备注
     */
    private String userMemo;

    /**
     * 卖家备注
     */
    private String sellerMemo;

    /**
     * 管理员备注
     */
    private String adminMemo;

    /**
     * 订单附带信息，由业务接入方自己控制和使用其中的数据，平台只负责透传
     */
    private String attachInfo;
    /**
     * 下单时间
     */
    private Date orderTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 发货时间
     */
    private Date consignTime;
    /**
     * 确认收货时间
     */
    private Date receiptTime;

    private Date gmtCreated;

    private Date gmtModified;
    
    private String consignee;
    
    private String consigneeMobile;
    
    /**
     * 浮动价格
     */
    private Long floatingPrice;
    
    /**
     * 0 代表 不是加星。 1 代表是加星
     */
    private Integer asteriskMark;
    
    /**
     * 卖家取消订单原因
     */
    private String cancelReason;
    
    /**
     * 原始订单
     */
    private Long originalOrder;
    
    private Integer settlementMark;

    private Long refundAmount;
    
    private Integer refundMark;
    
    private Long point;
    
    private Long pointDiscountAmount;
    
    private Integer appType;
    
    private Long timeoutCancelSeconds;
    
    private Long activityId;

    /**
     * 跨境税费
     */
    private Long taxFee;

    /**
     * 跨境扩展信息，以json格式存放税费等信息
     */
    private String higoExtraInfo;

    /**
     * 跨境标志，如果订单商品中包含跨境商品，那么higoMark则为1，否则为0
     */
    private Integer higoMark = 0;
    
    private Long mallCommission;//商城佣金

    /**
     * 仓库id，订单需要根据仓库进行分仓分单
     */
    private Long storeId;
    /**
     * 供应商id，一个供应商对应若干仓库。供应商与仓库之间的关系是一对多的关系
     */
    private Long supplierId;

    /**
     * 父订单标志，拆单场景，父订单会打上该标志。0代表正常订单或者子订单，1代表父订单
     */
    private Integer parentMark = 0;
    /**
     * 订单取消时间
     */
    private Date cancelTime;

//    private Long shareUserId ;


//    public Long getShareUserId() {
//        return shareUserId;
//    }

//    public void setShareUserId(Long shareUserId) {
//        this.shareUserId = shareUserId;
//    }

    public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Integer getRefundMark() {
		return refundMark;
	}

	public void setRefundMark(Integer refundMark) {
		this.refundMark = refundMark;
	}

	public Long getOriginalOrder() {
		return originalOrder;
	}

	public void setOriginalOrder(Long originalOrder) {
		this.originalOrder = originalOrder;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
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

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getUserMemo() {
        return userMemo;
    }

    public void setUserMemo(String userMemo) {
        this.userMemo = userMemo;
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }

    public String getAdminMemo() {
        return adminMemo;
    }

    public void setAdminMemo(String adminMemo) {
        this.adminMemo = adminMemo;
    }

    public String getAttachInfo() {
        return attachInfo;
    }

    public void setAttachInfo(String attachInfo) {
        this.attachInfo = attachInfo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getConsignTime() {
        return consignTime;
    }

    public void setConsignTime(Date consignTime) {
        this.consignTime = consignTime;
    }

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public Long getFloatingPrice() {
		return floatingPrice;
	}

	public void setFloatingPrice(Long floatingPrice) {
		this.floatingPrice = floatingPrice;
	}

	public Integer getAsteriskMark() {
		return asteriskMark;
	}

	public void setAsteriskMark(Integer asteriskMark) {
		this.asteriskMark = asteriskMark;
	}

	public Integer getSettlementMark() {
		return settlementMark;
	}

	public void setSettlementMark(Integer settlementMark) {
		this.settlementMark = settlementMark;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public Long getPointDiscountAmount() {
		return pointDiscountAmount;
	}

	public void setPointDiscountAmount(Long pointDiscountAmount) {
		this.pointDiscountAmount = pointDiscountAmount;
	}

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	public Long getTimeoutCancelSeconds() {
		return timeoutCancelSeconds;
	}

	public void setTimeoutCancelSeconds(Long timeoutCancelSeconds) {
		this.timeoutCancelSeconds = timeoutCancelSeconds;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}


    public Long getTaxFee() {
        return taxFee;
    }

    public void setTaxFee(Long taxFee) {
        this.taxFee = taxFee;
    }

    public String getHigoExtraInfo() {
        return higoExtraInfo;
    }

    public void setHigoExtraInfo(String higoExtraInfo) {
        this.higoExtraInfo = higoExtraInfo;
    }
	public Long getMallCommission() {
		return mallCommission;
	}

	public void setMallCommission(Long mallCommission) {
		this.mallCommission = mallCommission;
	}

    public Integer getHigoMark() {
        return higoMark;
    }

    public void setHigoMark(Integer higoMark) {
        this.higoMark = higoMark;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getParentMark() {
        return parentMark;
    }

    public void setParentMark(Integer parentMark) {
        this.parentMark = parentMark;
    }
}
