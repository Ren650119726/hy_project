package com.mockuai.marketingcenter.common.domain.qto;

public class PropertyTmplQTO {
    private Long id;
    private Integer ownerType;
    private Long ownerId;
    private Long creatorId;
    private String pkey;
    private String bzCode;

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

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getPkey() {
        return this.pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getBzCode() {
        return bzCode;
    }

    public void setBzCode(String bzCode) {
        this.bzCode = bzCode;
    }
}