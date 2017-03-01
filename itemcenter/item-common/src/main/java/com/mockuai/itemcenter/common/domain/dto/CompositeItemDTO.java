package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;

public class CompositeItemDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1981971233706332664L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSubSkuId() {
		return subSkuId;
	}

	public void setSubSkuId(Long subSkuId) {
		this.subSkuId = subSkuId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	
	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	private Long id;
	
	private Long itemId;
	
	private Long skuId;//
	
	private Long subSkuId;//该组合商品关联的具体的规格商品id

    private Long subItemId;//组合商品关联商品的规格

    private String subItemName ;

    private String iconUrl;//商品图片

    private Long promotionPrice ;//商品价格

	private Integer num;//数量
	
	private Long supplierId;//供应商id
	
	private String bizCode;//

    private String subSkuCode;
    private String subSkuDesc;

    public String getSubSkuCode() {
        return subSkuCode;
    }

    public void setSubSkuCode(String subSkuCode) {
        this.subSkuCode = subSkuCode;
    }

    public String getSubSkuDesc() {
        return subSkuDesc;
    }

    public void setSubSkuDesc(String subSkuDesc) {
        this.subSkuDesc = subSkuDesc;
    }


    public Long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Long subItemId) {
        this.subItemId = subItemId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Long getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getSubItemName() {
        return subItemName;
    }

    public void setSubItemName(String subItemName) {
        this.subItemName = subItemName;
    }
}
