package com.mockuai.tradecenter.mop.api.domain;

/**
 * Created by zengzhangqiang on 5/29/15.
 */
public class MopOrderDiscountInfoDTO {

    /**
     * 商品sku信息
     */
    private Long itemSkuId;
    private Integer discountType;
    private String discountCode;
    private String discountDesc;
    private Long discountAmount;

	public Long getItemSkuId() {
		return itemSkuId;
	}

	public void setItemSkuId(Long itemSkuId) {
		this.itemSkuId = itemSkuId;
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
}
