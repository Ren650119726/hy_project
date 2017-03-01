package com.mockuai.tradecenter.common.domain;

import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;

import java.io.Serializable;
import java.util.List;

public class OrderItemDTO implements Serializable {
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
		
	private String shareUserMobile;//分享用户手机号
	
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
	 * 组合商品SKU ID
	 */
	private Long combineItemSkuId;
	/**
	 * 组合商品总价
	 */
	private Long combineItemPrice;
	/**
	 * 组合商品名称
	 */
	private String combineItemName;
	/**
	 * 商品规格描述
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

	private String unitPriceStr;
	/**
	 * 商品的发货方式
	 */
	private Integer deliveryType;
	/**
	 * 下单数量
	 */
	private Integer number;
	/**
	 * 组合商品下单数量
	 */
	private Integer combineItemNumber;

	private String sellerName;

	List<SkuPropertyDTO> skuPropertyList;

	private ItemSkuDTO sku;

	private Long categoryId;

	private Long itemBrandId;
	
	private String itemUrl;
	
	private Integer itemType;
	
	private String outTradeNo; //第三方流水
	
	private Long refundAmount;//退款金额
	
	private Long paymentAmount;//实付金额
	
	private Integer refundStatus;
	
    private Integer refundReasonId;
    
    private Integer refundType;
    
    private Long discountAmount;
    
    private Long point;
    
    private Long pointAmount;//积分金额
    
    private Integer deliveryMark;

    
    private Long deliveryInfoId;
    
    private List<ItemServiceDTO> serviceList;

	//换购活动的条件商品列表
    private List<OrderItemDTO> itemList;
    
    private Long originalSkuId;
    
    private Long activityId;
    
    private List<OrderServiceDTO> orderServiceList;

	/**
	 * 跨境扩展信息，包含税费、税率等信息
	 */
	private HigoExtraInfoDTO higoExtraInfoDTO;
	
	private Long originalOrderItemId;
	
	private Integer canRefundMark;
	
	private Integer higoMark;
		
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
	 * 所使用的虚拟财富
	 */
	private Long virtualWealthAmount;
    /**
     * 商品编码
      */
    private String itemSn;

    
	

	

	

	public Integer getCombineItemNumber() {
		return combineItemNumber;
	}

	public void setCombineItemNumber(Integer combineItemNumber) {
		this.combineItemNumber = combineItemNumber;
	}

	public String getCombineItemName() {
		return combineItemName;
	}

	public void setCombineItemName(String combineItemName) {
		this.combineItemName = combineItemName;
	}

	public Long getCombineItemPrice() {
		return combineItemPrice;
	}

	public void setCombineItemPrice(Long combineItemPrice) {
		this.combineItemPrice = combineItemPrice;
	}

	public Long getCombineItemSkuId() {
		return combineItemSkuId;
	}

	public void setCombineItemSkuId(Long combineItemSkuId) {
		this.combineItemSkuId = combineItemSkuId;
	}

	public String getShareUserMobile() {
		return shareUserMobile;
	}

	public void setShareUserMobile(String shareUserMobile) {
		this.shareUserMobile = shareUserMobile;
	}

	public Long getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Long shareUserId) {
		this.shareUserId = shareUserId;
	}

	public Integer getHigoMark() {
		return higoMark;
	}

	public void setHigoMark(Integer higoMark) {
		this.higoMark = higoMark;
	}

	public Integer getCanRefundMark() {
		return canRefundMark;
	}

	public void setCanRefundMark(Integer canRefundMark) {
		this.canRefundMark = canRefundMark;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public ItemSkuDTO getSku() {
		return sku;
	}

	public void setSku(ItemSkuDTO sku) {
		this.sku = sku;
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

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public List<SkuPropertyDTO> getSkuPropertyList() {
		return skuPropertyList;
	}

	public void setSkuPropertyList(List<SkuPropertyDTO> skuPropertyList) {
		this.skuPropertyList = skuPropertyList;
	}

	public String getUnitPriceStr() {
		return unitPriceStr;
	}

	public void setUnitPriceStr(String unitPriceStr) {
		this.unitPriceStr = unitPriceStr;
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

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Long getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Integer getRefundReasonId() {
		return refundReasonId;
	}

	public void setRefundReasonId(Integer refundReasonId) {
		this.refundReasonId = refundReasonId;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
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

	public Integer getDeliveryMark() {
		return deliveryMark;
	}

	public void setDeliveryMark(Integer deliveryMark) {
		this.deliveryMark = deliveryMark;
	}

	public Long getDeliveryInfoId() {
		return deliveryInfoId;
	}

	public void setDeliveryInfoId(Long deliveryInfoId) {
		this.deliveryInfoId = deliveryInfoId;
	}

	public Long getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(Long pointAmount) {
		this.pointAmount = pointAmount;
	}

	public List<ItemServiceDTO> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ItemServiceDTO> serviceList) {
		this.serviceList = serviceList;
	}

	public List<OrderItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<OrderItemDTO> itemList) {
		this.itemList = itemList;
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

	public List<OrderServiceDTO> getOrderServiceList() {
		return orderServiceList;
	}

	public void setOrderServiceList(List<OrderServiceDTO> orderServiceList) {
		this.orderServiceList = orderServiceList;
	}

	public Long getOriginalOrderItemId() {
		return originalOrderItemId;
	}

	public void setOriginalOrderItemId(Long originalOrderItemId) {
		this.originalOrderItemId = originalOrderItemId;
	}

	public HigoExtraInfoDTO getHigoExtraInfoDTO() {
		return higoExtraInfoDTO;
	}

	public void setHigoExtraInfoDTO(HigoExtraInfoDTO higoExtraInfoDTO) {
		this.higoExtraInfoDTO = higoExtraInfoDTO;
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

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public Long getVirtualWealthAmount() {
		return virtualWealthAmount;
	}

	public void setVirtualWealthAmount(Long virtualWealthAmount) {
		this.virtualWealthAmount = virtualWealthAmount;
	}

    public String getItemSn() {
        return itemSn;
    }

    public void setItemSn(String itemSn) {
        this.itemSn = itemSn;
    }
}
