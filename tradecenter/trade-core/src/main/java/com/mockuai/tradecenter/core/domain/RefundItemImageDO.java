package com.mockuai.tradecenter.core.domain;

import java.util.Date;

public class RefundItemImageDO {
	private Long id;

	/**
	 * 退款订单id
	 */
	private Long orderId;

	/**
	 * 退款订单商品id
	 */
	private Long orderItemId;

	private Long refundItemLogId;

	private Long itemId;// 商品ID

	private Long sellerId;// 供应商ID

	private String imageName;// 图片名称

	private String imageUrl;// 图片地址

	private String bizCode;

	private Long itemSkuId;

	private Integer deleteMark;

	private Date gmtCreated;

	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
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

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getRefundItemLogId() {
		return refundItemLogId;
	}

	public void setRefundItemLogId(Long refundItemLogId) {
		this.refundItemLogId = refundItemLogId;
	}

	@Override
	public String toString() {
		return "RefundItemImageDO [id=" + id + ", itemId=" + itemId + ", sellerId=" + sellerId + "]";
	}

}
