package com.mockuai.marketingcenter.core.domain;

import java.util.Date;

public class PropertyDO {

    private Long id;
    private Integer ownerType;
    private Long ownerId;
    private String name;
    private String pkey;
    private String value;
    private Integer valueType;
    private String desc;
    private Integer creatorType;
    private Long creatorId;
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

    public String getValue() {

        return this.value;
    }

    public void setValue(String value) {

        this.value = value;
    }

    public Integer getValueType() {

        return this.valueType;
    }

    public void setValueType(Integer valueType) {

        this.valueType = valueType;
    }

    public String getDesc() {

        return this.desc;
    }

    public void setDesc(String desc) {

        this.desc = desc;
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
}