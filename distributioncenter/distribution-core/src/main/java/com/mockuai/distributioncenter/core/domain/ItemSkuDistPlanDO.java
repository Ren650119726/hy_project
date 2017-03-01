package com.mockuai.distributioncenter.core.domain;

import java.util.Date;

/**
 * Created by duke on 16/5/11.
 * 新添加字段distGainsRatio lizg  2016/09/02
 */
public class ItemSkuDistPlanDO {
    /**
     * 主键
     * */
    private Long id;

    /**
     * 商品ID
     * */
    private Long itemId;

    /**
     * 商品SKU ID
     * */
    private Long itemSkuId;

    /**
     * 销售分拥比率
     * */
    private Double saleDistRatio;

    private Double distGainsRatio;   //分佣收益比率

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemSkuId() {
        return itemSkuId;
    }

    public void setItemSkuId(Long itemSkuId) {
        this.itemSkuId = itemSkuId;
    }

    public Double getSaleDistRatio() {
        return saleDistRatio;
    }

    public void setSaleDistRatio(Double saleDistRatio) {
        this.saleDistRatio = saleDistRatio;
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

    public Double getDistGainsRatio() {
        return distGainsRatio;
    }

    public void setDistGainsRatio(Double distGainsRatio) {
        this.distGainsRatio = distGainsRatio;
    }
}
