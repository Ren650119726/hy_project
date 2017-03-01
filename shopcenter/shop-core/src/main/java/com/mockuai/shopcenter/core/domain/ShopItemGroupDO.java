package com.mockuai.shopcenter.core.domain;

import java.util.Date;

/**
 * Created by luliang on 15/7/26.
 */
public class ShopItemGroupDO {

//    CREATE TABLE `shop_item_group` (
//            `id` bigint(20) NOT NULL AUTO_INCREMENT,
//    `biz_code` varchar(32) DEFAULT NULL,
//    `seller_id` bigint(20) NOT NULL COMMENT '卖家ID',
//            `shop_id` bigint(20) NOT NULL COMMENT '商品ID',
//            `group_name` varchar(32) NOT NULL COMMENT '店铺分组名称',
//            `delete_mark` tinyint(4) NOT NULL COMMENT '删除标志',
//            `gmt_created` datetime NOT NULL COMMENT '创建时间',
//            `gmt_modified` datetime NOT NULL COMMENT '更新时间',
//    PRIMARY KEY (`id`)
//    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='店铺商品分组表’;

    private Long id;
    private String bizCode;
    private Long sellerId;
    private Long shopId;
    private String groupName;
    private Integer deleteMark;
    private Long deleteVersion;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Long getDeleteVersion() {
        return deleteVersion;
    }

    public void setDeleteVersion(Long deleteVersion) {
        this.deleteVersion = deleteVersion;
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
