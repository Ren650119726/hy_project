package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;

/**
 * Created by zengzhangqiang on 5/24/15.
 */
public class OrderDiscountInfoDTO implements Serializable{
    private Long id;
    private String bizCode;
    /**
     * 所属订单ID
     */
    private Long orderId;
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
     * 优惠类型
     */
    private Integer discountType;

    /**
     * 优惠标志码，如果优惠类型为1（营销活动），则discountCode对应toolCode;如果优惠类型为2（虚拟账户），则discountCode对应wealthType
     */
    private String discountCode;

    /**
     * 优惠描述
     */
    private String discountDesc;
    /**
     * 优惠额度
     */
    private Long discountAmount;

    /**
     * 关联的营销活动id
     */
    private Long marketActivityId;

    /**
     * 关联的营销活动id
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

    public Long getActivityDiscountAmount() {
        return activityDiscountAmount;
    }

    public void setActivityDiscountAmount(Long activityDiscountAmount) {
        this.activityDiscountAmount = activityDiscountAmount;
    }
}
