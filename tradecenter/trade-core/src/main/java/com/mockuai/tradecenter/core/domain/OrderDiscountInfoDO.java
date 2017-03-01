package com.mockuai.tradecenter.core.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by zengzhangqiang on 5/24/15.
 */
public class OrderDiscountInfoDO {
    private Long id;
    private String bizCode;
    /**
     * 所属订单ID
     */
    private Long orderId;

    /**
     * 订单流水号
     */
    private String orderSn;
    /**
     * 所属订单商品ID
     */
    private Long orderItemId;
    /**
     * 所属订单组合商品ID
     */
    private Long combOrderItemId;
	
	private Integer itemType;
    /**
     * 商品id
     */
    private Long itemId;
    /**
     * 商品sku信息
     */
    private Long itemSkuId;
    /**
     * 分销商信息
     */
    private Long distributorId;
    /**
     * 下单用户ID
     */
    private Long userId;

    /**
     * 优惠类型；1代表营销活动，2代表虚拟账户
     */
    private Integer discountType;

    /**
     * 优惠码，如果是营销活动，则对应营销平台的toolCode;如果是虚拟账户，则对应虚拟账户的type;
     */
    private String discountCode;

    /**
     * 优惠描述
     */
    private String discountDesc;
    /**
     * 本优惠记录所占优惠额度
     */
    private Long discountAmount;
    /**
     * 关联的营销活动id
     */
    private Long marketActivityId;
    /**
     * 关联的营销活动子id
     */
    private Long subMarketActivityId;
    /**
     * 所使用的优惠券id
     */
    private Long userCouponId;

    /**
     * 关联的营销活动的总优惠金额
     */
    private Long activityDiscountAmount;

    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;

    public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Long getCombOrderItemId() {
		return combOrderItemId;
	}

	public void setCombOrderItemId(Long combOrderItemId) {
		this.combOrderItemId = combOrderItemId;
	}

	public Long getSubMarketActivityId() {
		return subMarketActivityId;
	}

	public void setSubMarketActivityId(Long subMarketActivityId) {
		this.subMarketActivityId = subMarketActivityId;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountDesc() {
        return discountDesc;
    }

    public void setDiscountDesc(String discountDesc) {
        this.discountDesc = discountDesc;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public Long getActivityDiscountAmount() {
        return activityDiscountAmount;
    }

    public void setActivityDiscountAmount(Long activityDiscountAmount) {
        this.activityDiscountAmount = activityDiscountAmount;
    }

    public Long getMarketActivityId() {
        return marketActivityId;
    }

    public void setMarketActivityId(Long marketActivityId) {
        this.marketActivityId = marketActivityId;
    }

    public Long getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Long userCouponId) {
        this.userCouponId = userCouponId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public static void main(String[] args) {
        String a = "19.90";
        System.out.println(Double.valueOf(a)*100);
    }
}
