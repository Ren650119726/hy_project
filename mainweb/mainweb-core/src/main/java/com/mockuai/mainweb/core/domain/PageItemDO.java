package com.mockuai.mainweb.core.domain;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/19.
 * 增加 删除 查询
 */
public class PageItemDO {
    private Long id;
    private Long pageItemCategoryId;
    private Long pageId;
    private String itemName ;
    private String iconUrl;
    private Long ItemId;
    private Long sellerId;
    private Date gmtCreated;
    private Date gmtModified;
    private Integer deleteMark;
    private Integer itemOrder;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPageItemCategoryId() {
        return pageItemCategoryId;
    }

    public void setPageItemCategoryId(Long pageItemCategoryId) {
        this.pageItemCategoryId = pageItemCategoryId;
    }

    public Long getItemId() {
        return ItemId;
    }

    public void setItemId(Long itemId) {
        ItemId = itemId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
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

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }


    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }


    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    @Override
    public String toString() {
        return "PageItemDO{" +
                "id=" + id +
                ", pageItemCategoryId=" + pageItemCategoryId +
                ", ItemId=" + ItemId +
                ", sellerId=" + sellerId +

                '}';
    }
}
