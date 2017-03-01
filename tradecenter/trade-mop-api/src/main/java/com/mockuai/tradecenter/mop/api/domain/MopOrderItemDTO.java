package com.mockuai.tradecenter.mop.api.domain;

import java.util.List;

public class MopOrderItemDTO {
	private String orderItemUid;
	private Long itemSkuId;
	private String skuUid;
	private String skuSnapshot;
	private String itemUid;
	private String itemName;
	private String iconUrl;
	private Integer deliveryType;
	private Long price;
	private Integer number;
	private Long distributorId;
	//分享人id
	private Long shareUserId;
	/**
	 * 订单商品卖家信息
	 */
	private MopSellerDTO seller;

	private String itemUrl;

	private Integer itemType;

	private Long refundAmount;// 退款金额

	private Long paymentAmount;// 实付金额

	private Integer refundStatus;

	private Integer refundReasonId;
	
	private Long discountAmount;
	
	private Long point;
	
	private Integer deliveryMark;
	
	private Long pointAmount;//积分金额
	
	private List<MopItemServiceDTO> serviceList;

	//关联营销活动信息
	private MopActivityInfoDTO activityInfo;
	
	private String skuBarcode;
	
	private Integer canRefundMark;
	
	private Long supplierId;
	
	private Integer higoMark;

	public Long getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Long shareUserId) {
		this.shareUserId = shareUserId;
	}

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}

	public Integer getHigoMark() {
		return higoMark;
	}

	public void setHigoMark(Integer higoMark) {
		this.higoMark = higoMark;
	}

	public String getOrderItemUid() {
		return this.orderItemUid;
	}

	public void setOrderItemUid(String orderItemUid) {
		this.orderItemUid = orderItemUid;
	}

	public String getSkuUid() {
		return this.skuUid;
	}

	public void setSkuUid(String skuUid) {
		this.skuUid = skuUid;
	}

	public String getSkuSnapshot() {
		return this.skuSnapshot;
	}

	public void setSkuSnapshot(String skuSnapshot) {
		this.skuSnapshot = skuSnapshot;
	}

	public String getItemUid() {
		return this.itemUid;
	}

	public void setItemUid(String itemUid) {
		this.itemUid = itemUid;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getIconUrl() {
		return this.iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public MopSellerDTO getSeller() {
		return seller;
	}

	public void setSeller(MopSellerDTO seller) {
		this.seller = seller;
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

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
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

	public Long getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(Long pointAmount) {
		this.pointAmount = pointAmount;
	}

	public List<MopItemServiceDTO> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<MopItemServiceDTO> serviceList) {
		this.serviceList = serviceList;
	}

	public MopActivityInfoDTO getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(MopActivityInfoDTO activityInfo) {
		this.activityInfo = activityInfo;
	}

	public String getSkuBarcode() {
		return skuBarcode;
	}

	public void setSkuBarcode(String skuBarcode) {
		this.skuBarcode = skuBarcode;
	}

	public Integer getCanRefundMark() {
		return canRefundMark;
	}

	public void setCanRefundMark(Integer canRefundMark) {
		this.canRefundMark = canRefundMark;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}
}
