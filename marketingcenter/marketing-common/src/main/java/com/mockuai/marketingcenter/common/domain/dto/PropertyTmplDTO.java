package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

public class PropertyTmplDTO implements Serializable {

    private Long id;
    private Integer ownerType;
    private Long ownerId;
    private Integer requiredMark;
    private String name;
    private String pkey;
    private Integer valueType;
    private Integer creatorType;
    private Long creatorId;
    private String bizCode;
    private Integer deleteMark;

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
}