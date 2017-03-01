package com.mockuai.tradecenter.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车商品传输类
 */
public class CartItemDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7058183008779773165L;
	private Long id;
	private String bizCode;
	/**
	 * 用户ID
	 */
	private Long userId;

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
	 * 商品原价
	 */
	private Long marketPrice;
	/**
	 * 商品现价
	 */
	private Long promotionPrice;
	/**
	 * 商品无线价格
	 */
	private Long wirelessPrice;
	/**
	 * 商品的发货方式
	 */
	private Integer deliveryType;
	/**
	 * 下单数量
	 */
	private Integer number;
	
	/**
	 * 商品链接
	 */
	private String itemUrl;

	private Integer itemType;
	
	private List<CartItemDTO> subCartList;
	
	private Long originalId;
	
	private List<ItemServiceDTO> serviceList;
	
	private List<CartItemServiceDTO> cartItemServiceList;

    private List<BizMarkDTO> bizMarkList;

	/**
	 * 跨境标志，1代表跨境，0代表非跨境
	 */
	private Integer higoMark;

	/**
	 * 跨境扩展信息，包含税费、税率等信息
	 */
	private HigoExtraInfoDTO higoExtraInfoDTO;
	
	private Long supplierId;

	/**
	 * 分销商id，代表该商品从哪个分销商那里购买的
	 */
	private Long distributorId;

	/**
	 * 分销商店铺名称
	 */
	private String distributorName;

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
	 * 商品库存
	 */
	private Integer stockNum;

    private Long brandId;
    private Long categoryId ;
    private Long shareUserId;

	
	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
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

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Long getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(Long promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Long getWirelessPrice() {
		return wirelessPrice;
	}

	public void setWirelessPrice(Long wirelessPrice) {
		this.wirelessPrice = wirelessPrice;
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

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public List<CartItemDTO> getSubCartList() {
		return subCartList;
	}

	public void setSubCartList(List<CartItemDTO> subCartList) {
		this.subCartList = subCartList;
	}

	public Long getOriginalId() {
		return originalId;
	}

	public void setOriginalId(Long originalId) {
		this.originalId = originalId;
	}

	public List<ItemServiceDTO> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ItemServiceDTO> serviceList) {
		this.serviceList = serviceList;
	}

	public List<CartItemServiceDTO> getCartItemServiceList() {
		return cartItemServiceList;
	}

	public void setCartItemServiceList(List<CartItemServiceDTO> cartItemServiceList) {
		this.cartItemServiceList = cartItemServiceList;
	}

	public Integer getHigoMark() {
		return higoMark;
	}

	public void setHigoMark(Integer higoMark) {
		this.higoMark = higoMark;
	}

	public HigoExtraInfoDTO getHigoExtraInfoDTO() {
		return higoExtraInfoDTO;
	}

	public void setHigoExtraInfoDTO(HigoExtraInfoDTO higoExtraInfoDTO) {
		this.higoExtraInfoDTO = higoExtraInfoDTO;
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

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorShopName) {
		this.distributorName = distributorShopName;
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

    public List<BizMarkDTO> getBizMarkList() {
        return bizMarkList;
    }

    public void setBizMarkList(List<BizMarkDTO> bizMarkList) {
        this.bizMarkList = bizMarkList;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Long shareUserId) {
        this.shareUserId = shareUserId;
    }
}
