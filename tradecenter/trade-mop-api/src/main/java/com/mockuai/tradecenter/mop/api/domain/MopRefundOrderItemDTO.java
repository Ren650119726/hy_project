package com.mockuai.tradecenter.mop.api.domain;

import java.util.List;

public class MopRefundOrderItemDTO {

	private String skuUid;
	private Integer refundReasonId;
	private Long refundAmount;
	private String refundDesc;
	private List<MopRefundItemImageDTO> refundImageList;
	private String orderItemUid;

	public String getSkuUid() {
		return skuUid;
	}

	public void setSkuUid(String skuUid) {
		this.skuUid = skuUid;
	}

	public Integer getRefundReasonId() {
		return refundReasonId;
	}

	public void setRefundReasonId(Integer refundReasonId) {
		this.refundReasonId = refundReasonId;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

	public List<MopRefundItemImageDTO> getRefundImageList() {
		return refundImageList;
	}

	public void setRefundImageList(List<MopRefundItemImageDTO> refundImageList) {
		this.refundImageList = refundImageList;
	}

	public String getOrderItemUid() {
		return orderItemUid;
	}

	public void setOrderItemUid(String orderItemUid) {
		this.orderItemUid = orderItemUid;
	}
	
	

}
