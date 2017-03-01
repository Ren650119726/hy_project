package com.mockuai.dts.common.domain;

import com.mockuai.itemcenter.common.page.PageInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品导出QTO
 * Created by luliang on 15/7/22.
 */
public class ItemExportQTO extends PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;
    private Long sellerId;
    private Integer itemStatus;
    private Long brandId;
    private String barCode;
    private Long categoryId;
    private Date startTime;
    private Date endTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Integer itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
