package com.mockuai.marketingcenter.common.domain.qto;

import java.io.Serializable;
import java.util.List;

public class PropertyQTO implements Serializable {
    private Long id;
    private Integer ownerType;
    private Long ownerId;
    private String pkey;
    private Integer creatorType;
    private Long creatorId;
    private String bizCode;
    private List<Long> ownerIdList;

    public PropertyQTO() {
    }

    public PropertyQTO(Integer ownerType, List<Long> ownerIdList, String bizCode) {
        this.ownerType = ownerType;
        this.ownerIdList = ownerIdList;
        this.bizCode = bizCode;
    }

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

    public String getPkey() {
        return this.pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
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

    public List<Long> getOwnerIdList() {
        return ownerIdList;
    }

    public void setOwnerIdList(List<Long> ownerIdList) {
        this.ownerIdList = ownerIdList;
    }
}