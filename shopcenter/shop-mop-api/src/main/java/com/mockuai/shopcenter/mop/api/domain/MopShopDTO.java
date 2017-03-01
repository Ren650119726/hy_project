package com.mockuai.shopcenter.mop.api.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luliang on 15/8/6.
 */
public class MopShopDTO implements Serializable {

    private long shopId;
    private long sellerId;
    private String shopUid;
    private String sellerName;
    private String shopName;
    private String shopDesc;
    private String iconUrl;
    private String bgImageUrl;
    private String shopAddress;
    private String customerServicePhone;
    private String createTime;

    public List<MopShopCouponDTO> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<MopShopCouponDTO> couponList) {
        this.couponList = couponList;
    }

    public List<MopShopItemDTO> getItemList() {
        return itemList;
    }

    public void setItemList(List<MopShopItemDTO> itemList) {
        this.itemList = itemList;
    }

    public List<MopBannerDTO> getShopBannerList() {
        return shopBannerList;
    }

    public void setShopBannerList(List<MopBannerDTO> shopBannerList) {
        this.shopBannerList = shopBannerList;
    }

    private List<MopShopCouponDTO> couponList;

    private List<MopShopItemDTO> itemList;

    private List<MopBannerDTO> shopBannerList;

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getBgImageUrl() {
        return bgImageUrl;
    }

    public void setBgImageUrl(String bgImageUrl) {
        this.bgImageUrl = bgImageUrl;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }

    // 是否已收藏
    private int isCollected;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getShopUid() {
        return shopUid;
    }

    public void setShopUid(String shopUid) {
        this.shopUid = shopUid;
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

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
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

}
