package com.mockuai.shopcenter.core.domain;

import java.util.Date;

/**
 * Created by luliang on 15/7/26.
 */
public class ShopDO {

//    CREATE TABLE `shop` (
//            `id` bigint(20) NOT NULL AUTO_INCREMENT,
//    `biz_code` varchar(32) DEFAULT NULL,
//    `seller_id` bigint(20) NOT NULL COMMENT '卖家ID',
//            `shop_name` varchar(256) NOT NULL COMMENT '商品名称',
//            `shop_desc` varchar(256) DEFAULT NULL COMMENT '店铺描述',
//            `shop_icon_id` bigint(20) DEFAULT NULL COMMENT '店铺图标ID',
//            `shop_banner_image_id` bigint(20) DEFAULT NULL COMMENT '店铺banner图片ID',
//            `shop_address` varchar(32) NOT NULL COMMENT '店铺地址',
//            `customer_service_phone` varchar(32) NOT NULL COMMENT '客服联系方式',
//            `delete_mark` tinyint(4) NOT NULL COMMENT '删除标志',
//            `gmt_created` datetime NOT NULL COMMENT '创建时间',
//            `gmt_modified` datetime NOT NULL COMMENT '更新时间',
//    PRIMARY KEY (`id`)
//    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='店铺表’;

    private Long id;
    private String bizCode;
    private Long sellerId;
    private Long deposit;
    private String sellerName;
    private String shopName;

    public Long getDeposit() {
        return deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    private Integer shopStatus;
    private String shopDesc;
    private Long shopIconId;
    private Long shopBannerImageId;
    private String shopAddress;
    private String contactName;

    private String customerServicePhone;
    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;

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

    public Long getShopBannerImageId() {
        return shopBannerImageId;
    }

    public void setShopBannerImageId(Long shopBannerImageId) {
        this.shopBannerImageId = shopBannerImageId;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
