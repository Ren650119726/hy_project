package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by duke on 16/5/11.
 */
public class ItemDistPlanDTO implements Serializable {
    /**
     * 主键
     * */
    private Long id;

    /**
     * 商品ID
     * */
    private Long itemId;

    /**
     * 等级
     * */
    private Integer level;

    /**
     * 店主分拥比率
     * */
    private Double realDistRatio;

    /**
     * 店主嗨币分拥比率
     * */
    private Double virtualDistRatio;

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Double getRealDistRatio() {
        return realDistRatio;
    }

    public void setRealDistRatio(Double realDistRatio) {
        this.realDistRatio = realDistRatio;
    }

    public Double getVirtualDistRatio() {
        return virtualDistRatio;
    }

    public void setVirtualDistRatio(Double virtualDistRatio) {
        this.virtualDistRatio = virtualDistRatio;
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
