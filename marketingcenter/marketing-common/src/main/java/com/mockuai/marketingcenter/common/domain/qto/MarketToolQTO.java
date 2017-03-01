package com.mockuai.marketingcenter.common.domain.qto;

import java.io.Serializable;

public class MarketToolQTO extends PageQTO implements Serializable {
    private static final long serialVersionUID = -4641359279184166531L;
    private Long id;
    private String bizCode;
    /**
     * 工具类型
     */
    private Integer type;
    private Integer providerType;
    private Long providerId;
    /**
     * 父工具id
     */
    private Long parentId;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProviderType() {
        return this.providerType;
    }

    public void setProviderType(Integer providerType) {
        this.providerType = providerType;
    }

    public Long getProviderId() {
        return this.providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}