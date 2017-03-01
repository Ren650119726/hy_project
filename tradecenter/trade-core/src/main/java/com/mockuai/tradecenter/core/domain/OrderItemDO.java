package com.mockuai.tradecenter.core.domain;

import java.util.Date;
import java.util.List;

public class OrderItemDO {
    private Long id;
    private String bizCode;
    /**
     * 下单用户ID
     */
    private Long userId;
    /**
     * 分享人ID
     */
    private Long shareUserId;
    /**
     * 下单用户昵称
     */
    private String userName;
    /**
     * 所属订单ID
     */
    private Long orderId;
    /**
     * 商品ID
     */
    private Long itemId;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品主图URL
     */
    private String itemImageUrl;
    /**
     * 商品SKU ID
     */
    private Long itemSkuId;
    /**
     * 规格描述
     */
    private String itemSkuDesc;
    /**
     * 商品卖家ID
     */
    private Long sellerId;
    /**
     * 商品单价
     */
    private Long unitPrice;
    /**
     * 商品的发货方式
     */
    private Integer deliveryType;
    /**
     * 下单数量
     */
    private Integer number;
    /**
     * 跨境标志 1代表跨境商品，0代表非跨境商品
     */
    private Integer higoMark;
    /**
     * 跨境扩展信息，以json格式存放
     */
    private String higoExtraInfo;
    /**
     * 逻辑删除标记
     */
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;
    
    /** add cateogry_id/ item_brand_id  用来统计 分类、和品牌 成交额
     * 
     */
    private Long categoryId;
    
    private Long itemBrandId;

    /**
     * 用户申请的退款金额
     */
    private Long refundAmount;
    
    private Integer refundReasonId;
    
    private Long paymentAmount;//实付金额
    
    private Integer refundStatus;
    
    private Integer refundType;
    
    private String refundBatchNo;//退款批次号

    @Deprecated
    private Long discountAmount;//折扣金额（这里只是记录虚拟财富的金额）

    @Deprecated
    private Long point;//积分 

    @Deprecated
    private Long pointAmount;//
    
    private Long deliveryInfoId;
    
    private List<Long> skuIdList;
    
    private Integer deliveryMark;
    
    private Integer itemType;
    
    private Long originalSkuId;
    
    private Long activityId;
    
    private List<OrderServiceDO> orderServiceDOList;
    
    private Long serviceUnitPrice;//服务单价
    
    private boolean isSuitSubItem;
    
    private List<Long> orderItemIds;
    private Integer saleNumber;
    
    private Integer virtualMark;

    /**
     * 分销商id，代表该商品是从哪个分销商哪里购买的
     */
    private Long distributorId;

    /**
     * 分销商店铺名称
     */
    private String distributorName;

    /**
     * 该订单商品所使用虚拟财富数量
     */
    private Long virtualWealthAmount;

    public Long getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Long shareUserId) {
		this.shareUserId = shareUserId;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }

    public String getItemSkuDesc() {
        return itemSkuDesc;
    }

    public void setItemSkuDesc(String itemSkuDesc) {
        this.itemSkuDesc = itemSkuDesc;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getItemBrandId() {
		return itemBrandId;
	}

	public void setItemBrandId(Long itemBrandId) {
		this.itemBrandId = itemBrandId;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getRefundReasonId() {
		return refundReasonId;
	}

	public void setRefundReasonId(Integer refundReasonId) {
		this.refundReasonId = refundReasonId;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public String getRefundBatchNo() {
		return refundBatchNo;
	}

	public void setRefundBatchNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}

	public Long getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public Long getDeliveryInfoId() {
		return deliveryInfoId;
	}

	public void setDeliveryInfoId(Long deliveryInfoId) {
		this.deliveryInfoId = deliveryInfoId;
	}

	public List<Long> getSkuIdList() {
		return skuIdList;
	}

	public void setSkuIdList(List<Long> skuIdList) {
		this.skuIdList = skuIdList;
	}

	public Integer getDeliveryMark() {
		return deliveryMark;
	}

	public void setDeliveryMark(Integer deliveryMark) {
		this.deliveryMark = deliveryMark;
	}

	public Long getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(Long pointAmount) {
		this.pointAmount = pointAmount;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Long getOriginalSkuId() {
		return originalSkuId;
	}

	public void setOriginalSkuId(Long originalSkuId) {
		this.originalSkuId = originalSkuId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public List<OrderServiceDO> getOrderServiceDOList() {
		return orderServiceDOList;
	}

	public void setOrderServiceDOList(List<OrderServiceDO> orderServiceDOList) {
		this.orderServiceDOList = orderServiceDOList;
	}

	public Long getServiceUnitPrice() {
		return serviceUnitPrice;
	}

	public void setServiceUnitPrice(Long serviceUnitPrice) {
		this.serviceUnitPrice = serviceUnitPrice;
	}

    public Integer getHigoMark() {
        return higoMark;
    }

    public void setHigoMark(Integer higoMark) {
        this.higoMark = higoMark;
    }

    public String getHigoExtraInfo() {
        return higoExtraInfo;
    }

    public void setHigoExtraInfo(String higoExtraInfo) {
        this.higoExtraInfo = higoExtraInfo;
    }

	public boolean isSuitSubItem() {
		return isSuitSubItem;
	}

	public void setSuitSubItem(boolean isSuitSubItem) {
		this.isSuitSubItem = isSuitSubItem;
	}

	public List<Long> getOrderItemIds() {
		return orderItemIds;
	}

	public void setOrderItemIds(List<Long> orderItemIds) {
		this.orderItemIds = orderItemIds;
	}
	public Integer getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(Integer saleNumber) {
		this.saleNumber = saleNumber;
	}

	public Integer getVirtualMark() {
		return virtualMark;
	}

	public void setVirtualMark(Integer virtualMark) {
		this.virtualMark = virtualMark;
	}

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public Long getVirtualWealthAmount() {
        return virtualWealthAmount;
    }

    public void setVirtualWealthAmount(Long virtualWealthAmount) {
        this.virtualWealthAmount = virtualWealthAmount;
    }
}