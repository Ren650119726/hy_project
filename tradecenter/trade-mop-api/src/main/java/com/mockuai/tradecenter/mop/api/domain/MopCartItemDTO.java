package com.mockuai.tradecenter.mop.api.domain;

import java.util.List;

public class MopCartItemDTO {
    private String cartItemUid;
    private String skuUid;
    private Long shareUserId;
    private String skuSnapshot;
    private String itemUid;
    private String itemName;
    private String itemSkuDesc;
    private Long sellerId;
    private String iconUrl;
    private Integer deliveryType;
    private Long marketPrice;
    private Long promotionPrice;
    private Long wirelessPrice;
    private Integer number;
    private String itemUrl;
    private Integer itemType;
    private MopCartActivityInfoDTO activityInfo;
    private List<MopItemServiceDTO> serviceList;
    //跨境标志，1代表跨境，0代表非跨境
    private Integer higoMark;
    //跨境扩展信息
    private MopHigoExtraInfoDTO higoExtraInfo;
    /**
     * 最小购买限制，0代表无限制
     */
    private Integer saleMinNum;

    /**
     * 最大购买限制，0代表无限制
     */
    private Integer saleMaxNum;

    /**
     * 购物车商品状态，1代表上架状态，2代表下架状态
     */
    private Integer status;

    /**
     * 库存数量
     */
    private Integer stockNum;

    private List<MopBizMarkDTO> bizMarkList;
    

    public String getCartItemUid() {
        return this.cartItemUid;
    }

    public void setCartItemUid(String cartItemUid) {
        this.cartItemUid = cartItemUid;
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

    public String getItemSkuDesc() {
        return itemSkuDesc;
    }

    public void setItemSkuDesc(String itemSkuDesc) {
        this.itemSkuDesc = itemSkuDesc;
    }

    public Long getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
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

    public Long getMarketPrice() {
        return this.marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getPromotionPrice() {
        return this.promotionPrice;
    }

    public void setPromotionPrice(Long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Long getWirelessPrice() {
        return this.wirelessPrice;
    }

    public void setWirelessPrice(Long wirelessPrice) {
        this.wirelessPrice = wirelessPrice;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

	public MopCartActivityInfoDTO getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(MopCartActivityInfoDTO activityInfo) {
		this.activityInfo = activityInfo;
	}

	public List<MopItemServiceDTO> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<MopItemServiceDTO> serviceList) {
		this.serviceList = serviceList;
	}

    public Integer getHigoMark() {
        return higoMark;
    }

    public void setHigoMark(Integer higoMark) {
        this.higoMark = higoMark;
    }

    public MopHigoExtraInfoDTO getHigoExtraInfo() {
        return higoExtraInfo;
    }

    public void setHigoExtraInfo(MopHigoExtraInfoDTO higoExtraInfo) {
        this.higoExtraInfo = higoExtraInfo;
    }

    public Integer getSaleMinNum() {
        return saleMinNum;
    }

    public void setSaleMinNum(Integer saleMinNum) {
        this.saleMinNum = saleMinNum;
    }

    public Integer getSaleMaxNum() {
        return saleMaxNum;
    }

    public void setSaleMaxNum(Integer saleMaxNum) {
        this.saleMaxNum = saleMaxNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public List<MopBizMarkDTO> getBizMarkList() {
        return bizMarkList;
    }

    public void setBizMarkList(List<MopBizMarkDTO> bizMarkList) {
        this.bizMarkList = bizMarkList;
    }

    public Long getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Long shareUserId) {
        this.shareUserId = shareUserId;
    }
}