package com.mockuai.marketingcenter.common.domain.dto;

import java.io.Serializable;

public class MarketingFormalParamDTO
        implements Serializable {
    private static final long serialVersionUID = -4069280324477312405L;
    private Long id;
    private Long ownerId;
    private Integer ownerType;
    private String paramName;
    private String dispName;
    private Integer valueType;
    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getDispName() {
        return this.dispName;
    }

    public void setDispName(String dispName) {
        this.dispName = dispName;
    }

    public Integer getValueType() {
        return this.valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getOwnerType() {
        return this.ownerType;
    }

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }
}