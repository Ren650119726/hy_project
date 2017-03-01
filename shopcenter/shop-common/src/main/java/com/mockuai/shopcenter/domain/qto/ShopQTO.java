package com.mockuai.shopcenter.domain.qto;

import com.mockuai.shopcenter.page.PageInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by luliang on 15/7/26.
 */
public class ShopQTO extends PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private List<Long> idList;
    private String bizCode;
    private Long sellerId;
    private Long[] sellerIds;
    private String sellerName;
    private String shopName;
    private Integer shopStatus;
    private String shopDesc;
    private Long shopIconId;
    private String shopIconUrl;
    private Long shopBannerImageId;
    private String shopBannerImageUrl;
    private String shopAddress;
    private String customerServicePhone;
    private Integer deleteMark;

    private Long days;
    private Date startTime;
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
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

    public Long[] getSellerIds(){return  sellerIds;}

    public void setSellerIds(Long[] sellerIds){this.sellerIds=sellerIds;}

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

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
