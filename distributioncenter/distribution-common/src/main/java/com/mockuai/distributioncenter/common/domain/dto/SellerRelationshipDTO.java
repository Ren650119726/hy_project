package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by duke on 15/10/28.
 */
public class SellerRelationshipDTO implements Serializable {
    /**
     * id
     * */
    private Long id;

    /**
     * 父级用户ID
     * */
    private Long parentId;

    /**
     * 用户ID
     * */
    private Long userId;

    /**
     * 分销商启用状态
     * */
    private Integer status;

    /**
     * 关系类型
     * */
    private Integer type;

    private Integer deleteMark;

    private Date gmtCreated;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
