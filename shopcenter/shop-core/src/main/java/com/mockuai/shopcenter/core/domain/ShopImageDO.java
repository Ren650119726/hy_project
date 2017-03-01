package com.mockuai.shopcenter.core.domain;

import java.util.Date;

/**
 * Created by luliang on 15/7/26.
 */
public class ShopImageDO {
//    CREATE TABLE `shop_image` (
//            `id` bigint(20) NOT NULL AUTO_INCREMENT,
//    `biz_code` varchar(32) DEFAULT NULL,
//    `seller_id` bigint(20) NOT NULL COMMENT '卖家ID',
//            `shop_id` bigint(20) NOT NULL COMMENT '商品ID',
//            `image_type` tinyint(4) DEFAULT '0' COMMENT '图片类型：1代表店铺icon，2代表店铺banner',
//            `image_name` varchar(128) DEFAULT NULL COMMENT '图片名称',
//            `image_url` varchar(256) NOT NULL COMMENT '图片地址',
//            `delete_mark` tinyint(4) NOT NULL COMMENT '删除标志',
//            `gmt_created` datetime NOT NULL COMMENT '创建时间',
//            `gmt_modified` datetime NOT NULL COMMENT '更新时间',
//    PRIMARY KEY (`id`)
//    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='店铺图片表';

    private Long id;
    private String bizCode;
    private Long sellerId;
    private Long shopId;
    private Integer imageType;
    private String imageName;
    private String imageUrl;
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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getImageType() {
        return imageType;
    }

    public void setImageType(Integer imageType) {
        this.imageType = imageType;
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
}
