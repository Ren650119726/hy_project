package com.mockuai.shopcenter.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luliang on 15/7/26.
 */
public class ShopDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String bizCode;
    private Long sellerId;
    private String sellerName;
    private String shopName;
    private Integer shopStatus;
    private String shopStatusName;
    private String shopDesc;
    private Long shopIconId;
    private String shopIconUrl;
    private Long shopBannerImageId;
    private String shopBannerImageUrl;
    private String shopAddress;
    private String customerServicePhone;
    private String contactName;
    private String createTime;
    private Boolean isCollected;

    private Integer collectionNum;
    private Long deposit;

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    private List<Long> itemIdList;
    private List<Long> couponIdList;

    public List<Long> getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(List<Long> itemIdList) {
        this.itemIdList = itemIdList;
    }

    public List<Long> getCouponIdList() {
        return couponIdList;
    }

    public void setCouponIdList(List<Long> couponIdList) {
        this.couponIdList = couponIdList;
    }

    private List<ShopItemDTO> shopItemDTOList;
    private List<ShopCouponDTO> shopCouponDTOList;
    private List<BannerDTO> bannerDTOList;

    public List<ShopItemDTO> getShopItemDTOList() {
        return shopItemDTOList;
    }

    public void setShopItemDTOList(List<ShopItemDTO> shopItemDTOList) {
        this.shopItemDTOList = shopItemDTOList;
    }

    public List<ShopCouponDTO> getShopCouponDTOList() {
        return shopCouponDTOList;
    }

    public void setShopCouponDTOList(List<ShopCouponDTO> shopCouponDTOList) {
        this.shopCouponDTOList = shopCouponDTOList;
    }

    public List<BannerDTO> getBannerDTOList() {
        return bannerDTOList;
    }

    public void setBannerDTOList(List<BannerDTO> bannerDTOList) {
        this.bannerDTOList = bannerDTOList;
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

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }

    public String getShopStatusName() {
        return shopStatusName;
    }

    public void setShopStatusName(String shopStatusName) {
        this.shopStatusName = shopStatusName;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public Long getShopIconId() {
        return shopIconId;
    }

    public void setShopIconId(Long shopIconId) {
        this.shopIconId = shopIconId;
    }

    public String getShopIconUrl() {
        return shopIconUrl;
    }

    public void setShopIconUrl(String shopIconUrl) {
        this.shopIconUrl = shopIconUrl;
    }

    public Long getShopBannerImageId() {
        return shopBannerImageId;
    }

    public void setShopBannerImageId(Long shopBannerImageId) {
        this.shopBannerImageId = shopBannerImageId;
    }

    public String getShopBannerImageUrl() {
        return shopBannerImageUrl;
    }

    public void setShopBannerImageUrl(String shopBannerImageUrl) {
        this.shopBannerImageUrl = shopBannerImageUrl;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getCustomerServicePhone() {
        return customerServicePhone;
    }

    public void setCustomerServicePhone(String customerServicePhone) {
        this.customerServicePhone = customerServicePhone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Boolean isCollected) {
        this.isCollected = isCollected;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
