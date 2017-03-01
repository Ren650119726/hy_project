package com.mockuai.marketingcenter.core.domain;

import java.util.Date;

/**
 * 属性模版，目前逻辑是为工具类指定可以拥有的属性，并指定必须要拥有的属性
 * 目前的 ownerType 只有 1.工具参数
 * 于此刚好相对的就是 property 只会用于 活动参数
 */
public class PropertyTmplDO {

    private Long id;
    /**
     * 属性属主类型：1.工具参数 2.活动参数
     */
    private Integer ownerType;
    /**
     * 属性属主id
     */
    private Long ownerId;
    private Integer requiredMark;
    private String name;
    private String pkey;
    private Integer valueType;
    private String description;
    private Integer creatorType;

    // 目前暂不支持隔离
    private Long creatorId;
    // 目前暂不支持隔离
    private String bizCode;

    private Integer deleteMark;
    private Date gmtCreated;
    private Date gmtModified;


    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Integer getOwnerType() {

        return this.ownerType;
    }

    public void setOwnerType(Integer ownerType) {

        this.ownerType = ownerType;
    }

    public Long getOwnerId() {

        return this.ownerId;
    }

    public void setOwnerId(Long ownerId) {

        this.ownerId = ownerId;
    }

    public Integer getRequiredMark() {

        return this.requiredMark;
    }

    public void setRequiredMark(Integer requiredMark) {

        this.requiredMark = requiredMark;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getPkey() {

        return this.pkey;
    }

    public void setPkey(String pkey) {

        this.pkey = pkey;
    }

    public Integer getValueType() {

        return this.valueType;
    }

    public void setValueType(Integer valueType) {

        this.valueType = valueType;
    }

    public Integer getCreatorType() {

        return this.creatorType;
    }

    public void setCreatorType(Integer creatorType) {

        this.creatorType = creatorType;
    }

    public Long getCreatorId() {

        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {

        this.creatorId = creatorId;
    }

    public String getBizCode() {

        return this.bizCode;
    }

    public void setBizCode(String bizCode) {

        this.bizCode = bizCode;
    }

    public Integer getDeleteMark() {

        return this.deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {

        this.deleteMark = deleteMark;
    }

    public Date getGmtCreated() {

        return this.gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {

        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {

        return this.gmtModified;
    }

    public void setGmtModified(Date gmtModified) {

        this.gmtModified = gmtModified;
    }

    public String getDescription() {

        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
    }
}