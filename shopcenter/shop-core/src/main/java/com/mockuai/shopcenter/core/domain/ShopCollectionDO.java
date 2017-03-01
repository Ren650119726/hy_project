package com.mockuai.shopcenter.core.domain;

import java.util.Date;

/**
 * Created by luliang on 15/8/7.
 */
public class ShopCollectionDO {

//    CREATE TABLE `shop_collection` (
//            `id` bigint(20) NOT NULL AUTO_INCREMENT,
//    `biz_code` varchar(32) DEFAULT NULL,
//    `seller_id` bigint(20) NOT NULL COMMENT '卖家ID',
//            `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
//            `user_id` bigint(20) DEFAULT NULL COMMENT ‘收藏用户ID',
//            `delete_mark` tinyint(4) NOT NULL COMMENT '删除标志',
//            `gmt_created` datetime NOT NULL COMMENT '创建时间',
//            `gmt_modified` datetime NOT NULL COMMENT '更新时间',
//    PRIMARY KEY (`id`)
//    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='店铺收藏表';

    private Long id; // 主键

    private Long userId; //用户ID

    private Long sellerId;//商家ID

    private Long shopId;//店铺ID

    private String bizCode;

    private Integer deleteMark; //是否删除

    private Date gmtCreated;//添加时间

    private Date gmtModified;//修改时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
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
